/**
 * Created on 27.11.2002 10:33:00
 *
 */
package org.wewi.medimg.seg.ac;

import java.util.Iterator;
import java.util.List;

import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.geom.Neighborhood;
import org.wewi.medimg.image.geom.Neighborhood2D8;
import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class NewGreedySnakeMinimizer implements ContourMinimizer {
    
    
    private final class GreedyMinimizerIterator implements AlgorithmIterator {
        
        public GreedyMinimizerIterator() {
            iterationCount = 0;   
        }
        
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
            System.out.println("" + lastEnergy + "-" + currentEnergy);
            return Math.abs(lastEnergy - currentEnergy) > ERROR_LIMIT;
        }

        /**
         * @see org.wewi.medimg.alg.AlgorithmIterator#nextIteration()
         */
        public void nextIteration() {
            ++iterationCount;
            iteration();
        }

    } 
    /**************************************************************************/    
    
    
    private double alpha = 1;
    private double beta = 1;
    private double gamma = 1;
    private static final double ERROR_LIMIT = 0.1;
    
    private ActiveContour contour;
    private OuterEnergy outerEnergy;
    private Dimension dim;
    
    private double lastEnergy = -Double.MAX_VALUE;
    private double currentEnergy = Double.MAX_VALUE;
    private int iterationCount;

	/**
	 * Constructor for NewGreedySnakeMinimizer.
	 */
	public NewGreedySnakeMinimizer(ActiveContour contour, OuterEnergy outerEnergy, Dimension dim) {
		super();
        this.contour = contour;
        this.outerEnergy = outerEnergy;
        this.dim = dim;
	}
    
    
    private void iteration() {
        List l = contour.getBasePoints();
        Point[] points = new Point[l.size()];
        l.toArray(points);
        
        //Berechnung des durchschnittlichen Abstandes zweier Punkte
        double pointDistance = 0;
        for (int i = 0, n = points.length; i < n; i++) {
            pointDistance += Math.sqrt(MathUtil.sqr(points[i].getOrdinate(0) -
                                                    points[i].getOrdinate(1)) +
                                       MathUtil.sqr(points[(i+1)%n].getOrdinate(0) - 
                                                    points[(i+1)%n].getOrdinate(1)));        
        }
        pointDistance /= (double)points.length;
        
        Point lastPoint, currentPoint, nextPoint;
        Neighborhood nhood = new Neighborhood2D8(dim);        
        Point[] neighbors = new Point[9];
        double[] inner = new double[9];
        double[] outer = new double[9];
        int best = 1;
        int counter = 0;
        double energy = 0;
        for (int i = 2, n = points.length+2; i < n; i++) {
            lastPoint = points[(i-1)%n];
            currentPoint = points[i%n];
            nextPoint = points[(i+1)%n];

            counter = 0;
            neighbors[0] = currentPoint;
            inner[0] = innerEnergy(lastPoint, currentPoint, nextPoint, pointDistance);
            outer[0] = outerEnergy.energy(currentPoint);
            counter++;
            
            for (Iterator it = nhood.getNeighbors(currentPoint); it.hasNext();) {
                neighbors[counter] = (Point)it.next();
                inner[counter] = innerEnergy(lastPoint, neighbors[counter], nextPoint, pointDistance);
                outer[counter] = outerEnergy.energy(neighbors[counter]);
                counter++;            
            } 
            
            MathUtil.normalize(inner);
            MathUtil.normalize(outer);
            
            //Finden der minimalen Energie
            double min = Integer.MAX_VALUE;
            int index = 0;
            for (int j = 0; j < counter; j++) {
                if (min > inner[j]+outer[j]) {
                    min = inner[j]+outer[j]; 
                    index = j;   
                }    
            }
            
            if (index != 0) {
                contour.replaceBasePoint(currentPoint, neighbors[index]);    
            }
            
            energy += min;     
        }
        
        lastEnergy = currentEnergy;
        currentEnergy = energy;
              
    } 
    
    protected double innerEnergy(Point lp, Point cp, Point np, double h) {
        double vix, viy, vimx, vimy;
        vix = cp.getOrdinate(0);
        viy = cp.getOrdinate(1);
        vimx = lp.getOrdinate(0);
        vimy = lp.getOrdinate(1);        
        
        //Berechnen des Stetikeitsterms der inneren Energie
        double d1 = h - Math.sqrt(MathUtil.sqr(vix-vimy) + MathUtil.sqr(viy-vimy));
        
        //Berechnen des Glattheitsterms
        double d2 = 0, vipx, vipy, norm, x1, y1, x2, y2;

        vipx = np.getOrdinate(0);
        vipy = np.getOrdinate(1); 
        
        norm = Math.sqrt(MathUtil.sqr(vipx - vix) + MathUtil.sqr(vipy - viy));
        x1 = (vipx - vix) / norm;
        y1 = (vipy - viy) / norm;
        
        norm = Math.sqrt(MathUtil.sqr(vix - vimx) + MathUtil.sqr(viy - vimy));
        x2 = (vix - vimx) / norm;
        y2 = (viy - vimy) / norm;
                 
        d2 = MathUtil.sqr(x1 - x2) + MathUtil.sqr(y1 - y2);
                       
        return alpha*d1 + beta*d2;
    }       

	/**
	 * @see org.wewi.medimg.seg.ac.ContourMinimizer#minimize()
	 */
	public ActiveContour minimize() {
        for (AlgorithmIterator it = getAlgorithmIterator(); it.hasNextIteration();) {
            it.nextIteration();    
        }
        
        return contour;
	}
    
    public AlgorithmIterator getAlgorithmIterator() {
        return new GreedyMinimizerIterator();    
    }    

	/**
	 * Returns the a.
	 * @return double
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Returns the b.
	 * @return double
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * Returns the gamma.
	 * @return double
	 */
	public double getGamma() {
		return gamma;
	}

	/**
	 * Sets the a.
	 * @param a The a to set
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Sets the b.
	 * @param b The b to set
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}

	/**
	 * Sets the gamma.
	 * @param gamma The gamma to set
	 */
	public void setGamma(double gamma) {
		this.gamma = gamma;
	}

}
























