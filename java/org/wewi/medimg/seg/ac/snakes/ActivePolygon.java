/*
 * ActivePolygon.java
 *
 * Created on 20. Februar 2002, 17:24
 */

package org.wewi.medimg.seg.ac.snakes;

import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.image.geom.Point2D;
import org.wewi.medimg.image.geom.PointIterator;

import org.wewi.medimg.seg.ac.ActiveContour;

import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ActivePolygon implements ActiveContour {
    private Point2D[] basePoints;
    
    public ActivePolygon(Point2D[] bp) {
        basePoints = bp;
    } 
    
    /**
     * Linescanning nach Bresenham
     */
    private final void segmentContourPoints(Point2D p, Point2D q, Vector scp) {  
        int x = p.getX();
        int y = p.getY();
        int D = 0;
        int HX = q.getX() - p.getX();
        int HY = q.getY() - p.getY();
        int c, M;
        int xInc = 1; 
        int yInc = 1;
        if (HX < 0) {
            xInc = -1; 
            HX = -HX;
        }
        if (HY < 0) {
            yInc = -1; 
            HY = -HY; 
        }
        if (HY <= HX) {  
            c = 2 * HX; 
            M = 2 * HY;
            while (true) {  
                //Point2D p2d = new Point2D(x,y);
                scp.add(new Point2D(x,y));
                if (x == q.getX()) {
                    break;
                }
                x += xInc;
                D += M;
                if (D > HX) {
                    y += yInc; 
                    D -= c;
                }
            }
        } else {  
            c = 2 * HY; 
            M = 2 * HX;
            while (true) {  
                //Point2D p2d = new Point2D(x,y);
                scp.add(new Point2D(x,y));
                if (y == q.getY()) {
                    break;
                }
                y += yInc;
                D += M;
                if (D > HY) {
                    x += xInc; 
                    D -= c;
                }
            }
        }  
    }
    
    
    public Iterator getContourPoints() {
        Vector segcont = new Vector();
        Vector contour = new Vector();
        
        for (int i = 0; i < basePoints.length-1; i++) {
            segcont.removeAllElements();
            segmentContourPoints((Point2D)basePoints[i], (Point2D)basePoints[i+1], segcont);
            for (Iterator it = segcont.iterator(); it.hasNext();) {
                contour.add(it.next());
            }
        }
        segcont.removeAllElements();
        segmentContourPoints((Point2D)basePoints[basePoints.length-1],(Point2D) basePoints[0], segcont);
        for (Iterator it = segcont.iterator(); it.hasNext();) {
            contour.add(it.next());
        }        
        
        PointIterator it = new PointIterator(contour.size());
        for (int i = 0; i < contour.size(); i++) {
            it.addPoint((Point)contour.get(i));
        }
        return it;
    }
    
    public Iterator getBasePoints() {
        PointIterator it = new PointIterator(basePoints.length);
        for (int i = 0; i < basePoints.length; i++) {
            it.addPoint(basePoints[i]);
        }
        return it;
    }    
    
    public void replaceBasePoint(Point oldPoint, Point newPoint) {
        for (int i = 0; i < basePoints.length; i++) {
            if (oldPoint.equals(basePoints[i])) {
                basePoints[i] = (Point2D)newPoint;
                if (!isSimple()) {
                    basePoints[i] = (Point2D)oldPoint;
                } 
                return;
            }
        }
    }
    
    /**
     * Testet, ob das Polygon überschneidungsfrei ist
     */
    private boolean isSimple() {
        return true;
    }
    
    public Object clone() {
        Point2D[] np = new Point2D[basePoints.length];
        for (int i = 0; i < np.length; i++) {
            np[i] = (Point2D)basePoints[i].clone();
        }
        return new ActivePolygon(np);
    }
    
    
    
    /*
    public static void main(String[] args) {
        int r = 100;
        int mx = 330;
        int my = 240;
        int BASE_POINTS = 10;
        
        Point2D[] points = new Point2D[BASE_POINTS];
        
        for (int i = 0; i < BASE_POINTS; i++) {
            double angle = (double)i*(2.0*Math.PI/(double)BASE_POINTS);
            System.out.print(" "+ angle);
            int x = (int)(Math.cos(angle)*r + mx);
            int y = (int)(Math.sin(angle)*r + my);
            
            Point2D p = new Point2D(x, y);
            
            points[i] = p;
            System.out.print(" ,"+ p.getAngel(mx, my) + "\n");
        } 
        
        ActivePolygon poly = new ActivePolygon(points);
        for (Iterator it = poly.getContourPoints(); it.hasNext();) {
            Point2D p2d = (Point2D)it.next();
            System.out.println(" " + p2d.getAngel(mx, my));
        }
        
        
    }
    */
}
