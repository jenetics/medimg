/*
 * FeatureDataHeader.java
 *
 * Created on 22. Februar 2002, 11:04
 */

package org.wewi.medimg.image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
class FeatureImageHeader implements ImageHeader {
    private int sizeX, sizeY, sizeZ;
    private int nfeatures;
    private double[] meanValues;
    private FeatureImage featureImage;

    /** Creates new FeatureDataHeader */
    public FeatureImageHeader(int sizeX, int sizeY, int sizeZ, int nf, double[] mv, FeatureImage fi) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        nfeatures = nf;
        meanValues = mv;
        featureImage = fi;
    }

    void setMeanValues(double[] mv) {
        meanValues = mv;
    }
    
    public void read(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        sizeX = din.readInt();
        sizeY = din.readInt();
        sizeZ = din.readInt();
        nfeatures = din.readInt();
        meanValues = new double[nfeatures];
        for (int i = 0; i < nfeatures; i++) {
            meanValues[i] = din.readDouble();
        }
        
        featureImage.init(sizeX, sizeY, sizeZ, nfeatures);
        featureImage.setMeanValues(meanValues);
    }
    
    public boolean isNull() {
        return false;
    }
    
    public void write(OutputStream out) throws IOException {
        DataOutputStream dout = new DataOutputStream(out);
        dout.writeInt(sizeX);
        dout.writeInt(sizeY);
        dout.writeInt(sizeZ);
        dout.writeInt(nfeatures);
        for (int i = 0; i < nfeatures; i++) {
            dout.writeDouble(meanValues[i]);
        }
    }
    
}
