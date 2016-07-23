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
 * MeanVarianceOperator.java
 *
 * Created on 24. Jänner 2003, 09:11
 */

package org.wewi.medimg.image.ops;

import org.wewi.medimg.math.MathUtil;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class MeanVarianceOperator implements UnaryOperator {
    private int n = 0;
    private long sum = 0;
    private long sqrSum = 0;
    
    /** Creates a new instance of MeanVarianceOperator */
    public MeanVarianceOperator() {
    }
    
    public void process(int color) {
        n++;
        sum += color;
        sqrSum += MathUtil.sqr(color);
    }
    
    
    public double getMean() {
        return (double)sum/(double)n;
    }
    
    public double getVariance() {
        double mean = getMean();
        return ((double)sqrSum - 2*mean*sum + n*MathUtil.sqr(mean))/(double)(n-1);
    }
}
