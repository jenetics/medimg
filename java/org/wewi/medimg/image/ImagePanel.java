/*
 * ResizeableImagePanel.java
 *
 * Created on 24. Januar 2002, 15:00
 */

package org.wewi.medimg.image;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

import org.wewi.medimg.image.geom.Point3D;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class ImagePanel extends JPanel {
    
    public interface ImageCanvas {
        public void draw(Graphics graph);
    }
    
    
    private Vector listener;
	
    private org.wewi.medimg.image.Image image = null;
    private org.wewi.medimg.image.ColorConversion conversion = null;
    private BufferedImage bufferedImage = null;
    private int[] rawData = null;
    
    private int sizeX, sizeY;
    private int ox, oy, x, y;
    private int slice = 0;
    private double qxy;
    
    private ImageCanvas imageCanvas;

    public ImagePanel(org.wewi.medimg.image.Image image) {
        this.image = image;
        sizeX = image.getMaxX() - image.getMinX() + 1;
        sizeY = image.getMaxY() - image.getMinY() + 1;
        qxy = (double)sizeX/(double)sizeY;
        conversion = image.getColorConversion();
        rawData = new int[sizeX*sizeY*3];      
        bufferedImage = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_3BYTE_BGR); 
        
        setBackground(Color.WHITE);
        
        addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent event) {
                                pointChoosed(event);    
                            }
                        });
                        
        listener = new Vector();
        imageCanvas = new ImageCanvas() {
                            public void draw(Graphics graph) {
                            }
                        };
    }
    
    private Point3D getImagePoint(Point p) {
        int imageX = (int)Math.rint((p.getX()-ox)*((double)sizeX/((double)getWidth()-2*ox)));
        int imageY = (int)Math.rint((p.getY()-oy)*((double)sizeY/((double)getHeight()-2*oy)));
        
        Point3D point = null;
        if (imageX >= image.getMinX() && imageX <= image.getMaxX() &&
            imageY >= image.getMinY() && imageY <= image.getMaxY()) {
                point = new Point3D(imageX, imageY, slice);
        }
        
        return point;     
    }
    
    private void pointChoosed(MouseEvent event) {
        notifyListener(new VoxelSelectorEvent(this, event.getPoint(), image, 
                                              getImagePoint(event.getPoint())));                                            
    }
    
    public void addVoxelSelectorListener(VoxelSelectorListener l) {
        listener.add(l);    
    }
    
    public void removeVoxelSelectorListener(VoxelSelectorListener l) {
        listener.remove(l);    
    }
    
    protected void notifyListener(VoxelSelectorEvent event) {
        Vector list = (Vector)listener.clone();
        VoxelSelectorListener l;
        for (Iterator it = list.iterator(); it.hasNext();) {
            l = (VoxelSelectorListener)it.next();
            l.voxelSelected(event);
        }    
    }
    
    
    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        
        double qx = (double)getWidth()/(double)sizeX;
        double qy = (double)getHeight()/(double)sizeY;
        if (qx < qy) {
            x = getWidth();
            y = (int)Math.rint((double)x/qxy);
            ox = 0;
            oy = (int)Math.rint((getHeight()-y)/2);
        } else {
            y = getHeight();
            x = (int)Math.rint(((double)y*qxy));  
            oy = 0;
            ox = (int)Math.rint((getWidth()-x)/2);
        }
        
        graph.drawImage(bufferedImage, ox, oy, x, y, this);
        
        //Dies bietet die Möglichkeit eine "Skizze" über das Bild zu legen
        imageCanvas.draw(graph);
        
        setVisible(true);
    }    
      
    public void setSlice(int slice) { 
        if (slice < image.getMinZ() || slice > image.getMaxZ()) {
            return;
        }
        this.slice = slice;
        
        int[] pixel = new int[3];
        int minX = image.getMinX();
        int maxX = image.getMaxX();
        int minY = image.getMinY();
        int maxY = image.getMaxY();
        int counter = 0;
        for (int j = minY; j <= maxY; j++) {
            for (int i = minX; i <= maxX; i++) {
                conversion.convert(image.getColor(i, j, slice), pixel);
                rawData[3*counter+0] = pixel[0];
                rawData[3*counter+1] = pixel[1];
                rawData[3*counter+2] = pixel[2]; 
                ++counter;
            }
        }
        
        WritableRaster raster = bufferedImage.getRaster();
        raster.setPixels(0, 0, sizeX, sizeY, rawData);
        this.repaint();
    }
    
    public Image getImage() {
        return image;    
    }
    
    public void setImageCanvas(ImageCanvas imageCanvas) {
        this.imageCanvas = imageCanvas;    
    }
    
    public ImageCanvas getImageCanvas() {
        return imageCanvas;    
    }    
}
