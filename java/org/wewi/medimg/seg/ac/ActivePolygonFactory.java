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
