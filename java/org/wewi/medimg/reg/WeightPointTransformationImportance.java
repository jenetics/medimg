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

/*
 * WeightPointStrategy.java
 *
 * Created on 26. März 2002, 09:20
 */

package org.wewi.medimg.reg;



/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class WeightPointTransformationImportance extends AbstractTransformationImportance {
    public static final WeightPointTransformationImportance INSTANCE = new WeightPointTransformationImportance();

    /** Creates new WeightPointStrategy */
    public WeightPointTransformationImportance() {
    }
    
    public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints) {
        
        double sumPoints = 0;
        double alpha = 0.0;
        double[] erg = new double[features.length];
        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                sumPoints += featureNPoints[i];
            }
        }

        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                alpha = featureNPoints[i] / sumPoints;
            } else {
                alpha = 0.0;
            }
            erg[i] = alpha;
        }        
        return erg;
    }    


    
}
