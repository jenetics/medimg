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
 * Created on 19.11.2002 21:40:19
 *
 */
package org.wewi.medimg.math.vec;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
interface RealDataArray {
    
    public void get(int x, int y, int z, double[] value);
    
    public void set(int x, int y, int z, double[] value);
    
    public void fill(double[] value);
    
    public void copy(RealDataArray target);
}
