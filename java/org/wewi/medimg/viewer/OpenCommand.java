/**
 * OpenCommand.java
 *
 * Created on 27. Juni 2002, 22:38
 */

package org.wewi.medimg.viewer;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import java.awt.Font;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.ImageIOProgressListener;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.image.io.ImageIOProgressEvent;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class OpenCommand implements Command, ImageIOProgressListener {
    private Viewer viewer;
    private String selectedFile;
    private ProgressFrame progressFrame;
    
    
    /** Creates a new instance of OpenCommand */
    public OpenCommand(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public OpenCommand(Viewer viewer, String file) {
        this(viewer);  
        selectedFile = file;
    }
    
    
    public void execute() {
        ImageFileChooser chooser = new ImageFileChooser();
        chooser.setDialogTitle("Datensatz auswählen");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setFont(new Font("Dialog", Font.PLAIN, 12));
        
        int returnVal = chooser.showOpenDialog(viewer);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        
        ImageReaderFactory readerFactory = chooser.getImageReaderFactory();
        String fileName = chooser.getSelectedFile().getAbsolutePath();
        ImageReader reader = readerFactory.createImageReader(ImageDataFactory.getInstance(),
                                                             new File(fileName));
        
        
        reader.addProgressListener(this);
        selectedFile = chooser.getSelectedFile().toString();
        progressFrame = new ProgressFrame(); 
        progressFrame.setTitle("Öffnen");
        progressFrame.setSubTitle("Beim Laden von: " + fileName);
        viewer.addViewerDesktopFrame(progressFrame);
        
        ImageReaderThread readerThread = new ImageReaderThread(reader, viewer);
        readerThread.setPriority(Thread.MIN_PRIORITY);
        readerThread.start();      
    }
    
	/**
	 * @see org.wewi.medimg.image.io.ImageIOProgressListener#progressChanged(ProgressEvent)
	 */
	public void progressChanged(ImageIOProgressEvent event) {
        if (event.isFinished()) {
            progressFrame.setVisible(false);
            viewer.removeViewerDesktopFrame(progressFrame);
            
            Image image = ((ImageReader)event.getSource()).getImage();
            ImageViewer iv = new ImageViewer(selectedFile, image);
            
            int sizeX = image.getMaxX() - image.getMinX() + 1;
            int sizeY = image.getMaxY() - image.getMinY() + 1;
            iv.setPreferredSize(new Dimension(sizeX, sizeY));            
            iv.pack();
            viewer.addViewerDesktopFrame(iv);                    
        } else {
            progressFrame.setProgress(event.getProgress());
        }
	}

}








