/*
 * Image.java
 *
 * Created on 18. Jänner 2002, 17:20
 *
 * 
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Nullable;


/**
 * Interface für alle Imagearte. Durch dieses Interface werden
 * Grauwertbilder und RGB- Farbbilder abgedeckt.
 *
 *
 * @author Franz Wilhelmstötter
 * @version 0.2;
 */
public interface Image extends Nullable, Cloneable {
       
    public void setColor(int x, int y, int z, int color);
      
    public void setColor(int pos, int color);
    
    public void resetColor(int color);
    
    public int getColor(int pos);
    
    public int getColor(int x, int y, int z);   
    
    public ColorRange getColorRange();
    
    public int getMinColor();
    
    public int getMaxColor();
    
    public int getMaxX();
    
    public int getMaxY();
    
    public int getMaxZ();
    
    public int getMinX();
    
    public int getMinY();
    
    public int getMinZ();
    
    public int getNVoxels();
    
    public int getPosition(int x, int y, int z);
    
    public int[] getCoordinates(int pos);   
    
    public void getCoordinates(int pos, int[] coordinate);
    
    public void getNeighbor3D12Positions(int pos, int[] n12);
    
    public void getNeighbor3D6Positions(int pos, int[] n6);
    
    public void getNeighbor3D18Positions(int pos, int[] n18);
    
    public VoxelIterator getVoxelIterator();
    
    public Object clone();
    
    public ImageHeader getHeader();
}

