/**
 * LineIterator.java
 * 
 * Created on 04.03.2003, 21:26:06
 *
 */
package org.wewi.medimg.viewer.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface LineIterator {
    
    public boolean hasNext();
    
    public void next(int[] begin, int[] end);
}
