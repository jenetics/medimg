/**
 * SegmentationWizard.java
 *
 * Created on 7. April 2002, 12:12
 */

package org.wewi.medimg.seg.wizard;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.wewi.medimg.alg.AlgorithmIterationEvent;
import org.wewi.medimg.alg.AlgorithmIterationListener;
import org.wewi.medimg.alg.InterruptableAlgorithm;
import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.NullImage;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.image.io.ReaderThreadEvent;
import org.wewi.medimg.image.io.ReaderThreadListener;
import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.SegmenterEnum;
import org.wewi.medimg.seg.SegmenterEvent;
import org.wewi.medimg.seg.SegmenterListener;
import org.wewi.medimg.seg.SegmenterThread;
import org.wewi.medimg.viewer.ImageFileChooser;
import org.wewi.medimg.viewer.ImageViewerSynchronizer;
import org.wewi.medimg.viewer.LogHandlerPanel;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.image.ImageViewer;
import org.wewi.medimg.viewer.wizard.Wizard;

  
/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class SegmentationWizard extends Wizard {
    
    /**
     * Hilfsklasse, die für das Einlesen des Bildes über
     * einen ImageReaderThread zuständig ist. Arbeitet sehr eng
     * mit den Methoden des SegmentationWizards zusammen.
     */
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

            /**************************************************************/
            wizardLogger.info("Das Bild wird gerade geladen...");
            /**************************************************************/

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
    
    /**
     * Hilfsklasse, die für den Segmentiervorgang über
     * den SegmenterThread zuständig ist. Auch diese Klasse 
     * arbeitet eng mit dem SegmentationWizard zusammen.
     */
    private class SegmenterWorker implements SegmenterListener,
                                                AlgorithmIterationListener {
        private SegmentationWizard wizard;
        private ObservableSegmenter segmenter;
        private TwinImageViewer twinImageViewer;
        
        private ImageViewerSynchronizer imageSynchronizer;
        
        public SegmenterWorker(SegmentationWizard wizard,
                                SegmenterArgumentPanel segmenterArgumentPanel) {
            this.wizard = wizard;
            segmenter = segmenterArgumentPanel.getSegmenter();
            segmenter.addLoggerHandler(wizard.logHandlerPanel.getHandler());
            
            segmenter.addIterationListener(this);
        }
        
        public void start() {
            SegmenterThread thread = new SegmenterThread(segmenter);
            thread.addSegmenterListener(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setImage(wizard.mrtImage);
            thread.start();
            
            
            wizard.segImage = thread.getSegmentedImage();
            wizard.segImage.setColorConversion(new FeatureColorConversion());
            
            ImageViewer mrtViewer = new ImageViewer(segmenter.toString(), mrtImage);
            ImageViewer segViewer = new ImageViewer(segmenter.toString(), segImage);
            mrtViewer.pack();
            segViewer.pack();
            imageSynchronizer = new ImageViewerSynchronizer();
            imageSynchronizer.addImageViewer(mrtViewer, new Point(0, 0), new Dimension(300, 300));
            imageSynchronizer.addImageViewer(segViewer, new Point(300, 0), new Dimension(300, 300));
            Viewer.getInstance().addImageViewerSynchronizer(imageSynchronizer);

            
            
            /*
            twinImageViewer = new TwinImageViewer(segmenter.toString(), 
                                                       mrtImage, segImage);
            twinImageViewer.pack();
            Viewer.getInstance().addViewerDesktopFrame(twinImageViewer);
            */
        }
        
        public void interruptSegmenter() {
            InterruptableAlgorithm algorithm;
            if (!(segmenter instanceof InterruptableAlgorithm)) {
                return;
            }
            
            algorithm = (InterruptableAlgorithm)segmenter;
            try {
                algorithm.interruptAlgorithm();
            } catch (UnsupportedOperationException e) {
                getLogger().warning("interruptAlgorithm not implemented");
            }  
        }
        
        public void resumeSegmenter() {
            InterruptableAlgorithm algorithm;
            if (!(segmenter instanceof InterruptableAlgorithm)) {
                return;
            }
            
            algorithm = (InterruptableAlgorithm)segmenter;
            try {
                algorithm.resumeAlgorithm();               
            } catch (UnsupportedOperationException e) {
                getLogger().warning("resumeAlgorithm not implemented");
            }    
        }
        
        public void cancelSegmenter() {
            InterruptableAlgorithm algorithm;
            if (!(segmenter instanceof InterruptableAlgorithm)) {
                return;
            }
            
            algorithm = (InterruptableAlgorithm)segmenter;
            try {
                algorithm.cancelAlgorithm();  
                wizard.cancelButton.setEnabled(false); 
                wizard.startButton.setEnabled(true);
            } catch (UnsupportedOperationException e) {
                getLogger().warning("cancleAlgorithm not implemented");
            }    
        }
        
        public void iterationStarted(AlgorithmIterationEvent event) {
            wizard.cancelButton.setEnabled(true);          
        }
        
        public void iterationFinished(AlgorithmIterationEvent event) {
            if (imageSynchronizer != null) {
                imageSynchronizer.repaintImages();    
            }
        } 
               
        
        /**
         * @see org.wewi.medimg.seg.SegmenterObserver#segmenterFinished(SegmenterEvent)
         */
        public void segmenterFinished(SegmenterEvent event) {
            SegmenterThread segmenterThread = (SegmenterThread)event.getSource();
            wizard.segImage = segmenterThread.getSegmentedImage();
            
            wizard.setClosable(true);
            wizard.closeButton.setEnabled(true);
            wizard.startButton.setEnabled(true); 
            wizard.cancelButton.setEnabled(false);
            wizard.cancelButton.setEnabled(false);
            
            /**************************************************************/
            wizardLogger.info("Segmentiervorgang beendet");
            /**************************************************************/ 
            
            if (imageSynchronizer != null) {
                imageSynchronizer.repaintImages();    
            }                      
        }
        
        /**
         * @see org.wewi.medimg.seg.SegmenterObserver#segmenterStarted(SegmenterEvent)
         */
        public void segmenterStarted(SegmenterEvent event) {
            /**************************************************************/
            wizardLogger.info("Segmentiervorgang gestartet");
            /**************************************************************/
        }
        
    }
    
    /*
     * Beginn des eigentlichen SegmentationWizards.
     */
    private static final String MENU_NAME = "Segmentierung";
    private static SegmentationWizard singleton = null;
    
    private SegmenterArgumentPanel segmenterArgumentPanel;
    private LogHandlerPanel logHandlerPanel;
    private SegmenterWorker segmenterWorker;
    private ImageReaderWorker imageReaderWorker;
    private Image mrtImage = new NullImage();
    private Image segImage = new NullImage();
    
    private SegmentationWizardPreferences swPrefs;
    
    private Logger wizardLogger;
    
    /**
     * Erzeugen eines neuen SegmentationWizards.
     */
    public SegmentationWizard() {
        super(MENU_NAME, true, true, false, false);
        initComponents();
        init();
    }
    
    /**
     * Zusätzliche Initialisierungsmethode, die
     * nach der Komponentenintialisierung aufgerufen
     * wird.
     */
    private void init() {
        swPrefs = SegmentationWizardPreferences.getInstance();
        wizardLogger = Logger.getLogger(SegmentationWizard.class.getName());
        
        setSize(swPrefs.getWizardDimension());
        setLocation(swPrefs.getWizardLocation()); 
        
        logHandlerPanel = new LogHandlerPanel();
        ws3CenterPanel.add(logHandlerPanel);
        
        for (int i = 0; i < SegmenterEnum.ENUMERATION.length; i++) {
            segEnumComboBox.addItem(SegmenterEnum.ENUMERATION[i]);
        }
        
        SegmenterEnum enum = (SegmenterEnum)segEnumComboBox.getSelectedItem();
        SegmenterArgumentPanel panel = null;
        if (enum.equals(SegmenterEnum.ML_CLUSTERER)) {
            panel = new MLKMeansClustererArgumentPanel();
        } else if (enum.equals(SegmenterEnum.MAP_CLUSTERER)) {
            panel = new MAPKMeansClustererArgumentPanel();
        }
        setSegmenterArgumentPanel(panel);
        
        wizardLogger.addHandler(logHandlerPanel.getHandler());
        
        cancelButton.setEnabled(true);
    } 
     
    
    /**
     * Liefert den Namen des Wizards.
     * 
     * @see org.wewi.medimg.viewer.wizard.Wizard#getMenuName()
     */
    public String getMenuName() {
        return MENU_NAME;
    }
    
    /**
     * Alle Parameter einer Segmentiermethode werden über
     * das SegmenterArgumentPanel gesammelt. Dieses Panel liefert
     * auch den entsprechen fertig parametrisierten Segmentieralgorithmus.
     * Wird ein anderer Algorithmus in der CompuBox ausgewählt,
     * so wird das neue Panel zum Wizard hinzugefügt, und das
     * Alte entfernt.
     */
    private void setSegmenterArgumentPanel(SegmenterArgumentPanel panel) {
        segmenterArgumentPanel = panel;
        segmenterPanel.removeAll();
        segmenterPanel.add(panel);
        
        segmenterPanel.updateUI();
    }
    
    public void dispose() {
        super.dispose();
    }
    
    /**
     * Diese Methode sollte beim Beenden des Wizards 
     * aufgerufen werden.
     */
    private void onClose() {
        swPrefs.setWizardDimension(getSize());
        swPrefs.setWizardLocation(getLocation());
        
        try {
            setClosed(true);
            dispose();
            Viewer.getInstance().removeWizard(this);
        } catch (PropertyVetoException pve) {
            /**************************************************************/
            logger.throwing(this.getClass().getName(), "onClose()", pve); 
            /**************************************************************/
        }
    }
    
    /**
     * Überprüft, ob die Minimalbedingung für den Start
     * der Segmentierung erfüllt sind.
     */
    private boolean checkSegmentationStart() {
        return imageReaderWorker != null;
    }
    
    /**
     * Anstoßen des Bildladevorganges.
     */
    private void loadImage(){
        imageReaderWorker.start();
    }
    
    /**
     * Anstoßen des Segmentiervorganges.
     */
    private void startSegmentation() {
        segmenterWorker = new SegmenterWorker(this, segmenterArgumentPanel);
        segmenterWorker.start();
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
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
        wizardStep3 = new javax.swing.JPanel();
        ws3NorthPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        ws3CenterPanel = new javax.swing.JPanel();
        ws3SouthPanel = new javax.swing.JPanel();
        southPanel = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();

        setForeground(java.awt.Color.white);
        setFont(new java.awt.Font("Dialog", 0, 12));
        setPreferredSize(new java.awt.Dimension(500, 300));
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

        centerPanel.setLayout(new java.awt.GridLayout(1, 0));

        wizardTappedPanel.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        wizardTappedPanel.setDoubleBuffered(true);
        wizardTappedPanel.setFocusCycleRoot(true);
        wizardTappedPanel.setFont(new java.awt.Font("Dialog", 0, 12));
        wizardTappedPanel.setName("Segmentation Wizard");
        wizardTappedPanel.setPreferredSize(new java.awt.Dimension(550, 350));
        wizardTappedPanel.setAutoscrolls(true);
        wizardTappedPanel.setOpaque(true);
        wizardStep1.setLayout(new java.awt.GridLayout(2, 1));

        wizardStep1.setFont(new java.awt.Font("Dialog", 0, 10));
        imageDataSourceTextField.setEditable(false);
        imageDataSourceTextField.setFont(new java.awt.Font("Dialog", 2, 12));
        imageDataSourceTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        imageDataSourceTextField.setMinimumSize(new java.awt.Dimension(150, 21));
        imageDataSourceTextField.setPreferredSize(new java.awt.Dimension(400, 21));
        jPanel24.add(imageDataSourceTextField);

        imageDataSearchButton.setFont(new java.awt.Font("Dialog", 0, 12));
        imageDataSearchButton.setMnemonic('w');
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

        segEnumComboBox.setFont(new java.awt.Font("Dialog", 0, 12));
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

        wizardStep3.setLayout(new java.awt.BorderLayout());

        startButton.setFont(new java.awt.Font("Dialog", 0, 12));
        startButton.setMnemonic('S');
        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        ws3NorthPanel.add(startButton);

        cancelButton.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelButton.setMnemonic('A');
        cancelButton.setText("Abbrechen");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        ws3NorthPanel.add(cancelButton);

        wizardStep3.add(ws3NorthPanel, java.awt.BorderLayout.NORTH);

        ws3CenterPanel.setLayout(new java.awt.GridLayout(1, 0));

        wizardStep3.add(ws3CenterPanel, java.awt.BorderLayout.CENTER);

        ws3SouthPanel.setLayout(new java.awt.BorderLayout());

        wizardStep3.add(ws3SouthPanel, java.awt.BorderLayout.SOUTH);

        wizardTappedPanel.addTab("Starten", null, wizardStep3, "");

        centerPanel.add(wizardTappedPanel);

        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

        closeButton.setFont(new java.awt.Font("Dialog", 0, 12));
        closeButton.setMnemonic('c');
        closeButton.setText("Schlie\u00dfen");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        jPanel30.add(closeButton);

        southPanel.add(jPanel30);

        getContentPane().add(southPanel, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-564)/2, (screenSize.height-301)/2, 564, 301);
    }//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if (segmenterWorker == null) {
            return;    
        }
        segmenterWorker.cancelSegmenter();       
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
        if (!checkSegmentationStart()) {
            return;
        }
        
        setClosable(false);
        closeButton.setEnabled(false);
        startButton.setEnabled(false);
        
        loadImage();
    }//GEN-LAST:event_startButtonActionPerformed
    
    private void segEnumComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_segEnumComboBoxItemStateChanged
        SegmenterEnum enum = (SegmenterEnum)segEnumComboBox.getSelectedItem();
        if (enum.equals(SegmenterEnum.ML_CLUSTERER)) {
            setSegmenterArgumentPanel(new MLKMeansClustererArgumentPanel());
        } else if (enum.equals(SegmenterEnum.MAP_CLUSTERER)) {
            setSegmenterArgumentPanel(new MAPKMeansClustererArgumentPanel());
        }
        
    }//GEN-LAST:event_segEnumComboBoxItemStateChanged
    
    
    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        try {
            setClosed(true);
            Viewer.getInstance().removeWizard(this);
        } catch (PropertyVetoException pve) {
            logger.throwing(getClass().getName(), "frameInternalFrameClosed()", pve);
        }
    }//GEN-LAST:event_formInternalFrameClosed
    
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        onClose();
    }//GEN-LAST:event_closeButtonActionPerformed
                        
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
        ImageReader imageReader = readerFactory.createImageReader(IntImageFactory.getInstance(),
                                                            new File(fileName));
        imageReaderWorker = new ImageReaderWorker(this, imageReader);

        imageDataSourceTextField.setText(fileName);
    }//GEN-LAST:event_imageDataSearchButtonActionPerformed
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel comboBoxPanel;
    private javax.swing.JButton imageDataSearchButton;
    private javax.swing.JTextField imageDataSourceTextField;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel northPanel;
    private javax.swing.JComboBox segEnumComboBox;
    private javax.swing.JPanel segmenterPanel;
    private javax.swing.JPanel southPanel;
    private javax.swing.JButton startButton;
    private javax.swing.JPanel wizardStep1;
    private javax.swing.JPanel wizardStep2;
    private javax.swing.JPanel wizardStep3;
    private javax.swing.JTabbedPane wizardTappedPanel;
    private javax.swing.JPanel ws3CenterPanel;
    private javax.swing.JPanel ws3NorthPanel;
    private javax.swing.JPanel ws3SouthPanel;
    // End of variables declaration//GEN-END:variables
        
}
