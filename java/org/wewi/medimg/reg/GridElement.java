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
 * GridElement.java
 *
 * Created on 16. Mai 2002, 13:23
 */

package org.wewi.medimg.reg;

import java.util.Vector;

import org.wewi.medimg.math.geom.DoubleDataPoint;
/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class GridElement {
    
    private Vector referencePoints;
    private boolean computed = false;
    private double[] reference = new double[3];
    private int[] location = new int[3];
    private int[] size = new int[3];
    
   
    /** Creates new GridElement */
    public GridElement(int sizeX, int sizeY, int sizeZ) {
        referencePoints = new Vector();
        size[0] = sizeX;
        size[1] = sizeY;
        size[2] = sizeZ;
                    /*if (sizeX == 4 && sizeY == 4 && sizeZ == 1) {
                        System.out.println("actual grid size " + size[0] + " , " + size[1] + " , " + size[2]);
                    }*/        
    }
    
    public void addReferencePoint(int[] pointI) {
        double[] pointD = new double[pointI.length];
        for (int i = 0; i < pointI.length; i++) {
            pointD[i] = (double)pointI[i];
        }
        referencePoints.addElement(new DoubleDataPoint(pointD));
        computed = false;
    }
    
    public double[] getReferencePoint() {
        if (!computed) {
            computeReferencePoint();
            computed = true;
        }
        return reference;
    }
    
    public boolean referencePointExists() {
        return !(referencePoints.isEmpty());
    }
    
    private void computeReferencePoint() {
        //Todo
        double[] sum = new double[reference.length];

        for (int i = 0; i < referencePoints.size(); i++) {
            for (int j = 0; j < reference.length; j++) {
                sum[j] += ((DoubleDataPoint)referencePoints.elementAt(i)).getValue(j);
            }
        }
        for (int j = 0; j < reference.length; j++) {
            reference[j] = sum[j] / (double)referencePoints.size();
        }
        
    }
    
    // minimalste Punkt am Grid
    public void setLocation(int[] newLoc) {
        location = newLoc;
    }
    
    public int[] getLocation() {
        return location;
    }
    
    public int getSize(int pos) {
        return size[pos];
    }
}
