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

package org.wewi.medimg.reg;

import java.util.Arrays;


/**
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * 
 * @version 0.1  
 */
public class ConstantTransformationImportance extends AbstractTransformationImportance {

    public static final ConstantTransformationImportance INSTANCE = new ConstantTransformationImportance();

    /**
     * Constructor for ConstantTransformationImportance.
     */
    public ConstantTransformationImportance() {
        super();
    }

    /**

    /**
     * @see org.wewi.medimg.reg.TransformationImportance#transformationWeights(int[], double[], int[])
     */
    public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints) {
        double[] result = new double[features.length];
        double alpha = 0.0;
        double sumValid = 0;
        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                sumValid++;
            }
        }
        if (sumValid == 0) {
            Arrays.fill(result, 0);    
        } else {    
            for (int i = 0; i < features.length; i++) {
                if (similarity[i] >= getErrorLimit()) {
                    alpha = 1 / sumValid;
                } else {
                    alpha = 0.0;
                }
                result[i] = alpha;
            }
        }

        return result;
    }

    
}

