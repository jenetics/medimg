/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package org.wewi.medimg.reg;


import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
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
			IntImageFactory fac = IntImageFactory.getInstance();
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