/*
 * ImageGrid.java
 *
 * Created on 16. Mai 2002, 13:17
 */

package org.wewi.medimg.reg;

import java.util.Vector;

import org.wewi.medimg.image.Image;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class ImageGrid {
    
    private int[] size = new int[3];
    private int[] offset = new int[3];
    private final int[] gridSize = new int[3];
    private int[] cuboids = new int[3];
    public Vector grids;
    private int pos = -1;
    
    /** Creates new ImageGrid */
    public ImageGrid(int sizeX, int sizeY, int sizeZ) {
        grids = new Vector();
        size[0] = sizeX;
        size[1] = sizeY;
        size[2] = sizeZ;      
        gridSize[0] = sizeX;
        gridSize[1] = sizeY;
        gridSize[2] = sizeZ;   
        offset[0] = 0;
        offset[1] = 0;
        offset[2] = 0;
        System.out.println(" Imagegrid:constructor gridsize= " + sizeX + " , " + sizeY + " , " + sizeZ);         
    }

        /** Creates new ImageGrid */
    public ImageGrid(Image image) {
         this(image.getMaxX() - image.getMinX() + 1, image.getMaxY() - image.getMinY() + 1, image.getMaxZ() - image.getMinZ() + 1);
    }

    
    public int getSize(int pos) {
        return size[pos];
    }    
    
    public void setGridSize(int sizeX, int sizeY, int sizeZ) {
        gridSize[0] = sizeX;
        gridSize[1] = sizeY;
        gridSize[2] = sizeZ;        
    }
    
    public void setGridOffset(int sizeX, int sizeY, int sizeZ) {
        offset[0] = sizeX;
        offset[1] = sizeY;
        offset[2] = sizeZ;        
    }    
    
    
    public int[] getGridSize() {
        return gridSize;
    }
    
    public void init() {
        int[] loc = new int[3];
        for (int j = 0; j < 3; j++) {
            cuboids[j] = (int)Math.ceil((double)size[j] / (double)gridSize[j]);
        } 
        //System.out.println(" Imagegrid:init cuboids= " + cuboids[0] + " , " + cuboids[1] + " , " + cuboids[2]);  
        int[] actualSize;
        //int pos = 0;
        //int loc1[] = new int[3];
        //GridElement tset;
        GridElement temp;
        for (int i = 0; i < cuboids[0]; i++) {
            for (int j = 0; j < cuboids[1]; j++) {
                for (int k = 0; k < cuboids[2]; k++) {
                    actualSize = new int[3];
                    loc = new int[3];
                    loc[0] = offset[0] + (gridSize[0] * i); 
                    loc[1] = offset[1] + (gridSize[1] * j);
                    loc[2] = offset[2] + (gridSize[2] * k);
                    actualSize[0] = gridSize[0];
                    actualSize[1] = gridSize[1];
                    actualSize[2] = gridSize[2];                    
                    if (loc[0] + gridSize[0] > size[0]) {
                        actualSize[0] = size[0] - loc[0];                    
                    } 
                    if (loc[1] + gridSize[1] > size[1] ) {
                        actualSize[1] = size[1] - loc[1];  
                    } 
                    if (loc[2] + gridSize[2] > size[2] ) {
                        actualSize[2] = size[2] - loc[2];  
                    }
                    temp = new GridElement(actualSize[0], actualSize[1], actualSize[2]);
                    temp.setLocation(loc);
                    grids.addElement(temp);
                    //if (loc[0] == 496 && loc[1] == 496 && loc[2] == 0) {
                     /*   System.out.println("actual grid size " + actualSize[0] + " , " + actualSize[1] + " , " + actualSize[2]);
                        System.out.println("loc " + loc[0] + " , " + loc[1] + " , " + loc[2]);
                        System.out.println("temp actual grid size " + temp.getSize(0) + " , " + temp.getSize(1) + " , " + temp.getSize(2));
                    tset = (GridElement)grids.elementAt(pos);
                    loc1 = tset.getLocation();
                        System.out.println("Verify loc " + loc1[0] + " , " + loc1[1] + " , " + loc1[2]);
                        System.out.println("tset verify " + tset.getSize(0) + " , " + tset.getSize(1) + " , " + tset.getSize(2));
                    pos++;*/
                }
            }
        }
        /*int[] minimal = new int[3];
        int cou = 0;
        GridElement grid1;
         while (cou < 1024) {
            grid1 = (GridElement)grids.elementAt(cou);
            minimal = grid1.getLocation();
            System.out.println(" Test minimal " + minimal[0] + " , " + minimal[1] + " , " + minimal[2] );
            System.out.println("grid1 verify " + grid1.getSize(0) + " , " + grid1.getSize(1) + " , " + grid1.getSize(2));
            cou++;
         }*/
        //System.out.println("SIZE " + grids.size()); 
    }
    
    public boolean hasNext() {
        return (pos + 1 < grids.size());
    }
    
    public GridElement next() {
        pos++;
        return (GridElement)grids.elementAt(pos);
    }
    
    public GridElement getElement(int position) {
        pos = position;
        if (position < grids.size()) {
            return (GridElement)grids.elementAt(position);
        } else {
            return null;
        }
    }
    
    public void first() {
        pos = -1;
    }
    
    public int getPosition() {
        return (pos);
    }
    
    public void addGridElement(double[] middle, int pos) {
        int[] actualSize = new int[3];
        int[] loc = new int[3];
        loc[0] = (int)middle[0] - (gridSize[0]/2); 
        loc[1] = (int)middle[1] - (gridSize[1]/2); 
        loc[2] = (int)middle[2] - (gridSize[2]/2); 
        if (loc[0] < 0 ) {
            loc[0] = 0;
        }
        if (loc[1] < 0 ) {
            loc[1] = 0;
        }
        if (loc[2] < 0 ) {
            loc[2] = 0;
        }        
        actualSize[0] = gridSize[0];
        actualSize[1] = gridSize[1];
        actualSize[2] = gridSize[2];                    
        if (loc[0] + gridSize[0] > size[0]) {
            actualSize[0] = size[0] - loc[0];                    
        } 
        if (loc[1] + gridSize[1] > size[1] ) {
            actualSize[1] = size[1] - loc[1];  
        } 
        if (loc[2] + gridSize[2] > size[2] ) {
            actualSize[2] = size[2] - loc[2];  
        }       
        GridElement temp = new GridElement(actualSize[0], actualSize[1], actualSize[2]);
        temp.setLocation(loc);
        grids.removeElementAt(pos);
        grids.insertElementAt(temp, pos);        
    }
    
}
