/**
 * ImagePanel.java
 *
 * Created on 24. Januar 2002, 15:00
 */

package org.wewi.medimg.viewer;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelSelectorEvent;
import org.wewi.medimg.image.VoxelSelectorListener;
import org.wewi.medimg.image.geom.Point3D;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class ImagePanel extends JPanel {
    
    public interface ImageCanvas {
        public void draw(Graphics graph, ImagePanel panel);
    }
    
    /**
     * Diese Klasse nimmt eine Konvertierung zwischen
     * den Bild-Koordinaten und den Panel-Koordinaten durch.
     */
    public final class PointConverter {
        
        private PointConverter() {
        }
        
        /**
         * Konvertierung eines Bildpunktes in einen java.awt.Point
         * in Panel-Koordinaten.
         */
        public synchronized java.awt.Point convert(Point3D p) {
            int px = (int)Math.rint((double)p.getX()*(((double)getWidth()-2d*ox)/
                                                    (double)sizeX)+(double)ox);
            int py = (int)Math.rint((double)p.getY()*(((double)getHeight()-2d*oy)/
                                                    (double)sizeY)+(double)oy);                                                    
            
            return new java.awt.Point(px, py);    
        } 
        
        /**
         * Konvertierung eines java.awt.Point Panel-Punktes
         * in einen entsprechenden Bildpunkt.
         */
        public synchronized Point3D convert(java.awt.Point p) {
            int imageX = (int)Math.rint((p.getX()-ox)*((double)sizeX/
                                         ((double)getWidth()-2*ox)));
            int imageY = (int)Math.rint((p.getY()-oy)*((double)sizeY/
                                         ((double)getHeight()-2*oy)));
            
            Point3D point = null;
            if (imageX >= image.getMinX() && imageX <= image.getMaxX() &&
                imageY >= image.getMinY() && imageY <= image.getMaxY()) {
                    point = new Point3D(imageX, imageY, slice);
            }
            
            return point;  
        }
        
        public void imagePointToPanelPoint(double[] imagePoint, double[] panelPoint) {
            panelPoint[0] = imagePoint[0]*(((double)getWidth()-2d*ox)/(double)sizeX)+(double)ox;
            panelPoint[1] = imagePoint[1]*(((double)getHeight()-2d*oy)/(double)sizeY)+(double)oy;                                                    
                            
        }
        
        public void panelPointToImagePoint(double[] panelPoint, double[] imagePoint) {
            imagePoint[0] = (panelPoint[0]-ox)*((double)sizeX/((double)getWidth()-2*ox));
            imagePoint[1] = (panelPoint[0]-oy)*((double)sizeY/((double)getHeight()-2*oy));            
        }   
    }
    /**************************************************************************/
    
    private Vector listener;
	
    private org.wewi.medimg.image.Image image = null;
    private org.wewi.medimg.image.ColorConversion colorConversion = null;
    private BufferedImage bufferedImage = null;
    private int[] rawData = null;
    
    private int sizeX, sizeY;
    private int ox, oy, x, y;
    private int slice = 0;
    private double qxy;
    
    private ImageCanvas imageCanvas;
    private PointConverter pointConverter;

    public ImagePanel(org.wewi.medimg.image.Image image) {
        this.image = image;
        sizeX = image.getMaxX() - image.getMinX() + 1;
        sizeY = image.getMaxY() - image.getMinY() + 1;
        qxy = (double)sizeX/(double)sizeY;
        colorConversion = image.getColorConversion();
        rawData = new int[sizeX*sizeY*3];      
        bufferedImage = new BufferedImage(sizeX, sizeY, 
                                          BufferedImage.TYPE_3BYTE_BGR); 
        
        setBackground(Color.WHITE);
        
        addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent event) {
                                pointChoosed(event);    
                            }
                        });
                        
        listener = new Vector();
        imageCanvas = new ImageCanvas() {
                            public void draw(Graphics graph, ImagePanel panel) {
                            }
                        };
                        
        pointConverter = new PointConverter();
    }
    
    public PointConverter getPointConverter() {
        return pointConverter;    
    }
    
    private void pointChoosed(MouseEvent event) {
        notifyListener(new VoxelSelectorEvent(this, event.getPoint(), image, 
                                              pointConverter.convert(event.getPoint())));                                            
    }
    
    public synchronized void addVoxelSelectorListener(VoxelSelectorListener l) {
        listener.add(l);    
    }
    
    public synchronized void removeVoxelSelectorListener(VoxelSelectorListener l) {
        listener.remove(l);    
    }
    
    protected void notifyListener(VoxelSelectorEvent event) {
        Vector list;
        synchronized (listener) {
            list = (Vector)listener.clone();
        }
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
        if (imageCanvas != null) {
            imageCanvas.draw(graph, this);
        }
        
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
                colorConversion.convert(image.getColor(i, j, slice), pixel);
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
    
    public int getSlice() {
        return slice;    
    }
    
    public void repaintImage() {
        setSlice(getSlice());    
    }
    
    public Image getImage() {
        return image;    
    }
    
    public ColorConversion getColorConversion() {
        return colorConversion;    
    }
    
    public void setColorConversion(ColorConversion colorConversion) {
        this.colorConversion = colorConversion;
    }
    
    public void setImageCanvas(ImageCanvas imageCanvas) {
        if (imageCanvas == null) {
            return;    
        }
        this.imageCanvas = imageCanvas;    
    }
    
    public void removeImageCanvas() {
        imageCanvas = new ImageCanvas() {
                           public void draw(Graphics g, ImagePanel panel) {
                           }
                      };    
    }
    
    public ImageCanvas getImageCanvas() {
        return imageCanvas;    
    }    
}
