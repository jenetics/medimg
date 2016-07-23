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

/**
 * VarianceImageMeasure.java
 *
 * Created on 27. Jänner 2003, 09:25
 */

package org.wewi.medimg.seg.validation;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.image.ops.BinaryPointAnalyzer;
import org.wewi.medimg.image.ops.MutualInformationOperator;

/**
 *
 * @author  fwilhelm
 */
public class VarianceImageMeasure implements ValidationMeasure {
    
    /** Creates a new instance of VarianceImageMeasure */
    public VarianceImageMeasure() {
    }
    
    public double measure(Image modelImage, Image segmentedImage) {
        /*
        SecondOrder sec = new SecondOrder(modelImage);
        Image varModel = sec.varianceImage();
        
        sec = new SecondOrder(segmentedImage);
        Image varSegmented = sec.varianceImage();
        */
        ColorRange cr1 = AnalyzerUtils.getColorRange(modelImage);
        ColorRange cr2 = AnalyzerUtils.getColorRange(segmentedImage);
        
        //System.out.println("CR1:\n" + cr1);
        //System.out.println("CR2:\n" + cr2);
        
        MutualInformationOperator op = new MutualInformationOperator(cr1, cr2);
        BinaryPointAnalyzer analyzer = new BinaryPointAnalyzer(modelImage, segmentedImage, op);
        analyzer.analyze(ROI.create(modelImage.getDimension(), 1)); 
        
        return op.getMutualInformation();
    }
    
}
