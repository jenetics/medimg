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
 * DataPoint.java
 *
 * Created on 5. Februar 2002, 13:45
 */

package org.wewi.medimg.math.geom;

import org.wewi.medimg.util.MathematicaStringable;



/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface DataPoint extends Cloneable, MathematicaStringable {
    
    public DataPoint add(DataPoint p);
    
    public DataPoint sub(DataPoint p);
    
    public DataPoint scale(double d);
    
    public double distance(DataPoint point);
    
    public double norm();
    
    public DataPoint getNullInstance();
    
    public DataPoint getOneInstance();
    
    public int getDimension();
    
    public Number getOrdinateNumber(int dim);
    
    public boolean equals(Object obj);
    
    public Object clone();
    
    public String toString();
}
