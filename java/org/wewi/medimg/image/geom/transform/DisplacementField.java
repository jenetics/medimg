/*
 * DisplacementField.java
 *
 * Created on 13. April 2002, 10:23
 */

package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Image;

import org.wewi.medimg.reg.metric.DoublePoint3D;

import java.util.Vector;
import java.util.Arrays;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class DisplacementField implements Transform {
    
    protected int DIM;
    protected DoublePoint3D[][][] field;
    protected boolean[] filled;
    public Vector referencePoints;
    private int sizeX;
    private int sizeY;
    private int sizeZ;

   
    /** Creates new DisplacementField */
    protected DisplacementField(int sizeX, int sizeY, int sizeZ) {
        field = new DoublePoint3D[sizeX][sizeY][sizeZ];
        filled = new boolean[sizeX * sizeY * sizeZ];
        Arrays.fill(filled, false);
        referencePoints = new Vector();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }
    
    /** Creates new DisplacementField */
    protected DisplacementField(Image image) {
        this(image.getMaxX() - image.getMinX() + 1, image.getMaxY() - image.getMinY() + 1, image.getMaxZ() - image.getMinZ() + 1);
    }
        
    public double[] transform_fw(double[] source) {
        return null;
    }
    
    public double[] transform_bw(double[] source) {
        return null;
    }
    
    public void addDisplacementVector(DoublePoint3D vec, DoublePoint3D src) {
        //Ausgangspunkt des DisplacementVectors wird in Liste der DisplacementVectoren eingetragen
        // und der DisplacementVector selbst in das Transformationsarray
        referencePoints.add(src);
        field[(int)src.getValue(0)][(int)src.getValue(1)][(int)src.getValue(2)] = vec;
        filled[(int)src.getValue(0) + ((int)src.getValue(1) * sizeX) + ((int)src.getValue(2) * sizeX * sizeY)] = true;
    }
    
    public void addNewDisplacementVector(DoublePoint3D vec, DoublePoint3D src) {
        field[(int)src.getValue(0)][(int)src.getValue(1)][(int)src.getValue(2)] = vec;
        filled[(int)src.getValue(0) + ((int)src.getValue(1) * sizeX) + ((int)src.getValue(2) * sizeX * sizeY)] = true;
    }    
    
    public DoublePoint3D getDisplacementVector(DoublePoint3D src) {
        double[] zero = new double[DIM];
        Arrays.fill(zero, (double) 0);
        DoublePoint3D temp = new DoublePoint3D(zero);
        if (filled[(int)src.getValue(0) + ((int)src.getValue(1) * sizeX) + ((int)src.getValue(2) * sizeX * sizeY)]) {
            temp = field[(int)src.getValue(0)][(int)src.getValue(1)][(int)src.getValue(2)];
        }
        return temp;        
    }
    
    public boolean displacementVectorExists(DoublePoint3D src) {
        return (filled[(int)src.getValue(0) + ((int)src.getValue(1) * sizeX) + ((int)src.getValue(2) * sizeX * sizeY)]);
    }
    
    public void transformBackward(double[] source, double[] target) {
    }
    
    public void transformBackward(float[] source, float[] target) {
    }
    
    public void transformForward(double[] source, double[] target) {
    }
    
    public void transformForward(float[] source, float[] target) {
    }    
    
    public Image transform(Image source) {
        return null;
    } 
    
    public Transform scale(double alpha) {
        return null;
    }
    
    public Transform concatenate(Transform trans) {
        return null;
    }
    
    public Transform invert() {// throw TransformationException();
        //später
        return null;
    }
    
    public String toString() {
        return null;
    }    
    
}
