/**
 * Created on 25.11.2002 19:24:57
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImage;
import org.wewi.medimg.math.vec.GridVectorField;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GVFIntegral {
    private GridVectorField field;
    private Image image;

    public GVFIntegral(GridVectorField field) {
        this.field = field;
            
    }
    
    public void calculate() {
        image = new IntImage(field.getGridsX(), field.getGridsY(), 1);
        
        double value1 = 0;
        double value2 = 0;
        double[] vector = new double[3];
        
        for (int i = 0; i < field.getGridsX(); i++) {
            for (int j = 0; j < field.getGridsY(); j++) { 
            
                value1 = 0;
                for (int k = 0; k < j; k++) {
                    field.getVector(i, k, 0, vector); 
                    value1 += vector[1];       
                }
                
                value2 = 0;
                for (int k = 0; k < i; k++) {
                    field.getVector(k, j, 0, vector); 
                    value2 += vector[0];       
                }                
                
                value1 = 100*Math.pow(value1*value2,0.5);
                
                //System.out.println(value);
                
                image.setColor(i, j, 0, (int)Math.abs(value1));
                
            }   
        }      
    }
    
    public Image getImage() {
        return image;    
    }
}
