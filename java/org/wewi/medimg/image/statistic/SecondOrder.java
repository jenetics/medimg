/*
 * SecondOrder.java
 *
 * Created on 24. Jänner 2003, 09:01
 */

package org.wewi.medimg.image.statistic;

import org.wewi.medimg.image.GreyColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.ROI;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.RawImageReader;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.image.ops.ImageLoop;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class SecondOrder {
    private Image image;
    
    /** Creates a new instance of SecondOrder */
    public SecondOrder(Image image) {
        this.image = image;
    }
    
    public Image varianceImage() {
        Image varianceImage = (Image)image.clone();
        varianceImage.resetColor(0);
        varianceImage.setColorConversion(new GreyColorConversion());
        
        ROI roi = ROI.create(image.getDimension());
        //System.out.println("DIM: \n" + image.getDimension());
        //System.out.println("ROI: \n" + roi);
        
        Variance task = new Variance(varianceImage);
        ImageLoop loop = new ImageLoop(image,  task);
        loop.loop(roi);
        
        /*
        ImageFilter filter = new LinearNormalizeFilter(varianceImage, 0, 255);
        filter.filter();
        varianceImage = filter.getImage();
        */
        return varianceImage;
    }
    
    private final class Variance extends ImageLoop.Task {
        private Image varianceImage;
        private ROI imageROI;
        
        public Variance(Image image) {
            this.varianceImage = image;
            imageROI = ROI.create(image.getDimension());
        }
        
        public void execute(int x, int y, int z) {
            //System.out.println("x: " + x + ", y: " + y + ", z: " +z);
            ROI roi = imageROI.intersect(ROI.create(x, y, z, 1));
            //System.out.println("ROI:\n" + roi);
            int centerColor = getImage().getColor(x, y, z);
            
            int sum = 0;            
            for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i++) {
                for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j++) {
                    for (int k = roi.getMinZ(), u = roi.getMaxZ(); k <= u; k++) {
                        sum += getColor(i, j, k, centerColor);
                    }
                }
            }
            
            /*
            final double colors = 27;
            double mean = (double)sum/colors;
            double var = sum*(1 - 2*mean) + colors*MathUtil.sqr(mean);
            //var /= colors;
            */
            varianceImage.setColor(x, y, z, getFeature(sum));
        } 
        
        private int getColor(int x, int y, int z, int centerColor) {
            //System.out.println("x: " + x + ", y: " + y + ", z: " +z); 
            if (centerColor == getImage().getColor(x, y, z)) {
                return 1;
            } else {
                return 0;
            }
        }
        
        private int getFeature(int sum) {
            switch (sum) {
                case 1: return 1;                
                case 2: return 2;
                case 3: return 3;
                case 4: return 4;
                case 5: return 5;
                case 6: return 6;
                case 7: return 7;
                case 8: return 8;
                case 9: return 9;
                case 10: return 10;
                case 11: return 11;
                case 12: return 12;
                case 13: return 13;
                case 14: return 13;
                case 15: return 12;
                case 16: return 11;
                case 17: return 10;
                case 18: return 9;
                case 19: return 8;
                case 20: return 7;
                case 21: return 6;
                case 22: return 5;
                case 23: return 4;
                case 24: return 3;
                case 25: return 2;
                case 26: return 1;
                case 27: return 0;
                default: return 0;
            }
        }
        
        public Image getVarianceImage() {
            return varianceImage;
        }
        
    }
    
    
    
    
    public static void main(String[] args) {
        try {
            //String path = "/home/fwilhelm/Workspace/Projekte/Diplom/medimages/nhead/seg.model";
            String path = "/home/fwilhelm/Workspace/Projekte/Diplom/code/data/validation/segimg/1043416821202.rid";
            ImageReader reader = new RawImageReader(IntImageFactory.getInstance(), path);
            reader.read();
            
            Image image = reader.getImage();
            SecondOrder stat = new SecondOrder(image);
            Image var = stat.varianceImage();
            
            ImageWriter writer = new TIFFWriter(var, "/home/fwilhelm/var.img.rid.2");
            writer.write();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
