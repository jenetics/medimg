/*
 * RegistrationWizard
 *
 * Created on 7. April 2002, 12:12
 */

package org.wewi.medimg.reg.wizard;

import java.awt.Dimension;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Observer;

import javax.swing.JFileChooser;

import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.geom.transform.ImageTransformation;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.image.io.ReaderThreadEvent;
import org.wewi.medimg.image.io.ReaderThreadListener;
import org.wewi.medimg.image.io.WriterThreadEvent;
import org.wewi.medimg.image.io.WriterThreadListener;
import org.wewi.medimg.reg.BBAffinityMetric;
import org.wewi.medimg.reg.Registrator;
import org.wewi.medimg.reg.WeightPointTransformationImportance;
import org.wewi.medimg.reg.pca.NonRigidPCARegistration;
import org.wewi.medimg.reg.pca.RigidPCARegistration;
import org.wewi.medimg.viewer.ImageFileChooser;
import org.wewi.medimg.viewer.ImageViewerSynchronizer;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.image.ImageViewer;
import org.wewi.medimg.viewer.wizard.Wizard;


 /*
 * @author  Werner Weiser
 * @version 0.1
 */
public class RegistrationWizard extends Wizard implements Observer,
														      ReaderThreadListener,
													    	  WriterThreadListener,
															  RegistratorListener {

	private static final String MENU_NAME = "Registration-Wizard";
	private static RegistrationWizard singleton = null;

	private ImageReader imageReader1;
	private ImageReader imageReader2;
	private RegistratorEnumeration registrationEnumeration = RegistratorEnumeration.PCA_METHOD_RIGID;
	//private int nfeatures = 4;
	private ImageTransformation transformation;
	private Registrator registrator;
	private ObservableRegistrator obReg = null;
	private Image imageData1;
	private Image imageData2;
	private Image resultData;  
	private Image featureData = null;

	private RegistratorThread registratorThread;
	private ImageReaderThread readerThread1;
	private ImageReaderThread readerThread2;


    private ImageViewerSynchronizer imageSynchronizer;	

	// Creates new form RegistrationWizard *
	public RegistrationWizard() {
		super(MENU_NAME, true, true, false, false);
		initComponents();
		init();
	}

	public String getMenuName() {
		return MENU_NAME;
	}

	private void init() {
	}

	public void dispose() {
		super.dispose();
		//singleton = null;
	}

	private void onClose() {
		try {
			//imageViewer.setClosed(true);
			setClosed(true);
			dispose();
			Viewer.getInstance().removeWizard(this);
		} catch (PropertyVetoException pve) {
			//Zur Zeit nichts
		}
	}

	private void loadSourceImage() {
		regStartButton.setText("Laden des Ausgangsbildes...");
		FeatureColorConversion fcc = new FeatureColorConversion();
		imageReader1.setColorConversion(fcc);		
		readerThread1 = new ImageReaderThread(imageReader1);
		readerThread1.addReaderThreadListener(this);
		readerThread1.setPriority(Thread.MIN_PRIORITY);
		readerThread1.start();
	}

	private void loadTargetImage() {
		regStartButton.setText("Laden des Zielbildes...");
		FeatureColorConversion fcc = new FeatureColorConversion();
		imageReader2.setColorConversion(fcc);		
		readerThread2 = new ImageReaderThread(imageReader2);
		readerThread2.addReaderThreadListener(this);
		readerThread2.setPriority(Thread.MIN_PRIORITY);
		readerThread2.start();
	}

	private boolean checkRegistrationStart() {
		return true;
	}

	private void startRegistration() {
		loadSourceImage();
	}

	public void imageRead(ReaderThreadEvent event) {
		if ((ImageReaderThread) event.getSource() == readerThread1) {
			loadTargetImage();
		} else if ((ImageReaderThread) event.getSource() == readerThread2) {
			imageData1 = imageReader1.getImage();
			imageData2 = imageReader2.getImage();
			if (registrationEnumeration.equals(RegistratorEnumeration.PCA_METHOD_RIGID)) {
				WeightPointTransformationImportance myImportance = new WeightPointTransformationImportance();
				//ImportanceStrategy myStrategy = new ImportanceStrategy();
				//FittnessStrategy myStrategy = new FittnessStrategy();
				BBAffinityMetric myMetric = new BBAffinityMetric();
				//ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
				myImportance.setErrorLimit(0.2);
				RigidPCARegistration myRegistration = new RigidPCARegistration();
		        myRegistration.setAffinityMetric(myMetric);
		        myRegistration.setTransformationImportance(myImportance);
		        obReg = myRegistration;
			} else if (registrationEnumeration.equals(RegistratorEnumeration.PCA_METHOD_NONRIGID)) {
				WeightPointTransformationImportance myImportance = new WeightPointTransformationImportance();
				//ImportanceStrategy myStrategy = new ImportanceStrategy();
				//FittnessStrategy myStrategy = new FittnessStrategy();
				BBAffinityMetric myMetric = new BBAffinityMetric();
				//ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
				myImportance.setErrorLimit(0.2);
				NonRigidPCARegistration myRegistration = new NonRigidPCARegistration();
		        myRegistration.setAffinityMetric(myMetric);
		        myRegistration.setTransformationImportance(myImportance);
		        obReg = myRegistration;
			} else if (registrationEnumeration.equals(RegistratorEnumeration.MC_METHOD)) {
				WeightPointTransformationImportance myImportance = new WeightPointTransformationImportance();
				//ImportanceStrategy myStrategy = new ImportanceStrategy();
				//FittnessStrategy myStrategy = new FittnessStrategy();
				BBAffinityMetric myMetric = new BBAffinityMetric();
				//ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
				myImportance.setErrorLimit(0.2);
				RigidPCARegistration myRegistration = new RigidPCARegistration();
		        myRegistration.setAffinityMetric(myMetric);
		        myRegistration.setTransformationImportance(myImportance);
		        obReg = myRegistration;
			}
			//obReg.addRegistratorListener(this);
			registratorThread = new RegistratorThread(obReg);			
			registratorThread.addRegistratorListener(this);
		        	
            registratorThread.setPriority(Thread.MIN_PRIORITY);
            registratorThread.setImage(imageData1, imageData2);
            registratorThread.start();
            regStartButton.setText("Koregistrieren der Bilder...");			
		} else {
			System.out.println("Probleme beim Einlesen der Images");
		}
	}

	public void imageWritten(WriterThreadEvent event) {
	}

	public void registratorStarted(RegistratorEvent event) {
        ImageViewer viewer1 = new ImageViewer("Ausgangsbild", imageData1);
        ImageViewer viewer2 = new ImageViewer("Zielbild", imageData2);
        viewer1.pack();
        viewer2.pack();
        imageSynchronizer = new ImageViewerSynchronizer();
        imageSynchronizer.addImageViewer(viewer1, new Point(0, 0), new Dimension(300, 300));
        imageSynchronizer.addImageViewer(viewer2, new Point(300, 0), new Dimension(300, 300));
        Viewer.getInstance().addImageViewerSynchronizer(imageSynchronizer);
		//registrationStateTextField.setText(event.toString());
	}

	public void registratorFinished(RegistratorEvent event) {
        regStartButton.setText("Transformieren der Bilder...");
    	//registrationStateTextField.setText(event.toString());
        transformation = (AffineTransformation)registratorThread.getTransformation();
        resultData = transformation.transform(imageData1);
		ImageViewer viewer3 = new ImageViewer("Ergebnisbild", resultData);
		int sizeX = resultData.getMaxX() - resultData.getMinX() + 1;
		int sizeY = resultData.getMaxY() - resultData.getMinY() + 1;
		//viewer3.setPreferredSize(new Dimension(sizeX, sizeY));	
		viewer3.setPreferredSize(new Dimension(300, 300));	
		regStartButton.setText("Start");		
		viewer3.pack();
		Viewer.getInstance().addViewerDesktopFrame(viewer3, new Point(150, 300), new Dimension(sizeX, sizeY));
		setClosable(true);
		cancelButton.setEnabled(true);
		closeButton.setEnabled(true);
		regStartButton.setEnabled(true);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
        private void initComponents() {//GEN-BEGIN:initComponents
            northPanel = new javax.swing.JPanel();
            centerPanel = new javax.swing.JPanel();
            Datensätze = new javax.swing.JTabbedPane();
            wizardStep1 = new javax.swing.JPanel();
            jPanel24 = new javax.swing.JPanel();
            imageDataSourceTextField = new javax.swing.JTextField();
            sourceImageDataSearchButton = new javax.swing.JButton();
            startDataLoaded = new java.awt.Checkbox();
            jPanel2 = new javax.swing.JPanel();
            imageDataTargetTextField = new javax.swing.JTextField();
            targetImageDataSearchButton = new javax.swing.JButton();
            targetDataLoaded = new java.awt.Checkbox();
            wizardStep2 = new javax.swing.JPanel();
            jPanel32 = new javax.swing.JPanel();
            jPanel31 = new javax.swing.JPanel();
            rigidPCARegRadioButton = new javax.swing.JRadioButton();
            monteCarloRegRadioButton = new javax.swing.JRadioButton();
            nonRigidPCARegRadioButton = new javax.swing.JRadioButton();
            jPanel33 = new javax.swing.JPanel();
            wizardStep3 = new javax.swing.JPanel();
            jPanel40 = new javax.swing.JPanel();
            jPanel39 = new javax.swing.JPanel();
            regStartButton = new javax.swing.JButton();
            jPanel41 = new javax.swing.JPanel();
            registrationStateTextField = new javax.swing.JTextField();
            jPanel1 = new javax.swing.JPanel();
            southPanel = new javax.swing.JPanel();
            jPanel30 = new javax.swing.JPanel();
            cancelButton = new javax.swing.JButton();
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
            
            Datensätze.setName("Registration Wizard");
            Datensätze.setPreferredSize(new java.awt.Dimension(450, 150));
            wizardStep1.setLayout(new java.awt.GridLayout(2, 1));
            
            imageDataSourceTextField.setEditable(false);
            imageDataSourceTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            imageDataSourceTextField.setMinimumSize(new java.awt.Dimension(150, 21));
            imageDataSourceTextField.setPreferredSize(new java.awt.Dimension(400, 21));
            jPanel24.add(imageDataSourceTextField);
            
            sourceImageDataSearchButton.setText("Startdatensatz ausw\u00e4hlen...");
            sourceImageDataSearchButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    sourceImageDataSearchButtonActionPerformed(evt);
                }
            });
            
            jPanel24.add(sourceImageDataSearchButton);
            
            jPanel24.add(startDataLoaded);
            
            wizardStep1.add(jPanel24);
            
            imageDataTargetTextField.setEditable(false);
            imageDataTargetTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
            imageDataTargetTextField.setMinimumSize(new java.awt.Dimension(150, 21));
            imageDataTargetTextField.setPreferredSize(new java.awt.Dimension(400, 21));
            jPanel2.add(imageDataTargetTextField);
            
            targetImageDataSearchButton.setText("Zieldatensatz ausw\u00e4hlen...");
            targetImageDataSearchButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    targetImageDataSearchButtonActionPerformed(evt);
                }
            });
            
            jPanel2.add(targetImageDataSearchButton);
            
            jPanel2.add(targetDataLoaded);
            
            wizardStep1.add(jPanel2);
            
            Datensätze.addTab("Datensatz", wizardStep1);
            
            wizardStep2.setLayout(new java.awt.BorderLayout());
            
            wizardStep2.add(jPanel32, java.awt.BorderLayout.NORTH);
            
            jPanel31.setLayout(new java.awt.GridBagLayout());
            java.awt.GridBagConstraints gridBagConstraints1;
            
            rigidPCARegRadioButton.setSelected(true);
            rigidPCARegRadioButton.setText("Rigide PCA Registrierung");
            rigidPCARegRadioButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    rigidPCARadioButtonActionPerformed(evt);
                }
            });
            
            gridBagConstraints1 = new java.awt.GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 25);
            jPanel31.add(rigidPCARegRadioButton, gridBagConstraints1);
            
            monteCarloRegRadioButton.setText("MonteCarlo Registrierung");
            monteCarloRegRadioButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    monteCarloRegRadioButtonActionPerformed(evt);
                }
            });
            
            gridBagConstraints1 = new java.awt.GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 4;
            gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 25);
            jPanel31.add(monteCarloRegRadioButton, gridBagConstraints1);
            
            nonRigidPCARegRadioButton.setText("Affine PCA Registrierung");
            nonRigidPCARegRadioButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    nonRigidPCARegRadioButtonActionPerformed(evt);
                }
            });
            
            gridBagConstraints1 = new java.awt.GridBagConstraints();
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 2;
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            jPanel31.add(nonRigidPCARegRadioButton, gridBagConstraints1);
            
            wizardStep2.add(jPanel31, java.awt.BorderLayout.CENTER);
            
            wizardStep2.add(jPanel33, java.awt.BorderLayout.SOUTH);
            
            Datensätze.addTab("Verfahren", wizardStep2);
            
            wizardStep3.setLayout(new java.awt.BorderLayout());
            
            wizardStep3.add(jPanel40, java.awt.BorderLayout.NORTH);
            
            jPanel39.setLayout(new java.awt.GridBagLayout());
            java.awt.GridBagConstraints gridBagConstraints2;
            
            regStartButton.setText("Start...");
            regStartButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    regStartButtonActionPerformed(evt);
                }
            });
            
            gridBagConstraints2 = new java.awt.GridBagConstraints();
            jPanel39.add(regStartButton, gridBagConstraints2);
            
            wizardStep3.add(jPanel39, java.awt.BorderLayout.CENTER);
            
            jPanel41.setLayout(new java.awt.BorderLayout());
            
            jPanel41.add(registrationStateTextField, java.awt.BorderLayout.CENTER);
            
            wizardStep3.add(jPanel41, java.awt.BorderLayout.SOUTH);
            
            wizardStep3.add(jPanel1, java.awt.BorderLayout.EAST);
            
            Datensätze.addTab("Starten", wizardStep3);
            
            centerPanel.add(Datensätze);
            
            getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
            
            cancelButton.setText("Abbrechen");
            cancelButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cancelButtonActionPerformed(evt);
                }
            });
            
            jPanel30.add(cancelButton);
            
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

    private void nonRigidPCARegRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NonRigidPCARegRadioButtonActionPerformed
		monteCarloRegRadioButton.setSelected(false);
		rigidPCARegRadioButton.setSelected(false);
		registrationEnumeration = RegistratorEnumeration.PCA_METHOD_NONRIGID;        
    }//GEN-LAST:event_NonRigidPCARegRadioButtonActionPerformed

	private void targetImageDataSearchButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_targetImageDataSearchButtonActionPerformed
		// Add your handling code here:
		ImageFileChooser chooser = new ImageFileChooser();
		chooser.setDialogTitle("Datensätze auswählen");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//chooser.setCurrentDirectory(new File("E:/Daten/Diplom/data/reg.test.img/"));

		int returnVal = chooser.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}

		ImageReaderFactory readerFactory = chooser.getImageReaderFactory();
		String fileName = chooser.getSelectedFile().getAbsolutePath();
		imageReader2 =
			readerFactory.createImageReader(
				ImageDataFactory.getInstance(),
				new File(fileName));
		//imageReader2.setRange(new Range(100, 119)); 
		FeatureColorConversion fcc = new FeatureColorConversion();
		imageReader2.setColorConversion(fcc);
		imageDataTargetTextField.setText(fileName);
	} //GEN-LAST:event_targetImageDataSearchButtonActionPerformed

	private void regStartButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_regStartButtonActionPerformed
		// Add your handling code here:
		if (!checkRegistrationStart()) {
			return;
		}

		setClosable(true);
		cancelButton.setEnabled(false);
		closeButton.setEnabled(false);
		regStartButton.setEnabled(false);
		regStartButton.setText("Bild wird gerade koregistriert...");
		startRegistration();
	} //GEN-LAST:event_regStartButtonActionPerformed

	private void formInternalFrameClosed(
		javax.swing.event.InternalFrameEvent evt) {
		//GEN-FIRST:event_formInternalFrameClosed
		// Add your handling code here:
		try {
			setClosed(true);
			Viewer.getInstance().removeWizard(this);
		} catch (PropertyVetoException pve) {
			//Zur Zeit nichts
		}
	} //GEN-LAST:event_formInternalFrameClosed

	private void closeButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_closeButtonActionPerformed
		// Add your handling code here:
		onClose();
	} //GEN-LAST:event_closeButtonActionPerformed

	private void cancelButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_cancelButtonActionPerformed
		// Add your handling code here:
		onClose();
	} //GEN-LAST:event_cancelButtonActionPerformed

	private void monteCarloRegRadioButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_otherRegKindRadioButtonActionPerformed
		// Add your handling code here:
		rigidPCARegRadioButton.setSelected(false);
		nonRigidPCARegRadioButton.setSelected(false);
		registrationEnumeration = RegistratorEnumeration.MC_METHOD;
	} //GEN-LAST:event_otherRegKindRadioButtonActionPerformed

	private void rigidPCARadioButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_pcaRegKindRadioButtonActionPerformed
		// Add your handling code here:
		monteCarloRegRadioButton.setSelected(false);
		nonRigidPCARegRadioButton.setSelected(false);
		registrationEnumeration = RegistratorEnumeration.PCA_METHOD_RIGID;
	} //GEN-LAST:event_pcaRegKindRadioButtonActionPerformed

	private void sourceImageDataSearchButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_sourceImageDataSearchButtonActionPerformed
		// Add your handling code here:
        ImageFileChooser chooser = new ImageFileChooser();
        chooser.setDialogTitle("Datensatz auswählen");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//chooser.setCurrentDirectory(new File("E:/Daten/Diplom/data/reg.test.img/"));        
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

		ImageReaderFactory readerFactory = chooser.getImageReaderFactory();
		String fileName = chooser.getSelectedFile().getAbsolutePath();
		imageReader1 =
			readerFactory.createImageReader(
				ImageDataFactory.getInstance(),
				new File(fileName));
		//reader.setRange(new Range(100, 119));     
		FeatureColorConversion fcc = new FeatureColorConversion();
		imageReader1.setColorConversion(fcc);
		imageDataSourceTextField.setText(fileName);
	} //GEN-LAST:event_sourceImageDataSearchButtonActionPerformed

	public void update(java.util.Observable observable, java.lang.Object obj) {
	}

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JPanel northPanel;
        private javax.swing.JPanel centerPanel;
        private javax.swing.JTabbedPane Datensätze;
        private javax.swing.JPanel wizardStep1;
        private javax.swing.JPanel jPanel24;
        private javax.swing.JTextField imageDataSourceTextField;
        private javax.swing.JButton sourceImageDataSearchButton;
        private java.awt.Checkbox startDataLoaded;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JTextField imageDataTargetTextField;
        private javax.swing.JButton targetImageDataSearchButton;
        private java.awt.Checkbox targetDataLoaded;
        private javax.swing.JPanel wizardStep2;
        private javax.swing.JPanel jPanel32;
        private javax.swing.JPanel jPanel31;
        private javax.swing.JRadioButton rigidPCARegRadioButton;
        private javax.swing.JRadioButton monteCarloRegRadioButton;
        private javax.swing.JRadioButton nonRigidPCARegRadioButton;
        private javax.swing.JPanel jPanel33;
        private javax.swing.JPanel wizardStep3;
        private javax.swing.JPanel jPanel40;
        private javax.swing.JPanel jPanel39;
        private javax.swing.JButton regStartButton;
        private javax.swing.JPanel jPanel41;
        private javax.swing.JTextField registrationStateTextField;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel southPanel;
        private javax.swing.JPanel jPanel30;
        private javax.swing.JButton cancelButton;
        private javax.swing.JButton closeButton;
        // End of variables declaration//GEN-END:variables

}

