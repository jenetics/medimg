/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * ImagePanel.java
 *
 * Created on 24. Januar 2002, 15:00
 */

package org.wewi.medimg.viewer.image;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Point2D;
import org.wewi.medimg.image.geom.Point3D;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class ImagePanel extends JPanel {
    
    public abstract class PointConverter {
        private PointConverter() {
        }
        
        public abstract Point2D convert(Point3D p);
        
        public abstract Point3D convert(Point2D p);    
    }
    
    private final class PointConverterImpl extends PointConverter {
        /**
         * @see org.wewi.medimg.viewer.ImagePanel.PointConverter#convert(org.wewi.medimg.image.geom.Point3D)
         */
        public Point2D convert(Point3D p) {
            int px = (int)Math.rint((double)p.getX()*(((double)getWidth()-2d*ox)/
                                                    (double)sizeX)+(double)ox);
            int py = (int)Math.rint((double)p.getY()*(((double)getHeight()-2d*oy)/
                                                    (double)sizeY)+(double)oy);        
        
            return new Point2D(px, py);
        }
        /**
         * @see org.wewi.medimg.viewer.ImagePanel.PointConverter#convert(org.wewi.medimg.image.geom.Point2D)
         */
        public Point3D convert(Point2D p) {
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
    }
    
    
    
    private Vector listener;
    
    private Image image;
    private ColorConversion colorConversion;
    
    private ImageCanvas imageCanvas;
    private BufferedImage bufferedImage;
    private int[] rawData;
    
    private int sizeX, sizeY;
    private int ox, oy, x, y;
    private int slice = 0;
    private double qxy;    

    /**
     * Creating a new ImagePanel.
     * 
     * @param image This image is displayed in the panel.
     */
    public ImagePanel(Image image) {
        this.image = image;
        colorConversion = image.getColorConversion();
        init();    
    }
    
    /**
     * Initializing the panel.
     */
    private void init() {
        sizeX = image.getDimension().getSizeX();
        sizeY = image.getDimension().getSizeY();
        qxy = (double)sizeX/(double)sizeY;

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
                            public void draw(Graphics graph, ImagePanel panel) {
                            }
                        };
                        
    }
    
    /**
     * This method is called, whenever a mouse event occurs
     * 
     * @param event
     */
    private void pointChoosed(MouseEvent event) {
        PointConverter converter = new PointConverterImpl();
        
        Point2D p2d = new Point2D(event.getPoint().x, event.getPoint().y);
        Point3D p3d = converter.convert(p2d);
        
        VoxelSelectorEvent e = new VoxelSelectorEvent(this, p2d, p3d);
        notifyListener(e);                                            
    }
    
    /**
     * Adds a new VoxelSelectorListener.
     * 
     * @param l listener to be add.
     */
    public synchronized void addVoxelSelectorListener(VoxelSelectorListener l) {
        listener.add(l);    
    }
    
    /**
     * Removes a VoxelSelectorListener.
     * 
     * @param l listener to remove.
     */
    public synchronized void removeVoxelSelectorListener(VoxelSelectorListener l) {
        listener.remove(l);    
    }
    
    /**
     * This method notifys the listener, when a voxel is selected.
     * 
     * @param event VoxelSelectorEvent.
     */
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
    
    /**
     * 
     * @see javax.swing.JComponent#paintComponent(Graphics)
     */
    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        
        //This part preserves the image height and image width ratio.
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
        
        //This lets you draw an aditional graph onto the panel.
        if (imageCanvas != null) {
            imageCanvas.draw(graph, this);
        }
        
        setVisible(true);
    }    
      
    /**
     * Sets the image z-slice that should be displayed
     * 
     * @param slice
     */
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
    
    /**
     * Returns teh current slice, respectively the z-coordinate.
     * 
     * @return int the z slice.
     */
    public int getSlice() {
        return slice;    
    }
    
    /**
     * Forces a repaint of the image.
     */
    public void repaintImage() {
        setSlice(getSlice());    
    }
    
    /**
     * Returns the displayed image.
     * 
     * @return Image
     */
    public Image getImage() {
        return image;    
    }
    
    /**
     * Returns the ColorConversion of the image panel. This must not be
     * the ColorConversion of the image.
     * 
     * @return ColorConversion of the panel.
     */
    public ColorConversion getColorConversion() {
        return colorConversion;    
    }
    
    public PointConverter getPointConverter() {
        return new PointConverterImpl();   
    }
    
    public Dimension getDisplayedImageSize() {
        return new Dimension(x - ox, y - oy);
    }
    
    public Point getDisplayedImageOrigin() {
        return new Point(ox, oy);
    }
    
    /**
     * Sets the ColorConversion for this panel. This has no effect to the
     * ColorConversion of the image. To change the ColorConversion of the
     * image, you can do this as follows: <p/>
     * 
     * <pre>
     *       getImage().setColorConversion(colorConversion);
     * </pre>
     * 
     * @param colorConversion
     */
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
