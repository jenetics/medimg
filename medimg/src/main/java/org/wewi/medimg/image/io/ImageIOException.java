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
 * ImageIOException.java
 *
 * Created on 25. Februar 2002, 23:05
 */

package org.wewi.medimg.image.io;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageIOException extends Exception {

    /**
     * Creates new <code>ImageIOException</code> without detail message.
     */
    public ImageIOException() {
    }


    /**
     * Constructs an <code>ImageIOException</code> with the specified detail message.
     * 
     * @param msg the detail message.
     */
    public ImageIOException(String msg) {
        super(msg);
    }
    
    public ImageIOException(Throwable cause) {
        super(cause);
    }
    
    public ImageIOException(String msg, Throwable cause) {
        super(msg, cause);
    }
}


