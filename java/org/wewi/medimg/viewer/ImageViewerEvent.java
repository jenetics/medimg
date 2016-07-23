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
 * ImageViewerEvent.java
 *
 * Created on March 28, 2002, 11:41 AM
 */

package org.wewi.medimg.viewer;

import java.util.EventObject;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageViewerEvent extends EventObject {
    private int slice;
    private boolean closed;
    
    /** Creates a new instance of ImageViewerEvent */
    public ImageViewerEvent(Object source, int slice, boolean closed) {
        super(source);
        this.slice = slice;
        this.closed = closed;
    }
    
    public int getSlice() {
        return slice;    
    }
    
    public boolean isClosed() {
        return closed;    
    }
}
