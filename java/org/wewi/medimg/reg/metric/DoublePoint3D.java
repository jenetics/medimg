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
