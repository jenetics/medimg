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
 * Created on 11.08.2002
 *
 */
package org.wewi.medimg.image;

import org.wewi.medimg.util.Immutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Dimension implements Cloneable, Immutable {  
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;
    private final int minX, maxX;
    private final int minY, maxY;
    private final int minZ, maxZ;
    
    /**
     * @todo Introduce variable stepsize (so far set the default value to one).
     */
    private final int step = 1;
    
    public Dimension(Dimension dim) {
        sizeX = dim.sizeX;
        sizeY = dim.sizeY;
        sizeZ = dim.sizeZ;
        minX = dim.minX;
        maxX = dim.maxX;
        minY = dim.minY;
        maxY = dim.maxY;
        minZ = dim.minZ;
        maxZ = dim.maxZ; 
    }
    
    public Dimension(int sizeX, int sizeY, int sizeZ) throws IllegalArgumentException {
        this(0, sizeX-1, 0, sizeY-1, 0, sizeZ-1);        
    }
    
    public Dimension(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) 
                                                        throws IllegalArgumentException {
        
        if (minX > maxX || 
            minY > maxY || 
            minZ > maxZ) {
            throw new IllegalArgumentException("One of the \"min\" argument is " +
                                               "bigger than the coresponding \"max\" argument \n" +
                                               "(" + minX + "," + maxX + ")\n" +
                                               "(" + minY + "," + maxY + ")\n" +
                                               "(" + minZ + "," + maxZ + ")");    
        }
                                                                    
        this.sizeX = maxX-minX+1;
        this.sizeY = maxY-minY+1;
        this.sizeZ = maxZ-minZ+1;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;        
    }
    
    /**
     * Returns the maxX.
     * @return int
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Returns the maxY.
     * @return int
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Returns the maxZ.
     * @return int
     */
    public int getMaxZ() {
        return maxZ;
    }
    
    /**
     * Returns the maximum extention in the direction <code>dim</code>
     * 
     * @param dim
     * @return int
     */
    public int getMax(int dim) throws IllegalArgumentException {
        switch (dim) {
            case 0: return maxX;
            case 1: return maxY;
            case 2: return maxZ;
            default: throw new IllegalArgumentException("Argument dim > 2");
        }
    }

    /**
     * Returns the minX.
     * @return int
     */
    public int getMinX() {
        return minX;
    }

    /**
     * Returns the minY.
     * @return int
     */
    public int getMinY() {
        return minY;
    }

    /**
     * Returns the minZ.
     * @return int
     */
    public int getMinZ() {
        return minZ;
    }
    
    /**
     * Returns the minimum extention in the given direction <code>dim</dim>.
     * 
     * @param dim
     * @return int
     */
    public int getMin(int dim) throws IllegalArgumentException {
        switch (dim) {
            case 0: return minX;
            case 1: return minY;
            case 2: return minZ;
            default: throw new IllegalArgumentException("Argument dim > 2");
        }
    }

    /**
     * Returns the sizeX.
     * @return int
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Returns the sizeY.
     * @return int
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Returns the sizeZ.
     * @return int
     */
    public int getSizeZ() {
        return sizeZ;
    }
    
    /**
     * Returns the size in the given direction <code>dim</dim>
     * 
     * @param dim
     * @return int
     */
    public int getSize(int dim) {
        switch (dim) {
            case 0: return sizeX;
            case 1: return sizeY;
            case 2: return sizeZ;
            default: return 0;
        }
    }
    
    public int getStep() {
        return step;
    }
    
    public int hashCode() {
        int result = 17;
        result = 37*result + minX;
        result = 37*result + maxX;
        result = 37*result + minY;
        result = 37*result + maxY;
        result = 37*result + minZ;
        result = 37*result + maxZ;
        return result;    
    }
    
    public String toString() {
        return "X:(" + minX + "," + maxX + ")\n" +
               "Y:(" + minY + "," + maxY + ")\n" +
               "Z:(" + minZ + "," + maxZ + ")";    
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dimension)) {
            return false;    
        }    
        
        Dimension dim = (Dimension)o;
        
        return minX == dim.minX && maxX == dim.maxX &&
               minY == dim.minY && maxY == dim.maxY &&
               minZ == dim.minZ && maxZ == dim.maxZ;
    }
    
    public Object clone() {
        return new Dimension(this);    
    }

}






