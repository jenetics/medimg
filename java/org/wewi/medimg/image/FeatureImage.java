/*
 * SegmentationData.java
 *
 * Created on 17. Januar 2002, 16:41
 */

package org.wewi.medimg.image;

import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class FeatureImage implements Image {
    private byte[] features;
    private byte[] featuresOld;
    
    private int maxX, maxY, maxZ;
    private int minX, minY, minZ;
    private int size, sizeX, sizeY, sizeZ, sizeXY;
    private int nfeatures;
    private double[] meanValues;
    private int[] n6 = new int[6];
    private int[] n12 = new int[12];
    private int[] n18 = new int[18];
    
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
        sizeXY = fi.sizeXY;
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
        sizeXY = sizeX*sizeY;
        
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
        return features[sizeXY*z + sizeX*y + x];
    }
    
    public byte getFeature(int pos) {
        return features[pos];
    }

    public byte getOldFeature(int x, int y, int z) {
        return featuresOld[sizeXY*z + sizeX*y + x];
    }
    
    public byte getOldFeature(int pos) {
        return featuresOld[pos];
    }

    public void setFeature(int x, int y, int z, byte f) {
        int pos = sizeXY*z + sizeX*y + x;
        featuresOld[pos] = features[pos];
        features[pos] = f;
    } 
    
    public void setFeature(int pos, byte f) {
        featuresOld[pos] = features[pos];
        features[pos] = f;  
    }
    
    public int[] getNeighbor3D6Positions(int pos) {
        n6[0] = pos - 1;
        n6[1] = pos + 1;
        n6[2] = pos - sizeX;
        n6[3] = pos + sizeX;
        n6[4] = pos - sizeXY;
        n6[5] = pos + sizeXY;
        return n6;
    }
    
    public int[] getNeighbor3D12Positions(int pos) {
        n12[0] = pos - 1 - sizeXY;
        n12[1] = pos - 1 + sizeXY;
        n12[2] = pos - 1 - sizeX;
        n12[3] = pos - 1 + sizeX;
        n12[4] = pos + 1 - sizeXY;
        n12[5] = pos + 1 + sizeXY;
        n12[6] = pos + 1 - sizeX;
        n12[7] = pos + 1 + sizeX;
        n12[8] = pos - sizeX - sizeXY;
        n12[9] = pos - sizeX + sizeXY;
        n12[10] = pos + sizeX - sizeXY;
        n12[11] = pos + sizeX + sizeXY;
        return n12;
    }
    
    public int[] getNeighbor3D18Positions(int pos) {
        n18[0] = pos - 1;
        n18[1] = pos + 1;
        n18[2] = pos - sizeX;
        n18[3] = pos + sizeX;
        n18[4] = pos - sizeXY;
        n18[5] = pos + sizeXY;  
        n18[6] = pos - 1 - sizeXY;
        n18[7] = pos - 1 + sizeXY;
        n18[8] = pos - 1 - sizeX;
        n18[9] = pos - 1 + sizeX;
        n18[10] = pos + 1 - sizeXY;
        n18[11] = pos + 1 + sizeXY;
        n18[12] = pos + 1 - sizeX;
        n18[13] = pos + 1 + sizeX;
        n18[14] = pos - sizeX - sizeXY;
        n18[15] = pos - sizeX + sizeXY;
        n18[16] = pos + sizeX - sizeXY;
        n18[17] = pos + sizeX + sizeXY;        
        return n18;
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
    
    public void resetColor(int color) {
        Arrays.fill(features, (byte)color);
        Arrays.fill(featuresOld, (byte)color);        
    }    
    
    public int getNVoxels() {
        return size;
    }
    
    public ImageHeader getHeader() {
        return header;
    }
    
    public int getPosition(int x, int y, int z) {
        return (sizeXY*z + sizeX*y + x);
    }
    
    public int[] getCoordinates(int pos) {
        int[] erg = new int[3];
        erg[2] = pos / (sizeXY);
        pos = pos - (erg[2] * sizeXY);
        erg[1] = pos / (sizeX);
        pos = pos - (erg[1] * sizeX);
        erg[0] = pos;
        return erg;
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
