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
 * Created on 25.11.2002 19:24:57
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImage;
import org.wewi.medimg.math.vec.GridVectorField;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GVFIntegral {
    private GridVectorField field;
    private Image image;

    public GVFIntegral(GridVectorField field) {
        this.field = field;
            
    }
    
    public void calculate() {
        image = new IntImage(field.getGridsX(), field.getGridsY(), 1);
        
        double value1 = 0;
        double value2 = 0;
        double[] vector = new double[3];
        
        for (int i = 0; i < field.getGridsX(); i++) {
            for (int j = 0; j < field.getGridsY(); j++) { 
            
                value1 = 0;
                for (int k = 0; k < j; k++) {
                    field.getVector(i, k, 0, vector); 
                    value1 += vector[1];       
                }
                
                value2 = 0;
                for (int k = 0; k < i; k++) {
                    field.getVector(k, j, 0, vector); 
                    value2 += vector[0];       
                }                
                
                value1 = 100*Math.pow(value1*value2,0.5);
                
                //System.out.println(value);
                
                image.setColor(i, j, 0, (int)Math.abs(value1));
                
            }   
        }      
    }
    
    public Image getImage() {
        return image;    
    }
}
