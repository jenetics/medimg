/*
 * ResizeableImagePanel.java
 *
 * Created on 24. Januar 2002, 15:00
 */

package org.wewi.medimg.image;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class ImagePanel extends JPanel {
	
    private org.wewi.medimg.image.Image image = null;
    private org.wewi.medimg.image.ColorConversion conversion = null;
    private BufferedImage bufferedImage = null;
    private int[] rawData = null;
    
    private int sizeX;
    private int sizeY;
    private double qxy;

    public ImagePanel(org.wewi.medimg.image.Image image) {
        System.out.println(image);
        this.image = image;
        sizeX = image.getMaxX() - image.getMinX() + 1;
        sizeY = image.getMaxY() - image.getMinY() + 1;
        qxy = (double)sizeX/(double)sizeY;
        conversion = image.getColorConversion();
        rawData = new int[sizeX*sizeY*3];      
        bufferedImage = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_3BYTE_BGR); 
        
        setBackground(Color.WHITE);
        conversion = image.getColorConversion();
    }
      
    
    public void setColorConversion(ColorConversion c) {
        //conversion = c;
    }
    
    public ColorConversion getColorConversion() {
        return conversion;
    }
    
    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        double x, y, ox, oy;
        
        double qx = (double)getWidth()/(double)sizeX;
        double qy = (double)getHeight()/(double)sizeY;
        if (qx < qy) {
            x = sizeX*qx;
            y = x/qxy;
            ox = 0;
            oy = (getHeight()-y)/2;
        } else {
            y = sizeY*qy;
            x = y*qxy;  
            oy = 0;
            ox = (getWidth()-x)/2;
        }
        
        graph.drawImage(bufferedImage, (int)ox, (int)oy, (int)x, (int)y, this);
        setVisible(true);
    }    
      
    public void setSlice(int slice) { 
        if (slice < image.getMinZ() || slice > image.getMaxZ()) {
            return;
        }
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
    
}
