/**
 * ImageData.java
 *
 * Created on 5. Dezember 2001, 14:45
 */

package org.wewi.medimg.image;

import org.wewi.medimg.util.Timer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.3
 */
public class ImageData extends AbstractImage { 
    
    ImageData() {
       super();
    } 
    
    public ImageData(ImageData id) {
        super(id);
    }       
    
    public ImageData(Dimension dim) {
        super(dim);
    }
    
    public ImageData(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }
     
    public ImageData(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }
    
    /**
     * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
     */
    protected DiscreteData createDiscreteData(int size) {
        return new IntData(size);
    }     
    
    
    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return new ImageData(this);
    }   
    
  
    
    
    
    public static void main(String[] args) {
        Timer timer = new Timer("ImageData");
        
        Image image = new ImageData(181, 217, 181); 
         
        
        timer.start();
        for (int i = 0; i < image.getNVoxels(); i++) {
            image.getColor(i);    
        }
        timer.stop();
        timer.print();
        
        
        timer.start();
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            image.getColor(i);    
        }
        timer.stop();
        timer.print();        
        /*
        timer.start();
        for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
            it.next();    
        }
        timer.stop();
        timer.print();     
        
        timer.start();
        int[] p = new int[3];
        for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
            it.next(p);    
        }
        timer.stop();
        timer.print(); 
        */           
    }  

}



