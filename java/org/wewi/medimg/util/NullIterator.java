/**
 * NullIterator.java
 *
 * Created on 10. Juni 2002, 17:40
 */

package org.wewi.medimg.util;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class NullIterator implements Iterator {
    
    /** Creates a new instance of NullIterator */
    public NullIterator() {
    }
    
    public boolean hasNext() {
        return false;
    }
    
    public Object next() {
        return null;
    }
    
    public void remove() {
    }
    
}
