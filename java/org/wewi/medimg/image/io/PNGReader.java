package org.wewi.medimg.image.io;

import java.io.File;

import org.wewi.medimg.image.ImageFactory;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PNGReader extends JAIImageReader {

    /**
     * Constructor for PNGReader.
     * @param imageFactory
     * @param source
     */
    public PNGReader(ImageFactory imageFactory, String source) {
        super(imageFactory, source);
        init();
    }

    /**
     * Constructor for PNGReader.
     * @param imageFactory
     * @param source
     */
    public PNGReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
        init();
    }

    /**
     * Constructor for PNGReader.
     * @param imageFactory
     * @param source
     * @param range
     */
    public PNGReader(ImageFactory imageFactory, File source, Range range) {
        super(imageFactory, source, range);
        init();
    }

    /**
     * Constructor for PNGReader.
     * @param imageFactory
     * @param source
     * @param range
     */
    public PNGReader(ImageFactory imageFactory, String source, Range range) {
        super(imageFactory, source, range);
        init();
    }
    
    private void init() {
        fileFilter = new FileExtentionFilter(".png");  
    } 
    
    public String toString() {
        return "PNGReader: " + super.toString();    
    }     

}
