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
 * Created on 22.11.2002 12:18:55
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImage;
import org.wewi.medimg.image.MarginImage;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class MorphologicalOperation extends ImageFilter {


    protected Image b;
    protected Image marginImage;
    
    
    public MorphologicalOperation(Image image) {
        super(image); 
        
        b = new IntImage(3, 3, 1);
        b.resetColor(0);             
    }

    /**
     * Constructor for MorphologicalOperation.
     * @param image
     */
    public MorphologicalOperation(Image image, Image b) throws IllegalArgumentException {
        super(image);
        
        if (!checkSymetrie(b.getDimension())) {
            throw new IllegalArgumentException("Illegal Image b");    
        }
        
        this.b = b;        
    }
    
    public MorphologicalOperation(ImageFilter component) {
        super(component);  
        
        b = new IntImage(3, 3, 1);
        b.resetColor(0);          
    }

    /**
     * Constructor for MorphologicalOperation.
     * @param component
     */
    public MorphologicalOperation(ImageFilter component, Image b) throws IllegalArgumentException {
        super(component);
        
        if (!checkSymetrie(b.getDimension())) {
            throw new IllegalArgumentException();    
        }        
        
        this.b = b;
    }
    
    private boolean checkSymetrie(Dimension dim) {
        if ((dim.getSizeX() % 2) == 0 || (dim.getSizeX() != dim.getSizeY())) {
            return false;        
        }    
        return true;
    }
    
    protected void componentFilter() {
        int margin = b.getDimension().getSizeX()/2;
        marginImage = new MarginImage(image, margin);
        int minZ = image.getMinZ();
        
        for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
            for (int j = image.getMinY(), m = image.getMaxY(); j <= m; j++) {
                image.setColor(i, j, minZ, operation(i, j));           
            }    
        }
        
        
        marginImage = null;      
    }
    
    protected abstract int operation(int i, int j);

}













