/*
 * SegmentationWizard.java
 *
 * Created on 7. April 2002, 12:12
 */

package org.wewi.medimg.seg.wizard;

import org.wewi.medimg.util.Singleton;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.FeatureData;
import org.wewi.medimg.image.ImageFormatTypes;
import org.wewi.medimg.image.FeatureColorConversion;

import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.TIFFReaderFactory;
import org.wewi.medimg.image.io.BMPReaderFactory;
import org.wewi.medimg.image.io.RawImageReaderFactory;
import org.wewi.medimg.image.io.RawImageDataReaderFactory;
import org.wewi.medimg.image.io.ReaderThreadListener;
import org.wewi.medimg.image.io.WriterThreadListener;
import org.wewi.medimg.image.io.ReaderThreadEvent;
import org.wewi.medimg.image.io.WriterThreadEvent;

import org.wewi.medimg.seg.SegmentationStrategy;
import org.wewi.medimg.seg.SegmentationEvent;
import org.wewi.medimg.seg.SegmentationKind;
import org.wewi.medimg.seg.ImageSegmentationStrategy;
import org.wewi.medimg.seg.SegmentationStrategyThread;
import org.wewi.medimg.seg.SegmentationListener;
import org.wewi.medimg.seg.statistic.MLSegmentation;
import org.wewi.medimg.seg.statistic.MAPSegmentation;

import org.wewi.medimg.viewer.wizard.Wizard;
import org.wewi.medimg.viewer.Viewer;

import java.util.Observer;
import java.util.Observable;

import java.io.File;

