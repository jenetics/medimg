/*
 * ImageViewer.java
 *
 * Created on March 28, 2002, 11:31 AM
 */

package org.wewi.medimg.viewer;

import java.util.Vector;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;

import java.awt.Cursor;
import java.awt.event.FocusEvent;


import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImagePanel;
import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.GreyRGBConversion;
import org.wewi.medimg.image.TransformableImage;
import org.wewi.medimg.image.geom.Transform;


////////////////////////////////////////////////////////////////////////////////
import org.wewi.medimg.image.*;
import org.wewi.medimg.image.io.*;

import java.io.File;
////////////////////////////////////////////////////////////////////////////////

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageViewer extends ViewerDesktopFrame {
    private Image image;
    private int slice;
    private ImagePanel imagePanel;
    private Vector observers;
    
    private Command prevCommand;
    private Command nextCommand;
    private Command firstCommand;
    private Command lastCommand;
    private Command prevPrevCommand;
    private Command nextNextCommand;
    
    public ImageViewer(String title, Image image) {
        super(title, true, true, true, true);
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
        addListeners();
        
        //Initialisieren der Navigationsleiste
        prevCommand = new PrevCommand(this);
        nextCommand = new NextCommand(this);
        firstCommand = new FirstCommand(this);
        lastCommand = new LastCommand(this);
        prevPrevCommand = new PrevPrevCommand(this, 10);
        nextNextCommand = new NextNextCommand(this, 10);
         
    }
    
    public synchronized void addImageViewerObserver(ImageViewerObserver o) {
        observers.add(o);
    }
    
    public synchronized void removeImageViewerObserver(ImageViewerObserver o) {
        observers.remove(o);
    }
    
    protected void viewerEventOccurred(ImageViewerEvent event) {
        Vector o = (Vector)observers.clone();
        ImageViewerObserver observer;
        for (Iterator it = o.iterator(); it.hasNext();) {
            observer = (ImageViewerObserver)it.next();
            observer.update(event);
        }
    }
    
    public void setSlice(int s) {
        slice = s;
        imagePanel.setSlice(slice);
    }
    
    public int getSlice() {
        return slice;
    }
    
    public void setImageTransform(Transform trans) {
        //image.setTransform(trans);
        ColorConversion cc = imagePanel.getColorConversion();
        imagePanel = new ImagePanel(image);
        imagePanel.setColorConversion(cc);
    }
    
    public Image getImage() {
        return image;
    }
    
    public void setColorConversion(ColorConversion cc) {
        imagePanel.setColorConversion(cc);
    }
    
    public ColorConversion getColorConversion() {
        return imagePanel.getColorConversion();
    }
    
    private void setCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        np.setNextCommand(nextCommand);
        np.setPrevCommand(prevCommand);
        np.setFirstCommand(firstCommand);
        np.setLastCommand(lastCommand);
        np.setPrevPrevCommand(prevPrevCommand);
        np.setNextNextCommand(nextNextCommand);        
    }
    
    private void setNullCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        np.setNextCommand(new NullCommand());
        np.setPrevCommand(new NullCommand());
        np.setFirstCommand(new NullCommand());
        np.setLastCommand(new NullCommand());
        np.setPrevPrevCommand(new NullCommand());
        np.setNextNextCommand(new NullCommand());         
    }
   
    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////  
    public void focusGained(FocusEvent focusEvent) {
        setCommands();
    }
    
    public void internalFrameActivated(InternalFrameEvent internalFrameEvent) {
        setCommands();
    }
    
    public void internalFrameClosing(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameClosed(InternalFrameEvent internalFrameEvent) {
        imagePanel = null;
        image = null;
        setNullCommands();
    }    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) {
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                           new File("C:/Temp/head.in.001"));
        try {
            reader.read();
        } catch (Exception e) {
            System.out.println("Viewer: " + e);
        }
        
        Image image = reader.getImage();
        System.out.println(image);
        ImageViewer iv = new ImageViewer("ImageViewer", image);  
        //iv.repaint();
        iv.setSlice(90);
        iv.show();
        
        
    }
    
}
