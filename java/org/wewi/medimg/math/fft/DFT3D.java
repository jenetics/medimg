/**
 * DFT3D.java
 * 
 * Created on 17.12.2002, 11:35:22
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DFT3D {
    private DFT1D ft;
    
    public DFT3D() {
        this(new IterativeFFT1D());    
    }
    
    public DFT3D(DFT1D ft) {
        this.ft = ft;    
    }

    /**
     * In Place Transformation.
     * 
     * @param a Komplexes Eingabearray.
     */
    public void transform(Complex[][][] a) {
        int sizeX = a.length;
        int sizeY = a[0].length;
        int sizeZ = a[0][0].length;
        
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                ft.transform(a[i][j]);    
            }    
        }
        
        Complex[] z = new Complex[sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeZ; j++) {
                for (int k = 0; k < sizeY; k++) {
                    z[k] = a[i][k][j];    
                }
                
                ft.transform(z);
            
                for (int k = 0; k < sizeY; k++) {
                    a[i][k][j] = z[k];    
                }            
            }    
        }
        
        z = new Complex[sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeZ; j++) {
                for (int k = 0; k < sizeX; k++) {
                    z[k] = a[k][i][j];    
                }
                
                ft.transform(z);
            
                for (int k = 0; k < sizeX; k++) {
                    a[k][i][j] = z[k];    
                }            
            }    
        }        
                
    }
}
