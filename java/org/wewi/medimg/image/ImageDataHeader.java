/*
 * ImageDataHeader.java
 *
 * Created on 22. Februar 2002, 10:42
 */

package org.wewi.medimg.image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;


/**
 *
 * @author  Franz Wilhelmsötter
 * @version 0.1
 */
class ImageDataHeader implements ImageHeader {
    private int maxX;
    private int maxY;
    private int maxZ;
    private ImageData image;

    public ImageDataHeader(int x, int y, int z, ImageData image) {
        maxX = x;
        maxY = y;
        maxZ = z;
        this.image = image;
    }
    
    public void read(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        maxX = din.readInt();
        maxY = din.readInt();
        maxZ = din.readInt();
        image.init(maxX, maxY, maxZ);
    }
    
    public boolean isNull() {
        return false;
    }
    
    public void write(OutputStream out) throws IOException {
        DataOutputStream dout = new DataOutputStream(out);
        dout.writeInt(maxX);
        dout.writeInt(maxY);
        dout.writeInt(maxZ);
    }
    
}
