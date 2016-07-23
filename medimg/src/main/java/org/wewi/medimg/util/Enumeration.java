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
 * Enumeration.java
 *
 * Created on 11. Jänner 2002, 19:38
 */

package org.wewi.medimg.util;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class Enumeration {
    protected int type;

    protected Enumeration(int t) {
        type = (int)Math.rint(Math.pow(2, t));
    }
    
    protected Enumeration(Enumeration enum1, Enumeration enum2) throws IllegalArgumentException {
        if (enum1 == null || enum2 == null) {
            throw new IllegalArgumentException("Enumeration one or two are null");
        }
        
        if (enum1.equals(enum2)) {
            type = enum1.type;
        } else {
            type = enum1.type + enum2.type;
        }
    }
    
    public int hashCode() {
        int result = 17;
        result = 37*result + type;
        result = 37*result + super.hashCode();
        return result;
    }
    
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(this.getClass().isInstance(obj))) {
            return false;
        }
        
        return type == ((Enumeration)obj).type;
    }
}



