/**
 * Viewer.java
 *
 * Created on 28. März 2002, 16:55
 */

package org.wewi.medimg.viewer;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.InternalFrameListener;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.ImageIOProgressEvent;
import org.wewi.medimg.image.io.ImageIOProgressListener;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageReaderFactory;
import org.wewi.medimg.image.io.ImageReaderThread;
import org.wewi.medimg.image.io.RawImageReaderFactory;
import org.wewi.medimg.reg.wizard.RegistrationWizard;
import org.wewi.medimg.seg.ac.ActiveContourWizard;
import org.wewi.medimg.seg.wizard.SegmentationWizard;
import org.wewi.medimg.util.Singleton;
import org.wewi.medimg.viewer.image.ImageViewer;
import org.wewi.medimg.viewer.wizard.Wizard;
import org.wewi.medimg.visualisation.Viewer3D;
import org.wewi.medimg.visualisation.mc.wizard.MarchingCubeWizard;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 * 
 * Die Viewer-Klasse ist die gemeinsame Oberfläche
 * der Segmentierungs- und Registrierungsalgorithmen.
 * Der Viewer ist <code>Singleton</code>, und kann von
 * den Segmentierungs- und Registrierungsklassen über
 * die <code>getInstance()</code>-Methode jeder Zeit
 * angesprochen werden, um z.B. die <code>Command</code>-
 * Objekte der Menüzeile auszutauschen. 
 */
