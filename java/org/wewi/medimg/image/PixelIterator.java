/**
 * PixelIterator.java
 * Created on 14.06.2003
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface PixelIterator {
    
    public boolean hasNext();
    
    public byte getByteColor();
    
    public short getShortColor();
    
    public int getIntColor();
    
    public long getLongColor();
    
    public float getFloatColor();
    
    public double getDoubleColor();
    
}
