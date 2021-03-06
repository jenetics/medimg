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
 * Created on 07.10.2002 18:10:17
 *
 */
package org.wewi.medimg.util.param;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class IntegerParameterIterator implements ParameterIterator {
    private int start;
    private int stop;
    private int stride; 
    private String name;
    
    private int current;   

    /**
     * Constructor for IntParameterIterator.
     */
    public IntegerParameterIterator(String name, int start, int stop, int stride) {
        this.start = start;
        this.stop = stop;
        this.stride = stride;
        this.name = name;
        
        current = start;
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return current <= stop;
    }

    /**
     * @see java.util.Iterator#next()
     */
    public Object next() {
        IntegerParameter value = new IntegerParameter(name, current);
        current += stride;
        return value;
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
    public Object clone() {
        return new IntegerParameterIterator(name, start, stop, stride);    
    }

}
