/**
 * Created on 22.10.2002 20:20:23
 *
 */
package org.wewi.medimg.image;

import java.util.RandomAccess;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ROIImage implements Image, RandomAccess {
    private Image image;
    private final ROI roi;
    
    private ColorConversion colorConversion;
    private ColorRange colorRange;
    private int size;
    private int[] pixel;

	/**
	 * Constructor for ImageROI.
	 */
	public ROIImage(Image image, ROI roi) {
		super();
        this.image = image;
        this.roi = roi;
        
        colorConversion = image.getColorConversion();
        size = roi.getSizeX()*roi.getSizeY()*roi.getSizeZ();
        pixel = new int[3];
	}

	/**
	 * @see org.wewi.medimg.image.Image#setColor(int, int, int, int)
	 */
	public void setColor(int x, int y, int z, int color) {
        image.setColor(x, y, z, color);
	}

	/**
	 * @see org.wewi.medimg.image.Image#setColor(int, int)
	 */
	public void setColor(int pos, int color) {
	}

	/**
	 * @see org.wewi.medimg.image.Image#resetColor(int)
	 */
	public void resetColor(int color) {
        for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i++) {
            for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j++) {
                for (int k = roi.getMinZ(), l = roi.getMaxZ(); k <= l; k++) {
                    image.setColor(i, j, k, color);    
                }    
            }    
        }
	}

	/**
	 * @see org.wewi.medimg.image.Image#getColor(int)
	 */
	public int getColor(int pos) {
		return 0;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getColor(int, int, int)
	 */
	public int getColor(int x, int y, int z) {
		return image.getColor(x, y, z);
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinColor()
	 */
	public int getMinColor() {
        if (colorRange == null) {
            updateColorRange();    
        }
		return colorRange.getMinColor();
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxColor()
	 */
	public int getMaxColor() {
        if (colorRange == null) {
            updateColorRange();    
        }        
		return colorRange.getMaxColor();
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxX()
	 */
	public int getMaxX() {
		return roi.getMaxX();
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxY()
	 */
	public int getMaxY() {
		return roi.getMaxY();
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxZ()
	 */
	public int getMaxZ() {
		return roi.getMaxZ();
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinX()
	 */
	public int getMinX() {
		return roi.getMinX();
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinY()
	 */
	public int getMinY() {
		return roi.getMinY();
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinZ()
	 */
	public int getMinZ() {
		return roi.getMinZ();
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
        return 0; 
	}
    
    private int transformPosition(int roiImagePos) {
        return 0;    
    }

	/**
	 * @see org.wewi.medimg.image.Image#getCoordinates(int)
	 */
	public int[] getCoordinates(int pos) {
		return image.getCoordinates(transformPosition(pos));
	}

	/**
	 * @see org.wewi.medimg.image.Image#getCoordinates(int, int[])
	 */
	public void getCoordinates(int pos, int[] coordinate) {
        image.getCoordinates(pos, pixel);
        
        
        image.getCoordinates(transformPosition(pos), coordinate);
	}

	/**
	 * @see org.wewi.medimg.image.Image#getNeighbor3D12Positions(int, int[])
	 */
	public void getNeighbor3D12Positions(int pos, int[] n12) {
	}

	/**
	 * @see org.wewi.medimg.image.Image#getNeighbor3D6Positions(int, int[])
	 */
	public void getNeighbor3D6Positions(int pos, int[] n6) {
	}

	/**
	 * @see org.wewi.medimg.image.Image#getNeighbor3D18Positions(int, int[])
	 */
	public void getNeighbor3D18Positions(int pos, int[] n18) {
	}

	/**
	 * @see org.wewi.medimg.image.Image#getVoxelIterator()
	 */
	public VoxelIterator getVoxelIterator() {
		return null;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getColorRange()
	 */
	public ColorRange getColorRange() {
        updateColorRange();
		return colorRange;
	}
    
    private void updateColorRange() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int color = 0;
        for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i++) {
            for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j++) {
                for (int k = roi.getMinZ(), l = roi.getMaxZ(); k <= l; k++) {
                    color = image.getColor(i, j, k);  
                    if (color < min) {
                        min = color;    
                    } else if (color > max) {
                        max = color;    
                    }
                }    
            }    
        }
        
        colorRange = new ColorRange(min, max);            
    }

	/**
	 * @see org.wewi.medimg.image.Image#getDimension()
	 */
	public Dimension getDimension() {
		return roi;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getHeader()
	 */
	public ImageHeader getHeader() {
		return null;
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
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return null;
	}

	/**
	 * @see org.wewi.medimg.util.Nullable#isNull()
	 */
	public boolean isNull() {
		return false;
	}

}
