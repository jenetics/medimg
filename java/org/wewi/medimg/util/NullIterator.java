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
 * NullIterator.java
 *
 * Created on 10. Juni 2002, 17:40
 */

package org.wewi.medimg.util;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class NullIterator implements Iterator {
    
    /** Creates a new instance of NullIterator */
    public NullIterator() {
    }
    
    public boolean hasNext() {
        return false;
    }
    
    public Object next() {
        return null;
    }
    
    public void remove() {
    }
    
}
