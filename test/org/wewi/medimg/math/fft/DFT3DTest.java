/**
 * DFT3DTest.java
 * 
 * Created on 20.12.2002, 15:46:03
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DFT3DTest extends DFTTest {
    private static final double EPSILON = 0.001;

    /**
     * Constructor for DFT3DTest.
     * @param arg0
     */
    public DFT3DTest(String arg0) {
        super(arg0);
    }
    
    public void testAll() {
        DFT1D dft = new RecursiveFFT1D(-1, 1);
        DFT3D dft3d = new DFT3D(dft);
        
        int sizeX = (int)Math.rint(MathUtil.pow(2, 4));
        int sizeY = (int)Math.rint(MathUtil.pow(2, 3));
        int sizeZ = (int)Math.rint(MathUtil.pow(2, 5));
        
        Complex[][][] data = new Complex[sizeX][sizeY][sizeZ];
        Complex[][][] result = new Complex[sizeX][sizeY][sizeZ];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                for (int k = 0; k < sizeZ; k++) {
                    data[i][j][k] = new Complex(Math.random()*3+43,
                                                Math.random()*45+8);
                    result[i][j][k] = data[i][j][k];    
                }    
            }    
        }
        
        dft3d.transform(result);
        dft3d.transformInverse(result);
        
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                for (int k = 0; k < sizeZ; k++) {
                    assertEquals(data[i][j][k], result[i][j][k], EPSILON);    
                }    
            }    
        }
                  
    }

}