/**
 * Created on 19.11.2002 21:18:51
 *
 */
package org.wewi.medimg.math.vec;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class FloatDataArray implements RealDataArray {
    private float[][][][] data;
    
    private int sizeX;
    private int sizeY;
    private int sizeZ;

    /**
     * Constructor for DoubleDataArray.
     */
    public FloatDataArray(int sizeX, int sizeY, int sizeZ) {
        super();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        
        data = new float[sizeX][sizeY][sizeZ][3];
    }

    /**
     * @see org.wewi.medimg.image.geom.RealDataArray#get(int, int, int, int)
     */
    public void get(int x, int y, int z, double[] value) {
        value[0] = data[x][y][z][0];
        value[1] = data[x][y][z][1];
        value[2] = data[x][y][z][2];
    }

    /**
     * @see org.wewi.medimg.image.geom.RealDataArray#set(int, int, int, int, double)
     */
    public void set(int x, int y, int z, double[] value) {
        data[x][y][z][0] = (float)value[0];
        data[x][y][z][1] = (float)value[1];
        data[x][y][z][2] = (float)value[2];        
    }

    /**
     * @see org.wewi.medimg.image.geom.RealDataArray#fill(double)
     */
    public void fill(double[] value) {
        for (int k = 0; k < sizeZ; k++) {
            for (int j = 0; j < sizeY; j++) {
                for (int i = 0; i < sizeX; i++) {
                    data[i][j][k][0] = (float)value[0]; 
                    data[i][j][k][1] = (float)value[1];
                    data[i][j][k][2] = (float)value[2];   
                }    
            }    
        }
    }

    /**
     * @see org.wewi.medimg.image.geom.RealDataArray#copy(RealDataArray)
     */
    public void copy(RealDataArray target) {
        double[] p = new double[3];
        for (int k = 0; k < sizeZ; k++) {
            for (int j = 0; j < sizeY; j++) {
                for (int i = 0; i < sizeX; i++) {
                    get(i, j, k, p);
                    target.set(i, j, k, p);      
                }    
            }    
        }        
    }
}
