/*
 * SaveCommand.java
 *
 * Created on 27. Juni 2002, 22:52
 */

package org.wewi.medimg.viewer;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.ImageWriterFactory;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class SaveCommand implements Command {
    private Image image;
    private Component parent;
    
    /** Creates a new instance of SaveCommand */
    public SaveCommand(Component parent, Image image) {
        this.parent = parent;
        this.image = image;
    }
    
    public void execute() {
        ImageFileChooser chooser = new ImageFileChooser();
        chooser.setDialogTitle("Segmentierergebnis speichern");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        int returnVal = chooser.showSaveDialog(parent);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        ImageWriterFactory writerFactory = chooser.getImageWriterFactory();
        String fileName = chooser.getSelectedFile().getAbsolutePath();
        ImageWriter writer = writerFactory.createImageWriter(image,
                                                             new File(fileName));
        //reader.setRange(new Range(101, 110));
        try {
            writer.write();
        } catch (Exception e) {
            System.err.println("Viewer.openMenuItemActionPerformed: " + e);
            JOptionPane.showMessageDialog(parent, "Kann Datei: \n" + fileName + "\n nicht öffnen", 
                                                 "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }         
    }
    
}
