/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.seg.ac;

import java.util.Iterator;

import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Neighborhood;
import org.wewi.medimg.image.geom.Neighborhood2D4;
import org.wewi.medimg.image.geom.Point;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class SnakeGreedyMinimizer implements Minimizer {
    
    private class GreedyMinimizerIterator implements AlgorithmIterator {
        
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
    
    public static final double ERROR_LIMIT = 1;
    
    protected Image image;
    protected ActiveContour contour;
    
    protected double contourEnergy = -Double.MAX_VALUE;
    protected double newContourEnergy = -Double.MAX_VALUE;

	/**
	 * Constructor for SnakeGreedyMinimizer.
	 */
	public SnakeGreedyMinimizer(Image image, ActiveContour contour) {
		super();
        this.image = image;
        this.contour = contour;
	}
    
    protected double innerEnergy(ActiveContour ac) {
        return 0;
    }
    
    protected double outerEnergy(ActiveContour ac) {
        return 0;    
    }
    
    private double energy(ActiveContour ac) {
        return innerEnergy(ac) + outerEnergy(ac);    
    }
    
    private void iteration() {
        Neighborhood neighbor = new Neighborhood2D4(image.getMinX(), image.getMinY(),
                                                    image.getMaxX(), image.getMaxY());
       
        
        contourEnergy = newContourEnergy;
        double energy = contourEnergy;
        
        Point point = null;
        for (Iterator it = contour.getBasePoints().iterator(); it.hasNext();) {
            Point basePoint = (Point)it.next();
            for (Iterator it2 = neighbor.getNeighbors(basePoint); it2.hasNext();) {
                Point newBasePoint = (Point)it2.next();
                contour.replaceBasePoint(basePoint, newBasePoint);
                
                double newEnergy = energy(contour);
                if (newEnergy > energy) {
                    energy = newEnergy;    
                } else {
                    contour.replaceBasePoint(newBasePoint, basePoint);    
                }
            }            
        }
        
        newContourEnergy = energy;        
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
