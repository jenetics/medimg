/**
 * Created on 15.11.2002 11:02:37
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.alg.IterateableAlgorithm;
import org.wewi.medimg.alg.ObservableAlgorithm;
import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.filter.GradientFilter;
import org.wewi.medimg.image.geom.Point3D;
import org.wewi.medimg.math.ConstVectorFunction;
import org.wewi.medimg.math.DoubleGridVectorField;
import org.wewi.medimg.math.GridVectorField;
import org.wewi.medimg.math.GridVectorFieldTransformer;
import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.MaxVectorLengthOperator;
import org.wewi.medimg.math.ScaleVectorFunction;
import org.wewi.medimg.math.VectorFieldAnalyzer;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GradientVectorFlow extends ObservableAlgorithm 
                                 implements IterateableAlgorithm {
                                    
    private Image edgeMap;
    private GridVectorField gvf;
    private GridVectorField gradient;
    
    private final double dx = 1;
    private final double dy = 1;    
    private final double MU = 0.1;
    private final double dt = dx*dy/(25*MU);


	/**
	 * Constructor for GradientVectorFlow.
	 */
	public GradientVectorFlow(Image edgeMap) {
		super();
        this.edgeMap = edgeMap;
        init();
	}
    
    private void init() {
        
        Dimension dim = edgeMap.getDimension();
        Point3D origin = new Point3D(dim.getMinX(), dim.getMinY(), dim.getMinZ());
        gvf = new DoubleGridVectorField(origin, new int[]{dim.getSizeX(), dim.getSizeY(), 1}, new int[]{1, 1, 1});
        GridVectorFieldTransformer trans = new GridVectorFieldTransformer(gvf, 
                                           new ConstVectorFunction(new double[]{0, 0, 0}));
        trans.transform();  
  
        
        GradientFilter filter = new GradientFilter(edgeMap);
        filter.filter();
        gradient = filter.getGradientVectorField();
        
        MaxVectorLengthOperator op = new MaxVectorLengthOperator(); 
        VectorFieldAnalyzer analyzer = new VectorFieldAnalyzer(gradient, op);
        analyzer.analyze();
        double scale = 1d/op.getMaxLength(); 
        trans = new GridVectorFieldTransformer(gradient, 
                                           new ScaleVectorFunction(scale)); 
        trans.transform();        
    }
    
    
    /**
     * Arbeit einer Iteration
     */
    private void iteration() {
        double r = MU*dt/(dx*dy);
        double[] p0 = new double[3]; //Pi,j
        double[] p1 = new double[3]; //Pi+1,j
        double[] p2 = new double[3]; //Pi,j+1
        double[] p3 = new double[3]; //Pi-1,j
        double[] p4 = new double[3]; //Pi,j-1
        
        
        double u, v;
        for (int i = 1, n = gvf.getGridsX()-1; i < n; i++) {
            for (int j = 1, m = gvf.getGridsY()-1; j < m; j++) {
                gvf.getVector(i, j, 0, p0);
                gvf.getVector(i+1, j, 0, p1);
                gvf.getVector(i, j+1, 0, p2);
                gvf.getVector(i-1, j, 0, p3);
                gvf.getVector(i, j-1, 0, p4);
                
                u = (1 - b(i, j)*dt)*p0[0] + r*(p1[0] + p2[0] + p3[0] + p4[0] - 4*p0[0]) + c1(i, j)*dt;
                v = (1 - b(i, j)*dt)*p0[1] + r*(p1[1] + p2[1] + p3[1] + p4[1] - 4*p0[1]) + c2(i, j)*dt;
                
                p0[0] = u;
                p0[1] = v;
                
                gvf.setVector(i, j, 0, p0);
            }    
        }
        
         
    }
    
    public void start() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("Iteration: " + (i+1));
            iteration();    
        }  
        
        //System.out.println(gvf);
    }
    
    private double b(int x, int y) {
        double[] v = new double[3];
        gradient.getVector(x, y, 0, v);
        
        return MathUtil.sqr(v[0]) + MathUtil.sqr(v[1]);    
    }
    
    private double c1(int x, int y) {
        double[] v = new double[3];
        gradient.getVector(x, y, 0, v);
        
        return b(x, y)*v[0];        
    }
    
    private double c2(int x, int y) {
        double[] v = new double[3];
        gradient.getVector(x, y, 0, v);
        
        return b(x, y)*v[1];        
    }    
    
    
    
    public GridVectorField getGradientVectorField() {
    
        return gvf;    
    }

	/**
	 * @see org.wewi.medimg.alg.IterateableAlgorithm#getAlgorithmIterator()
	 */
	public AlgorithmIterator getAlgorithmIterator() {
		return null;
	}

	/**
	 * @see org.wewi.medimg.alg.IterateableAlgorithm#getIterations()
	 */
	public int getIterations() {
		return 0;
	}

}