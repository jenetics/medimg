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
                if (!file.isDirectory()) {
                     if (file.getName().endsWith(".tif")) {
                        return true;   
                     }
                     else return false;  
                }
                return true;
            } else if (type.equals(ImageFormatTypes.BMP_IMAGES)) {
                if (!file.isDirectory()) {
                     if (file.getName().endsWith(".bmp")) {
                        return true;   
                     }
                     else return false;  
                }
                return true;
            } else if (type.equals(ImageFormatTypes.JPEG_IMAGES)) {
                if (!file.isDirectory()) {
                     if (file.getName().endsWith(".jpg")) {
                        return true;   
                     }
                     else return false;  
                }
                return true;
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
