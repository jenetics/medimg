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
 * ROIIterator.java
 *
 * Created on 24. Jänner 2003, 21:33
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ROIIterator implements VoxelIterator {
    private Image image;
    private ROI roi;
   
    private int posX;
    private int posY;
    private int posZ;
    
    private int pos = 0;
    
    private final int size;
    
    
    /** Creates a new instance of ROIIterator */
    public ROIIterator(Image image, ROI roi) {
        if (!(image instanceof RowMajorImageGeometry)) {
            throw new IllegalArgumentException("The ImageGeometry of the image must be RowMajorImageGeometry.");
        }
        
        this.image = image;
        this.roi = roi;
        
        size = roi.getSizeX()*roi.getSizeY()*roi.getSizeZ();
        
        posX = roi.getMinX();
        posY = roi.getMinY();
        posZ = roi.getMinZ();
    }
    
    private void inc() {
        pos++;
        if (posX < roi.getMaxX()) {
            posX++;
        } else {
            posX = roi.getMinX();
            if (posY < roi.getMaxY()) {
                posY++;
            } else {
                posY = roi.getMinY();
                if (posZ < roi.getMaxZ()) {
                    posZ++;
                }
            }
        }
    }
    
    public boolean hasNext() {
        return pos < size;
    }
    
    public int next() {
        int color = image.getColor(posX, posY, posZ);
        inc();
        
        return color;
    }
    
    public int next(int[] point) {
        int color = image.getColor(posX, posY, posZ);
        point[0] = posX;
        point[1] = posY;
        point[2] = posZ;
        inc();
        
        return color;
    }
    
    public int next(double[] point) {
        int color = image.getColor(posX, posY, posZ);
        point[0] = posX;
        point[1] = posY;
        point[2] = posZ;
        inc();
        
        return color;
    }
    
    public int size() throws UnsupportedOperationException {
        return size;
    }
    
    public Object clone() {
        return new ROIIterator(image, roi);
    }
    
}
