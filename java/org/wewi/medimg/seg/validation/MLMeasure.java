/**
 * MLMeasure.java
 * 
 * Created on 07.01.2003, 20:16:57
 *
 */
package org.wewi.medimg.seg.validation;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MLMeasure implements ValidationMeasure {

    /**
     * Constructor for MLMeasure.
     */
    public MLMeasure() {
        super();
    }

    /**
     * @see org.wewi.medimg.seg.validation.ValidationMeasure#measure(Image, Image)
     */
    public double measure(Image modelImage, Image segmentedImage) {
        Image mi = modelImage;
        Image si = segmentedImage;
        
        ColorRange miColorRange = AnalyzerUtils.getColorRange(modelImage); 
        ColorRange siColorRange = AnalyzerUtils.getColorRange(si);     
        
        AccumulatorArray accu = new AccumulatorArray(miColorRange.getNColors(), siColorRange.getNColors());
        for (int i = 0, n = mi.getNVoxels(); i < n; i++) {
            accu.inc(mi.getColor(i), si.getColor(i));   
        }
        

        T3 t3 = new T3(accu);
        int micolor = 0, sicolor = 0;
        int errorSum = 0;
        for (int i = 0, n = mi.getNVoxels(); i < n; i++) {
            micolor = mi.getColor(i);
            sicolor = si.getColor(i);
            
            if (micolor != t3.transform(sicolor)) {
                ++errorSum;    
            }        
        }       
        
        return (double)errorSum/(double)mi.getNVoxels();
    }

}
