/*
 * VoxelIterator.java
 *
 * Created on 8. Mai 2002, 15:31
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface VoxelIterator extends Cloneable {
    public boolean hasNext();
    
    public int next();
    
    public int next(int[] p);
    
    public int size();
    
    public Object clone();
}
