/*
 * Point3D.java
 *
 * Created on 31. Mai 2002, 12:11
 */

package org.wewi.medimg.reg.metric;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class DoublePoint3D {

        protected double[] point;
        
        public DoublePoint3D(double[] p) {
            int i;
            point = new double[p.length];
            for( i = 0; i < p.length; i++) {
                point[i] = p[i];
            }
        }

        public DoublePoint3D(int length) {
            int i;
            point = new double[length];
            for( i = 0; i < length; i++) {
                point[i] = 0.0;
            }
        }
        
        public double getValue(int pos) {
            return point[pos];
        }
        
        public void setValue(int pos , double val) {
            point[pos] = val;
        }
        
        public DoublePoint3D plus(DoublePoint3D p1) {
            DoublePoint3D retVal = new DoublePoint3D(3);
            retVal.setValue(0, (point[0] + p1.getValue(0)));
            retVal.setValue(1, (point[1] + p1.getValue(1)));
            retVal.setValue(2, (point[2] + p1.getValue(2)));
            return retVal;
        }

        public DoublePoint3D minus(DoublePoint3D p1) {
            DoublePoint3D retVal = new DoublePoint3D(3);
            retVal.setValue(0, (point[0] - p1.getValue(0)));
            retVal.setValue(1, (point[1] - p1.getValue(1)));
            retVal.setValue(2, (point[2] - p1.getValue(2)));
            return retVal;
        }
        
        public DoublePoint3D distance(DoublePoint3D p1) {
            DoublePoint3D retVal = new DoublePoint3D(3);
            retVal.setValue(0, Math.abs(point[0] - p1.getValue(0)));
            retVal.setValue(1, Math.abs(point[1] - p1.getValue(1)));
            retVal.setValue(2, Math.abs(point[2] - p1.getValue(2)));
            return retVal;
        }   
        
        public DoublePoint3D scale(double scale) {

                DoublePoint3D retVal = new DoublePoint3D(3);
                DoublePoint3D temp = new DoublePoint3D(point);
            try {                
                retVal.setValue(0, (point[0] * scale));
                retVal.setValue(1, (point[1] * scale));
                retVal.setValue(2, (point[2] * scale));
            } catch (Exception e) {
                System.out.println(" temp.getValue(0) " + temp.getValue(0) + " temp[1] " + temp.getValue(1) +  " temp[2] " + temp.getValue(2));
                System.out.println(" scale " + scale );                
            }
            return retVal;
        }        
        
        public int[] coordinates() {
            int[] retVal = new int[3];
            retVal[0] = (int)Math.floor(point[0]);
            retVal[1] = (int)Math.floor(point[1]);
            retVal[2] = (int)Math.floor(point[2]);
            return retVal;
        } 
        
        public double[] toDouble() {
            double[] retVal = new double[3];
            retVal[0] = point[0];
            retVal[1] = point[1];
            retVal[2] = point[2];
            return retVal;        
        }
}
