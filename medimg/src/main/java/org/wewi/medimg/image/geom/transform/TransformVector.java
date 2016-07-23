/**
 * TransformVector.java
 *
 * Created on 23. März 2002, 12:45
 */

package org.wewi.medimg.image.geom.transform;


import java.util.Vector;

import org.wewi.medimg.image.Tissue;
/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class TransformVector {

    Vector transformVector;

    Vector tissueVector;

    Vector npointsVector;

    Vector fitnessVector;
    
    /** Creates new TransformVector */
    public TransformVector() {
        transformVector = new Vector();
        tissueVector = new Vector();
        npointsVector = new Vector();
        fitnessVector = new Vector();        
    }
    
    //tissue == color
    public void add(Tissue tissue, Transformation trans, int npoints) {
        transformVector.addElement(trans);
        tissueVector.addElement(tissue);
        npointsVector.addElement(new Integer(npoints));
    }

    public Transformation getTransformByTissue(Tissue tissue) {
        int pos = -1;
        for (int i = 0; i < size(); i++) {
            if (((Tissue)tissueVector.elementAt(i)).intValue() == tissue.intValue()) {
                pos = i;
                break;
            }
        }

        if (pos != -1) {
            return (Transformation)transformVector.elementAt(pos);
        } else {
            return null;
        }
    }

    public Transformation getTransformByPos(int pos) {
        return (Transformation)transformVector.elementAt(pos);
    }

    public int size() {
        return transformVector.size();
    }

    public int getNPoints(int pos) {
        return ((Integer)npointsVector.elementAt(pos)).intValue();
    }

    public Tissue getTissue(int pos) {
        return ((Tissue)tissueVector.elementAt(pos));
    }

    public void insertFitness(double f) {
        fitnessVector.addElement(new Double(f));
    }


    public double getFitness(int pos) {
        return ((Double)fitnessVector.elementAt(pos)).doubleValue();
    }


}
