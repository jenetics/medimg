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
 * Created on 21.11.2002 13:53:29
 *
 */
package org.wewi.medimg.math.vec.ops;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ScaleVectorFunction implements VectorFunction, GridVectorFunction {
    private double scale;

    /**
     * Constructor for ScaleVectorFunction.
     */
    public ScaleVectorFunction(double scale) {
        super();
        this.scale = scale;
    }


	/**
	 * @see org.wewi.medimg.math.vec.ops.VectorFunction#transform(double[])
	 */
	public void transform(double[] startPoint, double[] endPoint) {
        endPoint[0] = startPoint[0] + (endPoint[0] - startPoint[0])*scale;
		endPoint[1] = startPoint[1] + (endPoint[1] - startPoint[1])*scale;
		endPoint[2] = startPoint[2] + (endPoint[2] - startPoint[2])*scale;
	}
	/**
	 * @see org.wewi.medimg.math.vec.ops.GridVectorFunction#transform(int, int, int, double[])
	 */
	public void transform(int gridX, int gridY, int gridZ, double[] newVector) {
        newVector[0] *= scale;
        newVector[1] *= scale;
        newVector[2] *= scale;
	}


}
