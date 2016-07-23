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
 * DFT.java
 * 
 * Created on 14.12.2002, 15:28:34
 *
 */
package org.wewi.medimg.math.fft;


/**
 * Diese Klasse legt einige grundlegende Eigenschaften
 * der implementierten Transformationen fest. Verwendet
 * wird die allgemeine Form der Fouriertransformation
 * 
 * <pre>
 * Vorwärtstransformation:
 * 
 *                           N-1
 *                  1        ---
 *     F(s) =  ---------- *  \             2*PI*b*r*s/N
 *               (1-a)/2     /    f(r) * e
 *              N            ---
 *                           r=0
 * 
 * 
 * Rückwärtstransformation:
 * 
 *                           N-1
 *                  1        ---
 *     f(r) =  ---------- *  \             -2*PI*b*r*s/N
 *               (1+a)/2     /    F(s) * e
 *              N            ---
 *                           s=0
 * </pre>
 * 
 * Für die Datenanalyse werden für {a, b} = {-1, 1} und für
 * die Signalverarbeitung {1, -1} verwendet.  
 * 
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class DFT {
    /**
     * a = 1
     */
    protected double a = 1;
    /**
     * b = -1
     */
    protected double b = -1;

    /**
     * Constructor for DFT.
     */
    public DFT() {
        this(1, -1);
    }

    public DFT(double a, double b) {
        this.a = a;
        this.b = b;
    }

    protected boolean isPowerOfTwo(int n) {
        int log2N = 0;
        int k = 1;

        while (k < n) {
            k <<= 1;
            log2N++;
        }

        int ntest = (1 << log2N);

        if (n != ntest) {
            return false; // n is not a power of 2
        }

        return true;
    }

}
