/*
 * DataPoint.java
 *
 * Created on 5. Februar 2002, 13:45
 */

package org.wewi.medimg.seg.kmeans;



/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface DataPoint extends Cloneable {
    
    public DataPoint add(DataPoint p);
    
    public DataPoint sub(DataPoint p);
    
    public DataPoint div(double d);
    
    public double distance(DataPoint point);
    
    public double norm();
    
    public DataPoint getNullInstance();
    
    public DataPoint getOneInstance();
    
    public int getDim();
    
    public Number getOrdinateNumber(int dim);
    
    public boolean equals(Object obj);
    
    public Object clone();
    
    public String toString();
    
    public String toMathematicaString();
}
