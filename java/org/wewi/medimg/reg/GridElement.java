/*
 * GridElement.java
 *
 * Created on 16. Mai 2002, 13:23
 */

package org.wewi.medimg.reg;

import java.util.Vector;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class GridElement {
    
    private Vector referencePoints;
    private boolean computed = false;
    private double[] reference = new double[3];
    private double[] location = new double[3];
    private int[] size = new int[3];
    
    private class Point3D {
    
        protected double[] point;
        public Point3D (double[] p) {
            int i;
            point = new double[p.length];
            for( i = 0; i < p.length; i++) {
                point[i] = p[i];
            }
        }
        
        public double getValue(int pos) {
            return point[pos];
        }
    }
    
    /** Creates new GridElement */
    public GridElement(int sizeX, int sizeY, int sizeZ) {
        referencePoints = new Vector();
        size[0] = sizeX;
        size[1] = sizeY;
        size[2] = sizeZ;
    }
    
    public void addReferencePoint(int[] pointI) {
        double[] pointD = new double[pointI.length - 1];
        for (int i = 0; i < pointI.length; i++) {
            pointD[i] = (double)pointI[i];
        }
        referencePoints.add(new Point3D(pointD));
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
        double[] sum = new double[reference.length - 1];

        for (int i = 0; i < referencePoints.size(); i++) {
            for (int j = 0; j < reference.length; j++) {
                sum[j] += ((Point3D)referencePoints.elementAt(i)).getValue(j);
            }
        }
        for (int j = 0; j < reference.length; j++) {
            reference[j] = sum[j] / (double)referencePoints.size();
        }
        
    }
    
    public void setLocation(double[] newLoc) {
        location = newLoc;
    }
    
    public double[] getLocation() {
        return location;
    }
    
    public int getSize(int pos) {
        return size[pos];
    }
}
