/*
 * ImageGrid.java
 *
 * Created on 16. Mai 2002, 13:17
 */

package org.wewi.medimg.reg;

import java.util.Vector;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class ImageGrid {
    
    private int[] size = new int[3];
    private int[] gridSize = new int[3];
    private int[] cuboids = new int[3];
    private Vector grids;
    private int pos = 0;
    
    /** Creates new ImageGrid */
    public ImageGrid(int sizeX, int sizeY, int sizeZ) {
        grids = new Vector();
        size[0] = sizeX;
        size[1] = sizeY;
        size[2] = sizeZ;      
        gridSize[0] = sizeX;
        gridSize[1] = sizeY;
        gridSize[2] = sizeZ;         
    }

    
    public int getSize(int pos) {
        return size[pos];
    }    
    
    public void setGridSize(int sizeX, int sizeY, int sizeZ) {
        gridSize[0] = sizeX;
        gridSize[1] = sizeY;
        gridSize[2] = sizeZ;        
    }
    
    
    public int[] getGridSize() {
        return gridSize;
    }
    
    public void init() {
        double[] loc = new double[3];
        for (int j = 0; j < 3; j++) {
            cuboids[j] = (int)Math.ceil((double)size[j] / (double)gridSize[j]);
        }  
        for (int i = 0; i < cuboids[0]; i++) {
            for (int j = 0; j < cuboids[1]; j++) {
                for (int k = 0; k < cuboids[2]; k++) {
                    GridElement temp = new GridElement(gridSize[0], gridSize[1], gridSize[2]);
                    loc[0] = (gridSize[0] * i) + ((double)gridSize[0] / 2.0);
                    loc[1] = (gridSize[1] * j) + ((double)gridSize[1] / 2.0);
                    loc[2] = (gridSize[2] * k) + ((double)gridSize[2] / 2.0);
                    temp.setLocation(loc);
                    grids.add(temp);
                }
            }
        }
    }
    
    public boolean hasNext() {
        return (pos < grids.size());
    }
    
    public GridElement next() {
        return (GridElement)grids.elementAt(pos++);
    }
    
    public void first() {
        pos = 0;
    }
}
