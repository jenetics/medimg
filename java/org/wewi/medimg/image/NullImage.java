/*
 * NullImage.java
 *
 * Created on 26. Jänner 2002, 21:08
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Nullable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class NullImage implements Image, Nullable {
    
    private class NullImageVoxelIterator implements VoxelIterator {
        public boolean hasNext() {
            return false;
        }
        
        public int next() {
            return 0;
        }
        
        public int next(int[] p) {
            return 0;    
        }
        
        public int next(double[] p) {
            return 0;    
        }        
        
        public int size() {
            return 0;
        }
        public Object clone() {
            return new NullImageVoxelIterator();
        }
    }
    
    private class NullColorConversion implements ColorConversion {
        public NullColorConversion() {
        }
        public int convert(int[] rgb) {
            return 0;    
        }
        public void convert(int grey, int[] rgb) {
            rgb[0] = 0;
            rgb[1] = 0;
            rgb[2] = 0;    
        }
        public Object clone() {
            return new NullColorConversion();    
        }
    }
    
    public static final NullImage INSTANCE = new NullImage();
    
    private NullImageHeader header;

    public NullImage() {
        header = new NullImageHeader();
    }
    
    public int getColor(int x, int y, int z) {
        return 0;
    }
    
    public int getColor(int pos) {
        return 0;
    }
    
    public void setColor(int pos, int color) {
    }
    
    public void setColor(int x, int y, int z, int color) {
    }
    
    public int getMaxZ() {
        return 0;
    }
    
    public int getMaxY() {
        return 0;
    }
    
    public int getMaxX() {
        return 0;
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
    
    public Dimension getDimension() {
        return new Dimension(0, 0, 0, 0, 0, 0);    
    }
    
    public int getNVoxels() {
        return 0;
    }
    
    public boolean isNull() {
        return true;
    }
    
    public Object clone() {
        return new NullImage();
    }    
    
    public ImageHeader getHeader() {
        return header;
    }
    
    public ColorConversion getColorConversion() {
        return new NullColorConversion();    
    }
    
    public void setColorConversion(ColorConversion cc) {
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
        return new NullImageVoxelIterator();
    }
    
    public ColorRange getColorRange() {
        return new ColorRange(0, 0);
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
    
    public boolean equals(Object obj) {
        if (!(obj instanceof NullImage)) {
            return false;
        } 
        return true;  
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#setColor(int, int, int, double)
     */
    public void setColor(int x, int y, int z, double color) {
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#setColor(int, double)
     */
    public void setColor(int pos, double color) {
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#resetColor(double)
     */
    public void resetColor(double color) {
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getIntColor(int)
     */
    public int getIntColor(int pos) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getDoubleColor(int)
     */
    public double getDoubleColor(int pos) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getIntColor(int, int, int)
     */
    public int getIntColor(int x, int y, int z) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.ImageAccess#getDoubleColor(int, int, int)
     */
    public double getDoubleColor(int x, int y, int z) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, byte)
     */
    public void setColor(int pos, byte color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, short)
     */
    public void setColor(int pos, short color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, long)
     */
    public void setColor(int pos, long color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#setColor(int, float)
     */
    public void setColor(int pos, float color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#getByteColor(int)
     */
    public byte getByteColor(int pos) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.Image#getShortColor(int)
     */
    public short getShortColor(int pos) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.Image#getLongColor(int)
     */
    public long getLongColor(int pos) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.Image#getFloatColor(int)
     */
    public float getFloatColor(int pos) {
        return 0;
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(byte)
     */
    public void fill(byte color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(short)
     */
    public void fill(short color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(int)
     */
    public void fill(int color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(long)
     */
    public void fill(long color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(float)
     */
    public void fill(float color) {
    }

    /**
     * @see org.wewi.medimg.image.Image#fill(double)
     */
    public void fill(double color) {
    }
    
}
