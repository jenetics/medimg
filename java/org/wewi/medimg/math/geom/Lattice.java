/* 
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

/**
 * Lattice.java
 * 
 * Created on 07.01.2003, 16:08:16
 *
 */
package org.wewi.medimg.math.geom;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Lattice {
    private Dimension2D dim;
    private int gridsX;
    private int gridsY;
    
    private double strideX;
    private double strideY;

    /**
     * Constructor for Lattice.
     */
    public Lattice(Dimension2D dim, int gridsX, int gridsY) {
        this.dim = dim;
        this.gridsX = gridsX;
        this.gridsY = gridsY;
        
        strideX = (dim.getMaxX() - dim.getMinX())/(double)(gridsX);
        strideY = (dim.getMaxY() - dim.getMinY())/(double)(gridsY);
    }
    
    public Lattice(Dimension2D dim, int grids) {
        this(dim, grids, grids);    
    }
    
    
    public int index(double x, double y) {
        int indexX = 0, indexY = 0;
        
        if (x < dim.getMinX()) {
            indexX = 0;    
        } else {
            indexX = (int)Math.ceil((x - dim.getMinX())/strideX);    
        }
        
        if (y < dim.getMinY()) {
            indexY = 0;    
        } else {
            indexY = (int)Math.ceil((y - dim.getMinY())/strideY); 
        }
        
        indexX = Math.min(indexX, gridsX + 1);
        indexY = Math.min(indexY, gridsY + 1);
        
        return indexY*(gridsX + 2) + indexX;    
    }
    
    

}

















