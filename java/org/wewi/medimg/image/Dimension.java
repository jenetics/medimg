/**
 * Created on 11.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Dimension implements Cloneable {
	private final int sizeX;
	private final int sizeY;
	private final int sizeZ;
	private final int minX, maxX;
	private final int minY, maxY;
	private final int minZ, maxZ;
	
	public Dimension(Dimension dim) {
		sizeX = dim.sizeX;
		sizeY = dim.sizeY;
		sizeZ = dim.sizeZ;
		minX = dim.minX;
		maxX = dim.maxX;
		minY = dim.minY;
		maxY = dim.maxY;
		minZ = dim.minZ;
		maxZ = dim.maxZ;
	}
	
	public Dimension(int sizeX, int sizeY, int sizeZ) throws IllegalArgumentException {
		this(0, sizeX-1, 0, sizeY-1, 0, sizeZ-1);		
	}
	
	public Dimension(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) 
                                                        throws IllegalArgumentException {
        
        if (minX > maxX || minY > maxY || minZ > maxZ) {
            throw new IllegalArgumentException("One of the \"min\" argument is " +
                                                "bigger than the coresponding \"max\" argument \n" +
                                                "(" + minX + "," + maxX + ")\n" +
                                                "(" + minY + "," + maxY + ")\n" +
                                                "(" + minZ + "," + maxZ + ")");    
        }
                                                                    
		this.sizeX = maxX-minX+1;
		this.sizeY = maxY-minY+1;
		this.sizeZ = maxZ-minZ+1;
		this.minX = minX;
		this.maxX = maxX;
		this.minY = minY;
		this.maxY = maxY;
		this.minZ = minZ;
		this.maxZ = maxZ;		
	}
	
	/**
	 * Returns the maxX.
	 * @return int
	 */
	public int getMaxX() {
		return maxX;
	}

	/**
	 * Returns the maxY.
	 * @return int
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * Returns the maxZ.
	 * @return int
	 */
	public int getMaxZ() {
		return maxZ;
	}

	/**
	 * Returns the minX.
	 * @return int
	 */
	public int getMinX() {
		return minX;
	}

	/**
	 * Returns the minY.
	 * @return int
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * Returns the minZ.
	 * @return int
	 */
	public int getMinZ() {
		return minZ;
	}

	/**
	 * Returns the sizeX.
	 * @return int
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * Returns the sizeY.
	 * @return int
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * Returns the sizeZ.
	 * @return int
	 */
	public int getSizeZ() {
		return sizeZ;
	}
	
	public int hashCode() {
		int result = 17;
		result = 37*result+minX;
		result = 37*result+maxX;
		result = 37*result+minY;
		result = 37*result+maxY;
		result = 37*result+minZ;
		result = 37*result+maxZ;
		return result;	
	}
	
	public String toString() {
		return "X:(" + minX + "," + maxX + ")\n" +
		        "Y:(" + minY + "," + maxY + ")\n" +
		        "Z:(" + minZ + "," + maxZ + ")";	
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Dimension)) {
			return false;	
		}	
		
		Dimension dim = (Dimension)o;
		
		return minX == dim.minX && maxX == dim.maxX &&
		        minY == dim.minY && maxY == dim.maxX &&
		        minZ == dim.minZ && maxZ == dim.maxZ;
	}
	
	public Object clone() {
		return new Dimension(this);	
	}

}






