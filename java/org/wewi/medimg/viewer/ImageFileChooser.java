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
 * ImageFileChooser.java
 *
 * Created on 8. April 2002, 17:11
 */

package org.wewi.medimg.viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import org.wewi.medimg.image.io.BMPReaderFactory;
import org.wewi.medimg.image.io.BMPWriterFactory;
import org.wewi.medimg.image.io.ImageFormatEnum;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageWriterFactory;
import org.wewi.medimg.image.io.JPEGReaderFactory;
import org.wewi.medimg.image.io.JPEGWriterFactory;
import org.wewi.medimg.image.io.PNGReaderFactory;
import org.wewi.medimg.image.io.PNGWriterFactory;
import org.wewi.medimg.image.io.Range;
import org.wewi.medimg.image.io.RawImageReaderFactory;
import org.wewi.medimg.image.io.RawImageWriterFactory;
import org.wewi.medimg.image.io.TIFFReaderFactory;
import org.wewi.medimg.image.io.TIFFWriterFactory;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ImageFileChooser extends JFileChooser implements ActionListener {
    private ImageReaderFactory imageReaderFactory;
    private ImageWriterFactory imageWriterFactory;
    private RangePanel rangePanel;
    private Range range;
    
    public ImageFileChooser() {
        super();
        init();
    }
    
    public ImageFileChooser(File file) {
        super(file);
        init();
    }
    
    public ImageFileChooser(File file, FileSystemView fsv) {
        super(file, fsv);
        init();
    }
    
    public ImageFileChooser(FileSystemView fsv) {
        super(fsv);
        init();
    }
    
    public ImageFileChooser(String file) {
        super(file);
        init();
    }
    
    public ImageFileChooser(String file, FileSystemView fsv) {
        super(file, fsv);
        init();
    }   
    
    private void init() {
        for (int i = 0; i < ImageFormatEnum.TYPES.length; i++) {
            addChoosableFileFilter(new ImageFileFilter(ImageFormatEnum.TYPES[i]));    
        }
        
        setCurrentDirectory(new File(ViewerPreferences.getInstance().getMostRecentFile()));
        setCurrentFileType(ViewerPreferences.getInstance().getMostRecentFileType());
        
        addActionListener(this);
        
        rangePanel = new RangePanel(this);
        //setAccessory(rangePanel);
        
        setFileView(new ImageFileView(this));
        setDoubleBuffered(true);
    }
    
    private void setCurrentFileType(String type) {
        FileFilter[] filters = getChoosableFileFilters(); 
        ImageFileFilter f = null;
        for (int i = 0; i < filters.length; i++) {
            if (filters[i] instanceof ImageFileFilter) {
                f = (ImageFileFilter)filters[i];
                if (f.getImageFormatType().toString().equals(type)) {
                    setFileFilter(filters[i]);
                    return;
                }  
            }  
        }   
    }
    
    public Range getRange() {
        return range;
    }
    
    public void setRange(Range range) {
        this.range = new Range(range.getMinSlice(), range.getMaxSlice(), range.getStride());
    }
    
    public ImageReaderFactory getImageReaderFactory() {
        return imageReaderFactory;
    }
    
    public ImageWriterFactory getImageWriterFactory() {
        return imageWriterFactory;
    }

    
    public void actionPerformed(ActionEvent actionEvent) {       
        if (!(getFileFilter() instanceof ImageFileFilter)) {
            return;    
        }
        
        ImageFormatEnum type = ((ImageFileFilter)getFileFilter()).getImageFormatType();
        if (getDialogType() == OPEN_DIALOG) {
            if (ImageFormatEnum.TIFF_IMAGES.equals(type)) {
                imageReaderFactory = new TIFFReaderFactory();
            } else if (ImageFormatEnum.BMP_IMAGES.equals(type)) {
                imageReaderFactory = new BMPReaderFactory();
            } else if (ImageFormatEnum.RAW_IMAGE.equals(type)) {
                imageReaderFactory = new RawImageReaderFactory();
            } else if (ImageFormatEnum.JPEG_IMAGES.equals(type)) {
                imageReaderFactory = new JPEGReaderFactory();
            } else if (ImageFormatEnum.PNG_IMAGES.equals(type)) {
                imageReaderFactory = new PNGReaderFactory();
            }
            imageReaderFactory.setRange(rangePanel.getRange());
        } else {
            if (ImageFormatEnum.TIFF_IMAGES.equals(type)) {
                imageWriterFactory = new TIFFWriterFactory();
            } else if (ImageFormatEnum.BMP_IMAGES.equals(type)) {
                imageWriterFactory = new BMPWriterFactory();
            } else if (ImageFormatEnum.JPEG_IMAGES.equals(type)) {
                imageWriterFactory = new JPEGWriterFactory();
            } else if (ImageFormatEnum.RAW_IMAGE.equals(type)) {
                imageWriterFactory = new RawImageWriterFactory();
            } else if (ImageFormatEnum.PNG_IMAGES.equals(type)) {
                imageWriterFactory = new PNGWriterFactory();
            }
            imageWriterFactory.setRange(rangePanel.getRange());
        }
        
        //Speichern des Verzeichnisses in den ViewerPreferences
        String fileName = getCurrentDirectory().toString();
        ViewerPreferences.getInstance().setMostRecentFile(fileName);
        if (getFileFilter() instanceof ImageFileFilter) {
            String fileType = ((ImageFileFilter)getFileFilter()).getImageFormatType().toString();   
            ViewerPreferences.getInstance().setMostRecentFileType(fileType);
        }        
        
    }
    
}
