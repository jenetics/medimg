/**
 * Created on 21.11.2002 09:54:56
 *
 */
package org.wewi.medimg.math;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.wewi.medimg.viewer.image.ImagePanel;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class VectorFieldImageCanvasAdapter implements ImagePanel.ImageCanvas {
    private VectorField field;

	/**
	 * Constructor for VectorFieldImageCanvasAdapter.
	 */
	public VectorFieldImageCanvasAdapter(VectorField field) {
		super();
        this.field = field;
	}

	/**
	 * @see org.wewi.medimg.image.ImagePanel.ImageCanvas#draw(Graphics, ImagePanel)
	 */
	public void draw(Graphics g, ImagePanel panel) {
        double[] start = new double[3];
        double[] end = new double[3];
        double[] p1 = new double[2];
        double[] p2 = new double[2];
        
        Graphics2D graph = (Graphics2D)g;
        graph.setColor(Color.BLUE);        
        
        ImagePanel.PointConverter converter = panel.getPointConverter();
        
        for (VectorIterator it = field.getVectorIterator(); it.hasNext();) {
            it.next(start, end);
            
            //converter.imagePointToPanelPoint(start, p1);
            //converter.imagePointToPanelPoint(end, p2);
            
            
            graph.drawLine((int)Math.round(p1[0]), (int)Math.round(p1[1]), 
                           (int)Math.round(p2[0]), (int)Math.round(p2[1]));
            
            if (start[0] == end[0] && start[1] == end[1]) {
                continue;    
            }
            
            
            graph.fillOval((int)Math.round(p2[0])-2, (int)Math.round(p2[1])-2, 4, 4);
        }
	}
    

}
