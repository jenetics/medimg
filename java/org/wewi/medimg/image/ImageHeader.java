/* 
 * ImageHeader.java, created on 22. Februar 2002, 00:12
 * 
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


package org.wewi.medimg.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.wewi.medimg.util.Nullable;

/**
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageHeader extends Nullable {
    
    /**
     * The implementation of this method has to read the Header from an
     * InputStream AND initialize the coresponding image with the rigth size.
     * 
     * @param in header InputStream
     * 
     * @throws IOException if the header cannot be read.
     */
    public void read(InputStream in) throws IOException;

    public void write(OutputStream out) throws IOException;
    
    public void setImageProperties(ImageProperties properties);
   
    public ImageProperties getImageProperties();
}
