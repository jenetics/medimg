/*
 * OpenCommand.java
 *
 * Created on 27. Juni 2002, 22:38
 */

package org.wewi.medimg.viewer;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class OpenCommand implements Command {
    private Viewer viewer;
    
    /** Creates a new instance of OpenCommand */
    public OpenCommand(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public void execute() {
        ImageFileChooser chooser = new ImageFileChooser();
        chooser.setDialogTitle("Datensatz auswählen");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //chooser.setCurrentDirectory(new File(ViewerPreferences.getInstance().getMostRecentFile()));
        
        int returnVal = chooser.showOpenDialog(viewer);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        ImageReaderFactory readerFactory = chooser.getImageReaderFactory();
        String fileName = chooser.getSelectedFile().getAbsolutePath();
        ImageReader reader = readerFactory.createImageReader(ImageDataFactory.getInstance(),
                                                             new File(fileName));
        //reader.setRange(new Range(101, 110));
        try {
            reader.read();
        } catch (Exception e) {
            System.err.println("Viewer.openMenuItemActionPerformed: " + e);
            JOptionPane.showMessageDialog(viewer, "Kann Datei: \n" + fileName + "\n nicht öffnen", 
                                                 "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }     
        Image image = reader.getImage();
        ImageViewer iv = new ImageViewer(chooser.getSelectedFile().toString(), image);
        int sizeX = image.getMaxX() - image.getMinX() + 1;
        int sizeY = image.getMaxY() - image.getMinY() + 1;
        iv.setPreferredSize(new Dimension(sizeX, sizeY));            
        iv.pack();
        viewer.addViewerDesktopFrame(iv);        
    }
    
}
