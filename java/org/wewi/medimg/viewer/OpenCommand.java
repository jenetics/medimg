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
 * OpenCommand.java
 *
 * Created on 27. Juni 2002, 22:38
 */

package org.wewi.medimg.viewer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.io.File;

import javax.swing.JFileChooser;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.io.ImageIOProgressEvent;
import org.wewi.medimg.image.io.ImageIOProgressListener;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.viewer.image.ImageViewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class OpenCommand implements Command, ImageIOProgressListener {
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
        ImageReader reader = readerFactory.createImageReader(IntImageFactory.getInstance(),
                                                             new File(fileName));
        
        
        reader.addProgressListener(this);
        selectedFile = chooser.getSelectedFile().toString();
        progressFrame = new ProgressFrame(); 
        progressFrame.setTitle("Open");
        progressFrame.setSubTitle("Loading: " + fileName);
        viewer.addViewerDesktopFrame(progressFrame);
        
        ImageReaderThread readerThread = new ImageReaderThread(reader, viewer);
        readerThread.setPriority(Thread.MIN_PRIORITY);
        readerThread.start();      
    }
    
    /**
     * Das Laden des Bilder erfolgt asynchron. Diese
     * Methode wird aufgerufen, wenn sich der Lesefortschritt
     * des Bildes geändert hat. Im Besonderen wird das Ende
     * des Einlesen des Bildes angezeigt.
     * 
     * @see org.wewi.medimg.image.io.ImageIOProgressListener#progressChanged(ProgressEvent)
     */
    public void progressChanged(ImageIOProgressEvent event) {
        if (!event.isFinished()) {
            progressFrame.setProgress(event.getProgress());
            return;    
        }
        
        progressFrame.setVisible(false);
        viewer.removeViewerDesktopFrame(progressFrame);
        
        Image image = ((ImageReader)event.getSource()).getImage();
        int iw = image.getMaxX() - image.getMinX() + 1;
        int ih = image.getMaxY() - image.getMinY() + 1;
        int vw = viewer.getWidth()/2;
        int vh = viewer.getHeight()/2;
        Point pos = new Point(0, 0);
        Dimension size = new Dimension(Math.min(iw, vw), Math.min(iw, vw));
        
        ImageViewer iv = new ImageViewer(selectedFile, image);           
        iv.pack();
        viewer.addViewerDesktopFrame(iv, pos, size);  
              
    }

}








