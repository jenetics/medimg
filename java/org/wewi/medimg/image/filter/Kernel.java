/**
 * Created on 13.11.2002 19:58:32
 *
 */
package org.wewi.medimg.image.filter;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Kernel {
    private float[] rawKernel;
    private int dim;
    private int margin;
    private int bias;

	/**
	 * Constructor for Kernel.
	 */
	public Kernel(float[] rawKernel, int dim, int bias) {
		super();
        this.rawKernel = rawKernel;
        this.dim = dim;
        this.bias = bias;
        
        margin = dim/2;
	}
    
    public int getMargin() {
        return margin;    
    }
    
    public float getValue(int x, int y) {
        return rawKernel[(y+margin)*dim+(x+margin)];    
    }
    
    public int getBias() {
        return bias;    
    }

}
