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
 * CompareableInteger.java
 *
 * Created on 21. Februar 2002, 22:47
 */

package org.wewi.medimg.image.geom;


/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
final class ComparableInteger extends Number implements Comparable {
    private final int value;
    
    public ComparableInteger(int value) {
        this.value = value;
    }

    public int compareTo(Object obj) {
        ComparableInteger integer = (ComparableInteger)obj;
        return (value - integer.value);
    }
    
    /**
     * @see java.lang.Number#doubleValue()
     */
    public double doubleValue() {
        return (double)value;
    }

    /**
     * @see java.lang.Number#floatValue()
     */
    public float floatValue() {
        return (float)value;
    }

    /**
     * @see java.lang.Number#intValue()
     */
    public int intValue() {
        return value;
    }

    /**
     * @see java.lang.Number#longValue()
     */
    public long longValue() {
        return (long)value;
    }

}
