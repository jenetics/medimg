/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.seg.ac;

import java.util.Iterator;
import java.util.List;

import org.wewi.medimg.alg.AlgorithmIterationEvent;
import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.alg.IterateableAlgorithm;
import org.wewi.medimg.alg.ObservableAlgorithm;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.filter.BlurFilter;
import org.wewi.medimg.image.filter.ImageFilter;
import org.wewi.medimg.image.filter.NormalizeFilter;
import org.wewi.medimg.image.filter.SobelFilter;
import org.wewi.medimg.image.filter.TresholdFilter;
import org.wewi.medimg.image.geom.Neighborhood;
import org.wewi.medimg.image.geom.Neighborhood2D8;
import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.viewer.ImageViewer;
import org.wewi.medimg.viewer.Viewer;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class SnakeGreedyMinimizer extends ObservableAlgorithm
                                   implements ContourMinimizer,
                                               IterateableAlgorithm {
    
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
            System.out.println("" + contourEnergy + "-" + newContourEnergy);
			return Math.abs(contourEnergy - newContourEnergy) > ERROR_LIMIT;
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
    
    public static final double ERROR_LIMIT = 0.01;
    public double ALPHA = 1;
    public double BETA = 1;
    public double GAMMA = 1;
    
    protected Image image;
    protected Image gradImage;
    protected ActiveContour contour;
    protected Neighborhood neighbor;
    
    protected double contourEnergy = -Double.MAX_VALUE;
    protected double newContourEnergy = Double.MAX_VALUE;
    
    private int iterationCount = 0;

	/**
	 * Constructor for SnakeGreedyMinimizer.
	 */
	public SnakeGreedyMinimizer(Image image, ActiveContour contour) {
		super();
        this.image = image;
        this.contour = contour;
        
        neighbor = new Neighborhood2D8(image.getMinX(), image.getMinY(),
                                        image.getMaxX(), image.getMaxY());
        
        gradImage = (Image)image.clone();
        ImageFilter filter = new TresholdFilter(
                             new NormalizeFilter(
                             new SobelFilter(
                             new BlurFilter(gradImage)), 0, 255), 50, 255);

        filter.filter(); 
        
        ///////////////////////////////////////////////////////////////777
        ImageViewer imageViewer = new ImageViewer("Gradientenbild", gradImage);
        imageViewer.pack();
        imageViewer.show();
        
        java.awt.Point pos = new java.awt.Point(0, 300);
        java.awt.Dimension size = new java.awt.Dimension(300, 300);
        
        Viewer.getInstance().addViewerDesktopFrame(imageViewer, pos, size);        
        //////////////////////////////////////////////////////////////////
	}
    
    protected double innerEnergy(Point[] cp) {
        int size = cp.length;
        double h = 1 / (double)size;
        
        //Berechnen des Stetikeitsterms der inneren Energie
        int vix, viy, vimx, vimy;
        double d1 = 0;
        for (int i = 1, n = size+1; i < n; i++) {
            vix = cp[i%size].getOrdinate(0);
            viy = cp[i%size].getOrdinate(1);
            vimx = cp[i-1].getOrdinate(0);
            vimy = cp[i-1].getOrdinate(1);
            
            d1 += MathUtil.sqr(vix - vimx) + MathUtil.sqr(viy - vimy);
        }
        d1 /= h;
        
        //Berechnung des Glattheitstern der inneren Energie
        int vipx, vipy;
        double d2 = 0;
        for (int i = 1, n = size+1; i < n; i++) {
            vix = cp[i%size].getOrdinate(0);
            viy = cp[i%size].getOrdinate(1);
            vimx = cp[i-1].getOrdinate(0);
            vimy = cp[i-1].getOrdinate(1); 
            vipx = cp[(i+1)%size].getOrdinate(0);
            vipy = cp[(i+1)%size].getOrdinate(1); 
            
            d2 += MathUtil.sqr(vimx - 2*vix + vipx) + MathUtil.sqr(vimy - 2*viy + vipy);                   
        }
        d2 /= MathUtil.sqr(h);
        
        return ALPHA*d1 + BETA*d2;
        
        
        
        /* Funktioniert nicht ordentlich. Sollte eigentlich
         * Verhindern, daß die Abstände zweier benachbarte Punkte
         * zu weit auseinanderlaufen.
        int size = cp.length;
        double d = 0;
        //Berechnung des mittleren Abstandes d zweier benachbarte Punkte
        for (int i = 0; i < size; i++) {
            d += Math.sqrt(MathUtil.sqr(cp[i].getOrdinate(0) - cp[(i+1)%size].getOrdinate(0)) +
                           MathUtil.sqr(cp[i].getOrdinate(1) - cp[(i+1)%size].getOrdinate(1)));    
        }
        d /= (double)size;
        
        //Berechnen des Stetikeitsterms der inneren Energie
        double vix, viy, vimx, vimy, length;
        double d1 = 0;
        for (int i = 1, n = size+1; i < n; i++) {
            vix = cp[i%size].getOrdinate(0);
            viy = cp[i%size].getOrdinate(1);
            vimx = cp[i-1].getOrdinate(0);
            vimy = cp[i-1].getOrdinate(1);
            
            length = Math.sqrt(MathUtil.sqr(vix-vimx) + MathUtil.sqr(viy-vimy));
            d1 += (d - length) / length;      
        }
        d1 = MathUtil.sqr(d1);

        //Berechnung des Glattheitstern der inneren Energie
        double d2 = 0, vipx, vipy, norm, x1, y1, x2, y2;
        for (int i = 1, n = size+2; i < n; i++) {
            vix = cp[i%size].getOrdinate(0);
            viy = cp[i%size].getOrdinate(1);
            vimx = cp[(i-1)%size].getOrdinate(0);
            vimy = cp[(i-1)%size].getOrdinate(1);
            vipx = cp[(i+1)%size].getOrdinate(0);
            vipy = cp[(i+1)%size].getOrdinate(1);
            
            norm = Math.sqrt(MathUtil.sqr(vipx - vix) + MathUtil.sqr(vipy - viy));
            x1 = (vipx - vix) / norm;
            y1 = (vipy - viy) / norm;
            
            norm = Math.sqrt(MathUtil.sqr(vix - vimx) + MathUtil.sqr(viy - vimy));
            x2 = (vix - vimx) / norm;
            y2 = (viy - vimy) / norm;
            
            d2 += MathUtil.sqr(x1 - x2) + MathUtil.sqr(y1 - y2);   
        }

        return (ALPHA*d1 + BETA*d2);
        */
    }
    
    protected double outerEnergy(Point[] cp) {
        double g = 0;
        for (int i = 0, n = cp.length; i < n; i++) {
            g += gradImage.getColor(cp[i].getOrdinate(0), cp[i].getOrdinate(1), 0);        
        }
        return -GAMMA*(double)MathUtil.sqr(g);    
    }
    
    private double energy(ActiveContour ac) {
        List cpList = ac.getContourPoints();
        List bpList = ac.getBasePoints();
        Point[] bp = new Point[bpList.size()];
        Point[] cp = new Point[cpList.size()];
        cpList.toArray(cp);
        bpList.toArray(bp);
        
        return innerEnergy(bp) + outerEnergy(bp);    
    }
    
    private void iteration() {
        notifyIterationStarted(new AlgorithmIterationEvent(this, iterationCount));
        
        contourEnergy = newContourEnergy;
        
        double energy = contourEnergy;
        double newEnergy = Double.MAX_VALUE;
        Point point = null;
        List l = contour.getBasePoints();
        Point[] points = new Point[l.size()];
        l.toArray(points);
        
        for (int i = 0; i < points.length; i++) {
            
            Point basePoint = points[i];
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
        
        notifyIterationFinished(new AlgorithmIterationEvent(this, iterationCount));       
    }

	/**
	 * @see org.wewi.medimg.seg.ac.Minimizer#minimize()
	 */
	public ActiveContour minimize() {
        for (AlgorithmIterator it = getAlgorithmIterator(); it.hasNextIteration();) {
            it.nextIteration();    
        }
        
        return contour;              
	}

	/**
	 * @see org.wewi.medimg.seg.ac.Minimizer#getActiveContour()
	 */
	public ActiveContour getActiveContour() {
		return contour;
	}
    
    public int getIterations() {
        return iterationCount;    
    }
    
    public AlgorithmIterator getAlgorithmIterator() {
        return new GreedyMinimizerIterator();    
    }
    
    public double getALPHA() {
        return ALPHA;
    }
    
    public void setALPHA(double ALPHA) {
        this.ALPHA = ALPHA;
    }
    
    public double getBETA() {
        return BETA;
    }
    
    public void setBETA(double BETA) {
        this.BETA = BETA;
    }
    
    public double getGAMMA() {
        return GAMMA;
    }
    
    public void setGAMMA(double we) {
        GAMMA = we;
    }

}
