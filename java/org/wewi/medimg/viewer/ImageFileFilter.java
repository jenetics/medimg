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
 * ImageFileFilter.java
 *
 * Created on 8. April 2002, 18:27
 */

package org.wewi.medimg.viewer;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.wewi.medimg.image.io.ImageFormatEnum;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageFileFilter extends FileFilter {
        private ImageFormatEnum type;
        
        public ImageFileFilter(ImageFormatEnum type) {
            this.type = type;
        }
        
        public boolean accept(File file) {
            if (type.equals(ImageFormatEnum.TIFF_IMAGES)) {
                if (!file.isDirectory()) {
                     if (file.getName().toLowerCase().endsWith(".tif")) {
                        return true;   
                     }
                     else return false;  
                }
                return true;
            } else if (type.equals(ImageFormatEnum.BMP_IMAGES)) {
                if (!file.isDirectory()) {
                     if (file.getName().toLowerCase().endsWith(".bmp")) {
                        return true;   
                     }
                     else return false;  
                }
                return true;
            } else if (type.equals(ImageFormatEnum.JPEG_IMAGES)) {
                if (!file.isDirectory()) {
                     if (file.getName().toLowerCase().endsWith(".jpg")) {
                        return true;   
                     }
                     else return false;  
                }
                return true;
            } else if (type.equals(ImageFormatEnum.RAW_IMAGE)) {
                if (file.isDirectory()) {
                    return true;
                }
                return file.toString().toLowerCase().endsWith(".rid");
            }
            return false;
        }
        
        public String getDescription() {
            return type.toString();
        }
 
        public ImageFormatEnum getImageFormatType() {
            return type;
        }
}
