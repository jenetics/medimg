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
 * StatePanelEntry.java
 *
 * Created on 28. M�rz 2002, 20:17
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public class StatePanelEntry extends javax.swing.JPanel {
    private String name;
    private String value;

    /** Creates new form StatePanelEntry */
    public StatePanelEntry(String name) {
        this.name = name;
        initComponents();
        stateNameLabel.setText(name + ":");
    }
    
    public void setValue(String value) {
        this.value = value;
        stateValueLabel.setText(value);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        stateNameLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        stateValueLabel = new javax.swing.JLabel();
        
        setLayout(new java.awt.GridLayout(1, 0));
        
        setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));
        
        jPanel1.setBorder(new javax.swing.border.EtchedBorder());
        jPanel1.add(stateNameLabel);
        
        add(jPanel1);
        
        jPanel2.setLayout(new java.awt.GridLayout(1, 0));
        
        jPanel2.setBorder(new javax.swing.border.EtchedBorder());
        jPanel2.add(stateValueLabel);
        
        add(jPanel2);
        
    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel stateNameLabel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel stateValueLabel;
    // End of variables declaration//GEN-END:variables

    
}
