/*
 * SegmentationWizard.java
 *
 * Created on 7. April 2002, 12:12
 */

package org.wewi.medimg.seg.wizard;

import java.beans.PropertyVetoException;
import java.io.File;

import javax.swing.JFileChooser;

import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.image.io.ReaderThreadEvent;
import org.wewi.medimg.image.io.ReaderThreadListener;
import org.wewi.medimg.seg.Segmenter;
import org.wewi.medimg.seg.SegmenterEnumeration;
import org.wewi.medimg.seg.SegmenterEvent;
import org.wewi.medimg.seg.SegmenterListener;
import org.wewi.medimg.seg.SegmenterThread;
import org.wewi.medimg.viewer.ImageFileChooser;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.wizard.Wizard;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class SegmentationWizard extends Wizard {
    
    private class ImageReaderWorker implements ReaderThreadListener {
        private SegmentationWizard wizard;
        private ImageReader imageReader;
        private boolean imageRead = false;
        
        public ImageReaderWorker(SegmentationWizard wizard, ImageReader imageReader) {
            this.wizard = wizard; 
            this.imageReader = imageReader;   
        }  
        
        public void start() {
            if (imageRead) {
                wizard.startSegmentation();
                return;    
            }

            wizard.startButton.setText("Das Bild wird geladen...");

            ImageReaderThread readerThread = new ImageReaderThread(imageReader);
            readerThread.addReaderThreadListener(this);
            readerThread.setPriority(Thread.MIN_PRIORITY);
            readerThread.start();            
        } 
        
        /**
         * @see org.wewi.medimg.image.io.ReaderThreadListener#imageRead(ReaderThreadEvent)
         */
        public void imageRead(ReaderThreadEvent event) {
            imageRead = true;
            
            ImageReaderThread thread = (ImageReaderThread)event.getSource();
            ImageReader reader = thread.getImageReader();
            wizard.mrtImage = reader.getImage();
            
            wizard.startSegmentation();
        }         
    }
    
    
    private class SegmenterWorker implements SegmenterListener {
        private SegmentationWizard wizard;
        private Segmenter segmenter;
        
        public SegmenterWorker(SegmentationWizard wizard, Segmenter segmenter) {
            this.wizard = wizard;
            this.segmenter = segmenter;
        }
        
        public void start() {
            SegmenterThread thread = new SegmenterThread(segmenter);
            thread.addSegmenterListener(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setImage(wizard.mrtImage);
            thread.start();
            
            wizard.segImage = thread.getSegmentedImage();
            wizard.segImage.setColorConversion(new FeatureColorConversion());
            TwinImageViewer twinImageViewer = new TwinImageViewer("", mrtImage, segImage);
            twinImageViewer.pack();
            Viewer.getInstance().addViewerDesktopFrame(twinImageViewer);
        }
        
        /**
         * @see org.wewi.medimg.seg.SegmenterObserver#segmenterFinished(SegmenterEvent)
         */
        public void segmenterFinished(SegmenterEvent event) {
            SegmenterThread segmenterThread = (SegmenterThread)event.getSource();
            wizard.segImage = segmenterThread.getSegmentedImage();
            
            wizard.setClosable(true);
            wizard.cancleButton.setEnabled(true);
            wizard.closeButton.setEnabled(true);
            wizard.startButton.setEnabled(true);
            wizard.startButton.setText("Fertug");            
        }
        
        /**
         * @see org.wewi.medimg.seg.SegmenterObserver#segmenterStarted(SegmenterEvent)
         */
        public void segmenterStarted(SegmenterEvent event) {
            wizard.startButton.setText("Segmentierung des Bildes gestartet...");
        }
        
    }
    
    private static final String MENU_NAME = "Segmentierung";
    private static SegmentationWizard singleton = null;
    
    private SegmenterArgumentPanel segmenterArgumentPanel;
    private SegmenterWorker segmenterWorker;
    private ImageReaderWorker imageReaderWorker;
    private Image mrtImage = new NullImage();
    private Image segImage = new NullImage();
    
    private SegmentationWizardPreferences swPrefs;
    
    /** Creates new form SegmentationWizard */
    public SegmentationWizard() {
        super(MENU_NAME, false, true, false, false);
        initComponents();
        init();
    }
    
    public String getMenuName() {
        return MENU_NAME;
    }
    
    private void init() {
        swPrefs = SegmentationWizardPreferences.getInstance();
        for (int i = 0; i < SegmenterEnumeration.ENUMERATION.length; i++) {
            segEnumComboBox.addItem(SegmenterEnumeration.ENUMERATION[i]);
        }
        
        SegmenterEnumeration enum = (SegmenterEnumeration)segEnumComboBox.getSelectedItem();
        if (enum.equals(SegmenterEnumeration.ML_CLUSTERER)) {
            setSegmenterArgumentPanel(new MLKMeansClustererArgumentPanel());
        } else if (enum.equals(SegmenterEnumeration.MAP_CLUSTERER)) {
            setSegmenterArgumentPanel(new MAPKMeansClustererArgumentPanel());
        }
        segmenterWorker = new SegmenterWorker(this,
                                      segmenterArgumentPanel.getSegmenter());
    }
    
    private void setSegmenterArgumentPanel(SegmenterArgumentPanel panel) {
        segmenterArgumentPanel = panel;
        segmenterPanel.removeAll();
        segmenterPanel.add(panel);
        
        segmenterPanel.updateUI();
    }
    
    public void dispose() {
        super.dispose();
    }
    
    private void onClose() {
        try {
            setClosed(true);
            dispose();
            Viewer.getInstance().removeWizard(this);
        } catch (PropertyVetoException pve) {
            //Zur Zeit nichts
        }
    }
    
    private boolean checkSegmentationStart() {
        return true;
    }
    
    private void loadImage(){
        imageReaderWorker.start();
    }
    
    private void startSegmentation() {
        segmenterWorker.start();
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        northPanel = new javax.swing.JPanel();
        centerPanel = new javax.swing.JPanel();
        wizardTappedPanel = new javax.swing.JTabbedPane();
        wizardStep1 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        imageDataSourceTextField = new javax.swing.JTextField();
        imageDataSearchButton = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        wizardStep2 = new javax.swing.JPanel();
        comboBoxPanel = new javax.swing.JPanel();
        segEnumComboBox = new javax.swing.JComboBox();
        segmenterPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        southPanel = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        cancleButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

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

        getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);

        wizardTappedPanel.setName("Segmentation Wizard");
        wizardTappedPanel.setPreferredSize(new java.awt.Dimension(450, 150));
        wizardStep1.setLayout(new java.awt.GridLayout(2, 1));

        imageDataSourceTextField.setEditable(false);
        imageDataSourceTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        imageDataSourceTextField.setMinimumSize(new java.awt.Dimension(150, 21));
        imageDataSourceTextField.setPreferredSize(new java.awt.Dimension(400, 21));
        jPanel24.add(imageDataSourceTextField);

        imageDataSearchButton.setText("Datensatz ausw\u00e4hlen...");
        imageDataSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageDataSearchButtonActionPerformed(evt);
            }
        });

        jPanel24.add(imageDataSearchButton);

        wizardStep1.add(jPanel24);

        jPanel28.setLayout(new java.awt.GridLayout(3, 1));

        wizardStep1.add(jPanel28);

        wizardTappedPanel.addTab("Datensatz", null, wizardStep1, "");

        wizardStep2.setLayout(new java.awt.BorderLayout());

        comboBoxPanel.setLayout(new java.awt.GridLayout(1, 0));

        segEnumComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                segEnumComboBoxItemStateChanged(evt);
            }
        });

        comboBoxPanel.add(segEnumComboBox);

        wizardStep2.add(comboBoxPanel, java.awt.BorderLayout.NORTH);

        segmenterPanel.setLayout(new java.awt.GridLayout(1, 0));

        wizardStep2.add(segmenterPanel, java.awt.BorderLayout.CENTER);

        wizardTappedPanel.addTab("Segmentierungsart", wizardStep2);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        startButton.setText("Start...");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        jPanel3.add(startButton, new java.awt.GridBagConstraints());

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel1.add(jPanel4, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel5, java.awt.BorderLayout.EAST);

        wizardTappedPanel.addTab("Starten", null, jPanel1, "");

        centerPanel.add(wizardTappedPanel);

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        cancleButton.setText("Abbrechen");
        cancleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancleButtonActionPerformed(evt);
            }
        });

        jPanel30.add(cancleButton);

        closeButton.setText("Schlie\u00dfen");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jPanel30.add(closeButton);

        southPanel.add(jPanel30);

        getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (!checkSegmentationStart()) {
            return;
        }
        
        setClosable(false);
        cancleButton.setEnabled(false);
        closeButton.setEnabled(false);
        startButton.setEnabled(false);
        startButton.setText("Bild wird gerade segmentiert...");
        
        loadImage();
    }//GEN-LAST:event_startButtonActionPerformed
    
    private void segEnumComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_segEnumComboBoxItemStateChanged
        SegmenterEnumeration enum = (SegmenterEnumeration)segEnumComboBox.getSelectedItem();
        if (enum.equals(SegmenterEnumeration.ML_CLUSTERER)) {
            setSegmenterArgumentPanel(new MLKMeansClustererArgumentPanel());
        } else if (enum.equals(SegmenterEnumeration.MAP_CLUSTERER)) {
            setSegmenterArgumentPanel(new MAPKMeansClustererArgumentPanel());
        }
        
    }//GEN-LAST:event_segEnumComboBoxItemStateChanged
    
    
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        try {
            setClosed(true);
            Viewer.getInstance().removeWizard(this);
        } catch (PropertyVetoException pve) {
            //Zur Zeit nichts
        }
    }//GEN-LAST:event_formInternalFrameClosed
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        onClose();
    }//GEN-LAST:event_closeButtonActionPerformed
    
    private void cancleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancleButtonActionPerformed
        onClose();
    }//GEN-LAST:event_cancleButtonActionPerformed
                    
    private void imageDataSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageDataSearchButtonActionPerformed
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
        imageReaderWorker = new ImageReaderWorker(this, imageReader);

        imageDataSourceTextField.setText(fileName);
    }//GEN-LAST:event_imageDataSearchButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton imageDataSearchButton;
    private javax.swing.JPanel northPanel;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel wizardStep2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel wizardStep1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel southPanel;
    private javax.swing.JButton startButton;
    private javax.swing.JComboBox segEnumComboBox;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JTabbedPane wizardTappedPanel;
    private javax.swing.JPanel comboBoxPanel;
    private javax.swing.JButton cancleButton;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JTextField imageDataSourceTextField;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel segmenterPanel;
    private javax.swing.JPanel jPanel28;
    // End of variables declaration//GEN-END:variables
    
}
