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
