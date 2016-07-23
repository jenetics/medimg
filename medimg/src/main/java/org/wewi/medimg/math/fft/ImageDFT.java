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
        
        System.out.println("ImageDFT");
        System.out.println("x: " + x + ", y: " + y + ", z: " + z);
        
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
        
        //Füllen des Arrays
        for (int i = 0; i < dim.getSizeX(); i++) {
            for (int j = 0; j < dim.getSizeY(); j++) {
                for (int k = 0; k < dim.getSizeZ(); k++) {
                    c[i][j][k] =  new Complex(image.getColor(i+dim.getMinX(), 
                                                             j+image.getMinY(), 
                                                             k+image.getMinZ()), 0);   
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
                    //System.out.println(c[i][j][k]);  
                }    
            }
        }        
        
        return cimage;    
    }

}
