/*
 * DisplacementField3D.java
 *
 * Created on 13. April 2002, 10:27
 */

package org.wewi.medimg.image.geom.transform;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.math.geom.DoubleDataPoint;


import org.wewi.medimg.util.Timer;

import java.util.Arrays;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class DisplacementField3D extends DisplacementField {

    /** Creates new DisplacementField3D */
    /*public DisplacementField3D() {
    }*/
    
    /*public DisplacementField3D(int sizeX, int sizeY, int sizeZ) {
        DIM = 3;
    }*/
    
    public DisplacementField3D(Image image) {
        super(image);
        DIM = 3;
    }
        
    /**
     * Transformation Vorwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    public double[] transform_fw(double[] source) {
        DoubleDataPoint target = new DoubleDataPoint(source);
        //System.out.println(" target " + target.getValue(0) + " , " + target.getValue(1) +  " , " + target.getValue(2));
        //System.out.println(" size " + referencePoints.size() + " DIM " + DIM);
        double[] retVal = new double[DIM];
        double tempMetric = 0.0;
        double sumMetric = 0.0;
        Arrays.fill(retVal, (double) 0);
        DoubleDataPoint temp = new DoubleDataPoint(retVal);
        DoubleDataPoint temp1;
        DoubleDataPoint temp2;
        if (displacementVectorExists(target)) {
            temp = getDisplacementVector(target);
            temp2 = (DoubleDataPoint)target.add(temp);
            retVal = temp2.getValue();
            //grenzen überprüfen
            return retVal;
        }
        for (int i = 0; i < referencePoints.size(); i++) {
            temp1 = (DoubleDataPoint)referencePoints.elementAt(i);
            temp2 = getDisplacementVector(temp1);
            tempMetric = cityBlockMetric(target, temp1);
            //tempMetric = euclidianMetric(target, temp1);            
            temp2 = (DoubleDataPoint)temp2.scale(tempMetric);
            temp = (DoubleDataPoint)temp.add(temp2);
            sumMetric += tempMetric;
        }
        // keine Vektoren vorhanden
        if (sumMetric != 0) {
            sumMetric = 1 / sumMetric;
            temp = (DoubleDataPoint)temp.scale(sumMetric);
        }
        //System.out.println(" target " + target.getValue(0) + " , " + target.getValue(1) +  " , " + target.getValue(2));
        //System.out.println(" retVal " + retVal[0] + " , " + retVal[0] +  " , " + retVal[0]);
        //System.out.println(" temp " + temp.getValue(0) + " , " + temp.getValue(1) +  " , " + temp.getValue(2));
        addNewDisplacementVector(temp, target);
        temp2 = (DoubleDataPoint)target.add(temp);
        retVal = temp2.getValue();        
        return retVal;
    }     

    
    /**
     * Transformation Vorwärtstransformation.
     *
     * @param source Zu transformierende Punkt.
     * @return target Transformierter Punkt
     */
    public double[] transform_bw(double[] source) {
        DoubleDataPoint target = new DoubleDataPoint(source);
        //System.out.println(" target " + target.getValue(0) + " , " + target.getValue(1) +  " , " + target.getValue(2));
        //System.out.println(" size " + referencePoints.size() + " DIM " + DIM);
        double[] retVal = new double[DIM];
        double tempMetric = 0.0;
        double sumMetric = 0.0;
        Arrays.fill(retVal, (double) 0);
        DoubleDataPoint temp = new DoubleDataPoint(retVal);
        DoubleDataPoint temp1;
        DoubleDataPoint temp2;
        for (int i = 0; i < referencePoints.size(); i++) {
            temp1 = (DoubleDataPoint)referencePoints.elementAt(i);
            temp2 = getDisplacementVector(temp1);
            tempMetric = cityBlockMetric(target, (DoubleDataPoint)temp1.add(temp2));
            //tempMetric = euclidianMetric(target.plus(temp2), temp1);
            temp2 = (DoubleDataPoint)temp2.scale(-tempMetric);
            temp = (DoubleDataPoint)temp.add(temp2);
            sumMetric += tempMetric;
        }
        // keine Vektoren vorhanden
        if (sumMetric != 0) {
            sumMetric = 1 / sumMetric;
            temp = (DoubleDataPoint)temp.scale(sumMetric);
        }
        //System.out.println(" target " + target.getValue(0) + " , " + target.getValue(1) +  " , " + target.getValue(2));
        //System.out.println(" retVal " + retVal[0] + " , " + retVal[0] +  " , " + retVal[0]);
        //System.out.println(" temp " + temp.getValue(0) + " , " + temp.getValue(1) +  " , " + temp.getValue(2));
        addNewDisplacementVector(temp, target);
        temp2 = (DoubleDataPoint)target.add(temp);
        retVal = temp2.getValue();        
        return retVal;
    }         
    
    /**
     * Transformation of an whole ImageData
     * 
     * @param source transforming ImageData
     * @return target transfromed ImageData
     */
    public Image transform(Image source) {
        Image target = (Image)source.clone();
        int i, j, k, color;
        int maxX = target.getMaxX();
        int maxY = target.getMaxY();
        int maxZ = target.getMaxZ();
        int minX = target.getMinX();
        int minY = target.getMinY();
        int minZ = target.getMinZ();        
        //System.out.println(" X " + maxX + " Y " + maxY + " Z " + maxZ);
        double[] point_s = new double[3];
        double[] point_t = new double[3];
        Timer timer1 = new Timer("DisplacementField3D::transform1");
        timer1.start();
        int cou = 0;
        target.resetColor(255);
        for (i = minX; i <= maxX; i++) {
            for (j = minY; j <= maxY; j++) {
                for (k = minZ; k <= maxZ; k++) {
                    point_s[0] = i;
                    point_s[1] = j;
                    point_s[2] = k;
                    //color = source.getColor(i, j, k);
                    point_t = transform_bw(point_s);
                    if (!((int)point_t[0] > maxX || (int)point_t[1] > maxY || (int)point_t[2] > maxZ || 
                          (int)point_t[0] < minX || (int)point_t[1] < minY || (int)point_t[2] < minZ)) {
                        target.setColor(i, j, k, source.getColor((int)point_t[0], (int)point_t[1], (int)point_t[2]));      
                        //target.setColor((int)point_t[0], (int)point_t[1], (int)point_t[2], color);
                        cou++;
                    }/*
                    if ((i % 50 == 0) && (j % 50 == 0)) {
                        System.out.println("point_s " + point_s[0] + " , " + point_s[1] + " , " + point_s[2] + "color " + color);
                        System.out.println("point_t " + point_t[0] + " , " + point_t[1] + " , " + point_t[2]);
                    }*/
                }
            }
        }
        System.out.println("cou " + cou);
        timer1.stop();
        timer1.print();
        return target;
    }
    
    private double cityBlockMetric(DoubleDataPoint p1, DoubleDataPoint p2) {
        double retVal = 0.0;
        retVal = Math.abs(p1.getValue(0) - p2.getValue(0)) + Math.abs(p1.getValue(1) - p2.getValue(1)) + Math.abs(p1.getValue(2) - p2.getValue(2));
        retVal = 1.0 / retVal;
        //retVal = 1.0 / Math.exp(retVal);
        return retVal;
    }
    
    private double euclidianMetric(DoubleDataPoint p1, DoubleDataPoint p2) {
        double retVal = 0.0;
        retVal = Math.sqrt(((p1.getValue(0) - p2.getValue(0)) * (p1.getValue(0) - p2.getValue(0))) + 
                           ((p1.getValue(1) - p2.getValue(1)) * (p1.getValue(1) - p2.getValue(1))) + 
                           ((p1.getValue(2) - p2.getValue(2)) * (p1.getValue(2) - p2.getValue(2))));
        //retVal = Math.abs(p1.getValue(0) - p2.getValue(0)) + Math.abs(p1.getValue(1) - p2.getValue(1)) + Math.abs(p1.getValue(2) - p2.getValue(2));
        retVal = 1.0 / Math.exp(retVal);
        return retVal;
    }    
}
