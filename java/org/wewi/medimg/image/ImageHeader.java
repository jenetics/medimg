/**
 * ImageHeader.java
 *
 * Created on 22. Februar 2002, 00:12
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
 * @version 0.2
 */
public interface ImageHeader extends Nullable {
    
    /**
     * Die Implementierung dieser Methode muß den
     * Header vom InputStream lesen UND das zugehörige
     * Image mit der richtigen Größe neu initialisieren.
     * 
     * @param in InputStream aus dem der ImageHeader
     *             erzeugt wird.
     * 
     * @throws IOException wenn der Header nicht gelesen werden kann.
     */
    public void read(InputStream in) throws IOException;

    public void write(OutputStream out) throws IOException;
    
    public void setImageProperties(Properties prop);
    
    public void addImageProperties(Properties prop);
   
    public Properties getImageProperties();
}
