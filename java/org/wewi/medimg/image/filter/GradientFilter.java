/**
 * Created on 20.11.2002 17:54:53
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.MarginImage;
import org.wewi.medimg.image.geom.Point3D;
import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.vec.DoubleGridVectorFieldFactory;
import org.wewi.medimg.math.vec.GridVectorField;
import org.wewi.medimg.math.vec.GridVectorFieldFactory;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GradientFilter extends ImageFilter {
    private GridVectorField gvf;
    private GridVectorFieldFactory gvff;
    
    private double maxVectorLength = 10;
    private double scaleFactor;

    /**
     * Constructor for GradientFilter.
     * @param image
     */
    public GradientFilter(Image image) {
        this(image, new DoubleGridVectorFieldFactory());
    }
    
    public GradientFilter(Image image, GridVectorFieldFactory gvff) {
        super(image);
        this.gvff = gvff;    
    }

    /**
     * Constructor for GradientFilter.
     * @param component
     */
    public GradientFilter(ImageFilter component) {
        this(component, new DoubleGridVectorFieldFactory());        
    }
    
    public GradientFilter(ImageFilter component, GridVectorFieldFactory gvff) {
        super(component);
        this.gvff = gvff;    
    }
    

    protected void componentFilter() {      
         
        //Erzeugen des Gradienten-Vektorfeldes.
        Dimension dim = image.getDimension();
        Point3D origin = new Point3D(image.getMinX(), image.getMinY(), image.getMinZ());
        gvf = gvff.createGridVectorField(origin, new int[]{dim.getSizeX(), dim.getSizeY(), 1}, 
                                                 new int[]{1, 1, 1});
                                                 
        Kernel kernelX = Kernel.SOBEL_HORIZONTAL;
        Kernel kernelY = Kernel.SOBEL_VERTICAL;
        Image tempImage = new MarginImage(image, kernelX.getMargin());
        
        KernelMaskSum maskSumX = new KernelMaskSum(tempImage, dim.getMinZ(), kernelX);
        KernelMaskSum maskSumY = new KernelMaskSum(tempImage, dim.getMinZ(), kernelY);
        
        double[] point = new double[3];
        double[] vector = new double[3];
        vector[2] = 0;
        double maxLength = 0;
        double length = 0;
        for (int i = dim.getMinX(), n = dim.getMaxX(); i <= n; i++) {
            for (int j = dim.getMinY(), m = dim.getMaxY(); j <= m; j++) {
                vector[0] = maskSumX.getKernelMaskSum(i, j);
                vector[1] = maskSumY.getKernelMaskSum(i, j);
                
                length = length(vector);
                if (maxLength < length) {
                    maxLength = length;
                }
                
                gvf.setVector(i-dim.getMinX(), j-dim.getMinY(), 0, vector);                
            }
        }
        
        scaleFactor = maxVectorLength/maxLength;
        normalize();           
    }
    
    private double length(double[] v) {
        return Math.sqrt(MathUtil.sqr(v[0]) + MathUtil.sqr(v[1]) + MathUtil.sqr(v[2]));       
    }
    
    private void normalize() {
        double[] v = new double[3];
        for (int i = 0, n = gvf.getGridsX(); i < n; i++) {
            for (int j = 0, m = gvf.getGridsY(); j < m; j++) {
                gvf.getVector(i, j, 0, v);
                v[0] *= scaleFactor;
                v[1] *= scaleFactor;
                gvf.setVector(i, j, 0, v);       
            }    
        }
            
    }
    
    
    public GridVectorField getGradientVectorField() {
        return gvf;    
    }
}



















