/**
 * Created on 21.11.2002 16:08:14
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.filter.ImageFilter;
import org.wewi.medimg.image.filter.LinearNormalizeFilter;
import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.math.vec.GridVectorField;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GVFOuterEnergy extends OuterEnergyFunction {
    private GridVectorField gvf;
    private Image img;

    /**
     * Constructor for GVFOuterEnergy.
     * @param image
     */
    public GVFOuterEnergy(Image image) {
        super(image);
        init();
    }
    
    private void init() {
        GradientVectorFlow flow = new GradientVectorFlow(image);
        flow.start();
        gvf = flow.getGradientVectorField();
        
        GVFIntegral integral = new GVFIntegral(gvf);
        integral.calculate();
        img = integral.getImage();
        ImageFilter normal = new LinearNormalizeFilter(img, 0, 1000);
        normal.filter();        
    }

    /**
     * @see org.wewi.medimg.seg.ac.OuterEnergyFunction#energy(Point[])
     */
    public double energy(Point[] ac) {
        double[] p = new double[3];
        double e = 0;
        
        for (int i = 0; i < ac.length; i++) {
            //gvf.getVector(ac[i].getOrdinate(0), ac[i].getOrdinate(1), 0, p);
            //e += MathUtil.sqr(p[0]) + MathUtil.sqr(p[1]);    
            e += img.getColor(ac[i].getOrdinate(0), ac[i].getOrdinate(1), 0);
        }
        
        
        return -e;
    }

}
