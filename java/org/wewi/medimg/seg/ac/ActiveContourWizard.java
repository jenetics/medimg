/*
 * ActiveContourWizard.java
 *
 * Created on 19. September 2002, 09:11
 */

package org.wewi.medimg.seg.ac;

import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.JFileChooser;

import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.ImageIOProgressEvent;
import org.wewi.medimg.image.io.ImageIOProgressListener;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.viewer.Command;
import org.wewi.medimg.viewer.ImageFileChooser;
import org.wewi.medimg.viewer.ProgressFrame;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.wizard.Wizard;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class ActiveContourWizard extends Wizard {
    
    private final class OpenCommand implements Command, ImageIOProgressListener {
        private Viewer viewer;
        private String selectedFile;
        private ProgressFrame progressFrame;
        
        
        /** Creates a new instance of OpenCommand */
        public OpenCommand(Viewer viewer) {
            this.viewer = viewer;
        }
        
        public OpenCommand(Viewer viewer, String file) {
            this(viewer);    
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
                ActiveContourImageViewer iv = new ActiveContourImageViewer("AC: " + selectedFile, image);
                imageViewer = iv;
                iv.setActiveContour(contour);
                
                int sizeX = image.getMaxX() - image.getMinX() + 1;
                int sizeY = image.getMaxY() - image.getMinY() + 1;
                iv.setPreferredSize(new Dimension(sizeX, sizeY));            
                iv.pack();
                viewer.addViewerDesktopFrame(iv); 
                
                minimizer = new SnakeGreedyMinimizer(image, contour);
                ait = minimizer.getAlgorithmIterator();                   
            } else {
                progressFrame.setProgress(event.getProgress());
            }
        }

    }
    
    
    
    private static final String MENU_NAME = "Aktive Konturen";
    
    private ActiveContourImageViewer imageViewer;
    private ActiveContour contour = new ActivePolygon();
    private SnakeGreedyMinimizer minimizer;
    private AlgorithmIterator ait;
    
    /** Creates new form ActiveContourWizard */
    public ActiveContourWizard() {
        super(MENU_NAME, true, true, false, false);
        initComponents();
        init();
    }
    
    private void init() {
        Viewer.getInstance().setOpenCommand(new OpenCommand(Viewer.getInstance()));    
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        southPanel = new javax.swing.JPanel();
        nextIterationButton = new javax.swing.JButton();
        centerPanel = new javax.swing.JPanel();

        southPanel.setLayout(new java.awt.GridLayout());

        nextIterationButton.setFont(new java.awt.Font("Dialog", 0, 12));
        nextIterationButton.setText("N\u00e4chste Iteration");
        nextIterationButton.setDoubleBuffered(true);
        nextIterationButton.setPreferredSize(new java.awt.Dimension(120, 26));
        nextIterationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextIterationButtonActionPerformed(evt);
            }
        });

        southPanel.add(nextIterationButton);

        getContentPane().add(southPanel, java.awt.BorderLayout.NORTH);

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents

    private void nextIterationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextIterationButtonActionPerformed
        if (ait.hasNextIteration()) {
            System.out.println("next");
            ait.nextIteration();    
        } else {
            System.out.println("fertig");
        }
    }//GEN-LAST:event_nextIterationButtonActionPerformed

    public String getMenuName() {
        return MENU_NAME;
    }    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton nextIterationButton;
    private javax.swing.JPanel southPanel;
    private javax.swing.JPanel centerPanel;
    // End of variables declaration//GEN-END:variables
    
}
