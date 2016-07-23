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
 * MutableNumber.java
 * Created on 28.04.2003
 *
 */
package org.wewi.medimg.math;


import org.wewi.medimg.util.Mutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class MutableNumber extends Number 
                                    implements Comparable,
                                               Mutable, 
                                               Cloneable {

    public void setValue(byte n) {
        setValue((int)n);
    }
     
    public void setValue(short n) {
        setValue((int)n);
    }

    public abstract void setValue(int n);
    
    public abstract void setValue(long n);

    public abstract void setValue(float n);
    
    public abstract void setValue(double n);
    
    public abstract void setValue(Number n);

    public abstract Object clone();
}
