/**
 * Created on 19.11.2002 16:30:50
 *
 */
package org.wewi.medimg.image.geom;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface VectorIterator {
    
    public boolean hasNext();
    
    public void next(double[] start, double[] end);
    
    public void next(float[] start, float[] end);
    
}
