/*
 * TissueData.java
 *
 * Created on 04. April 2002, 14:35
 */

package org.wewi.medimg.reg.metric;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.Tissue;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class TissueData {
    
    private Image image;
    
    /** Creates new TissueData */
    public TissueData(Image img) {
        image = img;
    }
    
    public TissueIterator getTissueIterator(Tissue tissue) {
        return new TissueIterator(image, tissue);
    }
    
    /*public BoundingBox getBoundingBox(Tissue tissue) {

        // Dimensionen ermitteln
        double[] min = new double[3];
        double[] max = new double[3];
        TissueIterator temp = new TissueIterator(image, tissue);
        int npoints = temp.getNPoints();
        int i, j;
        for (i = 0; i < 3; i++) {
                min[i] = Double.MAX_VALUE;
                max[i] = Double.MIN_VALUE;
        }
        double[] tempPoint;// = new double[3];
        while(temp.hasNext()) {
            tempPoint = (double[])temp.next();
            for (j = 0; j < 3; j++) {
                if (tempPoint[j] < min[j]) {
                    min[j] = tempPoint[j];
                }
                if (tempPoint[j] > max[j]) {
                    max[j] = tempPoint[j];
                }
            }             
        }
        
        // für einzelne Slices
        for (i = 0; i < 3; i++) {
                if (Math.abs(min[i] - max[i]) < 0.001) {
                        max[i] = min[i] + 1;
                }
        }
        double[][] bb = new double[8][3];
        //Boden
        bb[0][0] = min[0];
        bb[0][1] = min[1];
        bb[0][2] = min[2];
        bb[1][0] = min[0];
        bb[1][1] = max[1];
        bb[1][2] = min[2];
        bb[2][0] = max[0];
        bb[2][1] = max[1];
        bb[2][2] = min[2];
        bb[3][0] = max[0];
        bb[3][1] = min[1];
        bb[3][2] = min[2];

        bb[4][0] = min[0];
        bb[4][1] = min[1];
        bb[4][2] = max[2];
        bb[5][0] = min[0];
        bb[5][1] = max[1];
        bb[5][2] = max[2];
        bb[6][0] = max[0];
        bb[6][1] = max[1];
        bb[6][2] = max[2];
        bb[7][0] = max[0];
        bb[7][1] = min[1];
        bb[7][2] = max[2];

        BoundingBox boundingBox = new BoundingBox(bb);
        return boundingBox;
    }*/
    
    

}
