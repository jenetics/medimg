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
import org.wewi.medimg.image.io.Range;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JPanel;
import javax.swing.JLabel;
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
    private RangePanel rangePanel;
    private Range range;
    
    /** Creates a new instance of ImageFileChooser */
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
        addChoosableFileFilter(new ImageFileFilter(ImageFormatTypes.RAW_IMAGE));
        addChoosableFileFilter(new ImageFileFilter(ImageFormatTypes.BMP_IMAGES));
        addChoosableFileFilter(new ImageFileFilter(ImageFormatTypes.TIFF_IMAGES));
        
        addActionListener(this);
        
        rangePanel = new RangePanel();
        setAccessory(rangePanel);
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
        ImageFormatTypes type = ((ImageFileFilter)getFileFilter()).getImageFormatType();
        if (getDialogType() == OPEN_DIALOG) {
            if (ImageFormatTypes.TIFF_IMAGES.equals(type)) {
                imageReaderFactory = new TIFFReaderFactory();
            } else if (ImageFormatTypes.BMP_IMAGES.equals(type)) {
                imageReaderFactory = new BMPReaderFactory();
            } else if (ImageFormatTypes.RAW_IMAGE.equals(type)) {
                imageReaderFactory = new RawImageReaderFactory();
            }
            imageReaderFactory.setRange(rangePanel.getRange());
        } else {
            if (ImageFormatTypes.TIFF_IMAGES.equals(type)) {
                imageWriterFactory = new TIFFWriterFactory();
            } else if (ImageFormatTypes.BMP_IMAGES.equals(type)) {
                imageWriterFactory = new BMPWriterFactory();
            } else if (ImageFormatTypes.RAW_IMAGE.equals(type)) {
                imageWriterFactory = new RawImageWriterFactory();
            }
            imageWriterFactory.setRange(rangePanel.getRange());
        }
    }
    
}
