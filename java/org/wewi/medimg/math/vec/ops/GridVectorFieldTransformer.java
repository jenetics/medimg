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
 * Created on 21.11.2002 13:34:15
 *
 */
package org.wewi.medimg.math.vec.ops;

import org.wewi.medimg.math.vec.GridVectorField;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class GridVectorFieldTransformer {    
    
    private GridVectorField field;
    private GridVectorFunction transformer;

    /**
     * Constructor for GridVectorFieldTransformer.
     */
    public GridVectorFieldTransformer(GridVectorField field, GridVectorFunction transformer) {
        super();
        this.field = field;
        this.transformer = transformer;
    }
    
    public void transform() {
        double[] v = new double[3];
        
        for (int k = 0, l = field.getGridsZ(); k < l; k++) {
            for (int j = 0, m = field.getGridsY(); j < m; j++) {
                for (int i = 0, n = field.getGridsX(); i < n; i++) {
                    field.getVector(i, j, k, v);
                    transformer.transform(i, j, k, v);
                    field.setVector(i, j, k, v);           
                }    
            }    
        }    
    }
    
    public GridVectorField getVectorField() {
        return field;    
    }
    
    public GridVectorFunction getVectorFunction() {
        return transformer;    
    }

}
