/*
 * TissueIterator.java
 *
 * Created on 04. April 2002, 14:39
 */

package org.wewi.medimg.reg.metric;


import java.util.Iterator;

import org.wewi.medimg.image.Tissue;
import org.wewi.medimg.image.Image;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class TissueIterator implements Iterator {

    private Tissue tissue;
    private Image image;
    private int pos;
    
    /** Creates new TissueIterator */
    public TissueIterator(Image img, Tissue tis) {
        image = img;
        tissue = tis;
        pos = 0;
    }
    
    public boolean hasNext() {
        int i;
        if (image != null) {
            for (i = pos + 1; i < image.getNVoxels(); i++) {
                if (image.getColor(i) == tissue.intValue()) {
                    pos = i;
                    return true;
                }
            }
        }
        //posNext = 0;
        return false;
    }
    
    public java.lang.Object next() {
        int i;
        double[] erg = new double[3];
        if (image != null) {        
            int[] coordinates = image.getCoordinates(pos);
            erg[0] = coordinates[0];    
            erg[1] = coordinates[1]; 
            erg[2] = coordinates[2];    
            return erg;
        }
        return erg;
    }

    /**
     * Liefert den Gewebetyp
     */
    public Tissue getFeatureType() {
        return tissue;
    }    

    
    /**
     * Liefert die Anzahl der Punkte. Alle, selbst jene, die sich
     * noch nicht in der matrix befinden
     */
    public int getNPoints() {
        int i;
        int count = 0;
        if (image != null) {
            for (i = 0; i < image.getNVoxels(); i++) {
                if (image.getColor(i) == tissue.intValue()) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public void remove() {
    }
    
    public void goToFirst() {
        pos = 0;
    }
    
    public BoundingBox getBoundingBox() {

        // Dimensionen ermitteln
        double[] min = new double[3];
        double[] max = new double[3];
        int npoints = this.getNPoints();
        int i, j;
        for (i = 0; i < 3; i++) {
                min[i] = Double.MAX_VALUE;
                max[i] = Double.MIN_VALUE;
        }
        double[] tempPoint;// = new double[3];
        this.goToFirst();
        while(this.hasNext()) {
            tempPoint = (double[])this.next();
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
    }

}
