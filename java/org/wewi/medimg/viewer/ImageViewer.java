/*
 * ImageViewer.java
 *
 * Created on March 28, 2002, 11:31 AM
 */

package org.wewi.medimg.viewer;

import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.InternalFrameEvent;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImagePanel;
import org.wewi.medimg.image.VoxelSelectorListener;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageViewer extends ViewerDesktopFrame implements ImageContainer {
    private Image image;
    private int slice;
    private ImagePanel imagePanel;
    private Vector observers;
    
    private String frameTitle;
    
    private ColorConversion cc = null;
    
    private Command prevCommand;
    private Command nextCommand;
    private Command firstCommand;
    private Command lastCommand;
    private Command prevPrevCommand;
    private Command nextNextCommand;
    
    //Menü-Kommandos
    private Command saveCommand;
    
    public ImageViewer(String title, Image image) {
        super(title, true, true, true, true);
        this.image = image;
        
        frameTitle = title;
        
        observers = new Vector();   
        slice = image.getMinZ();
        initFrame();
    }
    
    public ImageViewer(String title, Image image, ColorConversion cc) {
        super(title, true, true, true, true);
        this.cc = cc;
        this.image = image;//new TransformableImage(image);
        observers = new Vector();   
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
    }
    
    public synchronized void addImageViewerListener(ImageViewerListener o) {
        observers.add(o);
    }
    
    public synchronized void removeImageViewerListener(ImageViewerListener o) {
        observers.remove(o);
    }
    
    protected void viewerEventOccurred(ImageViewerEvent event) {
        Vector o = (Vector)observers.clone();
        ImageViewerListener observer;
        for (Iterator it = o.iterator(); it.hasNext();) {
            observer = (ImageViewerListener)it.next();
            observer.update(event);
        }
    }
    
    public void setSlice(int s) {
        if (s != slice) {
            viewerEventOccurred(new ImageViewerEvent(this));    
        }
        
        slice = s;
        imagePanel.setSlice(slice);
        
        setTitle("(" + slice + "-" + image.getMaxZ() + ") " + frameTitle);
    }
    
    public int getSlice() {
        return slice;
    }
    
    public void repaintImage() {
        setSlice(getSlice());    
    }
    
    public Image getImage() {
        return image;
    }
    
    public void setImageCanvas(ImagePanel.ImageCanvas canvas) {
        imagePanel.setImageCanvas(canvas);    
    }
    
    public void addVoxelSelectorListener(VoxelSelectorListener listener) {
        imagePanel.addVoxelSelectorListener(listener);
    }
    
    public void removeVoxelSelectorListener(VoxelSelectorListener listener) {
        imagePanel.removeVoxelSelectorListener(listener);    
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
    
}
