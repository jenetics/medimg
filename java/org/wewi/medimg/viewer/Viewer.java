/*
 * Viewer.java
 *
 * Created on 28. M�rz 2002, 16:55
 */

package org.wewi.medimg.viewer;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameListener;

import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.reg.wizard.RegistrationWizard;
import org.wewi.medimg.seg.wizard.SegmentationWizard;
import org.wewi.medimg.util.Singleton;
import org.wewi.medimg.viewer.wizard.Wizard;
import org.wewi.medimg.visualisation.Viewer3D;
import org.wewi.medimg.visualisation.mc.wizard.MarchingCubeWizard;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public class Viewer extends JFrame implements Singleton,
                                              ImageViewerObserver,
                                              InternalFrameListener {
    
    private static Viewer singleton = null;
    private ViewerPreferences viewerPrefs; 
    private Dimension desktopDim;
    
    //Command Objekte f�r Men�bar
    private Command openCommand;
    private Command saveCommand;
    private Command saveAsCommand;
    
    /** Creates new form Viewer */
    private Viewer() {
        viewerPrefs = ViewerPreferences.getInstance();
        initComponents();
        init();
    }
    
    private void init() {
        toolBar.add(NavigationPanel.getInstance());
        openCommand = new OpenCommand(this);
        
        setSize(viewerPrefs.getViewerDimension());
        setLocation(viewerPrefs.getViewerLocation());
    }
    
    private void onExit() {
        viewerPrefs.setViewerDimension(this.getSize());
        viewerPrefs.setViewerLocation(this.getLocation());
    }
    
    public static Viewer getInstance() {
        if (singleton == null) {
            singleton = new Viewer();
        }
        return singleton;
    }
    
    public void setOpenCommand(Command command) {
        openCommand = command;
    }
    
    public Command getOpenCommand() {
        return openCommand;
    }
    
    public void setSaveCommand(Command command) {
        saveCommand = command;
    }
    
    public Command getSaveCommand() {
        return saveCommand;
    }
    
    public void setSaveAsCommand(Command command) {
        saveAsCommand = command;
    }
    
    public Command getSaveAsCommand() {
        return saveAsCommand;
    }
    
    public void addViewerState(StatePanelEntry state) {
        statePanel.add(state);
    }
    
    public void removeViewerState(StatePanelEntry state) {
        statePanel.remove(state);
    }
    
    public void addViewerDesktopFrame(ViewerDesktopFrame frame) {
        desktopPane.add(frame);
        frame.addInternalFrameListener(this);
        frame.show();
    }
    
    public void removeViewerDesktopFrame(ViewerDesktopFrame frame) {
        desktopPane.remove(frame);
        frame.removeInternalFrameListener(this);
    }
    
    public void addWizard(Wizard wizard) {
        desktopPane.add(wizard);
        wizard.show();
        wizardMenu.setEnabled(false);
    }
    
    public void removeWizard(Wizard wizard) {
        desktopPane.remove(wizard);
        wizardMenu.setEnabled(true);
    }
    
    public NavigationPanel getNavigationPanel() {
        return NavigationPanel.getInstance();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        statePanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        toolBar = new javax.swing.JToolBar();
        rightSplitPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        wizardMenu = new javax.swing.JMenu();
        segmentaionWizardMenuItem = new javax.swing.JMenuItem();
        registrationWizardMenuItem = new javax.swing.JMenuItem();
        marchingCubeWizardMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        show3D = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Segmentierungs- und Registrierungsviewer");
        setForeground(java.awt.Color.white);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/logo.gif")).getImage());
        setName("viewerFrame");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        statePanel.setLayout(new java.awt.BorderLayout());

        getContentPane().add(statePanel, java.awt.BorderLayout.SOUTH);

        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel10.add(toolBar, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel10, java.awt.BorderLayout.NORTH);

        rightSplitPanel.setLayout(new java.awt.GridLayout(1, 0));

        rightSplitPanel.setAutoscrolls(true);
        jScrollPane7.setAutoscrolls(true);
        jScrollPane7.setViewportView(desktopPane);

        rightSplitPanel.add(jScrollPane7);

        getContentPane().add(rightSplitPanel, java.awt.BorderLayout.CENTER);

        fileMenu.setText("Datei");
        openMenuItem.setText("\u00d6ffnen");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(openMenuItem);
        saveMenuItem.setText("Speichern");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(saveMenuItem);
        saveAsMenuItem.setText("Speichern als...");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(saveAsMenuItem);
        exitMenuItem.setText("Beenden");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        editMenu.setText("Bearbeiten");
        cutMenuItem.setText("Ausschneiden");
        editMenu.add(cutMenuItem);
        copyMenuItem.setText("Kopieren");
        editMenu.add(copyMenuItem);
        pasteMenuItem.setText("Einf\u00fcgen");
        editMenu.add(pasteMenuItem);
        deleteMenuItem.setText("Enfernen");
        editMenu.add(deleteMenuItem);
        menuBar.add(editMenu);
        wizardMenu.setText("Assistenten");
        segmentaionWizardMenuItem.setText("Segmentierungsassistent");
        segmentaionWizardMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentaionWizardMenuItemActionPerformed(evt);
            }
        });

        wizardMenu.add(segmentaionWizardMenuItem);
        registrationWizardMenuItem.setText("Registrieringsassistent");
        registrationWizardMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrationWizardMenuItemActionPerformed(evt);
            }
        });

        wizardMenu.add(registrationWizardMenuItem);
        marchingCubeWizardMenuItem.setText("Marching Cube Assistent");
        marchingCubeWizardMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marchingCubeWizardMenuItemActionPerformed(evt);
            }
        });

        wizardMenu.add(marchingCubeWizardMenuItem);
        menuBar.add(wizardMenu);
        helpMenu.setText("Hilfe");
        helpMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuActionPerformed(evt);
            }
        });

        contentMenuItem.setText("Contents");
        contentMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentMenuItemActionPerformed(evt);
            }
        });

        helpMenu.add(contentMenuItem);
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });

        helpMenu.add(aboutMenuItem);
        show3D.setText("show3D");
        show3D.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                show3DMenuItemActionPerformed(evt);
            }
        });

        helpMenu.add(show3D);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new java.awt.Dimension(614, 422));
        setLocation((screenSize.width-614)/2,(screenSize.height-422)/2);
    }//GEN-END:initComponents

    private void marchingCubeWizardMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marchingCubeWizardMenuItemActionPerformed
        // Add your handling code here:
        addWizard(new MarchingCubeWizard());
    }//GEN-LAST:event_marchingCubeWizardMenuItemActionPerformed

    private void helpMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_helpMenuActionPerformed

    private void show3DMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show3DMenuItemActionPerformed
        // Add your handling code here:
        Viewer3D viewer3D = new Viewer3D("Test");
        viewer3D.setPreferredSize(new Dimension(640, 640));            
        viewer3D.pack();        
        addViewerDesktopFrame(viewer3D);
    }//GEN-LAST:event_show3DMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void registrationWizardMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrationWizardMenuItemActionPerformed
        // Add your handling code here:
        addWizard(new RegistrationWizard());        
    }//GEN-LAST:event_registrationWizardMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        // Add your handling code here:
        Object obj = desktopPane.getSelectedFrame();
        if (obj == null) {
            return;
        }
        Class[] interfaces = obj.getClass().getInterfaces();
        ImageContainer imageContainer = null;
        for (int i = 0; i < interfaces.length; i++) {
            System.out.println(interfaces[i].toString());
            if (interfaces[i].getName().endsWith("org.wewi.medimg.viewer.ImageContainer")) {
                imageContainer = (ImageContainer)obj;
            }
        }
        if (imageContainer == null) {
            return;
        }
        
        ImageFileChooser chooser = new ImageFileChooser();
        chooser.setDialogTitle("Datensatz ausw�hlen");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setCurrentDirectory(new File("C:/Workspace/fwilhelm/Projekte/Diplom/data"));
        
        int returnVal = chooser.showSaveDialog(this);
        if(returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }        
        
        ImageWriter writer = chooser.getImageWriterFactory()
                                         .createImageWriter(imageContainer.getImage(),
                                                            chooser.getSelectedFile());
        try {
            writer.write();
        } catch (Exception e) {
            System.err.println("Viewer.openMenuItemActionPerformed: " + e);
            JOptionPane.showMessageDialog(this, "Kann Datei: \n" + chooser.getSelectedFile().getAbsolutePath() + 
                                                "\n nicht �ffnen", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }        
    }//GEN-LAST:event_saveAsMenuItemActionPerformed
    
    private void segmentaionWizardMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentaionWizardMenuItemActionPerformed
        // Add your handling code here:
        addWizard(new SegmentationWizard());
    }//GEN-LAST:event_segmentaionWizardMenuItemActionPerformed
    
                                                  private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
                                                      // Add your handling code here:
                                                      JInternalFrame about = new AboutFrame();
                                                      desktopPane.add(about);
                                                      about.show();
                                                      aboutMenuItem.setEnabled(false);
                                                  }//GEN-LAST:event_aboutMenuItemActionPerformed
                                                  
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        // Add your handling code here:
        openCommand.execute();
    }//GEN-LAST:event_openMenuItemActionPerformed
    
    private void contentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentMenuItemActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_contentMenuItemActionPerformed
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        onExit();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        onExit();
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Viewer viewer = Viewer.getInstance();
        viewer.show();
    }
    
    public void update(ImageViewerEvent event) {
    }
    
    public void internalFrameActivated(javax.swing.event.InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameOpened(javax.swing.event.InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameIconified(javax.swing.event.InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameClosing(javax.swing.event.InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameClosed(javax.swing.event.InternalFrameEvent internalFrameEvent) {
        removeViewerDesktopFrame((ViewerDesktopFrame)internalFrameEvent.getSource());
        System.out.println("" + desktopPane.getAllFrames().length);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JMenuItem segmentaionWizardMenuItem;
    private javax.swing.JMenuItem registrationWizardMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel statePanel;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem show3D;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenu wizardMenu;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JMenuItem marchingCubeWizardMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JPanel rightSplitPanel;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenu helpMenu;
    // End of variables declaration//GEN-END:variables
    
    
}
