/**
 * VectorFieldPanel.java
 * 
 * Created on 10.03.2003, 11:18:03
 *
 */
package org.wewi.medimg.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.math.vec.VectorField;
import org.wewi.medimg.math.vec.VectorIterator;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class VectorFieldPanel extends JPanel {
    private VectorField field;
    private Dimension dim;
    
    public VectorFieldPanel(VectorField field, Dimension dim) {
        this.field = field;
        this.dim = dim;
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        
        double[] start = new double[3];
        double[] end = new double[3];
        double[] p1 = new double[2];
        double[] p2 = new double[2];
        
        Graphics2D graph = (Graphics2D)g;
        graph.setColor(Color.BLUE);
        
        double factorX = (double)getWidth()/(double)dim.getSizeX();
        double factorY = (double)getWidth()/(double)dim.getSizeY();        
        
        
        for (VectorIterator it = field.getVectorIterator(); it.hasNext();) {
            it.next(start, end);
            
            p1[0] = start[0]*factorX;
            p2[0] = end[0]*factorX;
            p1[1] = start[1]*factorY;
            p2[1] = end[1]*factorY;
            
            
            graph.drawLine((int)Math.round(p1[0]), (int)Math.round(p1[1]), 
                           (int)Math.round(p2[0]), (int)Math.round(p2[1]));
            
            if (start[0] == end[0] && start[1] == end[1]) {
                continue;    
            }
            
            
            graph.fillOval((int)Math.round(p2[0])-2, (int)Math.round(p2[1])-2, 4, 4);
        }
    }
}
