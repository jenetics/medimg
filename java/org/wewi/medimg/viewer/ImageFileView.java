package org.wewi.medimg.viewer;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

import org.wewi.medimg.image.io.ImageFormatEnum;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class ImageFileView extends FileView {
    private ImageFileChooser parent;
    
    private ImageIcon icon;

	/**
	 * Constructor for ImageFileView.
	 */
	public ImageFileView(ImageFileChooser parent) {
		super();
        icon = new ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/logo_seg_small.gif"));
        this.parent = parent;
	}

	/**
	 * @see javax.swing.filechooser.FileView#getIcon(File)
	 */
	public Icon getIcon(File file) {       
        FileFilter filter = parent.getFileFilter();
        if (!(filter instanceof ImageFileFilter)) {
            return super.getIcon(file);    
        }
        
        ImageFormatEnum type = ((ImageFileFilter)filter).getImageFormatType();
        
        if (ImageFormatEnum.RAW_IMAGE.equals(type)) {
            if (file.isDirectory()) {
                return super.getIcon(file);    
            } 
            if (file.toString().toLowerCase().endsWith(".rid")) {
                return icon;    
            }
        } else if (ImageFormatEnum.TIFF_IMAGES.equals(type)) {
            if (!file.isDirectory()) {
                if (file.getName().toLowerCase().endsWith(".tif")) {
                    return icon;    
                }
                return super.getIcon(file);    
            }
            String[] files = file.list();
            if (endsWith(files, ".tif")) {
                return icon;    
            }
            return super.getIcon(file);
        } else if (ImageFormatEnum.BMP_IMAGES.equals(type)) {
            if (!file.isDirectory()) {
                if (file.getName().toLowerCase().endsWith(".bmp")) {
                    return icon;    
                }                
                return super.getIcon(file);    
            } 
            String[] files = file.list();
            if (endsWith(files, ".bmp")) {
                return icon;    
            }                       
        } else if (ImageFormatEnum.JPEG_IMAGES.equals(type)) {
            if (!file.isDirectory()) {
                if (file.getName().toLowerCase().endsWith(".jpg")) {
                    return icon;    
                }                
                return super.getIcon(file);    
            } 
            String[] files = file.list();
            if (endsWith(files, ".jpg")) {
                return icon;    
            }                       
        }
        
		return super.getIcon(file);
	}
    
    private boolean endsWith(String[] files, String pattern) {
        if (files == null) {
            return false;    
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].toLowerCase().endsWith(pattern)) {
                return true;    
            }    
        }   
        return false; 
    }

	/**
	 * @see javax.swing.filechooser.FileView#getName(File)
	 */
	public String getName(File file) {
		return super.getName(file);
	}

	/**
	 * @see javax.swing.filechooser.FileView#getTypeDescription(File)
	 */
	public String getTypeDescription(File file) {
		return super.getTypeDescription(file);
	}

	/**
	 * @see javax.swing.filechooser.FileView#isTraversable(File)
	 */
	public Boolean isTraversable(File file) {
		return super.isTraversable(file);
	}
    
    /**
     * @see javax.swing.filechooser.FileView#getDescription(File)
     */
    public String getDescription(File file) {
        return super.getDescription(file);
    }    

}
