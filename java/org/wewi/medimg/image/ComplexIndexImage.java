/**
 * ComplexIndexImage.java
 * 
 * Created on 02.01.2003, 12:08:51
 *
 */
package org.wewi.medimg.image;

import org.wewi.medimg.math.Complex;
import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.geom.Dimension2D;
import org.wewi.medimg.math.geom.Lattice;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ComplexIndexImage extends ImageAdapter {
    private Dimension2D dim = new Dimension2D(-100, 100, -100, 100);
    private Lattice lattice;

    /**
     * Constructor for ComplexIndexImage.
     */
    public ComplexIndexImage(ComplexImage cimage) {
        image = new ImageData(cimage.getDimension());
        init(cimage);
    }
    
    public ComplexIndexImage(ComplexImage cimage, Dimension2D complexDimension) {
        dim = complexDimension;
        image = new ImageData(cimage.getDimension());
        init(cimage);          
    }
    
    private void init(ComplexImage cimage) {
        lattice = new Lattice(dim, 20);
        
        for (int k = cimage.getMinZ(), l = cimage.getMaxZ(); k <= l; k++) {
            for (int j = cimage.getMinY(), m = cimage.getMaxY(); j <= m; j++) {
                for (int i = cimage.getMinX(), n = cimage.getMaxX(); i <= n; i++) {
                    image.setColor(i, j, k, index(cimage.getColor(i, j, k)));    
                }    
            }    
        }           
    }
    
    private int index(Complex z) {
        z = MathUtil.log(z);
        
        //System.out.println(z);        
        
        return lattice.index(z.getRe(), z.getIm());    
    }

}
