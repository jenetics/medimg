/**
 * Created on 22.11.2002 22:49:21
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class RowMajorImageGeometry implements ImageGeometry {

    protected Dimension dimension;
    protected int maxX, maxY, maxZ;
    protected int minX, minY, minZ;
    private int sizeX, sizeY, sizeZ;
    private int sizeXY;
    protected int size;
    
    protected RowMajorImageGeometry() {
    }
  
    protected RowMajorImageGeometry(Dimension dimension) {
        init(dimension);
    }
    
    protected void init(Dimension dimension) {
        this.dimension = dimension;
        
        maxX = dimension.getMaxX();
        maxY = dimension.getMaxY();
        maxZ = dimension.getMaxZ();
        minX = dimension.getMinX();
        minY = dimension.getMinY();
        minZ = dimension.getMinZ();
        sizeX = dimension.getSizeX();
        sizeY = dimension.getSizeY();            
        sizeZ = dimension.getSizeZ();
        sizeXY = sizeX*sizeY;
        size = sizeXY*sizeZ;            
    }
    
    public int getMaxX() {
        return maxX;
    }
    
    public int getMaxY() {
        return maxY;
    }
    
    public int getMaxZ() {
        return maxZ;
    }
    
    public int getMinX() {
        return minX;
    }
    
    public int getMinY() {
        return minY;
    }
    
    public int getMinZ() {
        return minZ;
    }   
    
    public Dimension getDimension() {
        return dimension;   
    } 
    
    public int getNVoxels() {
        return size;
    }    
    
    public int getPosition(int x, int y, int z) {
        return (sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX));
    }
    
    public int[] getCoordinates(int pos) {
        int[] erg = new int[3];
        getCoordinates(pos, erg);
        return erg;
    } 
    
    
    public void getCoordinates(int pos, int[] coordinate) {
        coordinate[2] = pos / (sizeXY);
        pos = pos - (coordinate[2] * sizeXY);
        coordinate[1] = pos / (sizeX);
        pos = pos - (coordinate[1] * sizeX);
        coordinate[0] = pos; 
        
        coordinate[0] += minX;
        coordinate[1] += minY;
        coordinate[2] += minZ;
    } 
    
    protected void getCoordinates(int pos, double[] coordinate) {
        coordinate[2] = pos / (sizeXY);
        pos = (int)(pos - (coordinate[2] * sizeXY));
        coordinate[1] = pos / (sizeX);
        pos = (int)(pos - (coordinate[1] * sizeX));
        coordinate[0] = pos; 
        
        coordinate[0] += minX;
        coordinate[1] += minY;
        coordinate[2] += minZ;
    }    
    
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
    
    public void getNeighbor3D6Positions(int pos, int[] n6) {
        n6[0] = pos - 1;
        n6[1] = pos + 1;
        n6[2] = pos - sizeX;
        n6[3] = pos + sizeX;
        n6[4] = pos - sizeXY;
        n6[5] = pos + sizeXY;         
    }    
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName() + ":\n    ");
        buffer.append("(").append(minX);
        buffer.append(",").append(minY);
        buffer.append(",").append(minZ);
        buffer.append("),(").append(maxX);
        buffer.append(",").append(maxY);
        buffer.append(",").append(maxZ).append(")");
        return buffer.toString();
    }    

    //Additional methods for refactoring////////////////////////////////////////
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    
}
