/*
 * ImageFileChooser.java
 *
 * Created on 8. April 2002, 17:11
 */

package org.wewi.medimg.viewer;

import org.wewi.medimg.image.ImageFormatTypes;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.BMPReader;
import org.wewi.medimg.image.io.RawImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.TIFFReaderFactory;
import org.wewi.medimg.image.io.BMPReaderFactory;
import org.wewi.medimg.image.io.RawImageReaderFactory;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.TIFFWriter;
import org.wewi.medimg.image.io.BMPWriter;
import org.wewi.medimg.image.io.RawImageWriter;
import org.wewi.medimg.image.io.ImageWriterFactory;
import org.wewi.medimg.image.io.TIFFWriterFactory;
import org.wewi.medimg.image.io.BMPWriterFactory;
import org.wewi.medimg.image.io.RawImageWriterFactory;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import javax.swing.JPanel;
import javax.swing.JComboBox;

import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageFileChooser extends JFileChooser implements ActionListener {
    private ImageReaderFactory imageReaderFactory;
    private ImageWriterFactory imageWriterFactory;
    
    /** Creates a new instance of ImageFileChooser */
    public ImageFileChooser() {
        init();
    }
    
    private void init() {
        addChoosableFileFilter(new ImageFileFilter(ImageFormatTypes.TIFF_IMAGES));
        addChoosableFileFilter(new ImageFileFilter(ImageFormatTypes.BMP_IMAGES));
        addChoosableFileFilter(new ImageFileFilter(ImageFormatTypes.RAW_IMAGE));
        
        addActionListener(this);
    }
    
    
    public ImageReaderFactory getImageReaderFactory() {
        return imageReaderFactory;
    }
    
    public ImageWriter getImageWriter() {
        return null;
    }
    
    public void actionPerformed(ActionEvent actionEvent) {
        ImageFormatTypes type = ((ImageFileFilter)getFileFilter()).getImageFormatType();
        if (getDialogType() == OPEN_DIALOG) {
            if (ImageFormatTypes.TIFF_IMAGES.equals(type)) {
                imageReaderFactory = TIFFReaderFactory.getInstance();
            } else if (ImageFormatTypes.BMP_IMAGES.equals(type)) {
                imageReaderFactory = BMPReaderFactory.getInstance();
            } else if (ImageFormatTypes.RAW_IMAGE.equals(type)) {
                imageReaderFactory = RawImageReaderFactory.getInstance();
            }
        } else {
            if (ImageFormatTypes.TIFF_IMAGES.equals(type)) {
                imageWriterFactory = TIFFWriterFactory.getInstance();
            } else if (ImageFormatTypes.BMP_IMAGES.equals(type)) {
                imageWriterFactory = BMPWriterFactory.getInstance();
            } else if (ImageFormatTypes.RAW_IMAGE.equals(type)) {
                imageWriterFactory = RawImageWriterFactory.getInstance();
            }
        }
    }
    
}
