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
 * TissueIterator.java
 *
 * Created on 04. April 2002, 14:39
 */

package org.wewi.medimg.reg;



import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class FeatureIterator implements VoxelIterator {

    private int feature;
    private Image image;
    private int pos;
    private boolean end = false;
    
    /** Creates new TissueIterator */
    public FeatureIterator(Image img, int feat) {
        image = img;
        feature = feat;
        pos = 0;
    }
    
    public FeatureIterator(FeatureIterator fi) {
        this(fi.image, fi.feature);
    }
    
    public boolean hasNext() {
        if (end != true) {
                int temp = nextPosition();
            if ( temp <= image.getNVoxels()) {
                pos = temp;
                return true;
            } else {
                end = true;
                return false;
            }
        }
        return false;
    }
    
    public int next() {
        int c = 0;
        if (end != true) {
            c = image.getColor(pos);
            return c;
        }
        // there is no such element exception
        return c;
    }

    public int next(int[] p) {
        int c = 0;
        if (end != true) {
            c = image.getColor(pos);
            image.getCoordinates(pos, p);
            return c;
        }
        // there is no such element exception
        return c;
    }
    
    public int next(double[] p) {
        int c = 0;
        if (end != true) {
            int[] coordinates = image.getCoordinates(pos);
            p[0] = coordinates[0];    
            p[1] = coordinates[1]; 
            p[2] = coordinates[2];                
            c = image.getColor(pos);
            return c;
        }
        // there is no such element exception
        return c;
    }    
    

    /**
     * Liefert den Gewebetyp
     */
    public int getFeatureType() {
        return feature;
    }    

    
    /**
     * Liefert die Anzahl der Punkte. Alle, selbst jene, die sich
     * noch nicht in der matrix befinden
     */
    public int size() {
        int i;
        int count = 0;
        if (image != null) {
            for (i = 0; i < image.getNVoxels(); i++) {
                if (image.getColor(i) == feature) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public Dimension getDimension() {
    	return image.getDimension();
    }
    
    public void remove() {
    }
    
    public void goToFirst() {
        pos = 0;
    }
  
    public Object clone() {
        return new FeatureIterator(this);
    }
    
    private int nextPosition() {
        if (image != null && end != true) {
            for (int i = pos + 1, n = image.getNVoxels(); i < n; i++) {
                if (image.getColor(i) == feature) {
                    return i;
                }
            }
            return (image.getNVoxels() + 1); 
        } 
        end = true;
        return 0;
              
    }

}
