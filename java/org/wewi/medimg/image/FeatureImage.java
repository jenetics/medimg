/*
 * SegmentationData.java
 *
 * Created on 17. Januar 2002, 16:41
 */

package org.wewi.medimg.image;

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
public final class FeatureImage implements Image {
    private byte[] features;
    private byte[] featuresOld;
    
    private int maxX, maxY, maxZ, size;
    private int minX, minY, minZ;
    private int sizeX, sizeY, sizeZ;
    private int nfeatures;
    private double[] meanValues;
    
    private FeatureImageHeader header;

    public FeatureImage(int sizeX, int sizeY, int sizeZ, int nf) {
        init(sizeX, sizeY, sizeZ, nf);
        header = new FeatureImageHeader(sizeX, sizeY, sizeZ, nf, meanValues, this);
    }
    
    public FeatureImage(FeatureImage fi) {
        maxX = fi.maxX;
        maxY = fi.maxY;
        maxZ = fi.maxZ;
        size = fi.size;
        nfeatures = fi.nfeatures;
        
        features = new byte[size];
        featuresOld = new byte[size];
        meanValues = new double[nfeatures];
        System.arraycopy(fi.features, 0, features, 0, size);
        System.arraycopy(fi.featuresOld, 0, featuresOld, 0, size);
        System.arraycopy(fi.meanValues, 0, meanValues, 0, nfeatures);
        
        header = new FeatureImageHeader(maxX, maxY, maxZ, nfeatures, meanValues, this);
    }

    void init(int sizeX, int sizeY, int sizeZ, int nf) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        maxX = sizeX - 1; 
        maxY = sizeY - 1; 
        maxZ = sizeZ - 1;
        minX = minY = minZ = 0;
        nfeatures = nf;
        size = sizeX*sizeY*sizeZ;
        
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
    
    public boolean hasDifferentNeighbors(int x, int y, int z) {
        return true;
    }
    
    public boolean hasDifferentNeighbors(int pos) {
        return true;
    }
    
    public byte getFeature(int x, int y, int z) {
        return features[sizeX*sizeY*z + sizeX*y + x];
    }
    
    public byte getFeature(int pos) {
        return features[pos];
    }

    public byte getOldFeature(int x, int y, int z) {
        return featuresOld[(sizeX*sizeY*z + sizeX*y + x)];
    }

    public void setFeature(int x, int y, int z, byte f) {
        int pos = (sizeX*sizeY*z + sizeX*y + x);
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
        buffer.append("FeatureImage:\n    ");
        buffer.append("sizeX: ").append(sizeX).append(", ");
        buffer.append("sizeY: ").append(sizeY).append(", ");
        buffer.append("sizeZ: ").append(sizeZ).append('\n').append("    ");
        buffer.append("NFeatures: ").append(nfeatures).append('\n').append("    ");
        buffer.append("Mean Values:").append('\n').append("        ");
        for (int i = 0; i < nfeatures; i++) {
            buffer.append(meanValues[i]).append('\n').append("        ");
        }
        
        return buffer.toString();
    }
    
    public Object clone() {
        return new FeatureImage(this);
    }
    
}
