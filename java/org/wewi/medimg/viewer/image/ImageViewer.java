/**
 * ImageViewer.java
 *
 * Created on March 28, 2002, 11:31 AM
 */

package org.wewi.medimg.viewer.image;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.InternalFrameEvent;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.viewer.Command;
import org.wewi.medimg.viewer.ImageContainer;
import org.wewi.medimg.viewer.ImageViewerEvent;
import org.wewi.medimg.viewer.ImageViewerListener;
import org.wewi.medimg.viewer.NavigationPanel;
import org.wewi.medimg.viewer.NullCommand;
import org.wewi.medimg.viewer.PrintCommand;
import org.wewi.medimg.viewer.SaveCommand;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.ViewerDesktopFrame;
import org.wewi.medimg.viewer.tools.ImagePropertiesDialog;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageViewer extends ViewerDesktopFrame implements ImageContainer,
                                                                   Printable {
        
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
    
    //Menü-Kommandos
    private Command saveCommand;
    private Command printCommand;
    
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
        printCommand = new PrintCommand(this);
         
        setSlice(slice);
        
        ///////////////////////////////////////////////////////////////
        //Building the pop up menus////////////////////////////////////
        ///////////////////////////////////////////////////////////////
        //Popup the ColorConversion-menu
        popup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Colorconversion");
        menuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        menuItem.addActionListener(new ActionListener() {
                                       public void actionPerformed(ActionEvent evt) {
                                           colorConversionMenuItemActionPerformed(evt);
                                       }
                               });
        popup.add(menuItem);
        
        //Popup the save-dialog
        JMenuItem saveImageMenuItem = new JMenuItem("Save");
        saveImageMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        saveImageMenuItem.addActionListener(new ActionListener() {
                                           public void actionPerformed(ActionEvent evt) {
                                               saveCommand.execute();
                                           }
                               });
        popup.add(saveImageMenuItem);  
        
        //Popup the print-dialog
        JMenuItem printImageMenuItem = new JMenuItem("Print");
        printImageMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        printImageMenuItem.addActionListener(new ActionListener() {
                                           public void actionPerformed(ActionEvent evt) {
                                               printCommand.execute();
                                           }
                               });
        popup.add(printImageMenuItem); 
        
        //Popup the ImageProperty-dialog
        JMenuItem imagePropertiesImageMenuItem = new JMenuItem("Imageproperties");
        imagePropertiesImageMenuItem.setFont(new java.awt.Font("Dialog", 0, 12));
        imagePropertiesImageMenuItem.addActionListener(new ActionListener() {
                           public void actionPerformed(ActionEvent evt) {
                               new ImagePropertiesDialog(image.getHeader().getImageProperties(), 
                                                         Viewer.getInstance(), true).show();
                           }
                               });
        popup.add(imagePropertiesImageMenuItem);        
        
        
             
        imagePanel.addMouseListener(new MouseAdapter() {
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
        });  
        ///////////////////////////////////////////////////////////////      
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
    
    public synchronized void setImageCanvas(ImageCanvas canvas) {
        imagePanel.setImageCanvas(canvas);    
    }
    
    public int print(Graphics g, PageFormat pf, int pageIndex) {

        if (pageIndex!=0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D)g;
        double x = pf.getImageableX();
        double y = pf.getImageableY();
        double w = pf.getImageableWidth();
        double h = pf.getImageableHeight();
        imagePanel.printAll(g);
        
        return PAGE_EXISTS;

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
















