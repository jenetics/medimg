/**
 * AnalyzerUtils.java
 * 
 * Created on 07.01.2003, 20:24:33
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class AnalyzerUtils {

    /**
     * Constructor for AnalyzerUtils.
     */
    private AnalyzerUtils() {
        super();
    }


    public static ColorRange getColorRange(Image image) {
        ColorRangeOperator op = new ColorRangeOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze(); 
        
        return op.getColorRange();    
    }
    
    public static ColorRange getColorRange(Image image, ROI roi) {
        ColorRangeOperator op = new ColorRangeOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze(roi);
        
        return op.getColorRange();
    }
}
