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
 * BMPWriter.java
 *
 * Created on 14. Januar 2002, 11:59
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.io.OutputStream;

import org.wewi.medimg.image.Image;

import com.sun.media.jai.codec.BMPEncodeParam;
import com.sun.media.jai.codec.ImageCodec;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class BMPWriter extends JAIImageWriter {

    public BMPWriter(Image image, String target) {
        super(image, target);
        init();    
    }

    public BMPWriter(Image image, File destination) {
        super(image, destination);
        init();
    }
    
    
    private void init() {
        BMPEncodeParam param = new BMPEncodeParam();
        param.setVersion(BMPEncodeParam.VERSION_3);
        encodeParameter = param;
        imageExtention = ".bmp";        
    }
    
    
    protected void initEncoder(OutputStream out) {
        encoder = ImageCodec.createImageEncoder("bmp", out, encodeParameter);
    }
    
    public String toString() {
        return "BMPWriter: " + super.toString();    
    }    
    
}
