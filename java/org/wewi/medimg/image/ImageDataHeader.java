/*
 * ImageDataHeader.java
 *
 * Created on 22. Februar 2002, 10:42
 */

package org.wewi.medimg.image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 *
 * @author  Franz Wilhelmsötter
 * @version 0.1
 */
class ImageDataHeader implements ImageHeader {
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;
    private ImageData image;

    public ImageDataHeader(int minX, int minY, int minZ,
                           int maxX, int maxY, int maxZ, ImageData image) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        this.image = image;
    }
    
    public void read(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        minX = din.readInt();
        minY = din.readInt();
        minZ = din.readInt();
        maxX = din.readInt();
        maxY = din.readInt();
        maxZ = din.readInt();
        image.init(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public boolean isNull() {
        return false;
    }
    
    public void write(OutputStream out) throws IOException {
        DataOutputStream dout = new DataOutputStream(out);
        dout.writeInt(minX);
        dout.writeInt(minY);
        dout.writeInt(minZ);
        dout.writeInt(maxX);
        dout.writeInt(maxY);
        dout.writeInt(maxZ);
    }
    
}
