/*
 * TwinImageViewer.java
 *
 * Created on 6. April 2002, 20:32
 */

package org.wewi.medimg.seg.wizard;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImagePanel;
import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.GreyRGBConversion;

import org.wewi.medimg.viewer.ViewerDesktopFrame;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.NavigationPanel;
import org.wewi.medimg.viewer.Command;
import org.wewi.medimg.viewer.NullCommand;
import org.wewi.medimg.viewer.ImageContainer;

import org.wewi.medimg.viewer.wizard.WizardListener;
import org.wewi.medimg.viewer.wizard.WizardEvent;

import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.event.InternalFrameEvent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

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
    private ColorConversion conversion1;
    private ColorConversion conversion2;
    private int slice = 0;
    
    private JPopupMenu popUpMenu;
    private JMenuItem saveImageMenuItem;
    
    //Navigation-Commands
    private Command firstCommand;
    private Command lastCommand;
    private Command nextCommand;
    private Command prevCommand;
    private Command nextNextCommand;
    private Command prevPrevCommand;
    
    
    /** Creates a new instance of TwinImageViewer */
    public TwinImageViewer(String frameName, Image image1, Image image2) {
        super(frameName, true, false, true, true);
        this.image1 = image1;
        this.image2 = image2;
        
        init();
        setSlice(0);
    }
    
    private void init() {
        rootPanel = new JPanel();
        getContentPane().setLayout(new GridLayout(1, 2));        
        
        imagePanel1 = new ImagePanel(image1);
        imagePanel2 = new ImagePanel(image2);
        conversion1 = imagePanel1.getColorConversion();
        conversion2 = imagePanel2.getColorConversion();
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
        
        addListeners();
        
        firstCommand = new FirstCommand(this);
        lastCommand = new LastCommand(this);
        nextCommand = new NextCommad(this);
        prevCommand = new PrevCommand(this);
        nextNextCommand = new NextNextCommand(this, 10);
        prevPrevCommand = new PrevPrevCommand(this, 10);
        setCommands();
    }
    
    private void imagePanel2MouseClicked(MouseEvent event) {
    }
    
    private void saveImageMenuItemActionPerformed(ActionEvent event) {
    }
    
    private void setCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        np.setFirstCommand(firstCommand);
        np.setLastCommand(lastCommand);
        np.setNextCommand(nextCommand);
        np.setPrevCommand(prevCommand);
        np.setNextNextCommand(nextNextCommand);
        np.setPrevPrevCommand(prevPrevCommand);
    }
    
    private void setNullCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        np.setFirstCommand(new NullCommand());
        np.setLastCommand(new NullCommand());
        np.setNextCommand(new NullCommand());
        np.setPrevCommand(new NullCommand());
        np.setNextNextCommand(new NullCommand());
        np.setPrevPrevCommand(new NullCommand());        
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
    
    public void setColorConversion1(ColorConversion cc) {
        conversion1 = cc;
        imagePanel1.setColorConversion(conversion1);
    }
    
    public ColorConversion getColorConversion1() {
        return conversion1;
    }
    
    public void setColorConversion2(ColorConversion cc) {
        conversion2 = cc;
        imagePanel2.setColorConversion(conversion2);
    }
    
    public ColorConversion getColorConversion2() {
        return conversion2;
    }
    
    public void setSlice(int s) {
        slice = s;
        imagePanel1.setSlice(slice);
        imagePanel2.setSlice(slice);
    }
    
    public int getSlice() {
        return slice;
    }
    
    public void focusGained(FocusEvent focusEvent) {
        setCommands();
    }
    
    
    public void internalFrameActivated(InternalFrameEvent internalFrameEvent) {
        setCommands();
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
    
    public void internalFrameClosed(InternalFrameEvent internalFrameEvent) {
        imagePanel1 = null;
        imagePanel2 = null;
        image1 = null;
        image2 = null;
        setNullCommands();
    }    
        
}
