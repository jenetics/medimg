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
 * Created on 30.09.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.Arrays;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ErrorMeasure {
    private Image am;
    private Image s;
    private T3 t3;
    
    private double[] featureError;
    private double overallError;

    /**
     * Constructor for ErrorMeasure.
     */
    public ErrorMeasure(Image am, Image s, T3 t3) {
        super();
        this.am = am;
        this.s = s;
        this.t3 = t3;
        
        featureError = new double[t3.getAbstractFeatures()];
        overallError = 0;
        Arrays.fill(featureError, 0);
    }
    
    public void measure() {
        int[] abstractFeatureVoxels = new int[t3.getAbstractFeatures()];
        int[] errorVoxels = new int[t3.getAbstractFeatures()];
        Arrays.fill(abstractFeatureVoxels, 0);
        Arrays.fill(errorVoxels, 0);
        
        
        int af = 0, mf = 0;
        for (int i = 0, n = am.getNVoxels(); i < n; i++) {
            mf = am.getColor(i);
            af = s.getColor(i);
            ++abstractFeatureVoxels[af];
            
            if (mf != t3.transform(af)) {
                ++errorVoxels[af];    
            }        
        } 
        
        for (int i = 0, n = t3.getAbstractFeatures(); i < n; i++) {
            featureError[i] = (double)errorVoxels[i] / (double)abstractFeatureVoxels[i];    
        }  
        
        int errorSum = 0;
        for (int i = 0; i < errorVoxels.length; i++) {
            errorSum += errorVoxels[i];    
        }
        
        overallError = (double)errorSum / (double)am.getNVoxels();
    }
    
    public double[] getFeatureError() {
        return featureError;    
    }
    
    public double getOverallError() {
        return overallError;    
    }

}
