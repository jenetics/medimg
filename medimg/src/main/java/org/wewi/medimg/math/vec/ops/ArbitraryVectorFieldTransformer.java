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

package org.wewi.medimg.math.vec.ops;

import org.wewi.medimg.math.vec.ArbitraryVectorField;

/**
 * @author Werner Weiser
 * @version 0.1
 */
public final class ArbitraryVectorFieldTransformer {
	
	private ArbitraryVectorField field;
	private VectorFunction function;
	
	public ArbitraryVectorFieldTransformer(ArbitraryVectorField field, VectorFunction function) {
		this.field = field;
		this.function = function;
	}
	

	public void transform() {
		double[] start = new double[3];
		double[] end = new double[3];
		
		for (int i = 0, n = field.size(); i < n; ++i) {
			field.getVector(i, start, end);
			function.transform(start, end);
			field.changeVector(i, start, end);
		}	
	}
}
