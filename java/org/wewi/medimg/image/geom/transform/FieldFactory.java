/**
 * FieldFactory.java
 * 
 * Created on 10.03.2003, 10:06:32
 *
 */
package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.math.vec.ops.GridFieldLoop;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class FieldFactory {

    private FieldFactory(){
    }
    
    
    public static RegularDisplacementField createRegularField(AffineTransformation at, Dimension dim, 
                                                               int strideX, int strideY, int strideZ) {
                                                                
        final AffineTransformation trans = at;
                                                                
        RegularDisplacementField field = new RegularDisplacementField(dim, strideX, strideY, strideZ);
        GridFieldLoop loop = new GridFieldLoop(field, new GridFieldLoop.Task() {
            double[] start = new double[3];
            double[] end = new double[3];
            public void execute(int gridX, int gridY, int gridZ) {
                getVectorField().getGridStartPoint(gridX, gridY, gridZ, start);
                trans.transform(start, end);
                getVectorField().setGridEndPoint(gridX, gridY, gridZ, end);
            }
        });
        loop.loop();        
        
        return field;
    }

    
    
    
    public static RegularDisplacementField createRegularField(AffineTransformation at, Dimension dim, int stride) {
        return createRegularField(at, dim, stride, stride, stride);
    }
    
    public static RegularDisplacementField createRegularField(AffineTransformation at, Dimension dim){
        return createRegularField(at, dim, 1);   
    }
    
    public static void toIdentityField(RegularDisplacementField field) {
        GridFieldLoop loop = new GridFieldLoop(field, new GridFieldLoop.Task() {
            private double[] start = new double[3];
            public void execute(int gridX, int gridY, int gridZ) {
                getVectorField().getGridStartPoint(gridX, gridY, gridZ, start);
                getVectorField().setGridEndPoint(gridX, gridY, gridZ, start);
            }            
        });
        loop.loop();
    }

}















