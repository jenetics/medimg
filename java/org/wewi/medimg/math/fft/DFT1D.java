/**
 * DFT1D.java
 * 
 * Created on 17.12.2002, 10:13:23
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public interface DFT1D {
    
    /**
     * In Place Transformation.
     * 
     * @param a Komplexer Eingabevektor.
     */
    public void transform(Complex[] a);
    
}
