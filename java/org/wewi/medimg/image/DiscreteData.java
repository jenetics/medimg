/**
 * Created on 21.10.2002 23:12:00
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
interface DiscreteData {
    
    public int get(int pos);
    
    public void set(int pos, int value);
    
    public void fill(int value);
    
    public void copy(DiscreteData target);
}
