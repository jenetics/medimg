/**
 * LongData.java
 * Created on 13.06.2003
 *
 */
package org.wewi.medimg.image;

import java.util.Arrays;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class LongData implements ImageData {
    private long[] data;

    /**
     * Constructor for ByteData.
     */
    public LongData(int size) {
        data = new long[size];
    }
    
    public byte getByteValue(int pos) {
        return (byte)data[pos];
    }
    
    public short getShortValue(int pos) {
        return (short)data[pos];
    }

    public int getIntValue(int pos) {
        return (int)data[pos];
    }
    
    public long getLongValue(int pos) {
        return data[pos];
    }
    
    public float getFloatValue(int pos) {
        return data[pos];
    }
    
    public double getDoubleValue(int pos) {
        return data[pos];
    }
    
    public void setValue(int pos, byte value) {
        data[pos] = value;
    }

    public void setValue(int pos, double value) {
        data[pos] = (long)value;
    }

    public void setValue(int pos, float value) {
        data[pos] = (long)value;
    }

    public void setValue(int pos, int value) {
        data[pos] = (long)value;
    }

    public void setValue(int pos, long value) {
        data[pos] = value;
    }

    public void setValue(int pos, short value) {
        data[pos] = (long)value;
    }

    public void fill(byte value) {
        Arrays.fill(data, (long)value);
    }

    public void fill(double value) {
        Arrays.fill(data, (long)value);
    }

    public void fill(float value) {
        Arrays.fill(data, (long)value);
    }

    public void fill(int value) {
        Arrays.fill(data, (long)value);
    }

    public void fill(long value) {
        Arrays.fill(data, (long)value);
    }

    public void fill(short value) {
        Arrays.fill(data, (long)value);
    }


    public void copy(ImageData target) {
        if (!(target instanceof LongData)) {
            return;    
        }
        
        LongData t = (LongData)target;
        System.arraycopy(data, 0, t.data, 0, t.data.length);        
    }
}
