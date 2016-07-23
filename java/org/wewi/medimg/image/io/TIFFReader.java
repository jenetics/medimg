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
 * TIFFReader.java
 *
 * Created on 14. Januar 2002, 10:23
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 *
 * @author  Franz Wilehlmstötter
 * @version 0.1
 */
public final class TIFFReader extends JAIImageReader {

    public TIFFReader(ImageFactory imageFactory, String source) {
        super(imageFactory, source);
        init();    
    }

    public TIFFReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        init();
    }
    
    public TIFFReader(ImageFactory imageFactory, File source, Range range) {
        super(imageFactory, source, range);   
        init(); 
    }
    
    public TIFFReader(ImageFactory imageFactory, String source, Range range) {
        super(imageFactory, source, range);  
        init();  
    }
    
    private void init() {
        fileFilter = new FileExtentionFilter(".tif");   
    }
    
    public String toString() {
        return "TIFFReader: " + super.toString();    
    }
    
}
