/**
 * ReverseMLMeasure.java
 * 
 * Created on 13.01.2003, 14:19:18
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
public class ReverseMLMeasure implements ValidationMeasure {

    /**
     * Constructor for ReverseMLMeasure.
     */
    public ReverseMLMeasure() {
        super();
    }

    /**
     * @see org.wewi.medimg.seg.validation.ValidationMeasure#measure(Image, Image)
     */
    public double measure(Image modelImage, Image segmentedImage) {
        Image mi = modelImage;
        Image si = segmentedImage;
        
        ColorRange mcr= AnalyzerUtils.getColorRange(mi);
        ColorRange scr = AnalyzerUtils.getColorRange(si);
        
        AccumulatorArray accu = new AccumulatorArray(scr.getNColors(), mcr.getNColors());
        
        for (int k = modelImage.getMinZ(), l = modelImage.getMaxZ(); k <= l; k++) {
            for (int j = modelImage.getMinY(), m = modelImage.getMaxY(); j <= m; j++) {
                for (int i = modelImage.getMinX(), n = modelImage.getMaxX(); i <= n; i++) {
                    accu.inc(si.getColor(i, j, k), mi.getColor(i, j, k));    
                }    
            }    
        }
        
        T3 t3 = new T3(accu);
        
        int err = 0;
        for (int k = modelImage.getMinZ(), l = modelImage.getMaxZ(); k <= l; k++) {
            for (int j = modelImage.getMinY(), m = modelImage.getMaxY(); j <= m; j++) {
                for (int i = modelImage.getMinX(), n = modelImage.getMaxX(); i <= n; i++) {
                    if (t3.transform(mi.getColor(i, j, k)) != si.getColor(i, j, k)) {
                        ++err;    
                    }
                }    
            }    
        }        
        
        
        return (double)err / (double)mi.getNVoxels();
    }

}
