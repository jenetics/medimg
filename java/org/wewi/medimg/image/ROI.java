/**
 * Created on 22.10.2002 20:26:54
 *
 */
package org.wewi.medimg.image;

import org.wewi.medimg.util.Arrays;

/**
 * This class represents the region of interest of an image.
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ROI extends Dimension {

    /**
     * Constructor for ROI.
     * 
     * @see org.wewi.medimg.image.Dimension#Dimension(int, int, int)
     */
    public ROI(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }

    /**
     * Constructor for ROI.
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @param minZ
     * @param maxZ
     * @throws IllegalArgumentException
     */
    public ROI(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * Factory method, whitch creates a <code>ROI</code> from a
     * <code>Dimension</code>.
     * 
     * @param dim
     * @return ROI
     */
    public static ROI create(Dimension dim) {
        return new ROI(
            dim.getMinX(),
            dim.getMaxX(),
            dim.getMinY(),
            dim.getMaxY(),
            dim.getMinZ(),
            dim.getMaxZ());
    }

    /**
     * Factory method, whitch creates a <code>ROI</code> from a
     * <code>Dimension</code> and the given margins in x, y and z direction.
     * 
     * @param dim
     * @param marginX
     * @param marginY
     * @param marginZ
     * @return ROI
     */
    public static ROI create(Dimension dim, int marginX, int marginY, int marginZ) {
        return new ROI(
            dim.getMinX() + marginX,
            dim.getMaxX() - marginX,
            dim.getMinY() + marginY,
            dim.getMaxY() - marginY,
            dim.getMinZ() + marginZ,
            dim.getMaxZ() - marginZ);
    }

    /**
     * Factory method for creating a <code>ROI</code> object.
     * 
     * @param dim
     * @param margin
     * @return ROI
     */
    public static ROI create(Dimension dim, int margin) {
        return create(dim, margin, margin, margin);
    }

    /**
     * Creates a <code>ROI</code>-object with the center <code>(x, y, z)</code> and
     * the margins <code>marginX, marginY, marginZ</code>.
     *
     * @param x center x-coordinate.
     * @param y center y-coordinate.
     * @param z center z-coordinate.
     * @param marginX margin in x-direction.
     * @param marginY margin in y-direction.
     * @param marginZ margin in z-direction.
     *
     * @return <code>ROI</with> the given parameters.
     */
    public static ROI create(int x, int y, int z, int marginX, int marginY, int marginZ) {
        return new ROI(
            x - marginX,
            x + marginX,
            y - marginY,
            y + marginY,
            z - marginZ,
            z + marginZ);
    }

    /**
     * Factory method for creating <code>ROI</code>
     * 
     * @param x
     * @param y
     * @param z
     * @param margin
     * @return ROI
     */
    public static ROI create(int x, int y, int z, int margin) {
        return create(x, y, z, margin, margin, margin);
    }

    /**
     * Joins <code>this</code> with <code>b</code>
     *
     */
    public ROI join(ROI b) {
        int minX = Math.min(getMinX(), b.getMinX());
        int maxX = Math.max(getMaxX(), b.getMaxX());
        int minY = Math.min(getMinY(), b.getMinY());
        int maxY = Math.max(getMaxY(), b.getMaxY());
        int minZ = Math.min(getMinZ(), b.getMinZ());
        int maxZ = Math.max(getMaxZ(), b.getMaxZ());

        return new ROI(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * Intersects <code>this</code> with <code>b</code>
     *
     */
    public ROI intersect(ROI b) {
        int minX = Math.max(getMinX(), b.getMinX());
        int maxX = Math.min(getMaxX(), b.getMaxX());
        int minY = Math.max(getMinY(), b.getMinY());
        int maxY = Math.min(getMaxY(), b.getMaxY());
        int minZ = Math.max(getMinZ(), b.getMinZ());
        int maxZ = Math.min(getMaxZ(), b.getMaxZ());

        if (minX > maxX || minY > maxY || minZ > maxZ) {
            return new ROI(minX, minX, minY, minY, minZ, minZ);
        }

        return new ROI(minX, maxX, minY, maxY, minZ, maxZ);
    }
    
    /**
     * 
     * @param ratio
     * @return ROI[]
     */
    public ROI[] split(double ratio) {
        ROI[] rois = new ROI[2];
        split(ratio, rois);
        
        return rois;
    }
    
    /**
     * 
     * @param ratio
     * @param rois
     */
    public void split(double ratio, ROI[] rois) {
        int[] size = new int[3];
        int[] pivot = new int[3];
        
        for (int i = 0; i < 3; i++) {
            size[i] = getSize(i); 
        }
        
        Arrays.sort(size, pivot);       
         
        int part = (int)(ratio*(double)size())/(getSize(pivot[0])*getSize(pivot[1]));
        
        int[] min = new int[3];
        int[] max = new int[3];
        
        min[pivot[0]] = getMin(pivot[0]);
        max[pivot[0]] = getMax(pivot[0]);
        min[pivot[1]] = getMin(pivot[1]);
        max[pivot[1]] = getMax(pivot[1]);
        min[pivot[2]] = getMin(pivot[2]);
        max[pivot[2]] = getMin(pivot[2]) + part - 1;          
        rois[0] = new ROI(min[0], max[0], min[1], max[1], min[2], max[2]);
        
        min[pivot[2]] = getMin(pivot[2]) + part;
        max[pivot[2]] = getMax(pivot[2]);          
        rois[1] = new ROI(min[0], max[0], min[1], max[1], min[2], max[2]);
    }
    
    /**
     * Splits <code>this</code> ROI in (equal) <code>n</code> parts.
     * 
     * @param n
     * @return ROI
     */
    public ROI[] split(int n) {
        ROI[] rois = new ROI[n];
        split(n, rois);
        
        return rois;
    }
    
    public void split(int n, ROI[] rois) {
        ROI[] rt = new ROI[2];
        
        double ratio = 1.0/((double)(n));
        split(ratio, rt);
        rois[0] = rt[0];
        
        for (int i = n - 1; i > 1; i--) {
            ratio = 1.0/((double)(n));
            
            rt[1].split(ratio, rt);
            rois[n - i] = rt[0];
        }
        
        rois[n-1] = rt[1];
    }

    /**
     * Returns the number of pixels of this ROI.
     * 
     * @return int
     */
    public int size() {
        return getSizeX()*getSizeY()*getSizeZ();
    }

}





