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
 * DFT1D.java
 * 
 * Created on 17.12.2002, 10:13:23
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface DFT1D {
    
    /**
     * In Place Transformation.
     * 
     * @param a Komplexer Eingabevektor.
     */
    public void transform(Complex[] data);
    
    /**
     * In Place Transformation.
     * 
     * @param a Komplexer Eingabevektor.
     */
    public void transformInverse(Complex[] data);  
    
    //public Complex[] transform(double[] data);
    
    //public Complex[] transformInverse(double[] data);  
    
}
