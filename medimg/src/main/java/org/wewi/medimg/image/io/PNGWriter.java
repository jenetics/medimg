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
import java.io.OutputStream;

import org.wewi.medimg.image.Image;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.PNGEncodeParam;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PNGWriter extends JAIImageWriter {

    /**
     * Constructor for PNGWriter.
     * @param image
     * @param target
     */
    public PNGWriter(Image image, File target) {
        super(image, target);
        init();
    }

    /**
     * Constructor for PNGWriter.
     * @param image
     * @param target
     */
    public PNGWriter(Image image, String target) {
        super(image, target);
        init();
    }

    private void init() {
        PNGEncodeParam param = new PNGEncodeParam.RGB();
        encodeParameter = param;
        imageExtention = ".png";        
    }
    
    
    protected void initEncoder(OutputStream out) {
        encoder = ImageCodec.createImageEncoder("png", out, encodeParameter);
    }
    
    public String toString() {
        return "PNGWriter: " + super.toString();    
    } 

}
