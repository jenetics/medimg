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
 * Created on 08.10.2002 11:01:25
 *
 */
package org.wewi.medimg.util.param;


import org.wewi.medimg.image.ImageFactory;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageParameterIterator implements ParameterIterator {
    private String name;
    private ImageFactory factory;
    private Class readerClass;
    private String[] file;
    
    private int pos;

    /**
     * Constructor for ImageParameterIterator.
     */
    public ImageParameterIterator(String name, ImageFactory factory, Class reader, String[] file) {
        super();
        this.name = name;
        this.factory = factory;
        this.readerClass = reader;
        this.file = file;
        
        pos = 0;
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone()  {
        return new ImageParameterIterator(name, factory, readerClass, file);
    }

    /**
     * @see java.util.Iterator#hasNext() 
     */
    public boolean hasNext() {
        return pos < file.length;
    }

    /**
     * @see java.util.Iterator#next()
     */ 
    public Object next() {
        return new ImageParameter(name, factory, readerClass, file[pos++]);
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
    }

}
