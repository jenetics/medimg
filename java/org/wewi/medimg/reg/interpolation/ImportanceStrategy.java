/*
 * ImportanceStrategy.java
 *
 * Created on 26. März 2002, 09:38
 */

package org.wewi.medimg.reg.interpolation;

import java.util.Vector;

import org.wewi.medimg.image.Tissue;


/**
 *
 * @author  werner weiser
 * @version 
 */
public class ImportanceStrategy extends TransformInterpol {
    
    Vector tissueVector;
    Vector importanceVector;

    /** Creates new ImportanceStrategy */
    public ImportanceStrategy() {
        tissueVector = new Vector();
        importanceVector = new Vector();
        errorLimit = 0;
    }
    
    public void calculateWeights() {
       /*
        double sumimportance = 0.0;
        int transforms = transformVector.size();
        int i;
        double alpha = 0.0;
        for (i = 0; i < transforms; i++) {
            if (transformVector.getFitness(i) >= getErrorLimit()) {
                sumimportance += getImportance(transformVector.getTissue(i));
            }
        }
       for (i = 0; i < transforms; i++) {
            if (transformVector.getFitness(i) >= getErrorLimit()) {
                alpha = getImportance(transformVector.getTissue(i)) / 
                    sumimportance;
            } else {
                alpha = 0.0;
            }
            transWeights[i] = alpha;
        }
*/
    }

    public void setImportance(Tissue t, double i) {
        tissueVector.addElement(t);
        importanceVector.addElement(new Double(i));
    }

    public double getImportance(Tissue t) {
        for (int i = 0; i < tissueVector.size(); i++) {
            if (((Tissue)tissueVector.elementAt(i)).intValue() == t.intValue()) {
                return ((Double)importanceVector.elementAt(i)).doubleValue();
            }
        }
        return 1.0;
    }

}
