/**
 * SaveImageMeasure.java
 * 
 * Created on 19.01.2003, 11:53:35
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.File;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.RawImageWriter;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class SaveImageMeasure implements ValidationMeasure {

    /**
     * Constructor for SaveImageMeasure.
     */
    public SaveImageMeasure() {
        super();
    }

    /**
     * @see org.wewi.medimg.seg.validation.ValidationMeasure#measure(Image, Image)
     */
    public double measure(Image modelImage, Image segmentedImage) {
        try {
            ImageWriter writer = new RawImageWriter(segmentedImage, 
                                    new File("/home/fwilhelm/Workspace/Projekte/Diplom/code/data/segimg/t1.n3.rf20/" +
                                             System.currentTimeMillis() + ".rid"));
            writer.write();
        } catch (Exception e) {
            System.err.println("SaveImageMeasure");
            e.printStackTrace();   
        } finally {
            return 0;    
        }
    }

}
