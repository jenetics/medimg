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
 * Created on 21.11.2002 13:42:49
 *
 */
package org.wewi.medimg.math.vec.ops;

import org.wewi.medimg.math.vec.VectorField;
import org.wewi.medimg.math.vec.VectorIterator;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class VectorFieldAnalyzer {
    private VectorField field;
    private VectorOperator analyzer;

    /**
     * Constructor for VectorFieldAnalyzer.
     */
    public VectorFieldAnalyzer(VectorField field, VectorOperator analyzer) {
        super();
        this.field = field;
        this.analyzer = analyzer;
    }
    
    
    public void analyze() {
        double[] start = new double[3];
        double[] end = new double[3];
        for (VectorIterator it = field.getVectorIterator(); it.hasNext();) {
            it.next(start, end);
            analyzer.process(start, end);        
        }    
    }
    
    public VectorField getVectorField() {
        return field;    
    }
    
    public VectorOperator getVectorOperator() {
        return analyzer;    
    }
}
