/*
 * ImageWriter.java
 *
 * Created on 5. Dezember 2001, 16:16
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.GreyRGBConversion;
import org.wewi.medimg.image.Image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public abstract class ImageWriter {
    protected Image image;
    protected File target;
    protected String imageExtention = "";
    protected ColorConversion colorConversion;
    
    ImageWriter() {
        colorConversion = new GreyRGBConversion();
    }
    
    public ImageWriter(Image image, File target) {
        this.image = image;
        this.target = target;
        colorConversion = new GreyRGBConversion();
    }
    
    File getTarget() {
        return target;
    }
    
    public void setColorConversion(ColorConversion cc) {
        colorConversion = cc;
    }
    
    public ColorConversion getColorConversion() {
        return colorConversion;
    }
    
    public abstract void write() throws ImageIOException;   
}

