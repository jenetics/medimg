/*
 * NullImageHeader.java
 *
 * Created on 22. Februar 2002, 09:27
 */

package org.wewi.medimg.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.wewi.medimg.util.Nullable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class NullImageHeader implements ImageHeader, Nullable {
    private static NullImageHeader singleton = null;
    
    
    public NullImageHeader() {
    }
    
    public Dimension readDimension(InputStream in) throws IOException {
        return new Dimension(0, 0, 0);
    }
    
    public boolean isNull() {
        return true;
    }
    
	/**
	 * @see org.wewi.medimg.image.ImageHeader#read(InputStream)
	 */
	public void read(InputStream in) throws IOException {
	}

	/**
	 * @see org.wewi.medimg.image.ImageHeader#write(OutputStream)
	 */
	public void write(OutputStream out) throws IOException {
	}


	/**
	 * @see org.wewi.medimg.image.ImageHeader#getImageProperties()
	 */
	public Properties getImageProperties() {
		return new Properties();
	}

	/**
	 * @see org.wewi.medimg.image.ImageHeader#setImageProperties(Properties)
	 */
	public void setImageProperties(Properties prop) {
	}

}
