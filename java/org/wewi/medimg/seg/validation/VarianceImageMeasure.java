/*
 * VarianceImageMeasure.java
 *
 * Created on 27. Jänner 2003, 09:25
 */

package org.wewi.medimg.seg.validation;

import org.wewi.medimg.image.*;
import org.wewi.medimg.image.ops.*;
import org.wewi.medimg.image.filter.*;
import org.wewi.medimg.image.statistic.*;

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
        
        return op.getMutialInformation();
    }
    
}
