/**
 * ROIIterator.java
 *
 * Created on 24. Jänner 2003, 21:33
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ROIIterator implements VoxelIterator {
    private Image image;
    private ROI roi;
   
    private int posX;
    private int posY;
    private int posZ;
    
    private int pos = 0;
    
    private final int size;
    
    
    /** Creates a new instance of ROIIterator */
    public ROIIterator(Image image, ROI roi) {
        if (!(image instanceof RowMajorImageGeometry)) {
            throw new IllegalArgumentException("The ImageGeometry of the image must be RowMajorImageGeometry.");
        }
        
        this.image = image;
        this.roi = roi;
        
        size = roi.getSizeX()*roi.getSizeY()*roi.getSizeZ();
        
        posX = roi.getMinX();
        posY = roi.getMinY();
        posZ = roi.getMinZ();
    }
    
    private void inc() {
        pos++;
        if (posX < roi.getMaxX()) {
            posX++;
        } else {
            posX = roi.getMinX();
            if (posY < roi.getMaxY()) {
                posY++;
            } else {
                posY = roi.getMinY();
                if (posZ < roi.getMaxZ()) {
                    posZ++;
                }
            }
        }
    }
    
    public boolean hasNext() {
        return pos < size;
    }
    
    public int next() {
        int color = image.getColor(posX, posY, posZ);
        inc();
        
        return color;
    }
    
    public int next(int[] point) {
        int color = image.getColor(posX, posY, posZ);
        point[0] = posX;
        point[1] = posY;
        point[2] = posZ;
        inc();
        
        return color;
    }
    
    public int next(double[] point) {
        int color = image.getColor(posX, posY, posZ);
        point[0] = posX;
        point[1] = posY;
        point[2] = posZ;
        inc();
        
        return color;
    }
    
    public int size() throws UnsupportedOperationException {
        return size;
    }
    
    public Object clone() {
        return new ROIIterator(image, roi);
    }
    
}
