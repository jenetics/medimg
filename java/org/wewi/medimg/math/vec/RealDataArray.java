/**
 * Created on 19.11.2002 21:40:19
 *
 */
package org.wewi.medimg.math.vec;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
interface RealDataArray {
    
    public void get(int x, int y, int z, double[] value);
    
    public void set(int x, int y, int z, double[] value);
    
    public void fill(double[] value);
    
    public void copy(RealDataArray target);
}
