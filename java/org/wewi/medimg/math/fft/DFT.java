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
 * Vorw�rtstransformation:
 * 
 *                           N-1
 *                  1        ---
 *     F(s) =  ---------- *  \             2*PI*b*r*s/N
 *               (1-a)/2     /    f(r) * e
 *              N            ---
 *                           r=0
 * 
 * 
 * R�ckw�rtstransformation:
 * 
 *                           N-1
 *                  1        ---
 *     f(r) =  ---------- *  \             -2*PI*b*r*s/N
 *               (1+a)/2     /    F(s) * e
 *              N            ---
 *                           s=0
 * </pre>
 * 
 * F�r die Datenanalyse werden f�r {a, b} = {-1, 1} und f�r
 * die Signalverarbeitung {1, -1} verwendet.  
 * 
 * 
 * @author Franz Wilhelmst�tter
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
