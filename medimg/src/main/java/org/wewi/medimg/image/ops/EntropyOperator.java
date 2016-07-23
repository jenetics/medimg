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
 * EntropyOperator.java
 * 
 * Created on 12.12.2002, 11:52:02
 *
 */
package org.wewi.medimg.image.ops;

import java.util.Enumeration;

import org.wewi.medimg.math.MathUtil;
import org.wewi.medimg.math.MutableInteger;
import org.wewi.medimg.util.IntHashtable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class EntropyOperator implements UnaryOperator {
    private IntHashtable hist;
    private int size = 0;

    /**
     * Constructor for EntropyOperator.
     */
    public EntropyOperator() {
        super();
        hist = new IntHashtable(256);
    }

    /**
     * @see org.wewi.medimg.image.ops.UnaryOperator#process(int)
     */
    public void process(int color) {
        if (hist.containsKey(color)) {
            ((MutableInteger)hist.get(color)).inc();
        } else {
            hist.put(color, new MutableInteger(0));    
        }
        ++size;
    }
    
    /**
     * Berechnet die Entropie eines Bildes. (Einheit Bit pro Pixel).
     * <pre>
     *     E = - Sum[p(c)*Log[p(c), 2], {c, minColor, maxColor}], 
     *         wobei p(c) die relative Häufigkeit der Farbe c ist.
     * </pre>
     * 
     * @return E 
     */
    public double getEntropy() {
        double entropy = 0;

        Enumeration keys = hist.keys();
        Object key = null;
        double p = 0;
        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            
            //Berechnen der relativen Häufigkeit aus dem Histogram
            p = (double)((MutableInteger)hist.get(key)).intValue() / (double)size; 
            
            entropy += p*MathUtil.log2(p);           
        }
        
        return -entropy;    
    }

}
