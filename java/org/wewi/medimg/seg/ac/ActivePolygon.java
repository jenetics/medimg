/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.seg.ac;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.wewi.medimg.image.ImagePanel;
import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.image.geom.Point2D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public class ActivePolygon implements ActiveContour { 
    private Vector basePoints;
    private ImagePanel.ImageCanvas polygonCanvas;
    
    public ActivePolygon(ActivePolygon ac) {
        synchronized (ac.basePoints) {
            this.basePoints = (Vector)ac.basePoints.clone();
        }
        polygonCanvas = new ActivePolygonCanvasAdapter(this);  
    }

	/**
	 * Constructor for ActivePolygon.
	 */
	public ActivePolygon() {
        basePoints = new Vector();
        polygonCanvas = new ActivePolygonCanvasAdapter(this);
	}
    
    public ImagePanel.ImageCanvas getImageCanvas() {
        return polygonCanvas;    
    }
    
    /**
     * @see org.wewi.medimg.seg.ac.ActiveContour#addBasePoint(Point)
     */
    public synchronized void addBasePoint(Point point) {
        if (point == null) {
            return;    
        }
        synchronized (basePoints) {
            basePoints.add(point);
        }
    }

    /**
     * @see org.wewi.medimg.seg.ac.ActiveContour#removePoint(Point)
     */
    public synchronized void removePoint(Point point) {
        synchronized (basePoints) {
            basePoints.remove(point);
        }
    }    

	/**
	 * @see org.wewi.medimg.seg.ac.ActiveContour#replaceBasePoint(Point, Point)
	 */
	public synchronized void replaceBasePoint(Point oldPoint, Point newPoint) {
        if (oldPoint == null || newPoint == null) {
            return;    
        }
        synchronized (basePoints) {
            if (!basePoints.contains(oldPoint)) {
                return;    
            }       
            int index = basePoints.indexOf(oldPoint);
            basePoints.remove(index);     
            basePoints.add(index, newPoint);
        }
	}

	/**
	 * @see org.wewi.medimg.seg.ac.ActiveContour#getBasePoints()
	 */
	public List getBasePoints() {
        return basePoints;
	}
    
    public int getNBasePoints() {
        synchronized (basePoints) {
            return basePoints.size(); 
        }   
    }

	/**
	 * @see org.wewi.medimg.seg.ac.ActiveContour#getContourPoints()
	 */
	public synchronized List getContourPoints() {
        Vector result = new Vector();
        Point p = null, q = null;
        Point first = null;

        Vector v;
        synchronized (basePoints) {
            v = (Vector)basePoints.clone();
        }
        
        Iterator it = v.iterator();
        if (it.hasNext()) {
            q = (Point)it.next();
            first = q;    
        }    
        while(it.hasNext()) {
            p = q;
            q = (Point)it.next();
            List list = segmentContourPoints(p, q);
            for (Iterator it2 = list.iterator(); it2.hasNext();) {
                result.add(it2.next());    
            }            
        }
        List list = segmentContourPoints(q, first);
        for (Iterator it2 = list.iterator(); it2.hasNext();) {
            result.add(it2.next());    
        } 
               

        return result;
	}
    
    /**
     * Linescanning nach Bresenham
     */
    private final List segmentContourPoints(Point p, Point q) { 
        List result = new ArrayList();
         
        int x = p.getOrdinate(0);
        int y = p.getOrdinate(1);
        int D = 0;
        int HX = q.getOrdinate(0) - p.getOrdinate(0);
        int HY = q.getOrdinate(1) - p.getOrdinate(1);
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
                Point point = new Point2D(x,y);
                result.add(point);
                if (x == q.getOrdinate(0)) {
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
                Point point = new Point2D(x,y);
                result.add(point);
                if (y == q.getOrdinate(1)) {
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
        
        //Testen, ob sich Benachbarte Conturpunkte nur um 
        //eins, in den x und y Koordinaten unterscheiden
        /*
        int size = result.size();
        Point p1 = (Point)result.get(0);
        Point p2;
        for (int i = 1; i < size; i++) {
            p2 = p1;
            p1 = (Point)result.get(i);
            
            if (Math.abs(p1.getOrdinate(0) - p2.getOrdinate(0)) > 1) {
                System.out.println("Falsch");
                break;
            }   
            if (Math.abs(p1.getOrdinate(1) - p2.getOrdinate(1)) > 1) {
                System.out.println("Falsch");
                break;
            }            
        }
        */

        
        
        return result; 
    }    

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new ActivePolygon(this);
	}

}
