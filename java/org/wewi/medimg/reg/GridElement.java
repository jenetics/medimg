/*
 * GridElement.java
 *
 * Created on 16. Mai 2002, 13:23
 */

package org.wewi.medimg.reg;

import java.util.Vector;

import org.wewi.medimg.reg.metric.DoublePoint3D;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class GridElement {
    
    private Vector referencePoints;
    private boolean computed = false;
    private double[] reference = new double[3];
    private int[] location = new int[3];
    private int[] size = new int[3];
    
   
    /** Creates new GridElement */
    public GridElement(int sizeX, int sizeY, int sizeZ) {
        referencePoints = new Vector();
        size[0] = sizeX;
        size[1] = sizeY;
        size[2] = sizeZ;
                    /*if (sizeX == 4 && sizeY == 4 && sizeZ == 1) {
                        System.out.println("actual grid size " + size[0] + " , " + size[1] + " , " + size[2]);
                    }*/        
    }
    
    public void addReferencePoint(int[] pointI) {
        double[] pointD = new double[pointI.length];
        for (int i = 0; i < pointI.length; i++) {
            pointD[i] = (double)pointI[i];
        }
        referencePoints.addElement(new DoublePoint3D(pointD));
        computed = false;
    }
    
    public double[] getReferencePoint() {
        if (!computed) {
            computeReferencePoint();
            computed = true;
        }
        return reference;
    }
    
    public boolean referencePointExists() {
        return !(referencePoints.isEmpty());
    }
    
    private void computeReferencePoint() {
        //Todo
        double[] sum = new double[reference.length];

        for (int i = 0; i < referencePoints.size(); i++) {
            for (int j = 0; j < reference.length; j++) {
                sum[j] += ((DoublePoint3D)referencePoints.elementAt(i)).getValue(j);
            }
        }
        for (int j = 0; j < reference.length; j++) {
            reference[j] = sum[j] / (double)referencePoints.size();
        }
        
    }
    
    // minimalste Punkt am Grid
    public void setLocation(int[] newLoc) {
        location = newLoc;
    }
    
    public int[] getLocation() {
        return location;
    }
    
    public int getSize(int pos) {
        return size[pos];
    }
}
