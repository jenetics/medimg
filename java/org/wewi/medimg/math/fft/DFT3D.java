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
    
    public void transform(Complex[][][] data) {
        transform(data, new TransformFunction() {
                            public void transform(Complex[] data) {
                                ft.transform(data);    
                            }    
                        });        
    }
    
    public void transformInverse(Complex[][][] data) {
        transform(data, new TransformFunction() {
                            public void transform(Complex[] data) {
                                ft.transformInverse(data);    
                            }    
                        });        
    }

    /**
     * In Place Transformation.
     * 
     * @param a Komplexes Eingabearray.
     */
    private void transform(Complex[][][] data, TransformFunction f) {
        int sizeX = data.length;
        int sizeY = data[0].length;
        int sizeZ = data[0][0].length;
        
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                f.transform(data[i][j]);    
            }    
        }
        
        Complex[] z = new Complex[sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeZ; j++) {
                for (int k = 0; k < sizeY; k++) {
                    z[k] = data[i][k][j];    
                }
                
                f.transform(z);
            
                for (int k = 0; k < sizeY; k++) {
                    data[i][k][j] = z[k];    
                }            
            }    
        }
        
        z = new Complex[sizeX];
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeZ; j++) {
                for (int k = 0; k < sizeX; k++) {
                    z[k] = data[k][i][j];    
                }
                
                f.transform(z);
            
                for (int k = 0; k < sizeX; k++) {
                    data[k][i][j] = z[k];    
                }            
            }    
        }        
                
    }
}
