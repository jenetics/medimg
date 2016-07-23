package org.wewi.medimg.reg;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.image.ops.BinaryPointAnalyzer;
import org.wewi.medimg.image.ops.NormalizedMutualInformationOperator;

/**
 * @author werner weiser
 *
 */
public class NormalizedMIAffinityMetric implements AffinityMetric {
    
    private Transformation trans;

    public static final NormalizedMIAffinityMetric INSTANCE = new NormalizedMIAffinityMetric();
    
    
        /** Creates new BBAffinityMetric */
    public NormalizedMIAffinityMetric() {
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
        NormalizedMutualInformationOperator op = new NormalizedMutualInformationOperator(cr1, cr2);
        BinaryPointAnalyzer analyzer = new BinaryPointAnalyzer(compare, target, op);
        analyzer.analyze();
        return op.getMutualInformation();
	}
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;    
        }    
        if (!(o instanceof NormalizedMIAffinityMetric)) {
            return false;    
        }
        return true;
    }
    
    
    public String toString() {
        return "NormalizedMIAffinityMetric";   
    }

}