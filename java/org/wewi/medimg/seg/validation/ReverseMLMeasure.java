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
