/**
 * DFT.java
 * 
 * Created on 14.12.2002, 15:28:34
 *
 */
package org.wewi.medimg.math.fft;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class DFT {
    protected double alpha = 1;
    protected double beta = -1;

    /**
     * Constructor for DFT.
     */
    public DFT() {
        super();
    }

    public DFT(double alpha, double beta) {
        this.alpha = alpha;
        this.beta = beta;
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
