/*
 * ViewerDesktopFrame.java
 *
 * Created on 6. April 2002, 20:40
 */

package org.wewi.medimg.viewer;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameEvent;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ViewerDesktopFrame extends JInternalFrame
                                         implements MouseListener,
                                                    MouseMotionListener,
                                                    KeyListener,
                                                    FocusListener,
                                                    ComponentListener,
                                                    InternalFrameListener {
    
    public ViewerDesktopFrame() {
        super();
    }
    
    public ViewerDesktopFrame(String name) {
        super(name);
    }
    
    public ViewerDesktopFrame(String name, boolean resizeable) {
        super(name, resizeable);
    }
    
    public ViewerDesktopFrame(String name, boolean resizeable, boolean closable) {
        super(name, resizeable, closable);
    }
    
    public ViewerDesktopFrame(String name, boolean resizeable, boolean closable, boolean maximizable) {
        super(name, resizeable, closable, maximizable);    
    }
    
    public ViewerDesktopFrame(String name, boolean resizeable, boolean closable, boolean maximizable, boolean iconable) {
        super(name, resizeable, closable, maximizable, iconable);
    }
    
    protected void addListeners() {
        //Anmelden der Listener
        getContentPane().addFocusListener(this);
        getContentPane().addMouseListener(this);
        getContentPane().addMouseMotionListener(this);
        getContentPane().addKeyListener(this);
        addInternalFrameListener(this);
        getContentPane().addComponentListener(this);        
    }
    
    protected void removeListeners() {
        //Abmelden der Listener
        getContentPane().removeFocusListener(this);
        getContentPane().removeMouseListener(this);
        getContentPane().removeMouseMotionListener(this);
        getContentPane().removeKeyListener(this);
        removeInternalFrameListener(this);
        getContentPane().removeComponentListener(this);
    }
    
    /*
     * Methoden der implementierten Listener
     */
    public void componentHidden(ComponentEvent componentEvent) {
    }
    
    public void componentMoved(ComponentEvent componentEvent) {
    }
    
    public void componentResized(ComponentEvent componentEvent) {
    }
    
    public void componentShown(ComponentEvent componentEvent) {
    }
    
    public void keyPressed(KeyEvent keyEvent) {
    }
    
    public void keyReleased(KeyEvent keyEvent) {
    }
    
    public void keyTyped(KeyEvent keyEvent) {
    }
    
    public void mouseClicked(MouseEvent mouseEvent) {
    }
    
    public void mouseDragged(MouseEvent mouseEvent) {
    }
    
    public void mouseEntered(MouseEvent mouseEvent) {
    }
    
    public void mouseExited(MouseEvent mouseEvent) {
    }
    
    public void mouseMoved(MouseEvent mouseEvent) {
    }
    
    public void mousePressed(MouseEvent mouseEvent) {
    }
    
    public void mouseReleased(MouseEvent mouseEvent) {
    }
    
    public void focusGained(FocusEvent focusEvent) {
    }
    
    public void focusLost(FocusEvent focusEvent) {
    }
    
    public void internalFrameActivated(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameClosed(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameClosing(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameDeactivated(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameDeiconified(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameIconified(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameOpened(InternalFrameEvent internalFrameEvent) {
    }
    ////////////////////////////////////////////////////////////////////////////
    
}
