/*
 * RawImageReader.java
 *
 * Created on 25. Januar 2002, 13:32
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.NullImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.DataInputStream;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class RawImageDataReader extends ImageReader {
    private Image image;
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;

    public RawImageDataReader(File source) {
        this.source = source;
    }
    
    public Image getImage() {
        return image;
    }
    
    private void readHeader(DataInputStream in) throws IOException {
        minX = in.readInt();
        minY = in.readInt();
        minZ = in.readInt();
        maxX = in.readInt();
        maxY = in.readInt();
        maxZ = in.readInt();
    }
    
    public void read() throws ImageIOException {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(source));
            readHeader(in);
            int sizeX = maxX - minX + 1;
            int sizeY = maxY - minY + 1;
            int sizeZ = maxZ - minZ + 1;
            image = new ImageData(sizeX, sizeY, sizeZ);
            int size = image.getNVoxels();
            for (int i = 0; i < size; i++) {
                image.setColor(i, in.readShort());
            }
            in.close();
        } catch (IOException ioe) {
            System.err.println("RawImageReader.read: ");
            image = new NullImage();
            throw new ImageIOException("Can't read Image: " + ioe);
        }
    }
    
    
    
    
    /*
    public static void main(String[] args) {
        RawImageReader reader = new RawImageReader(new File("C:/Temp/viewer/3dhead"), 256, 256, 109);
        reader.read();
        ImageData image = (ImageData)reader.getImage();
        
        TIFFWriter writer = new TIFFWriter(image, new File("C:/temp/3dhead"));
        writer.write();
        
    }
    */
}
