/**
 * Created on 22.11.2002 21:59:28
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageView extends RowMajorImageGeometry implements Image {
    private Image image;
    private ROI roi;
    
    private int[] tempPoint = new int[3];


    /**
     * Constructor for ImageView.
     */
    public ImageView(Image image, ROI roi) throws IllegalArgumentException {
        super(roi);
        
        if (!(image instanceof RowMajorImageGeometry)) {
            throw new IllegalArgumentException("Parameter image is not an instance of RowMajorImageGeometry");    
        }
        
        this.image = image;
        this.roi = roi;
    }


    /**
     * @see org.wewi.medimg.image.Image#setColor(int, int, int, int)
     */
    public void setColor(int x, int y, int z, int color) {
        image.setColor(x + roi.getMinX(), y + roi.getMinY(), z + roi.getMinZ(), color);
    }
    
    public void setColor(int x, int y, int z, double color) {
        image.setColor(x + roi.getMinX(), y + roi.getMinY(), z + roi.getMinZ(), color);
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, int)
     */
    public void setColor(int pos, int color) {
        getCoordinates(pos, tempPoint);
        setColor(tempPoint[0], tempPoint[1], tempPoint[2], color);
    }
    
    public void setColor(int pos, double color) {
        getCoordinates(pos, tempPoint);
        setColor(tempPoint[0], tempPoint[1], tempPoint[2], color);
    }

    /**
     * @see org.wewi.medimg.image.Image#resetColor(int)
     */
    public void resetColor(int color) {
        for (int k = roi.getMinZ(), l = roi.getMaxZ(); k <= l; k++) {
            for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j++) {
                for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i++) {
                    setColor(i, j, k, color);    
                }    
            }    
        }
    }
    
    public void resetColor(double color) {
        for (int k = roi.getMinZ(), l = roi.getMaxZ(); k <= l; k++) {
            for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j++) {
                for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i++) {
                    setColor(i, j, k, color);    
                }    
            }    
        }
    }

    /**
     * @see org.wewi.medimg.image.Image#getColor(int)
     */
    public int getColor(int pos) {
        getCoordinates(pos, tempPoint);
        return getColor(tempPoint[0], tempPoint[1], tempPoint[2]);
    }
    
    public int getIntColor(int pos) {
        getCoordinates(pos, tempPoint);
        return getIntColor(tempPoint[0], tempPoint[1], tempPoint[2]);
    }
    
    public double getDoubleColor(int pos) {
        getCoordinates(pos, tempPoint);
        return getDoubleColor(tempPoint[0], tempPoint[1], tempPoint[2]);
    }

    /**
     * @see org.wewi.medimg.image.Image#getColor(int, int, int)
     */
    public int getColor(int x, int y, int z) {
        return image.getColor(x + roi.getMinX(), y + roi.getMinY(), z + roi.getMinZ());
    }
    
    public int getIntColor(int x, int y, int z) {
        return image.getIntColor(x + roi.getMinX(), y + roi.getMinY(), z + roi.getMinZ());
    }
    
    public double getDoubleColor(int x, int y, int z) {
        return image.getDoubleColor(x + roi.getMinX(), y + roi.getMinY(), z + roi.getMinZ());
    }

    /**
     * @see org.wewi.medimg.image.Image#getNVoxels()
     */
    public int getNVoxels() {
        return size;
    }

    /**
     * @see org.wewi.medimg.image.Image#getVoxelIterator()
     */
    public VoxelIterator getVoxelIterator() {
        return null;
    }


    /**
     * @see org.wewi.medimg.image.Image#getHeader()
     */
    public ImageHeader getHeader() {
        return image.getHeader();
    }

    /**
     * @see org.wewi.medimg.image.Image#getColorConversion()
     */
    public ColorConversion getColorConversion() {
        return image.getColorConversion();
    }

    /**
     * @see org.wewi.medimg.image.Image#setColorConversion(ColorConversion)
     */
    public void setColorConversion(ColorConversion cc) {
        image.setColorConversion(cc);
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone()  {
        Image id = new IntImage(roi);
        for (int k = roi.getMinZ(), l = roi.getMaxZ(); k <= l; k++) {
            for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j++) {
                for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i++) {
                    id.setColor(i, j, k, getColor(i, j, k));    
                }    
            }    
        }
        
        return id;
    }

    /**
     * @see org.wewi.medimg.util.Nullable#isNull()
     */
    public boolean isNull() {
        return false;
    }

}
