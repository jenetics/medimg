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
import javax.swing.filechooser.FileSystemView;

import org.wewi.medimg.image.io.BMPReaderFactory;
import org.wewi.medimg.image.io.BMPWriterFactory;
import org.wewi.medimg.image.io.ImageFormatTypes;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageWriterFactory;
import org.wewi.medimg.image.io.JPEGReaderFactory;
import org.wewi.medimg.image.io.JPEGWriterFactory;
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
        for (int i = 0; i < ImageFormatTypes.TYPES.length; i++) {
            addChoosableFileFilter(new ImageFileFilter(ImageFormatTypes.TYPES[i]));    
        }
        setCurrentDirectory(new File(ViewerPreferences.getInstance().getMostRecentFile()));
        
        addActionListener(this);
        
        rangePanel = new RangePanel(this);
        //setAccessory(rangePanel);
        
        setFileView(new ImageFileView(this));
        setDoubleBuffered(true);
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
            } else if (ImageFormatTypes.JPEG_IMAGES.equals(type)) {
                imageReaderFactory = new JPEGReaderFactory();
            }
            imageReaderFactory.setRange(rangePanel.getRange());
        } else {
            if (ImageFormatTypes.TIFF_IMAGES.equals(type)) {
                imageWriterFactory = new TIFFWriterFactory();
            } else if (ImageFormatTypes.BMP_IMAGES.equals(type)) {
                imageWriterFactory = new BMPWriterFactory();
            } else if (ImageFormatTypes.JPEG_IMAGES.equals(type)) {
                imageWriterFactory = new JPEGWriterFactory();
            } else if (ImageFormatTypes.RAW_IMAGE.equals(type)) {
                imageWriterFactory = new RawImageWriterFactory();
            }
            imageWriterFactory.setRange(rangePanel.getRange());
        }
        
        //Speichern des Verzeichnisses in den ViewerPreferences
        String fileName = getCurrentDirectory().toString();
        ViewerPreferences.getInstance().setMostRecentFile(fileName);
    }
    
}
