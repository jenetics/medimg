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
class FeatureDataHeader implements ImageHeader {
    private int maxX;
    private int maxY;
    private int maxZ;
    private int nfeatures;
    private double[] meanValues;
    private FeatureData featureData;

    /** Creates new FeatureDataHeader */
    public FeatureDataHeader(int x, int y, int z, int nf, double[] mv, FeatureData fd) {
        maxX = x;
        maxY = y;
        maxZ = z; 
        nfeatures = nf;
        meanValues = mv;
        featureData = fd;
    }

    void setMeanValues(double[] mv) {
        meanValues = mv;
    }
    
    public void read(InputStream in) throws IOException {
        DataInputStream din = new DataInputStream(in);
        maxX = din.readInt();
        maxY = din.readInt();
        maxZ = din.readInt();
        nfeatures = din.readInt();
        meanValues = new double[nfeatures];
        for (int i = 0; i < nfeatures; i++) {
            meanValues[i] = din.readDouble();
        }
        
        featureData.init(maxX, maxY, maxZ, nfeatures);
        featureData.setMeanValues(meanValues);
    }
    
    public boolean isNull() {
        return false;
    }
    
    public void write(OutputStream out) throws IOException {
        DataOutputStream dout = new DataOutputStream(out);
        dout.writeInt(maxX);
        dout.writeInt(maxY);
        dout.writeInt(maxZ);
        dout.writeInt(nfeatures);
        for (int i = 0; i < nfeatures; i++) {
            dout.writeDouble(meanValues[i]);
        }
    }
    
}
