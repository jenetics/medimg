package org.wewi.medimg.reg;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.image.ops.BinaryPointAnalyzer;
import org.wewi.medimg.image.ops.DirectComparisonOperator;

/**
 * @author werner weiser
 *
 */
public class DirectComparisonAffinityMetric implements AffinityMetric {
    
    private Transformation trans;

    public static final DirectComparisonAffinityMetric INSTANCE = new DirectComparisonAffinityMetric();
    
    
        /** Creates new BBAffinityMetric */
    public DirectComparisonAffinityMetric() {
            trans = null;
    }
    
  	/**
	 * @see org.wewi.medimg.reg.AffinityMetric#similarity(VoxelIterator, VoxelIterator, Transformation)
	 */
	public double similarity(Image source, Image target, Transformation trans) {
		Image compare = null;
		if (trans != null) {
			IntImageFactory fac = IntImageFactory.getInstance();
	        compare = ((AffineTransformation)trans).transform(source, fac);
		} else {
			compare = source;
		}
		
		ColorRange cr1 = AnalyzerUtils.getColorRange(compare);
        ColorRange cr2 = AnalyzerUtils.getColorRange(target);
        DirectComparisonOperator op = new DirectComparisonOperator(cr1, cr2);
        BinaryPointAnalyzer analyzer = new BinaryPointAnalyzer(compare, target, op);
        analyzer.analyze();
        return op.getDirectComparison();
	}
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;    
        }    
        if (!(o instanceof DirectComparisonAffinityMetric)) {
            return false;    
        }
        return true;
    }
    
    
    public String toString() {
        return "DirectComparisonAffinityMetric";   
    }

}

    
    
