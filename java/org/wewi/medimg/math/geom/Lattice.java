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

















