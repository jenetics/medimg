/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.seg.ac;

import java.util.Iterator;
import java.util.List;

import org.wewi.medimg.alg.AlgorithmIterationEvent;
import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.alg.ObservableAlgorithm;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.filter.ImageFilter;
import org.wewi.medimg.image.filter.SobelFilter;
import org.wewi.medimg.image.geom.Neighborhood;
import org.wewi.medimg.image.geom.Neighborhood2D4;
import org.wewi.medimg.image.geom.Point;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class SnakeGreedyMinimizer extends ObservableAlgorithm
                                   implements Minimizer {
    
    private final class GreedyMinimizerIterator implements AlgorithmIterator {
        
        /**
		 * @see org.wewi.medimg.alg.AlgorithmIterator#getInterimResult()
		 */
		public Object getInterimResult() throws UnsupportedOperationException {
			return contour;
		}

		/**
		 * @see org.wewi.medimg.alg.AlgorithmIterator#hasNextIteration()
		 */
		public boolean hasNextIteration() {
            System.out.println("" + contourEnergy + "-" + newContourEnergy);
			return Math.abs(contourEnergy - newContourEnergy) > ERROR_LIMIT;
		}

		/**
		 * @see org.wewi.medimg.alg.AlgorithmIterator#nextIteration()
		 */
		public void nextIteration() {
            iteration();
		}

    }
    /**************************************************************************/
    
    public static final double ERROR_LIMIT = 0.01;
    public static final double ALPHA = 1;
    public static final double BETA = 1;
    public static final double W_EDGE = 1;
    
    protected Image image;
    protected Image gradImage;
    protected ActiveContour contour;
    
    protected double contourEnergy = Double.MAX_VALUE;
    protected double newContourEnergy = -Double.MAX_VALUE;

	/**
	 * Constructor for SnakeGreedyMinimizer.
	 */
	public SnakeGreedyMinimizer(Image image, ActiveContour contour) {
		super();
        this.image = image;
        this.contour = contour;
        
        ImageFilter filter = new SobelFilter(image);
        filter.filter();
        gradImage = filter.getImage();
	}
    
    protected double innerEnergy(Point[] cp) {
        double d1 = 0;
        for (int i = 1, n = cp.length; i < n; i++) {
            double vix = cp[i].getOrdinate(0);
            double viy = cp[i].getOrdinate(1);
            double vimx = cp[i-1].getOrdinate(0);
            double vimy = cp[i-1].getOrdinate(1);
            
            d1 = ALPHA*((vix-vimx)*(vix-vimx) + (viy-vimy)*(viy-vimy));      
        }
        
        double d2 = 0;
        for (int i = 1, n = cp.length-1; i < n; i++) {
            double vix = cp[i].getOrdinate(0);
            double viy = cp[i].getOrdinate(1);
            double vimx = cp[i-1].getOrdinate(0);
            double vimy = cp[i-1].getOrdinate(1);
            double vipx = cp[i+1].getOrdinate(0);
            double vipy = cp[i+1].getOrdinate(1);
            
            double x = vimx-2*vix+vipx;
            double y = vimy-2*viy+vipy;
            
            d2 = BETA*(x*x + y*y);   
        }
        return d1 + d2;
    }
    
    protected double outerEnergy(Point[] cp) {
        double d1 = 0;
        
        double g = 0;
        for (int i = 0, n = cp.length; i < n; i++) {
            int vx = cp[i].getOrdinate(0);
            int vy = cp[i].getOrdinate(1);
            g += gradImage.getColor(vx, vy, 0)*gradImage.getColor(vx, vy, 0);        
        }
        return -W_EDGE*g;    
    }
    
    private double energy(ActiveContour ac) {
        List list = ac.getContourPoints();
        Point[] cp = new Point[list.size()];
        list.toArray(cp);
        
        return innerEnergy(cp) + outerEnergy(cp);    
    }
    
    private void iteration() {
        notifyIterationStarted(new AlgorithmIterationEvent(this));
        
        Neighborhood neighbor = new Neighborhood2D4(image.getMinX(), image.getMinY(),
                                                    image.getMaxX(), image.getMaxY());
        System.out.println("BasePoints: " + contour.getNBasePoints());
        
        contourEnergy = newContourEnergy;
        
        double energy = contourEnergy;
        double newEnergy = Double.MAX_VALUE;
        Point point = null;
        List l = contour.getBasePoints();
        Point[] points = new Point[l.size()];
        l.toArray(points);
        
        for (int i = 0; i < points.length; i++) {
            
            Point basePoint = (Point)points[i];
            for (Iterator it2 = neighbor.getNeighbors(basePoint); it2.hasNext();) {
                
                Point newBasePoint = (Point)it2.next();
                contour.replaceBasePoint(basePoint, newBasePoint);
                
                newEnergy = energy(contour);
                if (newEnergy < energy) {
                    energy = newEnergy;   
                } else {
                    contour.replaceBasePoint(newBasePoint, basePoint);    
                }
            }            
        }
        
        newContourEnergy = energy; 
        
        notifyIterationFinished(new AlgorithmIterationEvent(this));       
    }

	/**
	 * @see org.wewi.medimg.seg.ac.Minimizer#minimize()
	 */
	public void minimize() {
        for (AlgorithmIterator it = getAlgorithmIterator(); it.hasNextIteration();) {
            it.nextIteration();    
        }              
	}

	/**
	 * @see org.wewi.medimg.seg.ac.Minimizer#getActiveContour()
	 */
	public ActiveContour getActiveContour() {
		return contour;
	}
    
    public AlgorithmIterator getAlgorithmIterator() {
        return new GreedyMinimizerIterator();    
    }

}
