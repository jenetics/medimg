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
 * ImportanceStrategy.java
 *
 * Created on 26. M�rz 2002, 09:38
 */

package org.wewi.medimg.reg;

import java.util.Vector;



/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class ManualTransformationImportance extends AbstractTransformationImportance {
    
    Vector featureVector;
    Vector importanceVector;

    public static final ManualTransformationImportance INSTANCE = new ManualTransformationImportance();


    /** Creates new ImportanceStrategy */
    public ManualTransformationImportance() {
        featureVector = new Vector();
        importanceVector = new Vector();
    }
    
    public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints) {
        
        double sumImportance = 0;
        double alpha = 0.0;
        double[] erg = new double[features.length];
        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                sumImportance += getImportance(features[i]);
            }
        }

        for (int i = 0; i < features.length; i++) {
            if (similarity[i] >= getErrorLimit()) {
                alpha = getImportance(features[i]) / sumImportance;
            } else {
                alpha = 0.0;
            }
            erg[i] = alpha;
        }        
        return erg;
    } 
        
    public void setImportance(int t, double i) {
        featureVector.addElement(new Integer(t));
        importanceVector.addElement(new Double(i));
    }

    public double getImportance(int t) {
        for (int i = 0; i < featureVector.size(); i++) {
            if (((Integer)featureVector.elementAt(i)).intValue() == t) {
                return ((Double)importanceVector.elementAt(i)).doubleValue();
            }
        }
        return 1.0;
    }

}
