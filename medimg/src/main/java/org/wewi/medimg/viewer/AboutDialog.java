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
 * AboutDialog.java
 *
 * Created on 22. August 2002, 11:28
 */

package org.wewi.medimg.viewer;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class AboutDialog extends javax.swing.JDialog {
    
    /** Creates new form AboutDialog */
    public AboutDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        northPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        centerPanel = new javax.swing.JPanel();
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Info");
        setLocationRelativeTo(this);
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        
        northPanel.setBackground(java.awt.SystemColor.control);
        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/wewi/medimg/viewer/icons/logo_seg.gif")));
        jLabel1.setText("Segmentierungs- und Registrierungwerkzeug    ");
        northPanel.add(jLabel1);
        
        getContentPane().add(northPanel, java.awt.BorderLayout.NORTH);
        
        centerPanel.setLayout(new java.awt.GridLayout(2, 1));
        
        centerPanel.setBackground(java.awt.SystemColor.control);
        getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);
        
        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new java.awt.Dimension(432, 274));
        setLocation((screenSize.width-432)/2,(screenSize.height-274)/2);
    }//GEN-END:initComponents
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JPanel northPanel;
    // End of variables declaration//GEN-END:variables
    
}