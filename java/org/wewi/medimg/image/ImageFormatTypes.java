/*
 * ImageFormatEnum.java
 *
 * Created on 23. Januar 2002, 16:43
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Enumeration;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class ImageFormatTypes extends Enumeration {
    public static final ImageFormatTypes TIFF_IMAGES = new ImageFormatTypes("TIFF Images");
    public static final ImageFormatTypes BMP_IMAGES = new ImageFormatTypes("BMP Images");
    public static final ImageFormatTypes RAW_IMAGE = new ImageFormatTypes("RAW Images");
    public static final ImageFormatTypes ZIPED_IMAGES = new ImageFormatTypes("(Ziped)");
    
    public static final ImageFormatTypes[] TYPES = {TIFF_IMAGES, BMP_IMAGES,
                                                    RAW_IMAGE, ZIPED_IMAGES};
    
    public final String name;
    
    private static int refCount = 0;  
    private ImageFormatTypes(String name) { 
        super(++refCount);
        this.name = name;
    }
    public ImageFormatTypes(ImageFormatTypes enum1, ImageFormatTypes enum2) throws IllegalArgumentException {
        super(enum1, enum2);
        name = enum1.toString() + "/" + enum2.toString();
    }
    

    public String toString() {
        return name;
    }

}
