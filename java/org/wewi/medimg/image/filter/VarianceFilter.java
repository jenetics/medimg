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
 * VarianceFilter.java
 *
 * Created on 24. Jänner 2003, 14:32
 */

package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;
import org.wewi.medimg.image.ops.ImageLoop;
import org.wewi.medimg.image.ops.MeanVarianceOperator;
import org.wewi.medimg.image.ops.UnaryPointAnalyzer;

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
