package org.wewi.medimg.math.vec;

/**
 * @author Werner Weiser
 * @author Franz Wilhelmstötter
 *
 */
public interface ArbitraryVectorField extends VectorField {
	
	public void addVector(int index, double[] startPoint, double[] endPoint);
	
	public void changeVector(int index, double[] startPoint, double[] endPoint);
	
	public void removeVector(int index);
	
	public void getVector(int index, double[] startPoint, double[] endPoint);
	
	public int size();
	
	
}
