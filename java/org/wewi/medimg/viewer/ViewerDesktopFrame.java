/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * ViewerDesktopFrame.java
 *
 * Created on 6. April 2002, 20:40
 */

package org.wewi.medimg.viewer;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

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
    
    protected void addListeners(ViewerDesktopFrame frame) {
        //Anmelden der Listener
        getContentPane().addFocusListener(frame);
        getContentPane().addMouseListener(frame);
        getContentPane().addMouseMotionListener(frame);
        getContentPane().addKeyListener(frame);
        addInternalFrameListener(frame);
        getContentPane().addComponentListener(frame);        
    }
    
    protected void removeListeners(ViewerDesktopFrame frame) {
        //Abmelden der Listener
        getContentPane().removeFocusListener(frame);
        getContentPane().removeMouseListener(frame);
        getContentPane().removeMouseMotionListener(frame);
        getContentPane().removeKeyListener(frame);
        removeInternalFrameListener(frame);
        getContentPane().removeComponentListener(frame);
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
