/*
 * Viewer.java
 *
 * Created on 28. M�rz 2002, 16:55
 */

package org.wewi.medimg.viewer;

import org.wewi.medimg.util.Singleton;
import org.wewi.medimg.image.*;
import org.wewi.medimg.image.io.*;

import org.wewi.medimg.viewer.wizard.Wizard;
import org.wewi.medimg.seg.wizard.SegmentationWizard;
import org.wewi.medimg.seg.wizard.TwinImageViewer;

import java.io.File;

import java.util.Properties;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.event.InternalFrameListener;
import javax.swing.JMenuItem;
import javax.swing.*;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public class Viewer extends JFrame implements Singleton,
                                              ImageViewerObserver,
                                              InternalFrameListener {
    
    private static Viewer singleton = null;
    private Properties viewerStates;
    private Dimension desktopDim;
    
    //Angemeldete Wizard
    SegmentationWizard segmentationWizard;
    
    /** Creates new form Viewer */
    private Viewer() {
        viewerStates = new Properties();
        initComponents();
        init();
    }
    
    private void init() {
        toolBar.add(NavigationPanel.getInstance());
        
        segmentationWizard = SegmentationWizard.getInstance();
    }
    
    public static Viewer getInstance() {
        if (singleton == null) {
            singleton = new Viewer();
        }
        return singleton;
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
        //frame.pack();
        frame.show();
    }
    
    public void removeViewerDesktopFrame(ViewerDesktopFrame frame) {
        desktopPane.remove(frame);
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
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        wizardMenu = new javax.swing.JMenu();
        segmentaionWizardMenuItem = new javax.swing.JMenuItem();

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

        fileMenu.setText("File");
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(openMenuItem);
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);
        saveAsMenuItem.setText("Save As ...");
        fileMenu.add(saveAsMenuItem);
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        editMenu.setText("Edit");
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);
        menuBar.add(editMenu);
        helpMenu.setText("Help");
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
        menuBar.add(helpMenu);
        wizardMenu.setText("Wizard");
        segmentaionWizardMenuItem.setText("SegmentationWizard");
        segmentaionWizardMenuItem.setToolTipText("null");
        segmentaionWizardMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentaionWizardMenuItemActionPerformed(evt);
            }
        });

        wizardMenu.add(segmentaionWizardMenuItem);
        menuBar.add(wizardMenu);
        setJMenuBar(menuBar);

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new java.awt.Dimension(1024, 748));
        setLocation((screenSize.width-1024)/2,(screenSize.height-748)/2);
    }//GEN-END:initComponents
    
    private void segmentaionWizardMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentaionWizardMenuItemActionPerformed
        // Add your handling code here:
        addWizard(segmentationWizard);
    }//GEN-LAST:event_segmentaionWizardMenuItemActionPerformed
    
                                                  private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
                                                      // Add your handling code here:
                                                      JInternalFrame about = new AboutFrame();
                                                      desktopPane.add(about);
                                                      about.show();
                                                  }//GEN-LAST:event_aboutMenuItemActionPerformed
                                                  
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        // Add your handling code here:
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(),
        new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
        try {
            reader.read();
        } catch (Exception e) {
            System.out.println("Viewer: " + e);
        }
        
        Image image = reader.getImage();
        //ImageViewer iv = new ImageViewer("ImageViewer", image);
        TwinImageViewer iv = new TwinImageViewer("Viewer", image, image);
        iv.pack();
        //addImageViewer(iv);
        addViewerDesktopFrame(iv);
        
    }//GEN-LAST:event_openMenuItemActionPerformed
    
    private void contentMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentMenuItemActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_contentMenuItemActionPerformed
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
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
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JMenuItem segmentaionWizardMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel statePanel;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenu wizardMenu;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JPanel rightSplitPanel;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenu helpMenu;
    // End of variables declaration//GEN-END:variables
    
    
}
