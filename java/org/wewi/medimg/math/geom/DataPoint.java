/**
 * DataPoint.java
 *
 * Created on 5. Februar 2002, 13:45
 */

package org.wewi.medimg.math.geom;

import org.wewi.medimg.util.MathematicaStringable;



/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface DataPoint extends Cloneable, MathematicaStringable {
    
    public DataPoint add(DataPoint p);
    
    public DataPoint sub(DataPoint p);
    
    public DataPoint scale(double d);
    
    public double distance(DataPoint point);
    
    public double norm();
    
    public DataPoint getNullInstance();
    
    public DataPoint getOneInstance();
    
    public int getDimension();
    
    public Number getOrdinateNumber(int dim);
    
    public boolean equals(Object obj);
    
    public Object clone();
    
    public String toString();
}
