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
 * Created on 19.09.2002
 *
 */
package org.wewi.medimg.seg.ac;

import java.awt.Graphics;

import org.wewi.medimg.viewer.image.ImageCanvas;
import org.wewi.medimg.viewer.image.ImagePanel;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ActivePolygonCanvasAdapter implements ImageCanvas {
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
    public void draw(Graphics g, ImagePanel panel) {
       /*
        List contourPoints = null;
        
        Graphics2D graph = (Graphics2D)g;
        graph.setStroke(new BasicStroke(2f));
        graph.setColor(Color.BLUE);
        
        ImagePanel.PointConverter pc = panel.getPointConverter();
        List basePoints = polygon.getBasePoints();
        
        //Zeichnen der Polygonknoten
        if (basePoints.size() <= 0) {
            return;    
        }
        for (Iterator it = basePoints.iterator(); it.hasNext();) {
            Point pp = (Point)it.next();
            Point pg3 = pc.convert(new Point3D(pp.getOrdinate(0),
                                                         pp.getOrdinate(1),0));
            graph.drawOval((int)pg3.getX()-3, (int)pg3.getY()-3, 6, 6);                 
        }           

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
*/
   
    }

}
