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
 * SaveCommand.java
 *
 * Created on 27. Juni 2002, 22:52
 */

package org.wewi.medimg.viewer;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.io.ImageIOProgressEvent;
import org.wewi.medimg.image.io.ImageIOProgressListener;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.ImageWriterFactory;
import org.wewi.medimg.image.io.ImageWriterThread;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class SaveCommand implements Command, ImageIOProgressListener {
    private Image image;
    private Component parent;
    private ProgressFrame progressFrame;
    
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
        ImageWriter imageWriter = writerFactory.createImageWriter(image, new File(fileName));
        imageWriter.addProgressListener(this);
        
        progressFrame = new ProgressFrame(); 
        progressFrame.setTitle("Speichern");
        progressFrame.setSubTitle("Beim Speichern von: " + fileName);
        Viewer.getInstance().addViewerDesktopFrame(progressFrame);
                                                             
        ImageWriterThread writerThread = new ImageWriterThread(imageWriter);
        writerThread.setPriority(Thread.MIN_PRIORITY);
        writerThread.start();
                
    }
    
    public void progressChanged(ImageIOProgressEvent event) {
        Viewer viewer = Viewer.getInstance();
        if (event.isFinished()) {
            progressFrame.setVisible(false);
            viewer.removeViewerDesktopFrame(progressFrame);                  
        } else {
            progressFrame.setProgress(event.getProgress());
        }        
    }

}
