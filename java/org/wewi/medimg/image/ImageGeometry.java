/* 
 * ImageGeometry.java, created on 22.11.2002 23:23:45
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */


package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageGeometry {
    
    
    /**
     * Returns the maximal valid x-coordinate.
     * 
     * @return the maximal valid x-cooridnate.
     */
    public int getMaxX();
    
    /**
     * Returns the maximal valid y-coordinate.
     * 
     * @return the maximal valid y-cooridnate.
     */    
    public int getMaxY();
    
    /**
     * Returns the maximal valid z-coordinate.
     * 
     * @return the maximal valid z-cooridnate.
     */    
    public int getMaxZ();
    
    /**
     * Returns the minimal valid x-coordinate.
     * 
     * @return the minimal valid x-cooridnate.
     */    
    public int getMinX();
    
    /**
     * Returns the minimal valid y-coordinate.
     * 
     * @return the minimal valid y-cooridnate.
     */     
    public int getMinY();
    
    /**
     * Returns the minimal valid z-coordinate.
     * 
     * @return the minimal valid z-cooridnate.
     */     
    public int getMinZ(); 
    
    /**
     * A convinient method for getting the number
     * of voxels of the image.
     * 
     * @return the number of voxels the image contains.
     * 
     * @todo Rename to <code>getPixels()</code>
     */
    public int getNVoxels();     
    
    /**
     * Returns the image dimension
     * 
     * @return image dimension
     */
    public Dimension getDimension();       
    
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
    
    public int getPosition(int[] corodinate);
    
    /**
     * This method calculates the voxel coordinates at
     * the specified voxel position. Every method invocation
     * creates a new int[] array. Should only used for a view 
     * coordinate calculations.
     * 
     * @param position voxel position
     * @return the coordinates of the position position
     */
    public int[] getCoordinates(int pos);   
    
    /**
     * Calculates the voxel coordinates at the specified position.
     * 
     * @param position voxel position
     * @param coordinate copies the voxel coordinates into this
     *         int[] array.
     */
    public void getCoordinates(int pos, int[] coordinate);
    
    /**
     * Many algorithms in image segmentation need to know the
     * neighbor voxels.
     * 
     * @param position voxel position to calculate the neighbor voxels
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
     * @param position voxel position to calculate the neighbor voxels
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
     * @param position voxel position to calculate the neighbor voxels
     * @param n18 copies the position of the neighbor voxels
     *             in this int[] array. The lenght of this 
     *             array must be 18. The voxel position must between zero and
     *             <code>getNVoxels()-1</code>.
     */    
    public void getNeighbor3D18Positions(int pos, int[] n18);
}