import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JFileChooser;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class SegmentationWizard extends Wizard implements Observer,
                                                          ReaderThreadListener,
                                                          WriterThreadListener,
                                                          SegmentationListener {
    private static final String MENU_NAME = "Segmentierungs-Wizard";
    private static SegmentationWizard singleton = null;
    
    private ImageReader imageReader = null;    
    private SegmentationKind segmentationKind = SegmentationKind.ML;
    private int nfeatures = 4;
    
    private ImageSegmentationStrategy segmentationStrategy = null;
    private Image imageData = NullImage.getInstance();
    private Image featureData = null;
    
    private SegmentationStrategyThread segmenationThread;
    private TwinImageViewer imageViewer;
    
    /** Creates new form SegmentationWizard */
    private SegmentationWizard() {
        super(MENU_NAME, false, true, false, false);
        initComponents();
        init();
    }
    
    public static SegmentationWizard getInstance() {
        if (singleton == null) {
            singleton = new SegmentationWizard();
        }
        return singleton;
    }
    
    public String getMenuName() {
        return MENU_NAME;
    } 
    
    private void init() {
        int ntypes = ImageFormatTypes.TYPES.length;
        for (int i = 0; i < ntypes; i++) {
            imageFormatComboBox.addItem(ImageFormatTypes.TYPES[i]);
        }
        imageFormatComboBox.setSelectedItem(ImageFormatTypes.TIFF_IMAGES);
    }
    
    public void dispose() {
        super.dispose();
        //singleton = null;
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
    
    private void loadImage() {
        segStartButton.setText("Laden des Bildes...");
        
        ImageReaderThread readerThread = new ImageReaderThread(imageReader);
        readerThread.addReaderThreadListener(this);
        readerThread.setPriority(Thread.MIN_PRIORITY);
        readerThread.start();
    }
    
    private boolean checkSegmentationStart() {
        return true;
    }
    
    private void startSegmentation() {
        loadImage();
    }
    
    public void update(Observable observable, Object obj) {
    }   
    
    public void imageRead(ReaderThreadEvent event) {
        imageData = imageReader.getImage();
        if (segmentationKind.equals(SegmentationKind.ML)) {
            segmentationStrategy = new MLSegmentation(imageData, nfeatures);
        } else if (segmentationKind.equals(SegmentationKind.MAP)) {
            segmentationStrategy = new MAPSegmentation(imageData, nfeatures, 20);
        }
      
        segmentationStrategy.addSegmentationListener(this);
        SegmentationStrategyThread segmentationThread = new SegmentationStrategyThread(segmentationStrategy);
        segmentationThread.setPriority(Thread.MIN_PRIORITY);
        segmentationThread.start();
        segStartButton.setText("Segmentieren des Bildes...");        
    }    
           
    public void imageWritten(WriterThreadEvent event) {
    }
    
    public void iterationFinished(SegmentationEvent event) {  
        imageViewer.setSlice(imageViewer.getSlice());
        segmentationStateTextField.setText(event.toString());
    } 
    
    public void segmentationStarted(SegmentationEvent event) {
        imageViewer = new TwinImageViewer("Segmentiervorgang", imageData, segmentationStrategy.getFeatureData());
        imageViewer.setColorConversion2(new FeatureColorConversion());
        imageViewer.pack();
        Viewer.getInstance().addViewerDesktopFrame(imageViewer); 
        segmentationStateTextField.setText(event.toString());
    }     
    
    public void segmentationFinished(SegmentationEvent event) {
        segStartButton.setText("Start");
        segmentationStateTextField.setText(event.toString());
        imageViewer.setSlice(imageViewer.getSlice());
        setClosable(true);
        cancleButton.setEnabled(true);
        closeButton.setEnabled(true);
        segStartButton.setEnabled(true);        
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
        imageFormatComboBox = new javax.swing.JComboBox();
        jPanel28 = new javax.swing.JPanel();
        wizardStep2 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        mlSegKindRadioButton = new javax.swing.JRadioButton();
        mapSegKindRadioButton = new javax.swing.JRadioButton();
        mapNFeaturesTextField = new javax.swing.JTextField();
        mlNFeaturesTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        wizardStep3 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        segStartButton = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        segmentationStateTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
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

        imageFormatComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                imageFormatComboBoxItemStateChanged(evt);
            }
        });

        jPanel24.add(imageFormatComboBox);

        wizardStep1.add(jPanel24);

        jPanel28.setLayout(new java.awt.GridLayout(3, 1));

        wizardStep1.add(jPanel28);

        wizardTappedPanel.addTab("Datensatz", null, wizardStep1, "");

        wizardStep2.setLayout(new java.awt.BorderLayout());

        wizardStep2.add(jPanel32, java.awt.BorderLayout.NORTH);

        jPanel31.setLayout(new java.awt.GridBagLayout());

        mlSegKindRadioButton.setSelected(true);
        mlSegKindRadioButton.setText("ML- Segmentierung");
        mlSegKindRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mlSegKindRadioButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 25);
        jPanel31.add(mlSegKindRadioButton, gridBagConstraints);

        mapSegKindRadioButton.setText("MAP- MRF- Segmentierung");
        mapSegKindRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mapSegKindRadioButtonActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 25);
        jPanel31.add(mapSegKindRadioButton, gridBagConstraints);

        mapNFeaturesTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mapNFeaturesTextField.setText("4");
        mapNFeaturesTextField.setPreferredSize(new java.awt.Dimension(120, 21));
        mapNFeaturesTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                mapNFeaturesTextFieldFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        jPanel31.add(mapNFeaturesTextField, gridBagConstraints);

        mlNFeaturesTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        mlNFeaturesTextField.setText("4");
        mlNFeaturesTextField.setPreferredSize(new java.awt.Dimension(120, 21));
        mlNFeaturesTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                mlNFeaturesTextFieldFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel31.add(mlNFeaturesTextField, gridBagConstraints);

        jLabel13.setText("Anzahl der Merkmale");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        jPanel31.add(jLabel13, gridBagConstraints);

        wizardStep2.add(jPanel31, java.awt.BorderLayout.CENTER);

        wizardStep2.add(jPanel33, java.awt.BorderLayout.SOUTH);

        wizardTappedPanel.addTab("Verfahren", null, wizardStep2, "");

        wizardStep3.setLayout(new java.awt.BorderLayout());

        wizardStep3.add(jPanel40, java.awt.BorderLayout.NORTH);

        jPanel39.setLayout(new java.awt.GridBagLayout());

        segStartButton.setText("Start...");
        segStartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segStartButtonActionPerformed(evt);
            }
        });

        jPanel39.add(segStartButton, new java.awt.GridBagConstraints());

        wizardStep3.add(jPanel39, java.awt.BorderLayout.CENTER);

        jPanel41.setLayout(new java.awt.BorderLayout());

        jPanel41.add(segmentationStateTextField, java.awt.BorderLayout.CENTER);

        wizardStep3.add(jPanel41, java.awt.BorderLayout.SOUTH);

        wizardStep3.add(jPanel1, java.awt.BorderLayout.EAST);

        wizardTappedPanel.addTab("Starten", null, wizardStep3, "");

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

    private void segStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segStartButtonActionPerformed
        // Add your handling code here:
        if (!checkSegmentationStart()) {
            return;
        }
        
        setClosable(false);
        cancleButton.setEnabled(false);
        closeButton.setEnabled(false);
        segStartButton.setEnabled(false);
        segStartButton.setText("Bild wird gerade segmentiert...");
        startSegmentation();
    }//GEN-LAST:event_segStartButtonActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        // Add your handling code here:
        try {
            setClosed(true);
            Viewer.getInstance().removeWizard(this);
        } catch (PropertyVetoException pve) {
            //Zur Zeit nichts
        } 
    }//GEN-LAST:event_formInternalFrameClosed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // Add your handling code here:
        onClose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void cancleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancleButtonActionPerformed
        // Add your handling code here:
        onClose();
    }//GEN-LAST:event_cancleButtonActionPerformed

    private void mapSegKindRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mapSegKindRadioButtonActionPerformed
        // Add your handling code here:
        mlSegKindRadioButton.setSelected(false);
        segmentationKind = SegmentationKind.MAP;        
    }//GEN-LAST:event_mapSegKindRadioButtonActionPerformed

    private void mlSegKindRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mlSegKindRadioButtonActionPerformed
        // Add your handling code here:
        mapSegKindRadioButton.setSelected(false);
        segmentationKind = SegmentationKind.ML;        
    }//GEN-LAST:event_mlSegKindRadioButtonActionPerformed

    private void mapNFeaturesTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mapNFeaturesTextFieldFocusLost
        // Add your handling code here:
        try {
            nfeatures = Integer.parseInt(mapNFeaturesTextField.getText());
        } catch (NumberFormatException nfe) {
            nfeatures = 4;
            mapNFeaturesTextField.setText("4");
        }        
    }//GEN-LAST:event_mapNFeaturesTextFieldFocusLost

    private void mlNFeaturesTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_mlNFeaturesTextFieldFocusLost
        // Add your handling code here:
        try {
            nfeatures = Integer.parseInt(mlNFeaturesTextField.getText());
        } catch (NumberFormatException nfe) {
            nfeatures = 4;
            mlNFeaturesTextField.setText("4");
        }        
    }//GEN-LAST:event_mlNFeaturesTextFieldFocusLost

    private void imageFormatComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_imageFormatComboBoxItemStateChanged
        // Add your handling code here:
        imageReader = null;
        imageDataSourceTextField.setText("");
    }//GEN-LAST:event_imageFormatComboBoxItemStateChanged

    private void imageDataSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageDataSearchButtonActionPerformed
        // Add your handling code here:
        String fileName = null;
        ImageReaderFactory readerFactory = null;
        
        int selectMode = JFileChooser.FILES_AND_DIRECTORIES;
        int dialogType = JFileChooser.OPEN_DIALOG;
        
        //Richtiges Initialisieren des JFileChooser
        ImageFormatTypes imageFormat = (ImageFormatTypes)imageFormatComboBox.getSelectedItem();
        if (imageFormat.equals(ImageFormatTypes.RAW_IMAGE)) {
            selectMode = JFileChooser.FILES_ONLY;
            readerFactory = RawImageDataReaderFactory.getInstance();
        } else if (imageFormat.equals(ImageFormatTypes.TIFF_IMAGES)) {
            selectMode = JFileChooser.DIRECTORIES_ONLY;
            readerFactory = TIFFReaderFactory.getInstance();
        } else if (imageFormat.equals(ImageFormatTypes.BMP_IMAGES)) {
            selectMode = JFileChooser.DIRECTORIES_ONLY;
            readerFactory = BMPReaderFactory.getInstance();
        } else if (imageFormat.equals(ImageFormatTypes.RAW_IMAGE)) {
            selectMode = JFileChooser.FILES_ONLY;
            readerFactory = RawImageDataReaderFactory.getInstance();
        }
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(selectMode);
        chooser.setDialogType(dialogType);
        chooser.setDialogTitle("Datensatz auswählen");
        //chooser.setCurrentDirectory(new File("C:\\Workspace\\fwilhelm\\Projekte\\SRS\\pic\\seg\\heads\\001"));
        //chooser.changeToParentDirectory();
        
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = chooser.getSelectedFile().getAbsolutePath();
            imageReader = readerFactory.createImageReader(ImageDataFactory.getInstance(), new File(fileName));
            imageDataSourceTextField.setText(fileName);
        }        
    }//GEN-LAST:event_imageDataSearchButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton imageDataSearchButton;
    private javax.swing.JPanel northPanel;
    private javax.swing.JTextField segmentationStateTextField;
    private javax.swing.JTextField mapNFeaturesTextField;
    private javax.swing.JPanel wizardStep3;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel wizardStep2;
    private javax.swing.JPanel wizardStep1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel southPanel;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JComboBox imageFormatComboBox;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JTabbedPane wizardTappedPanel;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JButton segStartButton;
    private javax.swing.JButton cancleButton;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JTextField imageDataSourceTextField;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JButton closeButton;
    private javax.swing.JRadioButton mlSegKindRadioButton;
    private javax.swing.JTextField mlNFeaturesTextField;
    private javax.swing.JRadioButton mapSegKindRadioButton;
    private javax.swing.JPanel jPanel28;
    // End of variables declaration//GEN-END:variables
    
}
