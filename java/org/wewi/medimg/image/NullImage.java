/* 
 * NullImage.java, created on 26. Jänner 2002, 21:08
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */


package org.wewi.medimg.image;

import org.wewi.medimg.util.Nullable;

/**
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

    public void setColor(int x, int y, int z, double color) {
    }

    public void setColor(int pos, double color) {
    }

    public void resetColor(double color) {
    }

    public int getIntColor(int pos) {
        return 0;
    }

    public double getDoubleColor(int pos) {
        return 0;
    }

    public int getIntColor(int x, int y, int z) {
        return 0;
    }

    public double getDoubleColor(int x, int y, int z) {
        return 0;
    }

    public void setColor(int pos, byte color) {
    }

    public void setColor(int pos, short color) {
    }

    public void setColor(int pos, long color) {
    }

    public void setColor(int pos, float color) {
    }

    public byte getByteColor(int pos) {
        return 0;
    }

    public short getShortColor(int pos) {
        return 0;
    }

    public long getLongColor(int pos) {
        return 0;
    }

    public float getFloatColor(int pos) {
        return 0;
    }

    public void fill(byte color) {
    }

    public void fill(short color) {
    }

    public void fill(int color) {
    }

    public void fill(long color) {
    }

    public void fill(float color) {
    }

    public void fill(double color) {
    }

	public int getPosition(int[] corodinate) {
		return 0;
	}

	public PixelIterator iterator() {
		return null;
	}
    
}
