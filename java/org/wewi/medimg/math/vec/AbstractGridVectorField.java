/**
 * Created on 15.11.2002 20:15:08
 *
 */
package org.wewi.medimg.math.vec;

import org.wewi.medimg.image.geom.Point3D;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class AbstractGridVectorField implements GridVectorField {

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

		public boolean hasNext() {
			return pos < SIZE;
		}

		public void next(double[] start, double[] end) {
            getCoordinates(pos, xyz);
            
            getGridStartPoint(xyz[0], xyz[1], xyz[2], start);
            getGridEndPoint(xyz[0], xyz[1], xyz[2], end);
            
            pos++;
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
    
    private RealDataArray data;
    private final int SIZE;
    
    
    protected AbstractGridVectorField(AbstractGridVectorField field) {
        this(field.origin, field.grid, field.stride);  
        field.data.copy(data);  
    }


	/**
	 * Constructor for AbstractGridVectorField.
	 */
	public AbstractGridVectorField(Point3D origin, int[] gridsXYZ, int[] strideXYZ) {
        this.origin = origin;
        grid = new int[3];
        stride = new int[3];
        System.arraycopy(gridsXYZ, 0, grid, 0, 3);
        System.arraycopy(strideXYZ, 0, stride, 0, 3);
        
        data = createRealDataArray(grid[0], grid[1], grid[2]);
        SIZE = grid[0]*grid[1]*grid[2];
	}
    
    protected abstract RealDataArray createRealDataArray(int sizeX, int sizeY, int sizeZ);
    
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
        startPoint[0] = origin.getX() + gridX*stride[0];
        startPoint[1] = origin.getY() + gridY*stride[1];
        startPoint[2] = origin.getZ() + gridZ*stride[2];
    }   
    
    public void getGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint) {
        data.get(gridX, gridY, gridZ, endPoint);
    }   
    
    public void setGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint) {
        data.set(gridX, gridY, gridZ, endPoint);   
    }
    
    public void getVector(int gridX, int gridY, int gridZ, double[] vector) { 
        getGridEndPoint(gridX, gridY, gridZ, vector);
        
        vector[0] -= (origin.getX() + gridX*stride[0]);
        vector[1] -= (origin.getY() + gridY*stride[1]);
        vector[2] -= (origin.getZ() + gridZ*stride[2]);    
    }
    
    private double[] temp = new double[3];
    public void setVector(int gridX, int gridY, int gridZ, double[] vector) {
        temp[0] = vector[0] + origin.getX() + gridX*stride[0];
        temp[1] = vector[1] + origin.getY() + gridY*stride[1];
        temp[2] = vector[2] + origin.getZ() + gridZ*stride[2];
        data.set(gridX, gridY, gridZ, temp);
    }
    
    /**
     * This method rounds the value <code>startPoint</code> to the nearest
     * valid grid position (gridX, gridY, gridZ) and puts the vector
     * <code>endPoint - (gridX, gridY, gridZ)</code> into the vector field. <p/>
     * 
     * <pre>
     *   setGridEndPoint((int)Math.round((startPoint[0] - origin.getX())/(double)stride[0]),
     *                   (int)Math.round((startPoint[1] - origin.getY())/(double)stride[1]),
     *                   (int)Math.round((startPoint[2] - origin.getZ())/(double)stride[2]), endPoint);
     * </pre>
     * 
     * @see org.wewi.medimg.math.vec.VectorField#setVector(double[], double[])
     */
    public void addVector(double[] startPoint, double[] endPoint) {
        setGridEndPoint((int)Math.round((startPoint[0] - origin.getX())/(double)stride[0]),
                        (int)Math.round((startPoint[1] - origin.getY())/(double)stride[1]),
                        (int)Math.round((startPoint[2] - origin.getZ())/(double)stride[2]), endPoint);
        
        
    }

	public VectorIterator getVectorIterator() {
		return new RegularVectorIterator();
	}
    
    public abstract Object clone();
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ListPlotVectorField[{");
        
        double[] pt = new double[3];
        double[] vec = new double[3];
        for (int i = 0; i < getGridsX(); i++) {
            for (int j = 0; j < getGridsY(); j++) {
                getGridStartPoint(i, j, 0, pt);
                getVector(i, j, 0, vec);
                
                buffer.append("{");
                buffer.append("{").append(pt[0]).append(",").append(pt[1]).append("},");
                buffer.append("{").append(vec[0]).append(",").append(vec[1]).append("}");
                buffer.append("},\n");            
            }    
        }
        
        
        return buffer.toString();            
    }

}






