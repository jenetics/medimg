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
 * Created on 30.09.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.util.Arrays;

import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class T3 {
    private AccumulatorArray accu;
    private int[] f;
    
    
    public T3(AccumulatorArray accu) {
        this.accu = accu; 
        init();  
    }
    
    private void init() {
        double[][] H = new double[accu.getRows()][accu.getCols()];  
        int[] rowSums = new int[accu.getRows()];
        
        Arrays.fill(rowSums, 0);
        
        for (int i = 0, n = accu.getRows(); i < n; i++) {
            for (int j = 0, m = accu.getCols(); j < m; j++) {
                rowSums[i] += accu.getValue(i, j);    
            }           
        }
        
        for (int i = 0, n = accu.getRows(); i < n; i++) {
            for (int j = 0, m = accu.getCols(); j < m; j++) {
                H[i][j] = ((double)accu.getValue(i, j)) / ((double)rowSums[i]);    
            }           
        } 
        
        f = new int[accu.getCols()];
        for (int i = 0, n = accu.getCols(); i < n; i++) {
            f[i] = getMaxRowIndex(H, i);        
        }           
    }
    
    private int getMaxRowIndex(double[][] H, int col) {
        double max = -Double.MAX_VALUE;  
        int maxIndex = 0;  
        for (int i = 0, n = accu.getRows(); i < n; i++) {
            if (H[i][col] > max) {
                max = H[i][col];
                maxIndex = i;    
            }       
        }
        
        return maxIndex;
    }
    
    public int getAbstractFeatures() {
        return accu.getCols();    
    }
    
    
    public int transform(int af) {
        return f[af];    
    }
}




