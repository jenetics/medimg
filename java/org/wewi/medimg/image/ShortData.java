/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * Created on 22.10.2002 00:03:03
 *
 */
package org.wewi.medimg.image;

import java.util.Arrays;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class ShortData implements ImageData {
    private short[] data;

    /**
     * Constructor for ByteData.
     */
    public ShortData(int size) {
        data = new short[size];
    }
    
    public byte getByteValue(int pos) {
        return (byte)data[pos];
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
        data[pos] = (short)value;
    }

    public void setValue(int pos, float value) {
        data[pos] = (short)value;
    }

    public void setValue(int pos, int value) {
        data[pos] = (short)value;
    }

    public void setValue(int pos, long value) {
        data[pos] = (short)value;
    }

    public void setValue(int pos, short value) {
        data[pos] = (short)value;
    }

    public void fill(byte value) {
        Arrays.fill(data, value);
    }

    public void fill(double value) {
        Arrays.fill(data, (short)value);
    }

    public void fill(float value) {
        Arrays.fill(data, (short)value);
    }

    public void fill(int value) {
        Arrays.fill(data, (short)value);
    }

    public void fill(long value) {
        Arrays.fill(data, (short)value);
    }

    public void fill(short value) {
        Arrays.fill(data, value);
    }


    public void copy(ImageData target) {
        if (!(target instanceof ShortData)) {
            return;    
        }
        
        ShortData t = (ShortData)target;
        System.arraycopy(data, 0, t.data, 0, t.data.length);        
    }

}
