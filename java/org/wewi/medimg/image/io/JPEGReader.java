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

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class JPEGReader extends JAIImageReader {

    /**
     * Constructor for JPEGReader.
     * @param imageFactory
     * @param source
     */
    public JPEGReader(ImageFactory imageFactory, String source) {
        super(imageFactory, source);
        init();
    }

    /**
     * Constructor for JPEGReader.
     * @param imageFactory
     * @param source
     */
    public JPEGReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        init();
    }

    /**
     * Constructor for JPEGReader.
     * @param imageFactory
     * @param source
     * @param range
     */
    public JPEGReader(ImageFactory imageFactory, File source, Range range) {
        super(imageFactory, source, range);
        init();
    }

    /**
     * Constructor for JPEGReader.
     * @param imageFactory
     * @param source
     * @param range
     */
    public JPEGReader(ImageFactory imageFactory, String source, Range range) {
        super(imageFactory, source, range);
        init();
    }
    
    private void init() {
        fileFilter = new FileExtentionFilter(".jpg");  
    } 
    
    public String toString() {
        return "JPEGReader: " + super.toString();    
    }       

}
