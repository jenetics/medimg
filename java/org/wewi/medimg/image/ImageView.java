/**
 * Created on 22.11.2002 21:59:28
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageView extends LineScanImageGeometry implements Image {
    private Image image;
    private ROI roi;


	/**
	 * Constructor for ImageView.
	 */
	public ImageView(Image image, ROI roi) throws IllegalArgumentException {
		super(roi);
        
        if (!(image instanceof LineScanImageGeometry)) {
            throw new IllegalArgumentException("Parameter image is not an instance of LineScanImageGeometry");    
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

	/**
	 * @see org.wewi.medimg.image.Image#setColor(int, int)
	 */
	public void setColor(int pos, int color) {
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
		return 0;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getColor(int, int, int)
	 */
	public int getColor(int x, int y, int z) {
		return image.getColor(x + roi.getMinX(), y + roi.getMinY(), z + roi.getMinZ());
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMinColor()
	 */
	public int getMinColor() {
		return 0;
	}

	/**
	 * @see org.wewi.medimg.image.Image#getMaxColor()
	 */
	public int getMaxColor() {
		return 0;
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
	 * @see org.wewi.medimg.image.Image#getColorRange()
	 */
	public ColorRange getColorRange() {
		return image.getColorRange();
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
		return null;
	}

	/**
	 * @see org.wewi.medimg.util.Nullable#isNull()
	 */
	public boolean isNull() {
		return false;
	}

}
