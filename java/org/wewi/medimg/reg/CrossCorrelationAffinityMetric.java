package org.wewi.medimg.reg;


import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.image.ops.BinaryPointAnalyzer;
import org.wewi.medimg.image.ops.CrossCorrelationOperator;


/**
 * @author werner weiser
 *
 */
public class CrossCorrelationAffinityMetric implements AffinityMetric {
    
    private Transformation trans;
    private static double EPSILON = 0.01;

    public static final CrossCorrelationAffinityMetric INSTANCE = new CrossCorrelationAffinityMetric();
    
    
        /** Creates new BBAffinityMetric */
    public CrossCorrelationAffinityMetric() {
            trans = null;
    }
    
  	/**
	 * @see org.wewi.medimg.reg.AffinityMetric#similarity(VoxelIterator, VoxelIterator, Transformation)
	 */
	public double similarity(Image source, Image target, Transformation trans) {
		Image compare = null;
		if (trans != null) {
			ImageDataFactory fac = ImageDataFactory.getInstance();
	        compare = ((AffineTransformation)trans).transform(source, fac);
		} else {
			compare = source;
		}
		
		ColorRange cr1 = AnalyzerUtils.getColorRange(compare);
        ColorRange cr2 = AnalyzerUtils.getColorRange(target);
        CrossCorrelationOperator op = new CrossCorrelationOperator(cr1, cr2);
        BinaryPointAnalyzer analyzer = new BinaryPointAnalyzer(compare, target, op);
        analyzer.analyze();
        return op.getCrossCorrelation();
	}
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;    
        }    
        if (!(o instanceof CrossCorrelationAffinityMetric)) {
            return false;    
        }
        return true;
    }
    
    
    public String toString() {
        return "CrossCorrelationAffinityMetric";   
    }

}