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
 * FieldFactory.java
 * 
 * Created on 10.03.2003, 10:06:32
 *
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.math.vec.ops.GridFieldLoop;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class FieldFactory {

    private FieldFactory(){
    }
    
    
    public static RegularDisplacementField createRegularField(AffineTransformation at, Dimension dim, 
                                                               int strideX, int strideY, int strideZ) {
                                                                
        final AffineTransformation trans = at;
                                                                
        RegularDisplacementField field = new RegularDisplacementField(dim, strideX, strideY, strideZ);
        GridFieldLoop loop = new GridFieldLoop(field, new GridFieldLoop.Task() {
            double[] start = new double[3];
            double[] end = new double[3];
            public void execute(int gridX, int gridY, int gridZ) {
                getVectorField().getGridStartPoint(gridX, gridY, gridZ, start);
                trans.transform(start, end);
                getVectorField().setGridEndPoint(gridX, gridY, gridZ, end);
            }
        });
        loop.loop();        
        
        return field;
    }

    
    
    
    public static RegularDisplacementField createRegularField(AffineTransformation at, Dimension dim, int stride) {
        return createRegularField(at, dim, stride, stride, stride);
    }
    
    public static RegularDisplacementField createRegularField(AffineTransformation at, Dimension dim){
        return createRegularField(at, dim, 1);   
    }
    
    public static void toIdentityField(RegularDisplacementField field) {
        GridFieldLoop loop = new GridFieldLoop(field, new GridFieldLoop.Task() {
            private double[] start = new double[3];
            public void execute(int gridX, int gridY, int gridZ) {
                getVectorField().getGridStartPoint(gridX, gridY, gridZ, start);
                getVectorField().setGridEndPoint(gridX, gridY, gridZ, start);
            }            
        });
        loop.loop();
    }

}















