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
 * ButtonPanel.java
 *
 * Created on March 26, 2002, 1:37 PM
 */

package org.wewi.medimg.viewer;

import org.wewi.medimg.util.Singleton;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class NavigationPanel extends javax.swing.JPanel implements Singleton {
    private static NavigationPanel singleton = null;
    
    private Command firstCommand;
    private Command prevPrevCommand;
    private Command prevCommand;
    private Command nextCommand;
    private Command nextNextCommand;
    private Command lastCommand;
    private Command zoomInCommand;
    private Command zoomOutCommand;
    private Command rotateLeftCommand;
    private Command rotateRightCommand;
    
    /** Creates new form ButtonPanel */
    private NavigationPanel() {
        initComponents();
        firstCommand = new NullCommand();
        prevPrevCommand = new NullCommand();
        prevCommand = new NullCommand();
        nextCommand = new NullCommand();
        nextNextCommand = new NullCommand();
        lastCommand = new NullCommand();
        zoomInCommand = new NullCommand();
        zoomOutCommand = new NullCommand(); 
        rotateLeftCommand = new NullCommand();
        rotateRightCommand = new NullCommand();
    }
    
    static NavigationPanel getInstance() {
        if (singleton == null) {
            singleton = new NavigationPanel();
        }
        return singleton;
    }
    
    public void setFirstCommand(Command c) {
        firstCommand = c;
    }
    
    public Command getFirstCommand() {
        return firstCommand;
    }
    
    public void setPrevPrevCommand(Command c) {
        prevPrevCommand = c;
    }
    
    public Command getPrevPrevCommand() {
        return prevPrevCommand;
    }
    
    public void setPrevCommand(Command c) {
        prevCommand = c;
    }
    
    public Command getPrevCommand() {
        return prevCommand;
    }
    
    public void setNextCommand(Command c) {
        nextCommand = c;
    }
    
    public Command getNextCommand() {
        return nextCommand;
    }
    
    public void setNextNextCommand(Command c) {
        nextNextCommand = c;
    }
    
    public Command getNextNextCommand() {
        return nextNextCommand;
    }
    
    public void setLastCommand(Command c) {
        lastCommand = c;
    }
    
    public Command getLastCommand() {
        return lastCommand;
    }
    
    public void setZoomInCommand(Command c) {
        zoomInCommand = c;
    }
    
    public Command getZoomInCommand() {
        return zoomInCommand;
    }
    
    public void setZoomOutCommand(Command c) {
        zoomOutCommand = c;
    }
    
    public Command getZoomOutCommand() {
        return zoomOutCommand;
    }
    
    public void setRotateLeftCommand(Command c) {
        rotateLeftCommand = c;
    }
    
    public Command getRotateLeftCommand() {
        return rotateLeftCommand;
    }
    
    public void setRotateRightCommand(Command c) {
        rotateRightCommand =c;
    }
    
    public Command getRotateRightCommand() {
        return rotateRightCommand;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        firstButton = new javax.swing.JButton();
        prevPrevButton = new javax.swing.JButton();
        prevButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        nextNextButton = new javax.swing.JButton();
        lastButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        zoomInButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        rotateLeftButton = new javax.swing.JButton();
        rotateRightButton = new javax.swing.JButton();
        
        setLayout(new java.awt.GridLayout(1, 11));
        
        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(1, 1, 1, 1)));
        setMinimumSize(new java.awt.Dimension(56, 34));
        firstButton.setFont(new java.awt.Font("Dialog", 0, 12));
        firstButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/first.gif")));
        firstButton.setToolTipText("erste Schicht");
        firstButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        firstButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstButtonActionPerformed(evt);
            }
        });
        
        add(firstButton);
        
        prevPrevButton.setFont(new java.awt.Font("Dialog", 0, 12));
        prevPrevButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/prev10.gif")));
        prevPrevButton.setToolTipText("schnell zur\u00fcck");
        prevPrevButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        prevPrevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevPrevButtonActionPerformed(evt);
            }
        });
        
        add(prevPrevButton);
        
        prevButton.setFont(new java.awt.Font("Dialog", 0, 12));
        prevButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/prev.gif")));
        prevButton.setToolTipText("vorherige Schicht");
        prevButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });
        
        add(prevButton);
        
        nextButton.setFont(new java.awt.Font("Dialog", 0, 12));
        nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/next.gif")));
        nextButton.setToolTipText("n\u00e4chste Schicht");
        nextButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        
        add(nextButton);
        
        nextNextButton.setFont(new java.awt.Font("Dialog", 0, 12));
        nextNextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/next10.gif")));
        nextNextButton.setToolTipText("schnell vorw\u00e4rts");
        nextNextButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        nextNextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextNextButtonActionPerformed(evt);
            }
        });
        
        add(nextNextButton);
        
        lastButton.setFont(new java.awt.Font("Dialog", 0, 12));
        lastButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/last.gif")));
        lastButton.setToolTipText("letzte Schicht");
        lastButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        lastButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastButtonActionPerformed(evt);
            }
        });
        
        add(lastButton);
        
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
        add(jLabel1);
        
        zoomInButton.setFont(new java.awt.Font("Dialog", 0, 12));
        zoomInButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/zoom_in.gif")));
        zoomInButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });
        
        add(zoomInButton);
        
        zoomOutButton.setFont(new java.awt.Font("Dialog", 0, 12));
        zoomOutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/zoom_out.gif")));
        zoomOutButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });
        
        add(zoomOutButton);
        
        rotateLeftButton.setFont(new java.awt.Font("Dialog", 0, 12));
        rotateLeftButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/rotate_left.gif")));
        rotateLeftButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        rotateLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateLeftButtonActionPerformed(evt);
            }
        });
        
        add(rotateLeftButton);
        
        rotateRightButton.setFont(new java.awt.Font("Dialog", 0, 12));
        rotateRightButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/rotate_right.gif")));
        rotateRightButton.setDoubleBuffered(true);
        rotateRightButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        rotateRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateRightButtonActionPerformed(evt);
            }
        });
        
        add(rotateRightButton);
        
    }//GEN-END:initComponents

    private void rotateRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateRightButtonActionPerformed
        // Add your handling code here:
        rotateRightCommand.execute();
    }//GEN-LAST:event_rotateRightButtonActionPerformed

    private void rotateLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateLeftButtonActionPerformed
        // Add your handling code here:
        rotateLeftCommand.execute();
    }//GEN-LAST:event_rotateLeftButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
        // Add your handling code here:
        zoomOutCommand.execute();
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
        // Add your handling code here:
        zoomInCommand.execute();
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        // Add your handling code here:
        prevCommand.execute();
    }//GEN-LAST:event_prevButtonActionPerformed

    private void nextNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextNextButtonActionPerformed
        // Add your handling code here:
        nextNextCommand.execute();
    }//GEN-LAST:event_nextNextButtonActionPerformed

    private void lastButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastButtonActionPerformed
        // Add your handling code here:
        lastCommand.execute();
    }//GEN-LAST:event_lastButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // Add your handling code here:
        nextCommand.execute();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void prevPrevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevPrevButtonActionPerformed
        // Add your handling code here:+
        prevPrevCommand.execute();
    }//GEN-LAST:event_prevPrevButtonActionPerformed

    private void firstButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstButtonActionPerformed
        // Add your handling code here:
        firstCommand.execute();
    }//GEN-LAST:event_firstButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton prevButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton prevPrevButton;
    private javax.swing.JButton lastButton;
    private javax.swing.JButton nextNextButton;
    private javax.swing.JButton firstButton;
    private javax.swing.JButton rotateRightButton;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton rotateLeftButton;
    private javax.swing.JButton zoomOutButton;
    private javax.swing.JButton nextButton;
    // End of variables declaration//GEN-END:variables
    
}
