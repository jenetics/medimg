/**
 * Created on 22.10.2002 00:03:26
 *
 */
package org.wewi.medimg.image;

import java.util.Arrays;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class ByteData implements ImageData {
    private byte[] data;

    /**
     * Constructor for ByteData.
     */
    public ByteData(int size) {
        data = new byte[size];
    }
    
    public byte getByteValue(int pos) {
        return data[pos];
    }
    
    public short getShortValue(int pos) {
        return data[pos];
    }

    public int getIntValue(int pos) {
        return data[pos];
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
        data[pos] = (byte)value;
    }

    public void setValue(int pos, float value) {
        data[pos] = (byte)value;
    }

    public void setValue(int pos, int value) {
        data[pos] = (byte)value;
    }

    public void setValue(int pos, long value) {
        data[pos] = (byte)value;
    }

    public void setValue(int pos, short value) {
        data[pos] = (byte)value;
    }

    public void fill(byte value) {
        Arrays.fill(data, value);
    }

    public void fill(double value) {
        Arrays.fill(data, (byte)value);
    }

    public void fill(float value) {
        Arrays.fill(data, (byte)value);
    }

    public void fill(int value) {
        Arrays.fill(data, (byte)value);
    }

    public void fill(long value) {
        Arrays.fill(data, (byte)value);
    }

    public void fill(short value) {
        Arrays.fill(data, (byte)value);
    }


    public void copy(ImageData target) {
        if (!(target instanceof ByteData)) {
            return;    
        }
        
        ByteData t = (ByteData)target;
        System.arraycopy(data, 0, t.data, 0, t.data.length);        
    }

}
