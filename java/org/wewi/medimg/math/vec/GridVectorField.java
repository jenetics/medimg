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
 * Created on 19.11.2002 21:06:00
 *
 */
package org.wewi.medimg.math.vec;

import org.wewi.medimg.util.Mutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface GridVectorField extends VectorField, Cloneable, Mutable {
    
    public void setGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint);
    
    public void getGridStartPoint(int gridX, int gridY, int gridZ, double[] startPoint);
    
    public void getGridEndPoint(int gridX, int gridY, int gridZ, double[] endPoint);
    
    public void getVector(int gridX, int gridY, int gridZ, double[] vector);
    
    public void setVector(int gridX, int gridY, int gridZ, double[] vector);
    
    public int getGridsX();
    
    public int getGridsY();
    
    public int getGridsZ();
    
    public Object clone();
}
