/**
 * Created on 21.10.2002 23:12:00
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
interface ImageData {
    
    public byte getByteValue(int pos);
    
    public short getShortValue(int pos);
    
    public int getIntValue(int pos);
    
    public long getLongValue(int pos);
    
    public float  getFloatValue(int pos);
    
    public double getDoubleValue(int pos);
    
    public void setValue(int pos, byte value);
    
    public void setValue(int pos, short value);
    
    public void setValue(int pos, int value);
    
    public void setValue(int pos, long value);
    
    public void setValue(int pos, float value);
    
    public void setValue(int pos, double value);
    
    public void fill(byte value);
    
    public void fill(short value);
    
    public void fill(int value);
    
    public void fill(long value);
    
    public void fill(float value);
    
    public void fill(double value);
    
    public void copy(ImageData target);
}
