/**
 * DisplacementField.java
 *
 * Created on 13. April 2002, 10:23
 */

package org.wewi.medimg.image.geom.transform;

import java.util.Arrays;
import java.util.Vector;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.math.geom.DoubleDataPoint;
/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class DisplacementField implements Transformation {
    
    protected int DIM;
    protected DoubleDataPoint[][][] field;
    protected boolean[] filled;
    public Vector referencePoints;
    private int sizeX;
    private int sizeY;
    private int sizeZ;

   
    /** Creates new DisplacementField */
    protected DisplacementField(int sizeX, int sizeY, int sizeZ) {
        field = new DoubleDataPoint[sizeX][sizeY][sizeZ];
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
        
    public double[] transform(double[] source) {
        return null;
    }
    
    public double[] transformBackward(double[] source) {
        return null;
    }
    
    public void addDisplacementVector(DoubleDataPoint vec, DoubleDataPoint src) {
        //Ausgangspunkt des DisplacementVectors wird in Liste der DisplacementVectoren eingetragen
        // und der DisplacementVector selbst in das Transformationsarray
        referencePoints.add(src);
        field[(int)src.getValue(0)][(int)src.getValue(1)][(int)src.getValue(2)] = vec;
        filled[(int)src.getValue(0) + ((int)src.getValue(1) * sizeX) + ((int)src.getValue(2) * sizeX * sizeY)] = true;
    }
    
    public void addNewDisplacementVector(DoubleDataPoint vec, DoubleDataPoint src) {
        field[(int)src.getValue(0)][(int)src.getValue(1)][(int)src.getValue(2)] = vec;
        filled[(int)src.getValue(0) + ((int)src.getValue(1) * sizeX) + ((int)src.getValue(2) * sizeX * sizeY)] = true;
    }    
    
    public DoubleDataPoint getDisplacementVector(DoubleDataPoint src) {
        double[] zero = new double[DIM];
        Arrays.fill(zero, (double) 0);
        DoubleDataPoint temp = new DoubleDataPoint(zero);
        if (filled[(int)src.getValue(0) + ((int)src.getValue(1) * sizeX) + ((int)src.getValue(2) * sizeX * sizeY)]) {
            temp = field[(int)src.getValue(0)][(int)src.getValue(1)][(int)src.getValue(2)];
        }
        return temp;        
    }
    
    public boolean displacementVectorExists(DoubleDataPoint src) {
        return (filled[(int)src.getValue(0) + ((int)src.getValue(1) * sizeX) + ((int)src.getValue(2) * sizeX * sizeY)]);
    }
    
    private void transformBackward(double[] source, double[] target) {
    }
    
    private void transformBackward(float[] source, float[] target) {
    }
    
    public void transform(double[] source, double[] target) {
    }
    
    public void transform(float[] source, float[] target) {
    }    
    
    public void transform(int[] source, int[] target) {
    }    
        
    public void transform(Image source, Image target) {
    }
        
    public Image transform(Image source) {
        return null;
    } 
    
    public Transformation scale(double alpha) {
        return null;
    }
    
    public Transformation concatenate(Transformation trans) {
        return null;
    }
    
    public Transformation createInverse() {// throw TransformationException();
        //später
        return null;
    }
    
    public String toString() {
        return null;
    }    
    
}
