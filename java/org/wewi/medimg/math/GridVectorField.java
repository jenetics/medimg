/**
 * Created on 19.11.2002 21:06:00
 *
 */
package org.wewi.medimg.math;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface GridVectorField extends VectorField, Cloneable {
    
    public void setGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint);
    
    public void getGridStartPoint(int gridX, int gridY, int gridZ, double[] startPoint);
    
    public void getGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint);
    
    public void getVector(int gridX, int gridY, int gridZ, double[] vector);
    
    public void setVector(int gridX, int gridY, int gridZ, double[] vector);
}
