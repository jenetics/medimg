/**
 * IrregularDisplacementField.java
 * 
 * Created on 12.03.2003, 10:58:32
 *
 */
package org.wewi.medimg.image.geom.transform;


import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.vec.ArbitraryVectorField;
import org.wewi.medimg.math.vec.DoubleVectorField;
import org.wewi.medimg.math.vec.VectorIterator;
import org.wewi.medimg.math.vec.ops.ArbitraryVectorFieldTransformer;
import org.wewi.medimg.math.vec.ops.ScaleVectorFunction;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class IrregularDisplacementField extends DisplacementField 
                                         implements InterpolateableTransformation, 
                                                     ArbitraryVectorField {
    private ArbitraryVectorField field;


    public IrregularDisplacementField() {
        field = new DoubleVectorField();
        setFieldInterpolator(new GlobalInterpolator(
                             new GlobalInterpolator.ExponentialWeightFunction(1)));
                             //new GlobalInterpolator.CityBlockWeightFunction(1)));
    }

    /**
     * @see org.wewi.medimg.image.geom.transform.Transformation#scale(double)
     */
    public Transformation scale(double alpha) {
        ScaleVectorFunction f = new ScaleVectorFunction(alpha);
        ArbitraryVectorFieldTransformer t = new ArbitraryVectorFieldTransformer(field, f);
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
        IrregularDisplacementField newfield = new IrregularDisplacementField();
        
        double[] start = new double[3];
        double[] end = new double[3];
        for (int i = 0, n = field.size(); i <n; ++i) {
        	field.getVector(i, start, end);
        	getFieldInterpolator().interpolateStartPoint(start, end);
        	newfield.addVector(end, start);
        }
        
        return newfield;
    }

    public void addVector(double[] startPoint, double[] endPoint) {
        field.addVector(startPoint, endPoint);
    }
    
    public void clean(double epsilon) {
        double[] p = new double[3];
        double[] q = new double[3];
        double d;
        int removedVectors = 0;
        for (int i = 0, n = field.size(); i <n; ++i) {
            field.getVector(i - removedVectors, p, q);
            d = Math.sqrt(MathUtil.sqr(p[0] - q[0]) + 
                      MathUtil.sqr(p[1] - q[1]) +
                      MathUtil.sqr(p[2] - q[2]));
            if (d < epsilon) {
                field.removeVector(i - removedVectors);
                removedVectors++;
            }
            //////////////////
            
			if (d > 5.0) {
				field.removeVector(i - removedVectors);
				removedVectors++;
			}            
            
            ///////////////////
            
        }
    }

    public VectorIterator getVectorIterator() {
        return field.getVectorIterator();
    }


	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#addVector(int, double[], double[])
	 */
	public void addVector(int index, double[] startPoint, double[] endPoint) {
        field.addVector(index, startPoint, endPoint);
	}

	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#changeVector(int, double[], double[])
	 */
	public void changeVector(int index, double[] startPoint, double[] endPoint) {
        field.changeVector(index, startPoint, endPoint);
	}

	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#getVector(int, double[], double[])
	 */
	public void getVector(int index, double[] startPoint, double[] endPoint) {
        field.getVector(index, startPoint, endPoint);
	}

	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#removeVector(int)
	 */
	public void removeVector(int index) {
        field.removeVector(index);
	}

	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#size()
	 */
	public int size() {
		return field.size();
	}

	/**
	 * @see org.wewi.medimg.image.geom.transform.InterpolateableTransformation#interpolate(InterpolateableTransformation, double)
	 */
	public InterpolateableTransformation interpolate(InterpolateableTransformation trans, double w) {
		IrregularDisplacementField t = (IrregularDisplacementField)trans;
        scale(1 - w);
        double[] start = new double[3];
        double[] end = new double[3];
        
        for (int i = 0, n = t.size(); i < n; i++) {
            t.getVector(i, start, end);
            field.addVector(start, end);
        }
        
        return this;
	}


























}
