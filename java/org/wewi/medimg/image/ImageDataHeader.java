/*
 * ImageDataHeader.java
 *
 * Created on 22. Februar 2002, 10:42
 */

package org.wewi.medimg.image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


/**
 *
 * @author  Franz Wilhelmsötter
 * @version 0.1
 */
class ImageDataHeader implements ImageHeader {
    private int minX, minY, minZ;
    private int maxX, maxY, maxZ;
    private Dimension dim;
    private ImageData image;

    static final int HEADER_SIZE = 24;

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
    
    public void read(DataInputStream in) throws IOException {
        minX = in.readInt();
        minY = in.readInt();
        minZ = in.readInt();
        maxX = in.readInt();
        maxY = in.readInt();
        maxZ = in.readInt();
        image.init(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    public boolean isNull() {
        return false;
    }
    
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(minX);
        out.writeInt(minY);
        out.writeInt(minZ);
        out.writeInt(maxX);
        out.writeInt(maxY);
        out.writeInt(maxZ);
    }
    
    public Dimension readDimension(DataInputStream in) throws IOException {
        return dim;    
    }
    
}
