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
    public void transform(Complex[][] data) {
        transform(data, new TransformFunction() {
                            public void transform(Complex[] data) {
                                ft.transform(data);    
                            }    
                        });
    }

    /**
     * In Place Transformation.
     * 
     * @param a Komplexes Eingabearray.
     */
    public void transformInverse(Complex[][] data) {
        transform(data, new TransformFunction() {
                            public void transform(Complex[] data) {
                                ft.transformInverse(data);    
                            }    
                        });
    }


    private void transform(Complex[][] data, TransformFunction f) {
        //Zeilentransformation
        for (int i = 0; i < data.length; i++) {
            f.transform(data[i]);
        }

        //Spaltentransformation
        Complex[] col = new Complex[data.length];
        for (int i = 0; i < data[0].length; i++) {
            for (int j = 0; j < data.length; j++) {
                col[j] = data[j][i];
            }

            f.transform(col);

            for (int j = 0; j < data.length; j++) {
                data[j][i] = col[j];
            }
        }
    }

}
