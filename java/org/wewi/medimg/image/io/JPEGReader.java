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

}
