/* 
 * ImageAccess.java
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
public interface ImageAccess {
    
    /**
     * Sets the specific color at the position (x, y, z)
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     * @param z z-coordinate
     * @param color color to be set
     */
    public void setColor(int x, int y, int z, int color);
    
    public void setColor(int x, int y, int z, double color);
      
    /**
     * Sets the specific color at the specific position position.
     * This method doubles the color getter, but if you not
     * interested on the color coordinates, this getter gives
     * you a faster access.
     * 
     * @param position colorposition.
     * @param color color to be set.
     */
    public void setColor(int pos, int color);
    public void setColor(int pos, double color);
    
    /**
     * Resets, respectively initializes, the image to the specified color.
     * 
     * @param color the reset color.
     */
    public void resetColor(int color);
    public void resetColor(double color);
    
    /**
     * Gets the color at the specified position.
     * 
     * @param position Colorposition; <code>0 <= position < getNVoxels()</code>.
     * @return the greyvalue at the position position
     */
    public int getColor(int pos);
    public int getIntColor(int pos);
    public double getDoubleColor(int pos);
    
    /**
     * Gets the greyvalue at the coordinates (x, y, z)
     * 
     * @param x x-coordinate.
     * @param y y-coordinate.
     * @param z z-coordinate.
     * @return the greyvalue at the position (x, y, z).
     */
    public int getColor(int x, int y, int z); 
    public int getIntColor(int x, int y, int z);
    public double getDoubleColor(int x, int y, int z);
    
    /**
     * Returns the coresponding VoxelIterator of this image.
     * 
     * @return VoxelIterator of this image
     * 
     * @todo Rename to getPixelVoxelIterator. Also rename the class
     *       <code>VoxelIterator</code> to <code>PixelIterator</code>.
     */
    public VoxelIterator getVoxelIterator();  
      
}
