/*
 * AffineTransformableImageData.java
 *
 * Created on March 26, 2002, 3:20 PM
 */

package org.wewi.medimg.image;

import org.wewi.medimg.image.geom.IdentityTransform;
import org.wewi.medimg.image.geom.Transform;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class TransformableImage implements Image {
    private Image image;
    private ImageDataHeader header;
    private Transform transform;
    
    private int maxX, maxY, maxZ;
    private float[] tempPoint1 = new float[3];
    private float[] tempPoint2 = new float[3];
    
    public TransformableImage(Image image) {
        this.image = image;
        transform = new IdentityTransform();
        init();
    }
    
    private void init() {
        float[][] vertices = new float[8][3];
        float[][] dest = new float[8][3];
        
        vertices[0][0] = 0; vertices[0][1] = 0; vertices[0][2] = 0;
        vertices[1][0] = 0; vertices[1][1] = 0; vertices[1][2] = maxZ;
        vertices[2][0] = 0; vertices[2][1] = maxY; vertices[2][2] = 0;
        vertices[3][0] = 0; vertices[3][1] = maxY; vertices[3][2] = maxZ;
        vertices[4][0] = maxX; vertices[4][1] = 0; vertices[4][2] = 0;
        vertices[5][0] = maxX; vertices[5][1] = 0; vertices[5][2] = maxZ;
        vertices[6][0] = maxX; vertices[6][1] = maxY; vertices[6][2] = 0;
        vertices[7][0] = maxX; vertices[7][1] = maxY; vertices[7][2] = maxZ;
        
        for (int i = 0; i < 8; i++) {
            transform.transform(vertices[i], dest[i]);
        }
        
        float x, y, z;
        x =  y =  z = Float.MIN_VALUE;
        for (int i = 0; i < 8; i++) {
            if (x < dest[i][0]) {
                x = dest[i][0];
            }
            if (x < dest[i][1]) {
                x = dest[i][1];
            }
            if (z < dest[i][2]) {
                z = dest[i][2];
            }            
        }  
        maxX = (int)Math.rint(Math.ceil(x));
        maxY = (int)Math.rint(Math.ceil(y));
        maxZ = (int)Math.rint(Math.ceil(z));
        //header = new ImageDataHeader(maxX, maxY,maxZ, image);
    }
    
    public void setTransform(Transform at) {
        transform = at;
        init();
    }
    
    public Image getUnderlyingImage() {
        return image;
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
        return 0;
    }
    
    public int getMinY() {
        return 0;
    }
    
    public int getMinZ() {
        return 0;
    }    
    
    public int getNVoxels() {
        return image.getNVoxels();
    }    
    
    public int getColor(int pos) {
        return image.getColor(pos);
    }
    
    public int getColor(int x, int y, int z) {
        tempPoint1[0] = x; tempPoint1[1] = y; tempPoint1[0] = z;
        //transform.transformBackward(tempPoint1, tempPoint2);

        return 0;
    }
    
    public void setColor(int pos, int color) {
        image.setColor(pos, color);
    }
    
    public void setColor(int x, int y, int z, int color) {
    }    
    
    public ImageHeader getHeader() {
        return header;
    }
    
    public boolean isNull() {
        return image.isNull();
    }
    
    public Object clone() {
        return null;
    }
    
    public int getPosition(int x, int y, int z) {
        return 0;
    }
    
    public void resetColor(int color) {
    }
    
    public int[] getCoordinates(int pos) {
        int[] retVal = {};
        return retVal;
    }
    
    public void getCoordinates(int pos, int[] coordinates) {
    }
    
    public VoxelIterator getVoxelIterator() {
        return null;
    }
    
    public ColorRange getColorRange() {
        return null;
    }
    
    public int getMaxColor() {
        return 0;
    }
    
    public int getMinColor() {
        return 0;
    }
    
    public void getNeighbor3D12Positions(int pos, int[] n12) {
    }
    
    public void getNeighbor3D18Positions(int pos, int[] n18) {
    }
    
    public void getNeighbor3D6Positions(int pos, int[] n6) {
    }
    
}
