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
    
    /**
     * Setzen des Grauwertes eines Bildpunktes an den angegebene
     * Koordinaten.
     *
     * @param x X- Koordinaten des Bildpunktes
     * @param y Y-Koordinate des Bildpunktes
     * @param z Z-Koordinate des Bildpunktes
     * @param color zu setzender Grauwert
     */    
    public void setColor(int x, int y, int z, int color);
    
    /**
     * Setzen des Grauwertes eines Bildpunktes an den angegebene Stelle.
     * Ein Bildpunkt kann anstatt über seine Koordinaten auch über dessen
     * Posittion angesprochen werden. Diese Zugriffsmethode ist dann
     * zu bevorzugen, wenn die räumliche Lage des Bildementes keine Rolle
     * spielt. Ein Zugriff über die Position ist in der Regel schneller
     * als über die Koordinaten. Die Werte für die Position liegen zwischen
     * 0 und maxX*maxY*maxZ-1
     *
     * @param pos Position des Bildpunktes
     * @param color Zu setzender Grauwert
     */    
    public void setColor(int pos, int color);
    
    public int getColor(int pos);
    
    public int getColor(int x, int y, int z);    
    
    public int getMaxX();
    
    public int getMaxY();
    
    public int getMaxZ();
    
    public int getMinX();
    
    public int getMinY();
    
    public int getMinZ();
    
    public int getNVoxels();
    
    public Object clone();
    
    public ImageHeader getHeader();
}

