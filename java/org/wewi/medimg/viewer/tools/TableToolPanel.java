/**
 * TableToolPanel.java
 *
 * Created on 22. Februar 2003, 13:04
 */

package org.wewi.medimg.viewer.tools;

import org.wewi.medimg.viewer.Command;
import org.wewi.medimg.viewer.NullCommand;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class TableToolPanel extends javax.swing.JPanel {
    private Command deleteCommand = new NullCommand();
    private Command insertAfterCommand = new NullCommand();
    private Command insertBeforeCommand = new NullCommand();
    private Command moveDownCommand = new NullCommand();
    private Command moveUpCommand = new NullCommand();
    
    
    /** Creates new form TableToolPanel */
    public TableToolPanel() {
        initComponents();
    }
    
    public void setDeleteCommand(Command command) {
        deleteCommand = command;
    }
    
    public Command getDeleteCommand() {
        return deleteCommand;
    }
    
    public void setInsertAfterCommand(Command command) {
        insertAfterCommand = command;
    }
    
    public Command getInsertAfterCommand() {
        return insertAfterCommand;
    }
    
    public void setInsertBeforeCommand(Command command) {
        insertBeforeCommand = command;
    }
    
    public Command getInsertBeforeCommand() {
        return insertBeforeCommand;
    }
    
    public void setMoveDownCommand(Command command) {
        moveDownCommand = command;
    }
    
    public Command getMoveDownCommand() {
        return moveDownCommand;
    }
    
    public void setMoveUpCommand(Command command) {
        moveUpCommand = command;
    }
    
    public Command getMoveUpCommand() {
        return moveUpCommand;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        deleteButton = new javax.swing.JButton();
        insertAfterButton = new javax.swing.JButton();
        insertBeforeButton = new javax.swing.JButton();
        moveDownButton = new javax.swing.JButton();
        moveUpButton = new javax.swing.JButton();
        
        setLayout(new java.awt.GridLayout(1, 5));
        
        deleteButton.setFont(new java.awt.Font("Dialog", 0, 12));
        deleteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/row_delete_16.gif")));
        deleteButton.setToolTipText("Delete row");
        deleteButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        
        add(deleteButton);
        
        insertAfterButton.setFont(new java.awt.Font("Dialog", 0, 12));
        insertAfterButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/row_insert_after_16.gif")));
        insertAfterButton.setToolTipText("Insert row after selection");
        insertAfterButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        insertAfterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertAfterButtonActionPerformed(evt);
            }
        });
        
        add(insertAfterButton);
        
        insertBeforeButton.setFont(new java.awt.Font("Dialog", 0, 12));
        insertBeforeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/row_insert_before_16.gif")));
        insertBeforeButton.setToolTipText("Insert row before selection");
        insertBeforeButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        insertBeforeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBeforeButtonActionPerformed(evt);
            }
        });
        
        add(insertBeforeButton);
        
        moveDownButton.setFont(new java.awt.Font("Dialog", 0, 12));
        moveDownButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/down_16.gif")));
        moveDownButton.setToolTipText("Move row down");
        moveDownButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        moveDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveDownButtonActionPerformed(evt);
            }
        });
        
        add(moveDownButton);
        
        moveUpButton.setFont(new java.awt.Font("Dialog", 0, 12));
        moveUpButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/up_16.gif")));
        moveUpButton.setToolTipText("Move row up");
        moveUpButton.setMargin(new java.awt.Insets(1, 1, 1, 1));
        moveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveUpButtonActionPerformed(evt);
            }
        });
        
        add(moveUpButton);
        
    }//GEN-END:initComponents

    private void moveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveUpButtonActionPerformed
        moveUpCommand.execute();
    }//GEN-LAST:event_moveUpButtonActionPerformed

    private void moveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveDownButtonActionPerformed
        moveDownCommand.execute();
    }//GEN-LAST:event_moveDownButtonActionPerformed

    private void insertBeforeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertBeforeButtonActionPerformed
        insertBeforeCommand.execute();
    }//GEN-LAST:event_insertBeforeButtonActionPerformed

    private void insertAfterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertAfterButtonActionPerformed
        insertAfterCommand.execute();
    }//GEN-LAST:event_insertAfterButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        deleteCommand.execute();
    }//GEN-LAST:event_deleteButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton insertAfterButton;
    private javax.swing.JButton moveDownButton;
    private javax.swing.JButton insertBeforeButton;
    private javax.swing.JButton moveUpButton;
    // End of variables declaration//GEN-END:variables
    
}
