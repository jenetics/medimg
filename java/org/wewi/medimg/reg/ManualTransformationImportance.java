/*
 * ImportanceStrategy.java
 *
 * Created on 26. März 2002, 09:38
 */

package org.wewi.medimg.reg;

import java.util.Vector;



/**
 *
 * @author  werner weiser
 * @version 
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
