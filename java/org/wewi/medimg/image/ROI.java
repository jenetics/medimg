/**
 * Created on 22.10.2002 20:26:54
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ROI extends Dimension {

	/**
	 * Constructor for ROI.
	 * @param dim
	 */
	public ROI(ROI dim) {
		super(dim);
	}

	/**
	 * Constructor for ROI.
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 * @param minZ
	 * @param maxZ
	 * @throws IllegalArgumentException
	 */
	public ROI(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) throws IllegalArgumentException {
		super(minX, maxX, minY, maxY, minZ, maxZ);
	}

}
