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
 * RawImageReader.java
 *
 * Created on 25. Januar 2002, 13:32
 */

package org.wewi.medimg.image.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.wewi.medimg.image.ImageFactory;
import org.wewi.medimg.image.NullImage;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class RawImageReader extends ImageReader {

    public RawImageReader(ImageFactory imageFactory, File source) {
        super(imageFactory, source);
    }
    
    public RawImageReader(ImageFactory imageFactory, String source) {
        super(imageFactory, source);    
    }
    
    public RawImageReader(ImageFactory imageFactory, String source, Range range) {
        super(imageFactory, source, range);    
    }
    
    public RawImageReader(ImageFactory imageFactory, File source, Range range) {
        super(imageFactory, source, range);    
    }
    
    public void read() throws ImageIOException {
        FileInputStream fin = null;
        ZipInputStream zin = null;
        DataInputStream din = null;
        try {
            fin = new FileInputStream(source);
            zin = new ZipInputStream(fin); 
            din = new DataInputStream(zin);
            
            image = imageFactory.createImage(1, 1, 1);
            zin.getNextEntry();
            image.getHeader().read(din);
            din.close();
            
            //Der JDOM SAXInputter schließt leider den Stream nach dem Einleisen.
            //Ein weiteres öffnen ist daher notwendig.
            fin = new FileInputStream(source);
            zin = new ZipInputStream(fin); 
            din = new DataInputStream(zin);            
            
            zin.getNextEntry();
            zin.getNextEntry();
            int size = image.getNVoxels();
            int stride = size/100;
            for (int i = 0; i < size; i++) {
                if ((i % stride) == 0) {
                    notifyProgressListener(new ImageIOProgressEvent(this, 
                                                (double)i/(double)size, false));    
                }
                image.setColor(i, din.readInt());
            }
            din.close();            
        } catch (FileNotFoundException fnfe) {
            image = new NullImage();
            throw new ImageIOException("File not found: " + source.toString(), fnfe);
        } catch (IOException ioe) {
            image = new NullImage();
            throw new ImageIOException("Can't read Image: " + ioe, ioe);            
        } finally {
            notifyProgressListener(new ImageIOProgressEvent(this, 1, true));    
        }
        
    }
    
}
