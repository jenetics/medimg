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
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.math;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MutableDouble extends MutableNumber {
    private double value;
    
    public MutableDouble(double v) {
        value = v;
    }
    
    public MutableDouble() {
        value = 0;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(byte)
     */
    public void setValue(byte n) {
        value = n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(short)
     */
    public void setValue(short n) {
        value = n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(int)
     */
    public void setValue(int n) {
        value = n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(long)
     */
    public void setValue(long n) {
        value = n;
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(float)
     */
    public void setValue(float n) {
        value = n;        
    }

    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(double)
     */
    public void setValue(double n) {
        value = n;    
    }
    
    /**
     * @see org.wewi.medimg.math.MutableNumber#setValue(org.wewi.medimg.math.Number)
     */
    public void setValue(Number n) {
        value = n.doubleValue();
    }
    
    /**
     * @see java.lang.Number#intValue()
     */
    public int intValue() {
        return (int)Math.round(value);
    }

    /**
     * @see java.lang.Number#longValue()
     */
    public long longValue() {
        return Math.round(value);
    }

    /**
     * @see java.lang.Number#floatValue()
     */
    public float floatValue() {
        return (float)value;
    }

    /**
     * @see java.lang.Number#doubleValue()
     */
    public double doubleValue() {
        return value;
    }  
    
    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        MutableDouble mi = (MutableDouble)o;
        if (value > mi.value) {
            return 1;
        } else if (value < mi.value) {
            return -1;
        } else {
            return 0;
        }
    }  

    /**
     * @see org.wewi.medimg.math.MutableNumber#clone()
     */
    public Object clone() {
        return new MutableDouble(value);
    }

}
