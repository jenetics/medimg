/**
 * Created on 13.11.2002 20:16:00
 *
 */
package org.wewi.medimg.image.filter;

import java.io.File;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.ImageIOException;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class KernelFactory {
    private static final float[] BLUR_DATA = {1f/9f, 1f/9f, 1f/9f, 
                                                 1f/9f, 1f/9f, 1f/9f, 
                                                 1f/9f, 1f/9f, 1f/9f};
                                                 
    private static final float[] SHARPEN_DATA = {0f, -1f, 0f, 
                                                   -1f, 5f, -1f, 
                                                   0f, -1f, 0f};
                                                   
    private static final float[] EDGE_DETECTION_DATA = {-1, -1, -1, 
                                                           -1, 8, -1,  
                                                           -1, -1, -1};
                                                           
    private static final float[] EMBOSS_DATA = {1f, 1f, 0f, 
                                                   1f, 0f, -1f, 
                                                   0f, -1f, -1f};
                                                   
    private static final float[] PSYCHEDELIC_DISTILLATION_DATA = {0f, -1f, -2f, -3f, -4f, 
                                                                     0f, -1f,  3f,  2f,  1f, 
                                                                     0f, -1f, 10f,  2f,  1f, 
                                                                     0f, -1f,  3f,  2f,  1f, 
                                                                     0f, -1f, -2f, -3f, -4f};
                                                                     
    private static final float[] LITHOGRAPH_DATA = {-1f, -1f, -1f, -1f, -1f, 
                                                       -1f,-10f,-10f,-10f, -1f, 
                                                       -1f,-10f, 98f,-10f, -1f, 
                                                       -1f,-10f,-10f,-10f, -1f, 
                                                       -1f, -1f, -1f, -1, -1};
                                                       
    private static final float[] HORIZONTAL_SOBEL_DATA = {-1, 0, 1, 
                                                             -2, 0, 2, 
                                                             -1, 0, 1};
                                                             
    private static final float[] VERTICAL_SOBEL_DATA = {-1, -2, -1, 
                                                            0, 0, 0, 
                                                            1, 2, 1};
                                                            
    private static final float[] HORIZONTAL_PREWITT_DATA = {-1, 0, 1, 
                                                               -1, 0, 1, 
                                                               -1, 0, 1};
                                                               
    private static final float[] VERTICAL_PREWITT_DATA = {-1, -1, -1, 
                                                              0, 0, 0, 
                                                              1, 1, 1};    

	/**
	 * Constructor for KernelFactory.
	 */
	private KernelFactory() {
		super();
	}
    
    public static Kernel createBlurKernel() {
        return new Kernel(BLUR_DATA, 3, 0);    
    }
    
    public static Kernel createSharpenKernel() {
        return new Kernel(SHARPEN_DATA, 3, 0);    
    }
    
    public static Kernel createEdgeDetectionKernel() {
        return new Kernel(EDGE_DETECTION_DATA, 3, 0);    
    }
    
    public static Kernel createEmbossKernel() {
        return new Kernel(EMBOSS_DATA, 3, 128);    
    } 
    
    public static Kernel createPsychedelicDistillationKernel() {
        return new Kernel(PSYCHEDELIC_DISTILLATION_DATA, 5, 0);    
    }
    
    public static Kernel createLitographKernel() {
        return new Kernel(LITHOGRAPH_DATA, 5, 0);    
    }                   
    
    public static Kernel createLaplacianKernel() {
        float[] raw = {0f, -1f, 0f,
                       -1f, 4f, -1,
                       0f, -1f, 0f};
                       
        return new Kernel(raw, 3, 0);    
    }
    
    
    
    
    
    public static void main(String[] args) {
        ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                           new File("../../data/nhead/t1.n3.rf20/image.0045.tif")); 
                                           
        try {
            reader.read();
        } catch (ImageIOException e) {
            System.out.println("MarginImage: " + e); 
            return;
        }    
        
        Image image = reader.getImage();
        Kernel kernel = KernelFactory.createEmbossKernel();
        ImageFilter filter = new ConvolutionFilter(image, kernel);
        filter = new NormalizeFilter(filter, 0, 255);
        //filter = new NormalizeFilter(new SobelFilter(image), 0, 255);
        filter.filter();
        
        ImageWriter writer = new TIFFWriter(filter.getImage(), new File("X:/margin.image.tif"));
        try {
            writer.write();
        } catch (ImageIOException e) {
        }
        
                
        
    }

}
