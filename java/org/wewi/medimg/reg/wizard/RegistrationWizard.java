/*
 * SegmentationWizard.java
 *
 * Created on 7. April 2002, 12:12
 */

package org.wewi.medimg.reg.wizard;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.io.File;
import java.util.Observer;

import javax.swing.JFileChooser;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.TissueColorConversion;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.image.io.ReaderThreadEvent;
import org.wewi.medimg.image.io.ReaderThreadListener;
import org.wewi.medimg.image.io.WriterThreadEvent;
import org.wewi.medimg.image.io.WriterThreadListener;
import org.wewi.medimg.reg.PCARegStrategy;
import org.wewi.medimg.reg.RegStrategy;
import org.wewi.medimg.reg.RegisterParameter;
import org.wewi.medimg.reg.Registrate;
import org.wewi.medimg.reg.interpolation.WeightPointStrategy;
import org.wewi.medimg.reg.metric.BBAffinityMetric;
import org.wewi.medimg.seg.wizard.TwinImageViewer;
import org.wewi.medimg.viewer.ImageFileChooser;
import org.wewi.medimg.viewer.ImageViewer;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.wizard.Wizard;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class RegistrationWizard
	extends Wizard
	implements
		Observer,
		ReaderThreadListener,
		WriterThreadListener,
		RegistrationListener {

	private static final String MENU_NAME = "Registration-Wizard";
	private static RegistrationWizard singleton = null;

	private ImageReader imageReader1;
	private ImageReader imageReader2;
	private RegistrationKind registrationKind = RegistrationKind.PCA;
	//private int nfeatures = 4;

	private Registrate reg = null;
	private RegStrategy regStrategy = null;
	private RegisterParameter param = null;
	private Image imageData1;
	private Image imageData2;
	private Image featureData = null;

	private RegistrateThread registrationThread;
	private ImageReaderThread readerThread1;
	private ImageReaderThread readerThread2;

	private TwinImageViewer imageViewer;

	/** Creates new form SegmentationWizard */
	public RegistrationWizard() {
		super(MENU_NAME, false, true, false, false);
		initComponents();
		init();
	}

	/*
	public static SegmentationWizard getInstance() {
	    if (singleton == null) {
	        singleton = new SegmentationWizard();
	    }
	    return singleton;
	}
	*/

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
		readerThread1 = new ImageReaderThread(imageReader1);
		readerThread1.addReaderThreadListener(this);
		readerThread1.setPriority(Thread.MIN_PRIORITY);
		readerThread1.start();
	}

	private void loadTargetImage() {
		regStartButton.setText("Laden des Zielbildes...");
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
			if (registrationKind.equals(RegistrationKind.PCA)) {
				WeightPointStrategy myStrategy = new WeightPointStrategy();
				//ImportanceStrategy myStrategy = new ImportanceStrategy();
				//FittnessStrategy myStrategy = new FittnessStrategy();
				BBAffinityMetric myMetric = new BBAffinityMetric();
				//ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
				myStrategy.setErrorLimit(0.2);
				regStrategy = new PCARegStrategy(myStrategy, myMetric);
			} else if (registrationKind.equals(RegistrationKind.MoreToCome)) {
				WeightPointStrategy myStrategy = new WeightPointStrategy();
				//ImportanceStrategy myStrategy = new ImportanceStrategy();
				//FittnessStrategy myStrategy = new FittnessStrategy();
				BBAffinityMetric myMetric = new BBAffinityMetric();
				//ConstantAffinityMetric myMetric = new ConstantAffinityMetric();
				myStrategy.setErrorLimit(0.2);
				regStrategy = new PCARegStrategy(myStrategy, myMetric);
			}
			param = new RegisterParameter();
			param.setSourceImage(imageData1);
			param.setTargetImage(imageData2);
			reg = new Registrate(regStrategy, param);
			reg.addRegistrationListener(this);
			RegistrateThread registrationThread = new RegistrateThread(reg);
			registrationThread.setPriority(Thread.MIN_PRIORITY);
			registrationThread.start();
			regStartButton.setText("Koregistrieren der Bilder...");
		} else {
			System.out.println("Probleme beim Einlesen der Images");
		}
	}

	public void imageWritten(WriterThreadEvent event) {
	}

	/*public void iterationFinished(SegmentationEvent event) {  
	    imageViewer.setSlice(imageViewer.getSlice());
	    segmentationStateTextField.setText(event.toString());
	}*/

	public void registrationStarted(RegistrationEvent event) {
		TissueColorConversion tcc = new TissueColorConversion();
		imageData1.setColorConversion(tcc);
		imageData2.setColorConversion(tcc);
		imageViewer =
			new TwinImageViewer("Koregistriervorgang", imageData1, imageData2);
		//imageViewer.setColorConversion1(tcc);
		//imageViewer.setColorConversion2(tcc);
		imageViewer.pack();
		Viewer.getInstance().addViewerDesktopFrame(imageViewer);
		registrationStateTextField.setText(event.toString());
	}

	public void registrationFinished(RegistrationEvent event) {
		regStartButton.setText("Start");
		registrationStateTextField.setText(event.toString());
		TissueColorConversion tcc = new TissueColorConversion();
		ImageViewer iv =
			new ImageViewer(
				"Koregistriervorgang beendet",
				param.getTargetImage(),
				tcc);

		//iv.setColorConversion(tcc);
		int sizeX = imageData1.getMaxX() - imageData1.getMinX() + 1;
		int sizeY = imageData1.getMaxY() - imageData1.getMinY() + 1;
		iv.setPreferredSize(new Dimension(sizeX, sizeY));
		iv.pack();
		Viewer.getInstance().addViewerDesktopFrame(iv);
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
	private void initComponents() { //GEN-BEGIN:initComponents
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
		pcaRegKindRadioButton = new javax.swing.JRadioButton();
		otherRegKindRadioButton = new javax.swing.JRadioButton();
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

		addInternalFrameListener(
			new javax
			.swing
			.event
			.InternalFrameListener() {
			public void internalFrameOpened(
				javax.swing.event.InternalFrameEvent evt) {
			}
			public void internalFrameClosing(
				javax.swing.event.InternalFrameEvent evt) {
			}
			public void internalFrameClosed(
				javax.swing.event.InternalFrameEvent evt) {
				formInternalFrameClosed(evt);
			}
			public void internalFrameIconified(
				javax.swing.event.InternalFrameEvent evt) {
			}
			public void internalFrameDeiconified(
				javax.swing.event.InternalFrameEvent evt) {
			}
			public void internalFrameActivated(
				javax.swing.event.InternalFrameEvent evt) {
			}
			public void internalFrameDeactivated(
				javax.swing.event.InternalFrameEvent evt) {
			}
		});

		getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);

		Datensätze.setName("Segmentation Wizard");
		Datensätze.setPreferredSize(new java.awt.Dimension(450, 150));
		wizardStep1.setLayout(new java.awt.GridLayout(2, 1));

		imageDataSourceTextField.setEditable(false);
		imageDataSourceTextField.setHorizontalAlignment(
			javax.swing.JTextField.LEFT);
		imageDataSourceTextField.setMinimumSize(
			new java.awt.Dimension(150, 21));
		imageDataSourceTextField.setPreferredSize(
			new java.awt.Dimension(400, 21));
		jPanel24.add(imageDataSourceTextField);

		sourceImageDataSearchButton.setText("Startdatensatz ausw\u00e4hlen...");
		sourceImageDataSearchButton
			.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sourceImageDataSearchButtonActionPerformed(evt);
			}
		});

		jPanel24.add(sourceImageDataSearchButton);

		jPanel24.add(startDataLoaded);

		wizardStep1.add(jPanel24);

		imageDataTargetTextField.setEditable(false);
		imageDataTargetTextField.setHorizontalAlignment(
			javax.swing.JTextField.LEFT);
		imageDataTargetTextField.setMinimumSize(
			new java.awt.Dimension(150, 21));
		imageDataTargetTextField.setPreferredSize(
			new java.awt.Dimension(400, 21));
		jPanel2.add(imageDataTargetTextField);

		targetImageDataSearchButton.setText("Zieldatensatz ausw\u00e4hlen...");
		targetImageDataSearchButton
			.addActionListener(new java.awt.event.ActionListener() {
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

		pcaRegKindRadioButton.setSelected(true);
		pcaRegKindRadioButton.setText("PCA- Registrierung");
		pcaRegKindRadioButton
			.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pcaRegKindRadioButtonActionPerformed(evt);
			}
		});

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 1;
		gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 25);
		jPanel31.add(pcaRegKindRadioButton, gridBagConstraints1);

		otherRegKindRadioButton.setText("Andere Registrierung");
		otherRegKindRadioButton
			.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				otherRegKindRadioButtonActionPerformed(evt);
			}
		});

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.gridy = 3;
		gridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 25);
		jPanel31.add(otherRegKindRadioButton, gridBagConstraints1);

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
	} //GEN-END:initComponents

	private void targetImageDataSearchButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_targetImageDataSearchButtonActionPerformed
		// Add your handling code here:
		ImageFileChooser chooser = new ImageFileChooser();
		chooser.setDialogTitle("Datensätze auswählen");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setCurrentDirectory(new File("E:/temp/img"));

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
		TissueColorConversion tcc = new TissueColorConversion();
		//imageReader2.setColorConversion(tcc);
		imageDataTargetTextField.setText(fileName);
	} //GEN-LAST:event_targetImageDataSearchButtonActionPerformed

	private void regStartButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_regStartButtonActionPerformed
		// Add your handling code here:
		if (!checkRegistrationStart()) {
			return;
		}

		setClosable(false);
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

	private void otherRegKindRadioButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_otherRegKindRadioButtonActionPerformed
		// Add your handling code here:
		pcaRegKindRadioButton.setSelected(false);
		registrationKind = RegistrationKind.MoreToCome;
	} //GEN-LAST:event_otherRegKindRadioButtonActionPerformed

	private void pcaRegKindRadioButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_pcaRegKindRadioButtonActionPerformed
		// Add your handling code here:
		otherRegKindRadioButton.setSelected(false);
		registrationKind = RegistrationKind.PCA;
	} //GEN-LAST:event_pcaRegKindRadioButtonActionPerformed

	private void sourceImageDataSearchButtonActionPerformed(
		java.awt.event.ActionEvent evt) {
		//GEN-FIRST:event_sourceImageDataSearchButtonActionPerformed
		// Add your handling code here:

		ImageFileChooser chooser = new ImageFileChooser();
		chooser.setDialogTitle("Datensätze auswählen");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setCurrentDirectory(new File("E:/temp/img"));

		int returnVal = chooser.showOpenDialog(this);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			return;
		}
		ImageReaderFactory readerFactory = chooser.getImageReaderFactory();
		String fileName = chooser.getSelectedFile().getAbsolutePath();
		imageReader1 =
			readerFactory.createImageReader(
				ImageDataFactory.getInstance(),
				new File(fileName));
		//reader.setRange(new Range(100, 119));     
		TissueColorConversion tcc = new TissueColorConversion();
		//imageReader1.setColorConversion(tcc);
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
	private javax.swing.JRadioButton pcaRegKindRadioButton;
	private javax.swing.JRadioButton otherRegKindRadioButton;
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