public class Viewer extends JFrame implements Singleton,
                                              ImageViewerListener,
                                              InternalFrameListener,
                                              ImageIOProgressListener {
                                                  
    private final class ScrollPanelHelper implements ComponentListener {
        private Viewer viewer;
        private int minX, minY, maxX, maxY;
        
        public ScrollPanelHelper(Viewer viewer) {
            this.viewer = viewer;          
        }
         
        public void componentHidden(ComponentEvent componentEvent) {
        }        
        
        public void componentMoved(ComponentEvent componentEvent) {
            //calcSize();
        }
        
        public void componentResized(ComponentEvent componentEvent) {
            //calcSize();
        }
        
        public void componentShown(ComponentEvent componentEvent) {
            //calcSize();
        }
        
        private void calcSize() {
            int tminX = Integer.MAX_VALUE;
            int tminY = Integer.MAX_VALUE;
            int tmaxX = Integer.MIN_VALUE;
            int tmaxY = Integer.MIN_VALUE;
            
            JInternalFrame[] frames = viewer.desktopPane.getAllFrames();
            Point pos = null;
            Dimension dim = null;
            for (int i = 0; i < frames.length; i++) {
                pos = frames[i].getLocation();
                dim = frames[i].getPreferredSize();
                tminX = (int)Math.min(tminX, pos.getX());
                tminY = (int)Math.min(tminY, pos.getY());
                tmaxX = (int)Math.max(tmaxX, pos.getX() + dim.getWidth());
                tmaxY = (int)Math.max(tmaxY, pos.getY() + dim.getHeight());
            }
            
            //if (tmaxX > maxX || tmaxY > maxY) {
                maxX = tmaxX;
                maxY = tmaxY;
                minX = tminX;
                minY = tminY;
                //viewer.desktopPane.setLocation(minX, minY);
                viewer.desktopPane.setPreferredSize(new Dimension(maxX, maxY));
                viewer.desktopPane.updateUI();
            //}
        }
        
    }
    
    
    private static final String VERSION = "Image-Viewer version 0.1";
    
    private static Viewer singleton = null;
    private ViewerPreferences viewerPrefs; 
    private ScrollPanelHelper scrollPanelHelper;
    
    private ProgressFrame progressFrame;
    private Logger logger;
    
    //Command Objekte für Menübar
    private Command openCommand = new NullCommand();
    private Command saveCommand = new NullCommand();
    private Command saveAsCommand = new NullCommand();
    
    private JMenuItem[] lnfMenuItems;
    
    /** 
     * Privater Konstruktor für Singleton-Pattern
     */
    private Viewer() {
        super();
        initLogger();
        
        viewerPrefs = ViewerPreferences.getInstance();
        
        initComponents();
        init();
        initLookAndFeelMenu();
    }
    
    /**
     * Über diese Methode kann die einzig existierende
     * Instanz des <code>Viewer</code> geholt werden.
     * 
     * @return einzige Viewer-Instanz
     */
    public static Viewer getInstance() {
        if (singleton == null) {
            singleton = new Viewer();
        }
        return singleton;
    } 
    
    private void initLogger() {
        logger = Logger.getLogger(Viewer.class.getName());
        OutputStream logOutput = null;
        File dir = new File("./log");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File logFile = new File("./log/viewer.log");
        try {
            if (logFile.exists()) {
                logOutput = new FileOutputStream(logFile, true);
            } else {
                logOutput = new FileOutputStream(logFile, false);  
            }
        } catch (IOException ioe) {
            logger.throwing("","", ioe);    
        }
        logger.addHandler(new StreamHandler(logOutput, new SimpleFormatter()));
        
        logger.info("Gestartet");        
    }   
    
    /**
     * Eigene Initialisierungsmethod, die nach der
     * automatischen Komponenteninitialisierung
     * aufgerufen wird.
     */
    private void init() {
        toolBar.add(NavigationPanel.getInstance());
        openCommand = new OpenCommand(this);
        scrollPanelHelper = new ScrollPanelHelper(this);
        
        setLookAndFeel(viewerPrefs.getLookAndFeelClassName(), this);
        
        setSize(viewerPrefs.getViewerDimension());
        setLocation(viewerPrefs.getViewerLocation());                
    }
    
    
    private class LookAndFeelActionListener implements ActionListener {
        private String className;
        private JFrame frame;
        
        public LookAndFeelActionListener(String className, JFrame frame) {
            this.className = className;  
            this.frame = frame;  
        }
		public void actionPerformed(ActionEvent arg0) {            
            setLookAndFeel(className, frame); 
            viewerPrefs.setLookAndFeelClassName(className);                 
		}

    }
    
    private void setLookAndFeel(String className, JFrame frame) {
        try {
            UIManager.setLookAndFeel(className);
        } catch (ClassNotFoundException e) {
            logger.throwing(LookAndFeelActionListener.class.getName(),
                            "actionPerformed()", e);
        } catch (InstantiationException e) {
            logger.throwing(LookAndFeelActionListener.class.getName(),
                            "actionPerformed()", e);                
        } catch (IllegalAccessException e) {
            logger.throwing(LookAndFeelActionListener.class.getName(),
                            "actionPerformed()", e);                
        } catch (UnsupportedLookAndFeelException e) {
            logger.throwing(LookAndFeelActionListener.class.getName(),
                            "actionPerformed()", e);                
        }
        SwingUtilities.updateComponentTreeUI(frame);             
        
        Dimension dim = frame.getSize();
        Point point = frame.getLocation();
        
        frame.pack();             
        frame.setSize(dim);
        frame.setLocation(point);        
        frame.validate();        
    }
    
    private void initLookAndFeelMenu() {
        UIManager.LookAndFeelInfo[] lnfi = UIManager.getInstalledLookAndFeels();
        if (lnfi == null) {
            return;    
        }
        if (lnfi.length <= 0) {
            return;    
        }
        
        lnfMenuItems = new JMenuItem[lnfi.length];
        for (int i = 0; i < lnfi.length; i++) {
            lnfMenuItems[i] = new JMenuItem();
            lnfMenuItems[i].setFont(new java.awt.Font("Dialog", 0, 12));
            lnfMenuItems[i].setText(lnfi[i].getName());
            
            lnfMenuItems[i].addActionListener(new LookAndFeelActionListener(lnfi[i].getClassName(),
                                                                             this));            
        }
        
        for (int i = 0; i < lnfi.length; i++) {
            lnfMenu.add(lnfMenuItems[i]);    
        }
     
    }
    
    
    /**
     * Diese Methode soll beim Beenden des
     * Viewers aufgerufen werden.
     */
    private void onExit() {
        //Sichern der aktuellen Viewerposition und Größe.
        viewerPrefs.setViewerDimension(this.getSize());
        viewerPrefs.setViewerLocation(this.getLocation());
    }
    
    public Logger getLogger() {
        return logger;    
    }
    
    public synchronized void addLoggerHandler(Handler handler) {
        logger.addHandler(handler);
    }
    
    public synchronized void removeLoggerHandler(Handler handler) {
        logger.removeHandler(handler);
    }
        
    
    /**
     * Diese Methde erlaubt es das Öffnen-Kommando
     * in der Menüzeile zu änndern.
     * 
     * @param command Das Öffnen-Kommando
     */
    public synchronized void setOpenCommand(Command command) {
        openCommand = command;
    }
    
	/**
	 * Diese Methode liefert das aktuelle Öffnen-Kommando
     * der Menüzeile wieder.
     * 
	 * @return Öffnene-Kommando
	 */
    public Command getOpenCommand() {
        synchronized (openCommand) {
            return openCommand;
        }
    }
    
    /**
     * Diese Methde erlaubt es das Speichern-Kommando
     * in der Menüzeile zu änndern.
     * 
     * @param command Das Speichern-Kommando
     */
    public synchronized void setSaveCommand(Command command) {
        saveCommand = command;
    }
    
    /**
     * Diese Methode liefert das aktuelle Speichern-Kommando
     * der Menüzeile wieder.
     * 
     * @return Speichern-Kommando
     */    
    public Command getSaveCommand() {
        synchronized (saveCommand) {
            return saveCommand;
        }
    }
    
    /**
     * Diese Methde erlaubt es das Speichern als-Kommando
     * in der Menüzeile zu änndern.
     * 
     * @param command Das Speichern als-Kommando
     */    
    public synchronized void setSaveAsCommand(Command command) {
        saveAsCommand = command;
    }
    
    /**
     * Diese Methode liefert das aktuelle Speichern als-Kommando
     * der Menüzeile wieder.
     * 
     * @return Speichern als-Kommando
     */    
    public Command getSaveAsCommand() {
        synchronized (saveAsCommand) {
            return saveAsCommand;
        }
    }
    
    public synchronized void addViewerState(StatePanelEntry state) {
        statePanel.add(state);
    }
    
    public synchronized void removeViewerState(StatePanelEntry state) {
        statePanel.remove(state);
    }
    
    /**
     * Hinzufügen eines zusätzlichen DesktopFrame. Der
     * Frame wird gleichzeitig zu den FrameListenern 
     * hinzugefügt.
     * 
     * @param frame ViewerDesktopFrame der hinzugefügt wird.
     */
    public synchronized void addViewerDesktopFrame(ViewerDesktopFrame frame) {
        desktopPane.add(frame);
        frame.addComponentListener(scrollPanelHelper);
        frame.addInternalFrameListener(this);
        frame.show();
    }
    
    public void addViewerDesktopFrame(ViewerDesktopFrame frame, Point pos, Dimension size) {
        desktopPane.add(frame);
        frame.addComponentListener(scrollPanelHelper);
        frame.addInternalFrameListener(this);
        frame.show();
           
        frame.setLocation(pos);
        frame.setSize(size);
    }
    
    public synchronized void addViewerDesktopFrame(ViewerDesktopFrame frame, Point pos) {
        desktopPane.add(frame);
        frame.addComponentListener(scrollPanelHelper);
        frame.addInternalFrameListener(this);
        frame.show();
        
        frame.setLocation(pos);    
    }
    
    public synchronized void addViewerDesktopFrame(ViewerDesktopFrame frame, Dimension size) {
        addViewerDesktopFrame(frame);
        frame.setSize(size);    
    }
    
    public void addImageViewerSynchronizer(ImageViewerSynchronizer ivs) {
        int size = ivs.size();
        ImageViewer[] viewer = new ImageViewer[size];
        ivs.getImageViewer(viewer);
        Point[] location = new Point[size];
        ivs.getImageViewerLocation(location);
        Dimension[] dim = new Dimension[size];
        ivs.getImageViewerSize(dim);
        
        for (int i = 0; i < size; i++) {
            addViewerDesktopFrame(viewer[i], location[i], dim[i]);    
        }
    }
    
    /**
     * Entfernen eines ViewerDesktopFrame. Wird auch gleichzeitig
     * von den Listenern ausgehängt.
     * 
     * @param frame ViewerDesktopFrame der entfernt wreden soll.
     */
    public void removeViewerDesktopFrame(ViewerDesktopFrame frame) {
        desktopPane.remove(frame);
        frame.removeInternalFrameListener(this);
        frame.removeComponentListener(scrollPanelHelper);
    }
    
    /**
     * Hinzufügen eines Wizards. Der Wizard-Menüpunkt 
     * wird auf <code>enabled = false</code> geschaltet.
     * Dadurch soll verhindert werden, daß zwei 
     * Wizards gleichzeitig aktiv sind und sich
     * in die Quere kommen.
     * 
     * @param wizard der Hinzugefügt wird.
     */
    public void addWizard(Wizard wizard) {
        desktopPane.add(wizard);
        wizard.addComponentListener(scrollPanelHelper);
        wizard.show();
        wizardMenu.setEnabled(false);
        segmentaionWizardMenuItem.setEnabled(false);
        registrationWizardMenuItem.setEnabled(false);
        //marchingCubeWizardMenuItem.setEnabled(false);
    }
    
    /**
     * Entfernen eines Wizards. Der Wizard-Menüpunkt wird
     * wieder benutzbar.
     * 
     * @param wizard der Entfent werden soll
     */
    public void removeWizard(Wizard wizard) {
        desktopPane.remove(wizard);
        wizard.removeComponentListener(scrollPanelHelper);
        wizardMenu.setEnabled(true);
        segmentaionWizardMenuItem.setEnabled(true);
        registrationWizardMenuItem.setEnabled(true);  
        //marchingCubeWizardMenuItem.setEnabled(true);
    }
    
    /**
     * Liefert die einzig existierende Instance des NavigationPanel
     * wieder. Dort können dann die entsprechenden Command-Objekte
     * ausgetauscht werden.
     * 
     * @return NavigationPanel
     */
    public NavigationPanel getNavigationPanel() {
        return NavigationPanel.getInstance();
    }
    
    /**
     * Liefert den aktuell gewählten Frame
     */
    public JInternalFrame getSelectedFrame() {
        return desktopPane.getSelectedFrame();    
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        javax.swing.JScrollPane scrollPane;

        statePanel = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        toolBar = new javax.swing.JToolBar();
        rightSplitPanel = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        wizardMenu = new javax.swing.JMenu();
        segmentaionWizardMenuItem = new javax.swing.JMenuItem();
        registrationWizardMenuItem = new javax.swing.JMenuItem();
        marchingCubeWizardMenuItem = new javax.swing.JMenuItem();
        activeContourwizardMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        lnfMenu = new javax.swing.JMenu();

        setTitle("Segmentierungs- und Registrierungsviewer");
        setFont(new java.awt.Font("Dialog", 0, 12));
        setForeground(java.awt.Color.white);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/logo_seg.gif")).getImage());
        setName("viewerFrame");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        statePanel.setLayout(new java.awt.BorderLayout());

        getContentPane().add(statePanel, java.awt.BorderLayout.SOUTH);

        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));

        toolBar.setRollover(true);
        toolBar.setDoubleBuffered(true);
        toolBar.setFont(new java.awt.Font("Dialog", 0, 12));
        jPanel10.add(toolBar);

        getContentPane().add(jPanel10, java.awt.BorderLayout.NORTH);

        rightSplitPanel.setLayout(new java.awt.GridLayout(1, 0));

        rightSplitPanel.setAutoscrolls(true);
        scrollPane.setDoubleBuffered(true);
        scrollPane.setAutoscrolls(true);
        scrollPane.setViewportView(desktopPane);

        rightSplitPanel.add(scrollPane);

        getContentPane().add(rightSplitPanel, java.awt.BorderLayout.CENTER);

        menuBar.setFont(new java.awt.Font("Dialog", 0, 12));
        fileMenu.setMnemonic('D');
        fileMenu.setText("Datei");
        fileMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        openMenuItem.setMnemonic('f');
        openMenuItem.setText("\u00d6ffnen");
        openMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/open16.gif")));
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        saveMenuItem.setMnemonic('S');
        saveMenuItem.setText("Speichern");
        saveMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/save16.gif")));
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(saveMenuItem);

        exitMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        exitMenuItem.setMnemonic('e');
        exitMenuItem.setText("Beenden");
        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/stop16.gif")));
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        wizardMenu.setMnemonic('A');
        wizardMenu.setText("Assistenten");
        wizardMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        segmentaionWizardMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        segmentaionWizardMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        segmentaionWizardMenuItem.setText("Segmentierung...");
        segmentaionWizardMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentaionWizardMenuItemActionPerformed(evt);
            }
        });

        wizardMenu.add(segmentaionWizardMenuItem);

        registrationWizardMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        registrationWizardMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        registrationWizardMenuItem.setText("Registrierung...");
        registrationWizardMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrationWizardMenuItemActionPerformed(evt);
            }
        });

        wizardMenu.add(registrationWizardMenuItem);

        marchingCubeWizardMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        marchingCubeWizardMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        marchingCubeWizardMenuItem.setText("Marching Cube...");
        marchingCubeWizardMenuItem.setEnabled(false);
        marchingCubeWizardMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                marchingCubeWizardMenuItemActionPerformed(evt);
            }
        });

        wizardMenu.add(marchingCubeWizardMenuItem);

        activeContourwizardMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        activeContourwizardMenuItem.setText("Aktive Konturen...");
        activeContourwizardMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeContourwizardMenuItemActionPerformed(evt);
            }
        });

        wizardMenu.add(activeContourwizardMenuItem);

        menuBar.add(wizardMenu);

        helpMenu.setMnemonic('H');
        helpMenu.setText("Hilfe");
        helpMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        helpMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuActionPerformed(evt);
            }
        });

        aboutMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        aboutMenuItem.setMnemonic('I');
        aboutMenuItem.setText("Info...");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });

        helpMenu.add(aboutMenuItem);

        lnfMenu.setText("Look and Feel");
        lnfMenu.setFont(new java.awt.Font("Dialog", 0, 12));
        lnfMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lnfMenuActionPerformed(evt);
            }
        });

        helpMenu.add(lnfMenu);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new java.awt.Dimension(501, 376));
        setLocation((screenSize.width-501)/2,(screenSize.height-376)/2);
    }//GEN-END:initComponents

    private void lnfMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lnfMenuActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_lnfMenuActionPerformed

    private void activeContourwizardMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeContourwizardMenuItemActionPerformed
        addWizard(new ActiveContourWizard());
    }//GEN-LAST:event_activeContourwizardMenuItemActionPerformed

    private void marchingCubeWizardMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_marchingCubeWizardMenuItemActionPerformed
        addWizard(new MarchingCubeWizard());
    }//GEN-LAST:event_marchingCubeWizardMenuItemActionPerformed

    private void helpMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuActionPerformed
    }//GEN-LAST:event_helpMenuActionPerformed

    private void show3DMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_show3DMenuItemActionPerformed
        Viewer3D viewer3D = new Viewer3D("Test");
        viewer3D.setPreferredSize(new Dimension(640, 640));            
        viewer3D.pack();        
        addViewerDesktopFrame(viewer3D);
    }//GEN-LAST:event_show3DMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        saveCommand.execute();
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void registrationWizardMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrationWizardMenuItemActionPerformed
        addWizard(new RegistrationWizard());        
    }//GEN-LAST:event_registrationWizardMenuItemActionPerformed
    
    private void segmentaionWizardMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentaionWizardMenuItemActionPerformed
        addWizard(new SegmentationWizard());
    }//GEN-LAST:event_segmentaionWizardMenuItemActionPerformed
    
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        //AboutDialog about = new AboutDialog(this, true);
        //about.show();
        About about = new About(this, true);
        about.show();
     }//GEN-LAST:event_aboutMenuItemActionPerformed
                                                  
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        openCommand.execute();
    }//GEN-LAST:event_openMenuItemActionPerformed
        
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
     * Auswerten der Kommandozeilenparameter
     */
    private void command(String[] args) {
        if (args.length <= 0) {
            return;    
        }
        for (int i = 0; i < args.length; i++) {
            //System.out.println(args[i]);
            if (args[i].matches("*-version*")) {
                System.out.println(VERSION);
                return;    
            }
        }
        
        
        
        String name = args[0];
        if (!name.endsWith(".rid")) {
            return;    
        }
        
        ImageReaderFactory readerFactory = new RawImageReaderFactory();
        ImageReader imageReader = readerFactory.createImageReader(ImageDataFactory.getInstance(),
                                                                        new File(name));
                                                                        
        progressFrame = new ProgressFrame();                                                                
        progressFrame.setTitle("Öffnen");
        progressFrame.setSubTitle("Beim Laden von: " + name);                    
        this.addViewerDesktopFrame(progressFrame);                                        
        imageReader.addProgressListener(this);
        
        ImageReaderThread readerThread = new ImageReaderThread(imageReader, this);
        readerThread.setPriority(Thread.MIN_PRIORITY);
        readerThread.start(); 
    }
    
    public void progressChanged(ImageIOProgressEvent event) {
        if (event.isFinished()) {
            progressFrame.setVisible(false);
            this.removeViewerDesktopFrame(progressFrame);
            
            Image image = ((ImageReader)event.getSource()).getImage();
            String name = ((ImageReader)event.getSource()).getSource().getName();
            ImageViewer iv = new ImageViewer(name, image);
            
            int sizeX = image.getMaxX() - image.getMinX() + 1;
            int sizeY = image.getMaxY() - image.getMinY() + 1;
            iv.setPreferredSize(new Dimension(sizeX, sizeY));            
            iv.pack();
            this.addViewerDesktopFrame(iv);                    
        } else {
            progressFrame.setProgress(event.getProgress());
        }
    }
    

    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Viewer viewer = Viewer.getInstance();
        viewer.show();
        
        viewer.command(args);       
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
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem activeContourwizardMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel statePanel;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JToolBar toolBar;
    private javax.swing.JMenu lnfMenu;
    private javax.swing.JMenu wizardMenu;
    private javax.swing.JMenuItem registrationWizardMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem marchingCubeWizardMenuItem;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem segmentaionWizardMenuItem;
    private javax.swing.JPanel rightSplitPanel;
    // End of variables declaration//GEN-END:variables
    
    
}
