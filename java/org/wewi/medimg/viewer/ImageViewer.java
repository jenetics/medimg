/**
 * ImageViewer.java
 *
 * Created on March 28, 2002, 11:31 AM
 */

package org.wewi.medimg.viewer;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.InternalFrameEvent;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImagePanel;
import org.wewi.medimg.image.VoxelSelectorListener;


/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public class ImageViewer extends ViewerDesktopFrame implements ImageContainer {
        
    private Vector listener;

    protected Image image;
    protected int slice;
    protected ImagePanel imagePanel;
    protected String frameTitle;
    
    private JPopupMenu popup;
    
    //Navigation-Panel-Kommandos
    private Command prevCommand;
    private Command nextCommand;
    private Command firstCommand;
    private Command lastCommand;
    private Command prevPrevCommand;
    private Command nextNextCommand;
    
    //Men�-Kommandos
    private Command saveCommand;
    
    private final long id = System.currentTimeMillis();
    
    public ImageViewer(String title, Image image) {
        super(title, true, true, true, true);
        this.image = image;
        
        frameTitle = title;
        
        listener = new Vector();   
        slice = image.getMinZ();
        initFrame();
    }
       
    
    private void initFrame() {
        imagePanel = new ImagePanel(image);
        getContentPane().add(imagePanel);

        imagePanel.setSlice(slice);
        imagePanel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        
        //Anmelden der Listener
        addListeners(this);
        
        //Initialisieren der Navigationsleiste
        prevCommand = new PrevCommand(this);
        nextCommand = new NextCommand(this);
        firstCommand = new FirstCommand(this);
        lastCommand = new LastCommand(this);
        prevPrevCommand = new PrevPrevCommand(this, 10);
        nextNextCommand = new NextNextCommand(this, 10);
        
        saveCommand = new SaveCommand(Viewer.getInstance(), image);
         
        setSlice(0);
        
        //Aufbau des Popupnem�s///////////////////////////////////////
        popup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Bearbeiten der ColorConversion");
        menuItem.addActionListener(new ActionListener() {
                                       public void actionPerformed(ActionEvent evt) {
                                           colorConversionMenuItemActionPerformed(evt);
                                       }
                               });
        popup.add(menuItem);
        
        //Add listener to components that can bring up popup menus.
        MouseListener popupListener = new PopupListener();
        imagePanel.addMouseListener(popupListener);  
        ///////////////////////////////////////////////////////////////      
    }
    
    private class PopupListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            maybeShowPopup(e);
        }

        public void mouseReleased(MouseEvent e) {
            maybeShowPopup(e);
        }

        private void maybeShowPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),
                           e.getX(), e.getY());
            }
        }
    }
    
    private void colorConversionMenuItemActionPerformed(ActionEvent evt) {
        
    }    
    
    private void setCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        np.setNextCommand(nextCommand);
        np.setPrevCommand(prevCommand);
        np.setFirstCommand(firstCommand);
        np.setLastCommand(lastCommand);
        np.setPrevPrevCommand(prevPrevCommand);
        np.setNextNextCommand(nextNextCommand); 
        
        Viewer v = Viewer.getInstance();
        v.setSaveCommand(saveCommand);       
    }
    
    private void setNullCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        np.setNextCommand(new NullCommand());
        np.setPrevCommand(new NullCommand());
        np.setFirstCommand(new NullCommand());
        np.setLastCommand(new NullCommand());
        np.setPrevPrevCommand(new NullCommand());
        np.setNextNextCommand(new NullCommand()); 
        
        Viewer v = Viewer.getInstance();
        v.setSaveCommand(saveCommand);        
    }    
    
    public synchronized void addImageViewerListener(ImageViewerListener l) {
        listener.add(l);
    }
    
    public synchronized void removeImageViewerListener(ImageViewerListener l) {
        listener.remove(l);
    }
    
    /**
     * Diese Listener werden zum ImagePanel durchgereicht .
     */
    public synchronized void addVoxelSelectorListener(VoxelSelectorListener listener) {
        imagePanel.addVoxelSelectorListener(listener);
    }
    
    public synchronized void removeVoxelSelectorListener(VoxelSelectorListener listener) {
        imagePanel.removeVoxelSelectorListener(listener);    
    }    
    
    protected void notifyImageViewerListener(ImageViewerEvent event) {
        Vector l;
        synchronized (listener) {
            l = (Vector)listener.clone();
        }
        ImageViewerListener observer;
        for (Iterator it = l.iterator(); it.hasNext();) {
            observer = (ImageViewerListener)it.next();
            observer.update(event);
        }
    }
    
    public void setColorConversion(ColorConversion cc) {
        imagePanel.setColorConversion(cc);
    }
    
    public ColorConversion getColorConversion() {
        return imagePanel.getColorConversion();
    }
    
    public synchronized void setSlice(int s) {
        slice = s;
        imagePanel.setSlice(slice);
        
        setTitle("(" + slice + "-" + image.getMaxZ() + ") " + frameTitle);
        
        notifyImageViewerListener(new ImageViewerEvent(this, s, false));
    }
    
    public int getSlice() {
        return slice;
    }
    
    public void repaintImage() {
        imagePanel.repaintImage();    
    }
    
    public Image getImage() {
        return image;
    }
    
    public synchronized void setImageCanvas(ImagePanel.ImageCanvas canvas) {
        imagePanel.setImageCanvas(canvas);    
    }
    
   
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void focusGained(FocusEvent focusEvent) {
        setCommands();
    }
    
    public void internalFrameActivated(InternalFrameEvent internalFrameEvent) {
        setCommands();
    }
    
    public void internalFrameClosed(InternalFrameEvent internalFrameEvent) {
        imagePanel = null;
        image = null;
        setNullCommands();
        
        notifyImageViewerListener(new ImageViewerEvent(this, getSlice(), true));
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
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;    
        }    
        if (!(o instanceof ImageViewer)) {
            return false;    
        }
        
        ImageViewer iv = (ImageViewer)o;
        
        return id == iv.id;
    }

}
















