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
    
    private static final int[] BLUR_DATA = {1, 1, 1, 1, 1, 1, 1, 1, 1};                                  
    private static final int[] SHARPEN_DATA = {0, -1, 0, -1, 5, -1, 0, -1, 0};                                         
    private static final int[] EDGE_DETECTION_DATA = {-1, -1, -1, -1, 8, -1, -1, -1, -1};                                            
    private static final int[] EMBOSS_DATA = {1, 1, 0,1, 0, -1, 0, -1, -1};                                 
    private static final int[] PSYCHEDELIC_DISTILLATION_DATA = {0, -1, -2, -3, -4, 
                                                                   0, -1,  3,  2,  1, 
                                                                   0, -1, 10,  2,  1, 
                                                                   0, -1,  3,  2,  1, 
                                                                   0, -1, -2, -3, -4};                                            
    private static final int[] LITHOGRAPH_DATA = {-1, -1, -1, -1, -1, 
                                                     -1,-10,-10,-10, -1, 
                                                     -1,-10, 98,-10, -1, 
                                                     -1,-10,-10,-10, -1, 
                                                     -1, -1, -1, -1, -1};  
                                                                            
    private static final int[] HORIZONTAL_SOBEL_DATA = {-1, 0, 1, -2, 0, 2,-1, 0, 1};                       
    private static final int[] VERTICAL_SOBEL_DATA = {-1, -2, -1, 0, 0, 0, 1, 2, 1};
                                                            
    private static final int[] HORIZONTAL_PREWITT_DATA = {-1, 0, 1, -1, 0, 1, -1, 0, 1};          
    private static final int[] VERTICAL_PREWITT_DATA = {-1, -1, -1, 0, 0, 0, 1, 1, 1}; 

    
    public static final Kernel BLUR = new Kernel(BLUR_DATA, 3, 9, 0);
    public static final Kernel SHARPEN = new Kernel(SHARPEN_DATA, 3, 1, 0);
    public static final Kernel EDGE_DETECTION = new Kernel(EDGE_DETECTION_DATA, 3, 1, 0);
    public static final Kernel EMBOSS = new Kernel(EMBOSS_DATA, 3, 1, 0);
    public static final Kernel PSYCHEDELIC_DISTILLATION = new Kernel(PSYCHEDELIC_DISTILLATION_DATA, 5, 1, 0);
    public static final Kernel LITHOGRAPH = new Kernel(LITHOGRAPH_DATA, 5, 1, 0);
                                                            
    public static final Kernel SOBEL_HORIZONTAL = new Kernel(HORIZONTAL_SOBEL_DATA, 3, 1, 0);
    public static final Kernel SOBEL_VERTICAL = new Kernel(VERTICAL_SOBEL_DATA, 3, 1, 0);
    
    public static final Kernel PREWITT_HORIZONTAL = new Kernel(HORIZONTAL_PREWITT_DATA, 3, 1, 0);
    public static final Kernel PREWITT_VERTICAL = new Kernel(VERTICAL_PREWITT_DATA, 3, 1, 0);     
    
    
    
    
    private int[] raw;
    private int dim;
    private int divisor;
    private int bias;
    private int margin;

	/**
	 * Constructor for Kernel.
	 */
	public Kernel(int[] rawKernel, int dim, int divisor, int bias) {
		super();
        
        raw = new int[rawKernel.length];
        System.arraycopy(rawKernel, 0, raw, 0, raw.length);

        this.dim = dim;
        this.divisor = divisor;
        this.bias = bias;
        
        margin = dim/2;
	}
    
    public int getMargin() {
        return margin;    
    }
    
    public int getValue(int x, int y) {
        return raw[(y+margin)*dim+(x+margin)];    
    }
    
    public int getDivisor() {
        return divisor;    
    }
    
    public int getBias() {
        return bias;    
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("Kerneldimension: ").append(dim).append("x").append(dim).append("\n");
        buffer.append("Divisor: ").append(divisor).append("\n");
        
        int count = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                buffer.append(raw[count++]).append("  ");    
            } 
            buffer.append("\n");  
        } 
        
        return buffer.toString();   
    }

}










