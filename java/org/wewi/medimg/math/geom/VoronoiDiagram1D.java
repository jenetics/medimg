/*
 * VoronoiDiagram1D.java
 *
 * Created on 4. Juni 2002, 19:16
 */

package org.wewi.medimg.math.geom;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class VoronoiDiagram1D {
    private final double[] voronoiPoints;
    private final int ninterval;
    private final double[][] interval;
    
    /** Creates a new instance of VoronoiDiagram1D */
    public VoronoiDiagram1D(double[] voronoiPoints) {
        this.voronoiPoints = new double[voronoiPoints.length];
        System.arraycopy(voronoiPoints, 0, this.voronoiPoints, 0, voronoiPoints.length);
        
        //Initialisierung der Intervalle
        final double[] center = voronoiPoints;
        ninterval = center.length;
        interval = new double[ninterval][2];
        for (int i = 1; i < ninterval-1; i++) {
            interval[i][0] = (center[i-1]+center[i])/2;
            interval[i][1] = (center[i]+center[i+1])/2;
        }
        interval[0][0] = -Double.MAX_VALUE;
        if (ninterval > 1) {
            interval[0][1] = interval[1][0];
        } else {
            interval[0][1] = Double.MAX_VALUE;
            return;
        }
        interval[ninterval-1][1] = Double.MAX_VALUE; 
        interval[ninterval-1][0] = interval[ninterval-2][1];         
    }
    
    public double[] getVoronoiPoints() {
        double[] retVal = new double[voronoiPoints.length];
        System.arraycopy(voronoiPoints, 0, retVal, 0, voronoiPoints.length);
        return retVal;
    }
    
    public int getVoronoiCellNo(double point) {
        int l = 0;
        int u = ninterval-1;
        int m;
        while (true) {
            //m = (int)Math.ceil((double)(u-l+1)/(double)2.0)+(l-1);
            m = u-l+1; m = m >> 1; m += l; //Macht das Gleiche wie die obere Zeile
            if (point < interval[m][0]) {
                u = m-1;
                continue;
            }
            if (point >= interval[m][1]) {
                l = m+1;
                continue;
            }
            return m; 
        }
    }

    
    public double[] getVoronoiCell(double point) {
        double[] cell = new double[2];
        int cellNo = getVoronoiCellNo(point);
        cell[0] = interval[cellNo][0];
        cell[1] = interval[cellNo][1];
        return cell;
    }
        
}
