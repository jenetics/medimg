/**
 * Created on 15.11.2002 20:15:08
 *
 */
package org.wewi.medimg.image.geom;

import org.wewi.medimg.math.VectorField;
import org.wewi.medimg.math.VectorIterator;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RegularVectorField implements VectorField {

	/**
	 * @author Franz Wilhelmstötter
	 * @version 0.1
	 */
	private final class RegularVectorIterator implements VectorIterator {
        private int pos;
        private int[] xyz;
        
        private int sizeX = grid[0];
        private int sizeXY = grid[0]*grid[1];
        

		/**
		 * Constructor for RegularVectorIterator.
		 */
		public RegularVectorIterator() {
			super();
            pos = 0;
            xyz = new int[3];
		}

		/**
		 * @see org.wewi.medimg.image.geom.VectorIterator#hasNext()
		 */
		public boolean hasNext() {
			return pos < SIZE;
		}

		/**
		 * @see org.wewi.medimg.image.geom.VectorIterator#next(double[], double[])
		 */
		public void next(double[] start, double[] end) {
            getCoordinates(pos, xyz);
            
            getGridStartPoint(xyz[0], xyz[1], xyz[2], start);
            getGridEndPoint(xyz[0], xyz[1], xyz[2], end);
            
            ++pos;
		}
        
        private void getCoordinates(int pos, int[] coordinate) {
            coordinate[2] = pos / (sizeXY);
            pos = pos - (coordinate[2] * sizeXY);
            coordinate[1] = pos / (sizeX);
            pos = pos - (coordinate[1] * sizeX);
            coordinate[0] = pos; 
        }

	}
    
    
    private Point3D origin;
    private int[] grid;
    private int[] stride;
    
    private double[][][][] data;
    private final int SIZE;


	/**
	 * Constructor for RegularVectorField.
	 */
	public RegularVectorField(Point3D origin, int[] gridsXYZ, int[] strideXYZ) {
        this.origin = origin;
        grid = new int[3];
        stride = new int[3];
        System.arraycopy(gridsXYZ, 0, grid, 0, 3);
        System.arraycopy(strideXYZ, 0, stride, 0, 3);
        
        data = new double[grid[0]][grid[1]][grid[2]][3];
        SIZE = grid[0]*grid[1]*grid[2];
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
    
    public void getGridStartPoint(int gridX, int gridY, int gridZ, double[] startPoint) {
        startPoint[0] = origin.getX() + grid[0]*stride[0];
        startPoint[1] = origin.getY() + grid[1]*stride[1];
        startPoint[2] = origin.getZ() + grid[2]*stride[2];
    }   
    
    public void getGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint) {
        endPoint[0] = data[gridX][gridY][gridZ][0];
        endPoint[1] = data[gridX][gridY][gridZ][1];
        endPoint[2] = data[gridX][gridY][gridZ][2];
    }   
    
    public void setGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint) {
        data[gridX][gridY][gridZ][0] = endPoint[0]; 
        data[gridX][gridY][gridZ][1] = endPoint[1];
        data[gridX][gridY][gridZ][2] = endPoint[2];   
    }
    
    public void getVector(int gridX, int gridY, int gridZ, double[] vector) { 
        getGridEndPoint(gridX, gridY, gridZ, vector);
        
        vector[0] -= (origin.getX() + grid[0]*stride[0]);
        vector[1] -= (origin.getX() + grid[1]*stride[1]);
        vector[2] -= (origin.getX() + grid[2]*stride[2]);    
    }
    
    public void setVector(int gridX, int gridY, int gridZ, double[] vector) {
        data[gridX][gridY][gridZ][0] = vector[0] + origin.getX() + grid[0]*stride[0];
        data[gridX][gridY][gridZ][1] = vector[1] + origin.getX() + grid[1]*stride[1];
        data[gridX][gridY][gridZ][2] = vector[2] + origin.getX() + grid[2]*stride[2];
    }

	/**
	 * @see org.wewi.medimg.image.geom.VectorField#getVectorIterator()
	 */
	public VectorIterator getVectorIterator() {
		return new RegularVectorIterator();
	}

}






