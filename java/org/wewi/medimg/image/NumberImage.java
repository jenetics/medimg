/* 
 * NumberImage.java, created on 28.04.2003
 * 
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

package org.wewi.medimg.image;

import org.wewi.medimg.math.MutableDouble;
import org.wewi.medimg.math.MutableInteger;
import org.wewi.medimg.math.MutableNumber;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class NumberImage extends RowMajorImageGeometry {
    
    public static final class NumberType {
        private int type;
        
        private NumberType(int t) {
            type = t;
        }
        
        private MutableNumber create() {
            switch (type) {
                case 0:
                    //return new MutableByte();
                case 1:
                    //return new MutableShort();
                case 2:
                    return new MutableInteger();
                case 3:
                   //return new MutableLong();
                case 4:
                    //return new MutableFloat();
                case 5:
                    return new MutableDouble();
                default :
                    return new MutableInteger();
            }
        }
    }
    
    
    public static final NumberType BYTE = new NumberType(0);
    public static final NumberType SHORT = new NumberType(1);
    public static final NumberType INTEGER = new NumberType(2);
    public static final NumberType LONG = new NumberType(3);
    public static final NumberType FLOAT = new NumberType(4);
    public static final NumberType DOUBLE = new NumberType(5);
    
    
    private MutableNumber[] data;
    private NumberType numberType = INTEGER;

    public NumberImage(int sizeX, int sizeY, int sizeZ) {
        this(new Dimension(sizeX, sizeY, sizeZ));    
    }
    
    public NumberImage(int sizeX, int sizeY, int sizeZ, NumberType type) {
        this(new Dimension(sizeX, sizeY, sizeZ), type);   
    }

    /**
     * Constructor for ComplexImage.
     * @param dimension
     */
    public NumberImage(Dimension dimension) {
        super(dimension); 
        data = new MutableNumber[size];
        
        for (int i = 0, m = getNVoxels(); i < m; i++) {
            data[i] = numberType.create();        
        }
    }
    
    public NumberImage(Dimension dimension, NumberType type) {
        super(dimension); 
        data = new MutableNumber[size];
        
        numberType = type;
        
        for (int i = 0, m = getNVoxels(); i < m; i++) {
            data[i] = numberType.create();        
        }
    }

    public void setColor(int x, int y, int z, byte n) {
        data[getPosition(x, y, z)].setValue(n);
    }
    
    public void setColor(int x, int y, int z, short n) {
        data[getPosition(x, y, z)].setValue(n);
    }
    
    public void setColor(int x, int y, int z, int n) {
        data[getPosition(x, y, z)].setValue(n);
    }
    
    public void setColor(int x, int y, int z, long n) {
        data[getPosition(x, y, z)].setValue(n);
    }
    
    public void setColor(int x, int y, int z, float n) {
        data[getPosition(x, y, z)].setValue(n);
    }
    
    public void setColor(int x, int y, int z, double n) {
        data[getPosition(x, y, z)].setValue(n);
    }

    public void setColor(int pos, byte n) {
        data[pos].setValue(n);
    }
    
    public void setColor(int pos, short n) {
        data[pos].setValue(n);
    }
    
    public void setColor(int pos, int n) {
        data[pos].setValue(n);
    }
    
    public void setColor(int pos, long n) {
        data[pos].setValue(n);
    }
    
    public void setColor(int pos, float n) {
        data[pos].setValue(n);
    }
    
    public void setColor(int pos, double n) {
        data[pos].setValue(n);
    }

    public void resetImage(byte n) {
        for (int i = 0, m = getNVoxels(); i < m; i++) {
            setColor(i, n);    
        }
    }
    
    public void resetImage(short n) {
        for (int i = 0, m = getNVoxels(); i < m; i++) {
            setColor(i, n);    
        }
    }
    
    public void resetImage(int n) {
        for (int i = 0, m = getNVoxels(); i < m; i++) {
            setColor(i, n);    
        }
    }
    
    public void resetImage(long n) {
        for (int i = 0, m = getNVoxels(); i < m; i++) {
            setColor(i, n);    
        }
    }
    
    public void resetImage(float n) {
        for (int i = 0, m = getNVoxels(); i < m; i++) {
            setColor(i, n);    
        }
    }
    
    public void resetImage(double n) {
        for (int i = 0, m = getNVoxels(); i < m; i++) {
            setColor(i, n);    
        }
    }

    public MutableNumber getColor(int pos) {
        return data[pos];
    }

    public MutableNumber getColor(int x, int y, int z) {
        return data[getPosition(x, y, z)];
    }
    
    public NumberType getNumberType() {
        return numberType;
    }

    public boolean isNull() {
        return false;
    }
    
    public Object clone() {
        NumberImage img = new NumberImage(dimension);
        
        for (int i = 0, n = getNVoxels(); i < n; i++) {
            img.data[i].setValue(data[i]);    
        }
        
        return img;    
    }
    
}











