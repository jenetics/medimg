/**
 * ActiveContourWizard.java
 *
 * Created on 19. September 2002, 09:11
 */

package org.wewi.medimg.seg.ac;

import java.beans.PropertyVetoException;
import java.io.File;
import java.text.NumberFormat;

import javax.swing.JFileChooser;

import org.wewi.medimg.alg.AlgorithmIterator;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.VoxelSelectorEvent;
import org.wewi.medimg.image.VoxelSelectorListener;
import org.wewi.medimg.image.io.ImageIOProgressEvent;
import org.wewi.medimg.image.io.ImageIOProgressListener;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.image.io.ReaderThreadEvent;
import org.wewi.medimg.image.io.ReaderThreadListener;
import org.wewi.medimg.viewer.ImageFileChooser;
import org.wewi.medimg.viewer.ImageViewer;
import org.wewi.medimg.viewer.ImageViewerEvent;
import org.wewi.medimg.viewer.ImageViewerListener;
import org.wewi.medimg.viewer.LogHandlerPanel;
import org.wewi.medimg.viewer.ProgressFrame;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.wizard.Wizard;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ActiveContourWizard extends Wizard implements ImageViewerListener,
                                                           ReaderThreadListener,
                                                           VoxelSelectorListener,
                                                           ImageIOProgressListener {
                                                               
    private static final String MENU_NAME = "Aktive Konturen";

    private ActiveContour contour;
    private ActiveContour originalContour;
    private SnakeGreedyMinimizer minimizer;
    private double minimizerAlpha;
    private double minimizerBeta;
    private double minimizerOuterEnergyWeight;
    private NumberFormat format;
    private AlgorithmIterator ait;
    
    private Image image;
    private ImageViewer imageViewer;
    private ProgressFrame progressFrame;
    
    private LogHandlerPanel logPanel;
    
    /** Creates new form ActiveContourWizard */
    public ActiveContourWizard() {
        super(MENU_NAME, true, true, false, false);
        initComponents();
        init();
    }
    
    private void init() { 
        imageViewer = null;
        
        logPanel = new LogHandlerPanel();
        loggerPanel.add(logPanel);
        logger.addHandler(logPanel.getHandler());
        
        //Initialisierung des Polygons
        contour = new ActivePolygon();
        
        format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        format.setMinimumFractionDigits(2);
        format.setMaximumIntegerDigits(1);
        format.setMinimumIntegerDigits(1);        
    }
    
    public void update(ImageViewerEvent event) {
        if (event.isClosed()) {
            imageViewer = null;
            image = null;
        }
    } 
    
    public void voxelSelected(VoxelSelectorEvent event) {
        if (contour == null) {
            return;
        }
        
        contour.addBasePoint(event.getSelectedImagePoint());
        imageViewer.repaintImage();
    }
    
    public void progressChanged(ImageIOProgressEvent event) {
        if (!event.isFinished()) {
            progressFrame.setProgress(event.getProgress());
            return;    
        }
        
        progressFrame.setVisible(false);
        Viewer.getInstance().removeViewerDesktopFrame(progressFrame);        
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        southPanel = new javax.swing.JPanel();
        fileLoadButton = new javax.swing.JButton();
        centerPanel = new javax.swing.JPanel();
        parameterPanel = new javax.swing.JPanel();
        alphaLabel = new javax.swing.JLabel();
        alphaSlider = new javax.swing.JSlider();
        betaLabel = new javax.swing.JLabel();
        betaSlider = new javax.swing.JSlider();
        outerEnergyWeigthLabel = new javax.swing.JLabel();
        outerEnergySlider = new javax.swing.JSlider();
        loggerPanel = new javax.swing.JPanel();
        northPanel = new javax.swing.JPanel();
        newContourButton = new javax.swing.JButton();
        resetContourButton = new javax.swing.JButton();
        nextIterationButton = new javax.swing.JButton();
        doAllButton = new javax.swing.JButton();

        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        fileLoadButton.setFont(new java.awt.Font("Dialog", 0, 12));
        fileLoadButton.setText("Datensatz laden...");
        fileLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileLoadButtonActionPerformed(evt);
            }
        });

        southPanel.add(fileLoadButton);

        getContentPane().add(southPanel, java.awt.BorderLayout.NORTH);

        centerPanel.setLayout(new java.awt.GridLayout(2, 0));

        parameterPanel.setLayout(new java.awt.GridLayout(3, 2));

        parameterPanel.setBorder(new javax.swing.border.TitledBorder(null, "Parameter des Greedy-Algorithmus", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12)));
        alphaLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        alphaLabel.setText("    Innere Energie (Alpha):    0,20");
        parameterPanel.add(alphaLabel);

        alphaSlider.setMajorTickSpacing(10);
        alphaSlider.setMinorTickSpacing(5);
        alphaSlider.setPaintTicks(true);
        alphaSlider.setValue(20);
        alphaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                alphaSliderStateChanged(evt);
            }
        });

        parameterPanel.add(alphaSlider);

        betaLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        betaLabel.setText("    Innere Energie (Beta):    0,20");
        parameterPanel.add(betaLabel);

        betaSlider.setMajorTickSpacing(10);
        betaSlider.setMinorTickSpacing(5);
        betaSlider.setPaintTicks(true);
        betaSlider.setValue(20);
        betaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                betaSliderStateChanged(evt);
            }
        });

        parameterPanel.add(betaSlider);

        outerEnergyWeigthLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        outerEnergyWeigthLabel.setText("    \u00c4u\u00dfere Energie (We):    0,20");
        parameterPanel.add(outerEnergyWeigthLabel);

        outerEnergySlider.setMajorTickSpacing(10);
        outerEnergySlider.setMinorTickSpacing(5);
        outerEnergySlider.setPaintTicks(true);
        outerEnergySlider.setValue(20);
        outerEnergySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                outerEnergySliderStateChanged(evt);
            }
        });

        parameterPanel.add(outerEnergySlider);

        centerPanel.add(parameterPanel);

        loggerPanel.setLayout(new java.awt.GridLayout(1, 0));

        centerPanel.add(loggerPanel);

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        newContourButton.setFont(new java.awt.Font("Dialog", 0, 12));
        newContourButton.setText("Neue Kontur");
        newContourButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newContourButtonActionPerformed(evt);
            }
        });

        northPanel.add(newContourButton);

        resetContourButton.setFont(new java.awt.Font("Dialog", 0, 12));
        resetContourButton.setText("Zur\u00fccksetzen");
        resetContourButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetContourButtonActionPerformed(evt);
            }
        });

        northPanel.add(resetContourButton);

        nextIterationButton.setFont(new java.awt.Font("Dialog", 0, 12));
        nextIterationButton.setText("N\u00e4chste Iteration");
        nextIterationButton.setDoubleBuffered(true);
        nextIterationButton.setPreferredSize(new java.awt.Dimension(150, 26));
        nextIterationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextIterationButtonActionPerformed(evt);
            }
        });

        northPanel.add(nextIterationButton);

        doAllButton.setFont(new java.awt.Font("Dialog", 0, 12));
        doAllButton.setText("Automatisch");
        doAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doAllButtonActionPerformed(evt);
            }
        });

        northPanel.add(doAllButton);

        getContentPane().add(northPanel, java.awt.BorderLayout.SOUTH);

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new java.awt.Dimension(555, 380));
        setLocation((screenSize.width-555)/2,(screenSize.height-380)/2);
    }//GEN-END:initComponents

    private void resetContourButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetContourButtonActionPerformed
        if (originalContour != null) {
            contour = (ActiveContour)originalContour.clone();
            minimizer = new SnakeGreedyMinimizer(image, contour);
            minimizer.setALPHA(minimizerAlpha);
            minimizer.setBETA(minimizerBeta);
            minimizer.setWE(minimizerOuterEnergyWeight);
            
            if (imageViewer != null) {
                imageViewer.setImageCanvas(new ActivePolygonCanvasAdapter((ActivePolygon)contour));
            }            
            
            ait = minimizer.getAlgorithmIterator();
            imageViewer.repaintImage();
        }
    }//GEN-LAST:event_resetContourButtonActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        try {
            setClosed(true);
            Viewer.getInstance().removeWizard(this);
        } catch (PropertyVetoException pve) {
            logger.throwing(getClass().getName(), "frameInternalFrameClosed()", pve);
        }
    }//GEN-LAST:event_formInternalFrameClosed

    private void outerEnergySliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_outerEnergySliderStateChanged
        minimizerOuterEnergyWeight = (double)outerEnergySlider.getValue()/100d;
        outerEnergyWeigthLabel.setText("    \u00c4u\u00dfere Energie (We):    " + format.format(minimizerOuterEnergyWeight));
        if (minimizer != null) {
            minimizer.setWE(minimizerOuterEnergyWeight);
        }
    }//GEN-LAST:event_outerEnergySliderStateChanged

    private void betaSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_betaSliderStateChanged
        minimizerBeta = (double)betaSlider.getValue()/100d;
        betaLabel.setText("    Innere Energie (Beta):    " + format.format(minimizerBeta));
        if (minimizer != null) {
            minimizer.setBETA(minimizerBeta);
        }
    }//GEN-LAST:event_betaSliderStateChanged

    private void alphaSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_alphaSliderStateChanged
        minimizerAlpha = (double)alphaSlider.getValue()/100d;
        alphaLabel.setText("    Innere Energie (Alpha):    " + format.format(minimizerAlpha));
        if (minimizer != null) {
            minimizer.setALPHA(minimizerAlpha);
        }
    }//GEN-LAST:event_alphaSliderStateChanged

    private void doAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_doAllButtonActionPerformed
        if (ait == null) {
            return;
        }
        
        while (ait.hasNextIteration()) {
            ait.nextIteration();
            imageViewer.repaintImage();
        }
    }//GEN-LAST:event_doAllButtonActionPerformed

    private void newContourButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newContourButtonActionPerformed
        contour = new ActivePolygon();
        originalContour = null;
        minimizer = new SnakeGreedyMinimizer(image, contour);
        minimizer.setALPHA(minimizerAlpha);
        minimizer.setBETA(minimizerBeta);
        minimizer.setWE(minimizerOuterEnergyWeight);
        ait = minimizer.getAlgorithmIterator();

        if (imageViewer != null) {
            imageViewer.setImageCanvas(new ActivePolygonCanvasAdapter((ActivePolygon)contour));
        }
        
        imageViewer.repaintImage();
    }//GEN-LAST:event_newContourButtonActionPerformed

    private void fileLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileLoadButtonActionPerformed
        ImageFileChooser chooser = new ImageFileChooser();
        chooser.setDialogTitle("Datensatz auswählen");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }
        
        ImageReaderFactory readerFactory = chooser.getImageReaderFactory();
        String fileName = chooser.getSelectedFile().getAbsolutePath();
        ImageReader imageReader = readerFactory.createImageReader(ImageDataFactory.getInstance(),
                                                            new File(fileName));
        
        imageReader.addProgressListener(this);
        progressFrame = new ProgressFrame(); 
        progressFrame.setTitle("Öffnen");
        progressFrame.setSubTitle("Beim Laden von: " + fileName);
        Viewer.getInstance().addViewerDesktopFrame(progressFrame);        
        
        ImageReaderThread readerThread = new ImageReaderThread(imageReader, this);
        readerThread.addReaderThreadListener(this);
        readerThread.start();
    }//GEN-LAST:event_fileLoadButtonActionPerformed

    public void imageRead(ReaderThreadEvent event) {
        image = ((ImageReaderThread)event.getSource()).getImageReader().getImage();
        imageViewer = new ImageViewer("", image);
        imageViewer.addImageViewerListener(this);
        imageViewer.addVoxelSelectorListener(this);
        imageViewer.pack();
        imageViewer.show();
        
        java.awt.Point pos = new java.awt.Point(0, 0);
        java.awt.Dimension size = new java.awt.Dimension(300, 300);
        
        Viewer.getInstance().addViewerDesktopFrame(imageViewer, pos, size);
    }
    
    
    private void nextIterationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextIterationButtonActionPerformed
        if (ait == null) {
            return;
        }
        
        if (originalContour == null) {
            if (contour != null) {
                originalContour = (ActiveContour)contour.clone();
            }
        }
        
        if (ait.hasNextIteration()) {
            logger.info("next");
            ait.nextIteration(); 
            imageViewer.repaintImage();   
        } else {
            logger.info("fertig");
            imageViewer.repaintImage();  
        }
    }//GEN-LAST:event_nextIterationButtonActionPerformed

    public String getMenuName() {
        return MENU_NAME;
    }       
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel loggerPanel;
    private javax.swing.JButton resetContourButton;
    private javax.swing.JLabel betaLabel;
    private javax.swing.JPanel southPanel;
    private javax.swing.JLabel outerEnergyWeigthLabel;
    private javax.swing.JLabel alphaLabel;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JSlider betaSlider;
    private javax.swing.JSlider alphaSlider;
    private javax.swing.JPanel parameterPanel;
    private javax.swing.JButton newContourButton;
    private javax.swing.JButton nextIterationButton;
    private javax.swing.JPanel northPanel;
    private javax.swing.JButton doAllButton;
    private javax.swing.JSlider outerEnergySlider;
    private javax.swing.JButton fileLoadButton;
    // End of variables declaration//GEN-END:variables
    
}
