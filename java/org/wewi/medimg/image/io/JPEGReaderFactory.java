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
public class JPEGReaderFactory implements ImageReaderFactory {
    private Range range;

    public JPEGReaderFactory() {
        range = new Range(0, Integer.MAX_VALUE, 1);
    }
    
    public ImageReader createImageReader(ImageFactory imageFactory, File file) {
        ImageReader reader = new JPEGReader(imageFactory, file, range);
        return reader;
    }
    
    public void setRange(Range range) {
        this.range = range;
    }

}
