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
 * ImageFormatEnum.java
 *
 * Created on 23. Januar 2002, 16:43
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.util.Enumeration;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class ImageFormatEnum extends Enumeration {
    public static final ImageFormatEnum TIFF_IMAGES = new ImageFormatEnum("TIFF Images");
    public static final ImageFormatEnum BMP_IMAGES = new ImageFormatEnum("BMP Images");
    public static final ImageFormatEnum JPEG_IMAGES = new ImageFormatEnum("JPEG Images");
    public static final ImageFormatEnum PNG_IMAGES = new ImageFormatEnum("PNG Images");
    public static final ImageFormatEnum RAW_IMAGE = new ImageFormatEnum("RAW Images");
    
    public static final ImageFormatEnum[] TYPES = {TIFF_IMAGES, BMP_IMAGES,
                                                     JPEG_IMAGES, PNG_IMAGES, 
                                                     RAW_IMAGE};
    
    private final String name;
    
    private static int refCount = 0;  
    private ImageFormatEnum(String name) { 
        super(++refCount);
        this.name = name;
    }
    public ImageFormatEnum(ImageFormatEnum enum1, ImageFormatEnum enum2) throws IllegalArgumentException {
        super(enum1, enum2);
        name = enum1.toString() + "/" + enum2.toString();
    }
    

    public String toString() {
        return name;
    }

}
