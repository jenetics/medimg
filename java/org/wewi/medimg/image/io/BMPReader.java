/**
 * BMPReader.java
 *
 * Created on 11. Januar 2002, 14:53
 */

package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class BMPReader extends JAIImageReader {
    
    public BMPReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        init();
    }
    
    public BMPReader(ImageFactory imageFactory, String source) {
        super(imageFactory, source);
        init();    
    }
    
    public BMPReader(ImageFactory imageFactory, File source, Range range) {
        super(imageFactory, source, range);
        init();        
    }
    
    public BMPReader(ImageFactory imageFactory, String source, Range range) {
        super(imageFactory, source, range);
        init();    
    }
    

    private void init() {
        fileFilter = new FileExtentionFilter(".bmp");  
    } 
    
    public String toString() {
        return "BMPReader: " + super.toString();    
    }      
}
