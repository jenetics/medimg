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
 * Created on 21.11.2002 09:54:56
 *
 */
package org.wewi.medimg.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.wewi.medimg.math.vec.VectorField;
import org.wewi.medimg.math.vec.VectorIterator;
import org.wewi.medimg.viewer.image.ImageCanvas;
import org.wewi.medimg.viewer.image.ImagePanel;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class VectorFieldImageCanvasAdapter implements ImageCanvas {
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
