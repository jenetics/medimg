/**
 * Created on 16.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FileImageData implements Image {
    
    private class FileImageDataVoxelIterator implements VoxelIterator {
        private FileImageData parent;
        private int pos;
        
        
        public FileImageDataVoxelIterator(FileImageData parent) throws IOException {
            this.parent = parent; 
            parent.data.seek(ImageDataHeader.HEADER_SIZE);  
            pos = -1;
        }        
        
        /**
		 * @see java.lang.Object#clone()
		 */
		public Object clone() {
			try {
				return new FileImageDataVoxelIterator(parent);
			} catch (IOException e) {
                return new NullVoxelIterator();
			}
		}

		/**
		 * @see org.wewi.medimg.image.VoxelIterator#hasNext()
		 */
		public boolean hasNext() {
            ++pos;
			return pos < parent.size;
		}

		/**
		 * @see org.wewi.medimg.image.VoxelIterator#next()
		 */
		public int next() {
			try {
				return parent.data.readInt();
			} catch (IOException e) {
                return Integer.MIN_VALUE;
			}
		}

		/**
		 * @see org.wewi.medimg.image.VoxelIterator#size()
		 */
		public int size() {
			return parent.size;
		}

    }
    
    
    private File file;
    private RandomAccessFile data;
    
    private Dimension dim;
    private int minX, minY, minZ, maxX, maxY, maxZ;
    private int size, sizeX, sizeXY;
    private ColorRange colorRange;
    private int minColor, maxColor;
    private ImageHeader header;
    private ColorConversion colorConversion; 
    private ImageData.CoordinatesToPosition c2p;   
    
    
    public FileImageData(File file) throws FileNotFoundException, 
                                            IOException {
        this.file = file;   
        data = new RandomAccessFile(file, "rw");
        
        minX = data.readInt();
        minY = data.readInt();
        minZ = data.readInt();
        maxX = data.readInt();
        maxY = data.readInt();
        maxZ = data.readInt(); 
        size = (maxX-minX+1)*(maxY-minY+1)*(maxZ-minZ+1);
        sizeX = maxX-minX+1;
        sizeXY = sizeX*(maxY-minY+1);
        
        dim = new Dimension(minX, maxX, minY, maxY, minZ, maxZ); 
        colorConversion = new GreyColorConversion(); 
        c2p = new ImageData.CoordinatesToPosition(dim);      
    }

    private void setPosition(int pos) throws IOException {
        data.seek(pos*4+ImageDataHeader.HEADER_SIZE);    
    }
    
    private void setPosition(int x, int y, int z) throws IOException {
        data.seek(c2p.getPosition(x, y, z)*4 + ImageDataHeader.HEADER_SIZE);    
    }

	/**
	 * @see org.wewi.medimg.image.Image#setColor(int, int, int, int)
	 */
	public void setColor(int x, int y, int z, int color) {
        try {
            setPosition(x, y, z);
			data.writeInt(color);
		} catch (IOException e) {
		}
	}

	/**
	 * @see org.wewi.medimg.image.Image#setColor(int, int)
	 */
	public void setColor(int pos, int color) {
        try {
            setPosition(pos);
            data.writeInt(color);
        } catch (IOException e) {
        }        
	}

	/**
	 * @see org.wewi.medimg.image.Image#resetColor(int)
	 */
	public void resetColor(int color) {
	}

	/**
	 * @see org.wewi.medimg.image.Image#getColor(int)
	 */
	public int getColor(int pos) {
        try {
			setPosition(pos);
            return data.readInt();
		} catch (IOException e) {
		  return Integer.MIN_VALUE;
        }
	}

	/**
	 * @see org.wewi.medimg.image.Image#getColor(int, int, int)
	 */
	public int getColor(int x, int y, int z) {
        try {
            setPosition(x, y, z);
            return data.readInt();
        } catch (IOException e) {
          return Integer.MIN_VALUE;
        }
	}

	/**
	 * @see org.wewi.medimg.image.Image#getColorRange()
	 */
	public ColorRange getColorRange() {
        if (colorRange == null) {
            updateColorRange();    
        }
		return colorRange;
	}
    
    private void updateColorRange() {
        VoxelIterator it = getVoxelIterator(); 
        minColor = Integer.MAX_VALUE;
        maxColor = Integer.MIN_VALUE;
        
        int color;
        while (it.hasNext()) {
            color = it.next();
            if (color < minColor) {
                minColor = color;
            } else if (color > maxColor) {
                maxColor = color;
            }
        }
        
        colorRange = new ColorRange(minColor, maxColor);   
    }

	/**
	 * @see org.wewi.medimg.image.Image#getDimension()
	 */
	public Dimension getDimension() {
		return dim;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinColor()
	 */
	public int getMinColor() {
		return minColor;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxColor()
	 */
	public int getMaxColor() {
		return maxColor;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxX()
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxY()
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxZ()
	 */
	public int getMaxZ() {
		return maxZ;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinX()
	 */
	public int getMinX() {
		return minX;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinY()
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinZ()
	 */
	public int getMinZ() {
		return minZ;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getNVoxels()
	 */
	public int getNVoxels() {
		return size;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getPosition(int, int, int)
	 */
	public int getPosition(int x, int y, int z) {
		return c2p.getPosition(x, y, z);
	}

	/**
	 * @see org.wewi.medimg.image.Image#getCoordinates(int)
	 */
	public int[] getCoordinates(int pos) {
        int[] result = new int[3];
        c2p.getCoordinates(pos, result);
		return result;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getCoordinates(int, int[])
	 */
	public void getCoordinates(int pos, int[] coordinate) {
        c2p.getCoordinates(pos, coordinate);
	}

	/**
	 * @see org.wewi.medimg.image.Image#getNeighbor3D12Positions(int, int[])
	 */
	public void getNeighbor3D12Positions(int pos, int[] n12) {
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
	}

	/**
	 * @see org.wewi.medimg.image.Image#getNeighbor3D6Positions(int, int[])
	 */
	public void getNeighbor3D6Positions(int pos, int[] n6) {
        n6[0] = pos - 1;
        n6[1] = pos + 1;
        n6[2] = pos - sizeX;
        n6[3] = pos + sizeX;
        n6[4] = pos - sizeXY;
        n6[5] = pos + sizeXY;        
	}

	/**
	 * @see org.wewi.medimg.image.Image#getNeighbor3D18Positions(int, int[])
	 */
	public void getNeighbor3D18Positions(int pos, int[] n18) {
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
	}

	/**
	 * @see org.wewi.medimg.image.Image#getVoxelIterator()
	 */
	public VoxelIterator getVoxelIterator() {
		try {
			return new FileImageDataVoxelIterator(this);
		} catch (IOException e) {
            return new NullVoxelIterator();
		}
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone()  {
		try {
			return new FileImageData(file);
		} catch (FileNotFoundException e) {
            return new NullImage();
		} catch (IOException e) {
            return new NullImage();
		}
	}

	/**
	 * @see org.wewi.medimg.image.Image#getHeader()
	 */
	public ImageHeader getHeader() {
		return new NullImageHeader();
	}

	/**
	 * @see org.wewi.medimg.image.Image#getColorConversion()
	 */
	public ColorConversion getColorConversion() {
		return colorConversion;
	}

	/**
	 * @see org.wewi.medimg.image.Image#setColorConversion(ColorConversion)
	 */
	public void setColorConversion(ColorConversion cc) {
        colorConversion = cc;
	}

	/**
	 * @see org.wewi.medimg.util.Nullable#isNull()
	 */
	public boolean isNull() {
		return false;
	}
    
    public String toString() {
        return "FileImageData (\"" + file + "\"):\n" + dim;    
    }

}
