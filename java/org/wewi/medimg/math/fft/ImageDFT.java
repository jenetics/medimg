/**
 * ImageDFT.java
 * 
 * Created on 17.12.2002, 11:54:09
 *
 */
package org.wewi.medimg.math.fft;

import org.wewi.medimg.image.ComplexImage;
import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageDFT {
    private DFT1D ft;

    /**
     * Constructor for ImageDFT.
     */
    public ImageDFT() {
        this(new IterativeFFT1D());
    }
    
    public ImageDFT(DFT1D ft) {
        this.ft = ft;    
    }
    
    public ComplexImage transform(Image image) {
        Dimension dim = image.getDimension();
        int ldx = (int)Math.ceil(MathUtil.log2(dim.getSizeX()));
        int ldy = (int)Math.ceil(MathUtil.log2(dim.getSizeY()));
        int ldz = (int)Math.ceil(MathUtil.log2(dim.getSizeZ()));
        
        int x = MathUtil.pow2(ldx);
        int y = MathUtil.pow2(ldy);
        int z = MathUtil.pow2(ldz);
        
        Complex[][][] c = new Complex[x][y][z];
        
        //Initialisieren des Arrays;
        Complex temp = new Complex(0, 0);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    c[i][j][k] =  temp;   
                }    
            }
        }
        
        DFT3D dft = new DFT3D(ft);
        dft.transform(c);
        
        ComplexImage cimage = new ComplexImage(x, y, z);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                for (int k = 0; k < z; k++) {
                    cimage.setColor(i, j, k, c[i][j][k]);   
                }    
            }
        }        
        
        return cimage;    
    }

}
