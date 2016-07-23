/* 
 * ComplexImage.java, created on 12.12.2002, 09:49:36
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

import org.wewi.medimg.math.Complex;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ComplexImage extends RowMajorImageGeometry implements Cloneable {
    private Complex[] data;

    
    public ComplexImage(int sizeX, int sizeY, int sizeZ) {
        this(new Dimension(sizeX, sizeY, sizeZ));    
    }

    /**
     * Constructor for ComplexImage.
     * @param dimension
     */
    public ComplexImage(Dimension dimension) {
        super(dimension); 
        data = new Complex[size];
        resetImage(new Complex(0, 0));
    }

    public void setColor(int x, int y, int z, Complex zz) {
        data[getPosition(x, y, z)] = zz;
    }

    public void setColor(int pos, Complex z) {
        data[pos] = z;
    }

    public void resetImage(Complex z) {
        for (int i = 0, n = getNVoxels(); i < n; i++) {
            setColor(i, z);    
        }
    }

    public Complex getColor(int pos) {
        return data[pos];
    }

    public Complex getColor(int x, int y, int z) {
        return data[getPosition(x, y, z)];
    }

    public boolean isNull() {
        return false;
    }
    
    public Object clone() {
        ComplexImage img = new ComplexImage(dimension);
        for (int i = 0, n = getNVoxels(); i < n; i++) {
            img.data[i] = data[i];    
        }
        return img;    
    }

}
