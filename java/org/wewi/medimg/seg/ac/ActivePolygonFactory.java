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
 * Created on 21.11.2002 22:31:31
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.geom.Point2D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ActivePolygonFactory {

    /**
     * Constructor for ActivePolygonFactory.
     */
    private ActivePolygonFactory() {
        super();
    }


    public static ActiveContour createCircle(Point2D center, int rad, int nodes) {
        ActivePolygon polygon = new ActivePolygon();
        
        double x, y;
        double radDelta = 2*Math.PI/(double)nodes;
        for (int i = 0; i < nodes; i++) {
            x = (double)rad*Math.cos(radDelta*i) + center.getX();
            y = (double)rad*Math.sin(radDelta*i) + center.getY(); 
            
            polygon.addBasePoint(new Point2D((int)x, (int)y));      
        }
        
        return polygon;
    }
}
