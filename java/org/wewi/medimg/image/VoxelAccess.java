/**
 * Created on 22.11.2002 23:23:45
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface VoxelAccess {
    /**
     * This method calculates the voxel position at
     * the coordinates (x, y, z). (For all image voxels holds:
     * <code>getColor(x, y, z) == getColor(getPosition(x, y,z))</code>.)
     * 
     * @param x x-coordinate.
     * @param y y-coordinate.
     * @param z z-coordinate.
     * @return the coresponnding voxel position.
     */
    public int getPosition(int x, int y, int z);
    
    /**
     * This method calculates the voxel coordinates at
     * the specified voxel position. Every method invocation
     * creates a new int[] array. Should only used for a view 
     * coordinate calculations.
     * 
     * @param pos voxel position
     * @return the coordinates of the position pos
     */
    public int[] getCoordinates(int pos);   
    
    /**
     * Calculates the voxel coordinates at the specified position.
     * 
     * @param pos voxel position
     * @param coordinate copies the voxel coordinates into this
     *         int[] array.
     */
    public void getCoordinates(int pos, int[] coordinate);
    
    /**
     * Many algorithms in image segmentation need to know the
     * neighbor voxels.
     * 
     * @param pos voxel position to calculate the neighbor voxels
     * @param n12 copies the position of the neighbor voxels
     *             in this int[] array. The lenght of this 
     *             array must be 12. The voxel position must between zero and
     *             <code>getNVoxels()-1</code>.
     */
    public void getNeighbor3D12Positions(int pos, int[] n12);
    
    /**
     * Many algorithms in image segmentation need to know the
     * neighbor voxels.
     * 
     * @param pos voxel position to calculate the neighbor voxels
     * @param n6 copies the position of the neighbor voxels
     *             in this int[] array. The lenght of this 
     *             array must be 6. The voxel position must between zero and
     *             <code>getNVoxels()-1</code>.
     */    
    public void getNeighbor3D6Positions(int pos, int[] n6);
    
    /**
     * Many algorithms in image segmentation need to know the
     * neighbor voxels.
     * 
     * @param pos voxel position to calculate the neighbor voxels
     * @param n18 copies the position of the neighbor voxels
     *             in this int[] array. The lenght of this 
     *             array must be 18. The voxel position must between zero and
     *             <code>getNVoxels()-1</code>.
     */    
    public void getNeighbor3D18Positions(int pos, int[] n18);
}
