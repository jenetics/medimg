/**
 * DFT2D.java
 * 
 * Created on 17.12.2002, 11:39:45
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DFT2D extends DFT {
    private DFT1D ft;

    /**
     * Constructor for DFT2D.
     */
    public DFT2D() {
        this(new IterativeFFT1D());
    }
    
    public DFT2D(DFT1D ft) {
        this.ft = ft;    
    }
    
    /**
     * In Place Transformation.
     * 
     * @param a Komplexes Eingabearray.
     */ 
     public void transform(Complex[][] a) {
        //Zeilentransformation
        for (int i = 0; i < a.length; i++) {
            ft.transform(a[i]);   
        }
        
        
        //Spaltentransformation
        Complex[] col = new Complex[a.length];
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                col[j] = a[j][i];    
            }    
            
            ft.transform(col);
            
            for (int j = 0; j < a.length; j++) {
                a[j][i] = col[j];    
            }            
        }        
     }
          
}
