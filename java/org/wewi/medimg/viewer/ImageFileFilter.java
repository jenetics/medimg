/**
 * ImageFileFilter.java
 *
 * Created on 8. April 2002, 18:27
 */

package org.wewi.medimg.viewer;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.wewi.medimg.image.io.ImageFormatTypes;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageFileFilter extends FileFilter {
        private ImageFormatTypes type;
        
        public ImageFileFilter(ImageFormatTypes type) {
            this.type = type;
        }
        
        public boolean accept(File file) {
            if (type.equals(ImageFormatTypes.TIFF_IMAGES)) {
                return file.isDirectory();
            } else if (type.equals(ImageFormatTypes.BMP_IMAGES)) {
                return file.isDirectory();
            } else if (type.equals(ImageFormatTypes.JPEG_IMAGES)) {
                return file.isDirectory();
            } else if (type.equals(ImageFormatTypes.RAW_IMAGE)) {
                if (file.isDirectory()) {
                    return true;
                }
                return file.toString().endsWith(".rid");
            }
            return false;
        }
        
        public String getDescription() {
            return type.toString();
        }
 
        public ImageFormatTypes getImageFormatType() {
            return type;
        }
}
