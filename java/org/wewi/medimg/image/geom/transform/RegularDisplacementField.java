/**
 * RegularDisplacementField.java
 * 
 * Created on 07.03.2003, 13:47:58
 *
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.geom.Point3D;
import org.wewi.medimg.math.vec.DoubleGridVectorField;
import org.wewi.medimg.math.vec.GridVectorField;
import org.wewi.medimg.math.vec.VectorIterator;
import org.wewi.medimg.math.vec.ops.GridFieldLoop;
import org.wewi.medimg.math.vec.ops.GridVectorFieldTransformer;
import org.wewi.medimg.math.vec.ops.ScaleVectorFunction;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RegularDisplacementField extends DisplacementField 
                                       implements GridVectorField {
    
    private static class NearestNeighborInterpolator extends Interpolator {
        
        public void interpolateEndPoint(double[] startPoint, double[] endPoint) {
        }

        public void interpolateStartPoint(double[] endPoint, double[] startPoint) {
        }
        
        public Object clone() {
            return null;
        }
    }
    
    private static class TrilinearInterpolator extends Interpolator {

        public void interpolateEndPoint(double[] startPoint, double[] endPoint) {
        }

        public void interpolateStartPoint(double[] endPoint, double[] startPoint) {
        }
        
        public Object clone() {
            return null;
        }
    }
    
    /**
     * A pretty dirty implementation strategy!
     */
    public static final Interpolator NEAREST_NEIGHBOR = new NearestNeighborInterpolator();
    public static final Interpolator TRILINEAR = new TrilinearInterpolator();
    
     
    private Dimension dim;
    private int strideX;
    private int strideY;
    private int strideZ;
    private int[] strideXYZ = {0, 0, 0};
    
    private GridVectorField vectorField;
    
    /**
     * Copyconstructor for this class
     * 
     * @param df GridVectorField to be cloned.
     */
    private RegularDisplacementField(RegularDisplacementField df) {
        dim = df.dim;
        strideX = df.strideX;
        strideY = df.strideY;
        strideZ = df.strideZ;
        
        vectorField = (GridVectorField)df.vectorField.clone();
        setFieldInterpolator(df.getFieldInterpolator());
    }
    
    
    public RegularDisplacementField(Dimension dim) {
        this(dim, 1);
    }
    
    public RegularDisplacementField(Dimension dim, int stride) {
        this(dim, stride, stride, stride);
    }
    
    public RegularDisplacementField(Dimension dim, int strideX, int strideY, int strideZ) {
        this.dim = dim;
        this.strideX = strideX;
        this.strideY = strideY;
        this.strideZ = strideZ;
        
        init();
    }
    
    private void init() {
        Point3D origin = new Point3D(dim.getMinX(), dim.getMinY(), dim.getMinZ()); 
        int[] gridsXYZ = {(int)Math.ceil((double)dim.getSizeX()/(double)strideX),
                          (int)Math.ceil((double)dim.getSizeY()/(double)strideY),
                          (int)Math.ceil((double)dim.getSizeZ()/(double)strideZ)};
        strideXYZ[0] = strideX;
        strideXYZ[1] = strideY;
        strideXYZ[2] = strideZ;
        
        vectorField = new DoubleGridVectorField(origin, gridsXYZ, strideXYZ);
        
        //Init the field to the identity transformation
        FieldFactory.toIdentityField(this);
        
        setFieldInterpolator(NEAREST_NEIGHBOR);        
    }

	/**
	 * 
	 */
	public int getStrideX() {
		return strideX;
	}
    
	/**
	 * 
	 */
	public int getStrideY() {
		return strideY;
	}
    
	/**
	 * 
	 */
	public int getStrideZ() {
		return strideZ;
	}

	/**
	 * 
	 */
	public int[] getStrideXYZ() {
		return strideXYZ;
	}
    

    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#scale(double)
     */
    public Transformation scale(double alpha) {
        ScaleVectorFunction f = new ScaleVectorFunction(alpha);
        GridVectorFieldTransformer t = new GridVectorFieldTransformer(this, f);
        t.transform();
        
        return this;
    }

    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#concatenate(org.wewi.medimg.image.geom.transform.Transformation)
     */
    public Transformation concatenate(Transformation trans) {
        return null;
    }

    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#createInverse()
     */
    public Transformation createInverse() {
        RegularDisplacementField field = new RegularDisplacementField(this);
        
        
        GridFieldLoop loop = new GridFieldLoop(field, new GridFieldLoop.Task() {
            private double[] start = new double[3];
            private double[] end = new double[3];
            public void execute(int gridX, int gridY, int gridZ) {
//              The start point of the normal grid is the end point of the inverse grid.
                vectorField.getGridStartPoint(gridX, gridY, gridZ, end);
                
//              Interpolating the start point from the inverse end point.
                getFieldInterpolator().interpolateStartPoint(end, start);
                
                getVectorField().setGridEndPoint(gridX, gridY, gridZ, start);
            }
        
        });
        loop.loop();
        
        return field;
    }

    
    /***********Methods for the GridVectorField********************************/
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#setGridEndPoint(int, int, int, double[])
     */
    public void setGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint) {
        vectorField.setGridEndPoint(gridX, gridY, gridZ, endPoint);
    }
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#getGridStartPoint(int, int, int, double[])
     */
    public void getGridStartPoint(int gridX, int gridY, int gridZ, double[] startPoint) {
        vectorField.getGridStartPoint(gridX, gridY, gridZ, startPoint);
    }
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#getGridEndPoint(int, int, int, double[])
     */
    public void getGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint) {
        vectorField.getGridEndPoint(gridX, gridY, gridZ, endPoint);
    }
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#getVector(int, int, int, double[])
     */
    public void getVector(int gridX, int gridY, int gridZ, double[] vector) {
        vectorField.getVector(gridX, gridY, gridZ, vector);
    }
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#setVector(int, int, int, double[])
     */
    public void setVector(int gridX, int gridY, int gridZ, double[] vector) {
        vectorField.setVector(gridX, gridY, gridZ, vector);
    }
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#getGridsX()
     */
    public int getGridsX() {
        return vectorField.getGridsX();
    }
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#getGridsY()
     */
    public int getGridsY() {
        return vectorField.getGridsY();
    }
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#getGridsZ()
     */
    public int getGridsZ() {
        return vectorField.getGridsZ();
    }
    
    /**
     * @see org.wewi.medimg.math.vec.GridVectorField#clone()
     */
    public Object clone() {
        return new RegularDisplacementField(this);
    }
    
    /**
     * @see org.wewi.medimg.math.vec.VectorField#setVector(double[], double[])
     */
    public void addVector(double[] startPoint, double[] endPoint) {
        vectorField.addVector(startPoint, endPoint);
    }
    

	/**
	 * @see org.wewi.medimg.math.vec.VectorField#getVectorIterator()
	 */
	public VectorIterator getVectorIterator() {
		return vectorField.getVectorIterator();
	}
	


}
