/*
 * SegmentationData.java
 *
 * Created on 17. Januar 2002, 16:41
 */

package org.wewi.medimg.image;

import fwilhelm.segmentation.image.ImageData;

import java.util.Arrays;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class FeatureData implements Image {
    private byte[] features;
    private byte[] featuresOld;
    
    private int maxX, maxY, maxZ, size;
    private int minX, minY, minZ;
    private int nfeatures;
    private double[] meanValues;
    
    private FeatureDataHeader header;

    public FeatureData(int x, int y, int z, int nf) {
        init(x, y, z, nf);
        header = new FeatureDataHeader(x, y, z, nf, meanValues, this);
    }
    
    public FeatureData(FeatureData fd) {
        maxX = fd.maxX;
        maxY = fd.maxY;
        maxZ = fd.maxZ;
        size = fd.size;
        nfeatures = fd.nfeatures;
        
        features = new byte[size];
        featuresOld = new byte[size];
        meanValues = new double[nfeatures];
        System.arraycopy(fd.features, 0, features, 0, size);
        System.arraycopy(fd.featuresOld, 0, featuresOld, 0, size);
        System.arraycopy(fd.meanValues, 0, meanValues, 0, nfeatures);
        
        header = new FeatureDataHeader(maxX, maxY, maxZ, nfeatures, meanValues, this);
    }

    void init(int x, int y, int z, int nf) {
        maxX = x; maxY = y; maxZ = z;
        nfeatures = nf;
        size = maxX*maxY*maxZ;
        
        features = new byte[size];
        featuresOld = new byte[size];
        Arrays.fill(features, (byte)0);
        Arrays.fill(featuresOld, (byte)0);
        
        meanValues = new double[nfeatures];
        Arrays.fill(meanValues, 0);
    }
    
    public int getNFeatures() {
        return nfeatures;
    }
    
    public byte getFeature(int x, int y, int z) {
        return features[(maxX*maxY*z + maxX*y + x)];
    }
    
    public byte getFeature(int pos) {
        return features[pos];
    }

    public byte getOldFeature(int x, int y, int z) {
        return featuresOld[(maxX*maxY*z + maxX*y + x)];
    }

    public void setFeature(int x, int y, int z, byte f) {
        int pos = (maxX*maxY*z + maxX*y + x);
        featuresOld[pos] = features[pos];
        features[pos] = f;
    } 
    
    public void setFeature(int pos, byte f) {
        featuresOld[pos] = features[pos];
        features[pos] = f;  
    }
    
    public void setMeanValues(double[] mv) {
        System.arraycopy(mv, 0, meanValues, 0, nfeatures);
        header.setMeanValues(meanValues);
    }
    
    public double[] getMeanValues() {
        double[] mv = new double[meanValues.length];
        System.arraycopy(meanValues, 0, mv, 0, meanValues.length);
        return mv;
    }
    
    public int getMaxX() {
        return maxX;
    }
    
    public int getMaxY() {
        return maxY;
    }
    
    public int getMaxZ() {
        return maxZ;
    }
    
    public int getMinX() {
        return minX;
    }
    
    public int getMinY() {
        return minY;
    }
    
    public int getMinZ() {
        return minZ;
    }    
    
    public int getColor(int x, int y, int z) {
        return getFeature(x, y, z);
    }
    
    public int getColor(int pos) {
        return getFeature(pos);
    }
    
    public boolean isNull() {
        return false;
    }
    
    public void setColor(int pos, int color) {
        setFeature(pos, (byte)color);
    }
    
    public void setColor(int x, int y, int z, int color) {
        setFeature(x, y, z, (byte)color);
    }
    
    public int getNVoxels() {
        return size;
    }
    
    public ImageHeader getHeader() {
        return header;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("maxX: ").append(maxX).append(", ");
        buffer.append("maxY: ").append(maxY).append(", ");
        buffer.append("maxZ: ").append(maxZ).append('\n');
        buffer.append("NFeatures: ").append(nfeatures).append('\n');
        buffer.append("Mean Values:").append('\n');
        for (int i = 0; i < nfeatures; i++) {
            buffer.append(meanValues[i]).append('\n');
        }
        
        return buffer.toString();
    }
    
    public Object clone() {
        return new FeatureData(this);
    }
    
}
