/*
 * TwinImageViewer.java
 *
 * Created on 6. April 2002, 20:32
 */

package org.wewi.medimg.viewer.wizard.seg;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.InternalFrameEvent;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.viewer.Command;
import org.wewi.medimg.viewer.ImageContainer;
import org.wewi.medimg.viewer.NavigationPanel;
import org.wewi.medimg.viewer.NullCommand;
import org.wewi.medimg.viewer.SaveCommand;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.ViewerDesktopFrame;
import org.wewi.medimg.viewer.image.ImagePanel;
import org.wewi.medimg.viewer.wizard.WizardEvent;
import org.wewi.medimg.viewer.wizard.WizardListener;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class TwinImageViewer extends ViewerDesktopFrame implements WizardListener,
                                                                         ImageContainer {
    private Image image1;
    private Image image2;
    private JPanel rootPanel;
    private ImagePanel imagePanel1;
    private ImagePanel imagePanel2;
    private int slice = 0;
    
    private String frameTitle;
    
    private JPopupMenu popUpMenu;
    private JMenuItem saveImageMenuItem;
    
    //Navigation-Kommandos
    private Command firstCommand;
    private Command lastCommand;
    private Command nextCommand;
    private Command prevCommand;
    private Command nextNextCommand;
    private Command prevPrevCommand;
    
    //Menü-Kommandos
    private Command saveCommand;
    
    
    /** Creates a new instance of TwinImageViewer */
    public TwinImageViewer(String frameTitle, Image image1, Image image2) {
        super(frameTitle, true, true, true, true);
        this.image1 = image1;
        this.image2 = image2;
        
        this.frameTitle = frameTitle;
        
        init();
        setSlice(0);
    }
    
    private void init() {
        rootPanel = new JPanel();
        getContentPane().setLayout(new GridLayout(1, 2));        
        
        imagePanel1 = new ImagePanel(image1);
        imagePanel2 = new ImagePanel(image2);
        

        imagePanel2.addMouseListener(new MouseAdapter() {
                                        public void mouseClicked(MouseEvent event) {
                                            imagePanel2MouseClicked(event);
                                        }
                                     });
        
        getContentPane().add(imagePanel1);
        getContentPane().add(imagePanel2);
        int sizeX = image1.getMaxX() - image1.getMinX() + 1;
        int sizeY = image1.getMaxY() - image1.getMinY() + 1;
        setPreferredSize(new Dimension(2*sizeX, sizeY));
        
        popUpMenu = new JPopupMenu();
        saveImageMenuItem = new JMenuItem("Speichern als...");
        saveImageMenuItem.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent event) {
                                                    saveImageMenuItemActionPerformed(event);
                                                }
                                            });
        popUpMenu.add(saveImageMenuItem);
        
        addListeners(this);
        
        firstCommand = new FirstCommand(this);
        lastCommand = new LastCommand(this);
        nextCommand = new NextCommad(this);
        prevCommand = new PrevCommand(this);
        nextNextCommand = new NextNextCommand(this, 10);
        prevPrevCommand = new PrevPrevCommand(this, 10);
        saveCommand = new SaveCommand(Viewer.getInstance(), image2);
        setCommands();
    }
    
    private void imagePanel2MouseClicked(MouseEvent event) {
    }
    
    private void saveImageMenuItemActionPerformed(ActionEvent event) {
    }
    
    private void setCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        Viewer v = Viewer.getInstance();
        
        np.setFirstCommand(firstCommand);
        np.setLastCommand(lastCommand);
        np.setNextCommand(nextCommand);
        np.setPrevCommand(prevCommand);
        np.setNextNextCommand(nextNextCommand);
        np.setPrevPrevCommand(prevPrevCommand);
        
        v.setSaveCommand(saveCommand);
    }
    
    private void setNullCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        Viewer v = Viewer.getInstance();
        
        np.setFirstCommand(new NullCommand());
        np.setLastCommand(new NullCommand());
        np.setNextCommand(new NullCommand());
        np.setPrevCommand(new NullCommand());
        np.setNextNextCommand(new NullCommand());
        np.setPrevPrevCommand(new NullCommand());   
        
        v.setSaveCommand(new NullCommand());     
    }
    
    public void focusGained(FocusEvent focusEvent) {
        setCommands();
    }
    
    public void internalFrameActivated(InternalFrameEvent internalFrameEvent) {
        setCommands();
    }
    
    public void internalFrameClosed(InternalFrameEvent internalFrameEvent) {
        imagePanel1 = null;
        imagePanel2 = null;
        image1 = null;
        image2 = null;        
        setNullCommands();
    }     
    
    public Image getImage1() {
        return image1;
    }
    
    public Image getImage2() {
        return image2;
    }
    
    public Image getImage() {
        return image2;
    }
    
    public void setSlice(int s) {
        slice = s;
        imagePanel1.setSlice(slice);
        imagePanel2.setSlice(slice);
        
        setTitle("(" + slice + "-" + image1.getMaxZ() + ") " + frameTitle);
    }
    
    public int getSlice() {
        return slice;
    }
    
    public void redrawImages() {
        setSlice(getSlice());    
    }
    
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        
        switch (keyCode) {
            case KeyEvent.VK_PAGE_UP:
                nextNextCommand.execute();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                prevPrevCommand.execute();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                nextCommand.execute();
                break;               
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_DOWN:
                prevCommand.execute();
                break;
            case KeyEvent.VK_END:
                lastCommand.execute();
                break;
            case KeyEvent.VK_HOME:
                firstCommand.execute();
                break;
            default:
                //Nothing
        }
    }
    
    
    public void wizardEventOccurred(WizardEvent wizardEvent) {
        imagePanel1.repaint();
        imagePanel2.repaint();
    }
  
}
