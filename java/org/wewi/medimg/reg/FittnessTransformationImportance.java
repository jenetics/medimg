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
 * FittnessStrategy.java
 *
 * Created on 26. März 2002, 09:51
 */

package org.wewi.medimg.reg;


/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class FittnessTransformationImportance extends AbstractTransformationImportance {
    public static final FittnessTransformationImportance INSTANCE = new FittnessTransformationImportance();

    /** Creates new FittnessStrategy */
    public FittnessTransformationImportance() {
    }
    
    public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints) {
        
        double sumFittness = 0;
        double alpha = 0.0;
        double[] erg = new double[features.length];
        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                sumFittness += similarity[i];
            }
        }

        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                alpha = similarity[i] / sumFittness;
            } else {
                alpha = 0.0;
            }
            erg[i] = alpha;
        }        
        return erg;
    }    
    

}
