/**
 * Created on 15.11.2002 20:15:08
 *
 */
package org.wewi.medimg.image.geom;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RegularVectorField implements VectorField {
    private Point3D origin;
    private int[] grid;
    private int[] stride;
    
    private double[] data;


	/**
	 * Constructor for RegularVectorField.
	 */
	public RegularVectorField(Point3D origin, int[] gridsXYZ, int[] strideXYZ) {
        this.origin = origin;
        grid = new int[3];
        stride = new int[3];
        System.arraycopy(gridsXYZ, 0, grid, 0, 3);
        System.arraycopy(strideXYZ, 0, stride, 0, 3);
	}
    
    public Point3D getOrigin() {
        return origin;    
    }
    
    public int getGridsX() {
        return grid[0];    
    }
    
    public int getGridsY() {
        return grid[1];    
    }
    
    public int getGridsZ() {
        return grid[2];    
    }
    
    public void getVector(int[] gridPoint, double[] vector) {
        
    }

	/**
	 * @see org.wewi.medimg.image.geom.VectorField#getVectorIterator()
	 */
	public VectorIterator getVectorIterator() {
		return null;
	}

}
