/**
 * Created on 19.09.2002
 *
 */
package org.wewi.medimg.seg.ac;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;

import org.wewi.medimg.image.ImagePanel;
import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ActivePolygonCanvasAdapter implements ImagePanel.ImageCanvas {
    private ActivePolygon polygon;

	/**
	 * Constructor for ActivePolygonCanvas.
	 */
	public ActivePolygonCanvasAdapter(ActivePolygon polygon) {
		this.polygon = polygon;
	}

	/**
	 * @see org.wewi.medimg.image.ImagePanel.ImageCanvas#draw(Graphics, ImagePanel)
	 */
	public synchronized void draw(Graphics g, ImagePanel panel) {
        List contourPoints = null;
        
        Graphics2D graph = (Graphics2D)g;
        graph.setStroke(new BasicStroke(2f));
        
        ImagePanel.PointConverter pc = panel.getPointConverter();
        List basePoints = polygon.getBasePoints();
        
        //Zeichnen der Polygonknoten
        if (basePoints.size() <= 0) {
            return;    
        }
        for (Iterator it = basePoints.iterator(); it.hasNext();) {
            Point pp = (Point)it.next();
            java.awt.Point pg3 = pc.convert(new Point3D(pp.getOrdinate(0),
                                                         pp.getOrdinate(1),0));
            graph.drawOval((int)pg3.getX()-3, (int)pg3.getY()-3, 6, 6);                 
        }           

        synchronized (basePoints) {//Zeichnen der Polygonkanten
            if (basePoints.size() <= 1) {
                return;    
            }
            Point p = null, q = null;
            Point f = null;
        
            Iterator it = basePoints.iterator();
            if (it.hasNext()) {
                q = (Point)it.next();
                f = q;    
            }    
            while(it.hasNext()) {
                p = q;
                q = (Point)it.next();
                java.awt.Point pg1 = pc.convert(new Point3D(p.getOrdinate(0),
                                                             p.getOrdinate(1),0));
                java.awt.Point pg2 = pc.convert(new Point3D(q.getOrdinate(0),
                                                             q.getOrdinate(1),0));                                                             
                graph.drawLine((int)pg1.getX(), (int)pg1.getY(), 
                               (int)pg2.getX(), (int)pg2.getY());
            }
            java.awt.Point pg1 = pc.convert(new Point3D(q.getOrdinate(0),
                                                         q.getOrdinate(1),0));
            java.awt.Point pg2 = pc.convert(new Point3D(f.getOrdinate(0),
                                                         f.getOrdinate(1),0));                                                             
            graph.drawLine((int)pg1.getX(), (int)pg1.getY(), 
                           (int)pg2.getX(), (int)pg2.getY()); 
        } 
   
    }

}
