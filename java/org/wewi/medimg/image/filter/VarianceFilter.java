/**
 * VarianceFilter.java
 *
 * Created on 24. Jänner 2003, 14:32
 */

package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;
import org.wewi.medimg.image.ops.ImageLoop;
import org.wewi.medimg.image.ops.*;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class VarianceFilter extends ImageFilter {
    
    /** Creates a new instance of VarianceFilter */
    public VarianceFilter(Image image) {
        super(image);
    }
    
    public VarianceFilter(ImageFilter component) {
        super(component);
    }
    
    protected void componentFilter() {
        Image temp = (Image)image.clone();

        ImageLoop loop = new ImageLoop(temp, new Variance());
        loop.loop();

        LinearNormalizeFilter filter = new LinearNormalizeFilter(image, 0, 255);
        filter.filter();
    }
    
    private final class Variance extends ImageLoop.Task {
        private ROI imageSize = ROI.create(image.getDimension());
        
        public void execute(int x, int y, int z) {
            ROI roi = imageSize.intersect(ROI.create(x, y, z, 1));
            
            MeanVarianceOperator op = new MeanVarianceOperator();
            UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(getImage(), op);
            analyzer.analyze(roi);
            
            image.setColor(x, y, z, (int)(op.getVariance()*1000));
        }
    }
    
}
