/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.seg.ac;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.wewi.medimg.image.ImagePanel;
import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.image.geom.Point2D;
import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public class ActivePolygon implements ActiveContour {
    
    private final class ActivePolygonCanvas implements ImagePanel.ImageCanvas {
        
        public void draw(Graphics g, ImagePanel panel) {
            Graphics2D graph = (Graphics2D)g;
            graph.setStroke(new BasicStroke(2f));
            
            ImagePanel.PointConverter pc = panel.getPointConverter();
            
            //Zeichnen der Polygonknoten
            if (getNBasePoints() <= 0) {
                return;    
            }
            for (Iterator it = getBasePoints().iterator(); it.hasNext();) {
                Point pp = (Point)it.next();
                java.awt.Point pg3 = pc.convert(new Point3D(pp.getOrdinate(0),
                                                             pp.getOrdinate(1),0));
                graph.drawOval((int)pg3.getX()-3, (int)pg3.getY()-3, 6, 6);                 
            }            
            
            {//Zeichnen der Polygonkanten
                if (getNBasePoints() <= 1) {
                    return;    
                }
                Point p = null, q = null;
                Point f = null;
            
                Iterator it = getBasePoints().iterator();
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
            
            if (getNBasePoints() <= 3) {
                return;    
            }
            {//Zeichnen der Punkte dazwischen (Test)
                int count = -1;
                int mod;
                for (Iterator it = getContourPoints().iterator(); it.hasNext();) {
                    ++count;
                    mod = count % 20;
                    System.out.println(mod);
                    Point p = (Point)it.next();
                    if (mod != 0) {
                        return;    
                    }    
                    
                    java.awt.Point awtp = pc.convert(new Point3D(p.getOrdinate(0),
                                                                 p.getOrdinate(1),0));
                    graph.drawString(Integer.toString(count), (int)awtp.getX(), (int)awtp.getY());
                }
                
            } 
                  
        }    
    }
    /**************************************************************************/
    
    private List basePoints;
    private ImagePanel.ImageCanvas imageCanvas;
    
    public ActivePolygon(ActivePolygon ac) {
        this();
        for (Iterator it = ac.getBasePoints().iterator(); it.hasNext();) {
            addBasePoint((Point)it.next());        
        }    
    }

	/**
	 * Constructor for ActivePolygon.
	 */
	public ActivePolygon() {
        basePoints = new Vector();
        imageCanvas = new ActivePolygonCanvas();
	}
    
    public ImagePanel.ImageCanvas getImageCanvas() {
        return imageCanvas;    
    }
    
    /**
     * @see org.wewi.medimg.seg.ac.ActiveContour#addBasePoint(Point)
     */
    public void addBasePoint(Point point) {
        if (point == null) {
            return;    
        }
        basePoints.add(point);
    }

    /**
     * @see org.wewi.medimg.seg.ac.ActiveContour#removePoint(Point)
     */
    public void removePoint(Point point) {
        basePoints.remove(point);
    }    

	/**
	 * @see org.wewi.medimg.seg.ac.ActiveContour#replaceBasePoint(Point, Point)
	 */
	public void replaceBasePoint(Point oldPoint, Point newPoint) {
        if (oldPoint == null || newPoint == null) {
            return;    
        }
        if (!basePoints.contains(oldPoint)) {
            return;    
        }
        basePoints.add(basePoints.indexOf(oldPoint), newPoint);
	}

	/**
	 * @see org.wewi.medimg.seg.ac.ActiveContour#getBasePoints()
	 */
	public List getBasePoints() {
		return basePoints;
	}
    
    public int getNBasePoints() {
        return basePoints.size();    
    }

	/**
	 * @see org.wewi.medimg.seg.ac.ActiveContour#getContourPoints()
	 */
	public List getContourPoints() {
        List result = new Vector();
        Point p = null, q = null;
        Point first = null;

		List bp = getBasePoints();
        Iterator it = bp.iterator();
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
        System.out.println("NContourPoints: " + result.size());       

        return result;
	}
    
    /**
     * Linescanning nach Bresenham
     */
    private final List segmentContourPoints(Point p, Point q) { 
        List result = new Vector();
         
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
                if (y == q.getOrdinate(0)) {
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
        
        return result; 
    }    

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new ActivePolygon(this);
	}

}
