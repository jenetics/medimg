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
 * ImagePropertiesPanel.java
 *
 * Created on 17. Februar 2003, 13:57
 */

package org.wewi.medimg.viewer.tools;

import java.util.Iterator;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.wewi.medimg.image.ImageProperties;
import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ImagePropertiesPanel extends javax.swing.JPanel {
    
    private class ImagePropertiesTableModel extends AbstractTableModel {
       
        public ImagePropertiesTableModel() {
        }
        
        public String getColumnName(int col) {
            switch (col) {
                case 0: return "Key";
                case 1: return "Value";
                default: return "";
            }
        }
        
        public int findColumn(String name) {
            if ("Key".equals(name)) {
                return 0;
            } else {
                return 1;
            }
        }
        
        /**
         * @see javax.swing.table.TableModel#getRowCount()
         */
        public int getRowCount() {
            return properties.size();
        }
        
        /**
         * @see javax.swing.table.TableModel#getColumnCount()
         */
        public int getColumnCount() {
            return 2;
        }
        
        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0: return properties.getKey(row);
                case 1: return properties.getValue(row);
                default: return "Invalid column index: " + col;
            }
        }
        
        public boolean isCellEditable(int row, int col) {
            return true;
        }
        
        public void setValueAt(Object value, int row, int col) {
            if (value == null) {
                return;
            }
            switch (col) {
                case 0: {
                    Object oldKey = getValueAt(row, 0);
                    Object newValue = getValueAt(row, 1);
                    Object newKey = value;
                    
                    if (value.equals("")) {
                        return;
                    }
                    
                    properties.replace((String)oldKey, (String)newKey, (String)newValue);
                    break;
                }
                case 1: {
                    Object oldKey = getValueAt(row, 0);
                    Object newKey = oldKey;
                    Object newValue = value;
                    properties.replace((String)oldKey, (String)newKey, (String)newValue);
                    break;
                }
                default:
                    break;
            }
            
            fireTableCellUpdated(row, col);
        }
        
        public Class getColumnClass(int col) {
            return String.class;
        }
        
    }
    
    private class DeleteCommand implements Command {
        public void execute() {
            int[] row = imagePropertiesTable.getSelectedRows();
            String[] keys = new String[row.length];
            for (int i = 0; i < row.length; i++) {
                keys[i] = properties.getKey(row[i]);
            }

            for (int i = 0; i < row.length; i++) {
                properties.remove(keys[i]);
            }    
            imagePropertiesTable.setModel(new ImagePropertiesTableModel());            
        }
    }
    
    private class InsertAfterCommand implements Command {
        public void execute() {
            int row = imagePropertiesTable.getSelectedRow();
            int size = properties.size();
            properties.setProperty(row + 1, "Key" + (size+1), "Value" + (size+1));
            imagePropertiesTable.setModel(new ImagePropertiesTableModel());
            imagePropertiesTable.setRowSelectionInterval(row+1, row+1);            
        }
    }
    
    public class InsertBeforeCommand implements Command {
        public void execute() {
            int row = imagePropertiesTable.getSelectedRow();
            if (row == -1) {
                row++;
            }
            int size = properties.size();
            properties.setProperty(row, "Key" + (size+1), "Value" + (size+1));
            imagePropertiesTable.setModel(new ImagePropertiesTableModel());
            imagePropertiesTable.setRowSelectionInterval(row, row);            
        }
    }
    
    public class MoveDownCommand implements Command {
        public void execute() {
            int row = imagePropertiesTable.getSelectedRow();
            if (row >= properties.size() - 1) {
                return;
            }

            String key1 = properties.getKey(row);
            String key2 = properties.getKey(row+1);
            properties.swap(key1, key2);

            imagePropertiesTable.setModel(new ImagePropertiesTableModel());
            imagePropertiesTable.setRowSelectionInterval(row+1, row+1);              
        }
    }
    
    public class MoveUpCommand implements Command {
        public void execute() {
            int row = imagePropertiesTable.getSelectedRow();
            if (row == -1 || row == 0) {
                return;
            }

            String key1 = properties.getKey(row);
            String key2 = properties.getKey(row-1);
            properties.swap(key1, key2);

            imagePropertiesTable.setModel(new ImagePropertiesTableModel());
            imagePropertiesTable.setRowSelectionInterval(row-1, row-1);            
        }
    }
    
    private ImageProperties imageProperties;
    private ImageProperties properties;
    
    private TableToolPanel tableToolPanel;
    
    /** Creates new form ImagePropertiesPanel */
    public ImagePropertiesPanel(ImageProperties properties) {
        this.imageProperties = properties;
        this.properties = (ImageProperties)properties.clone();
        initComponents();
        
        init();
    }
    
    private void init() { 
        tableToolPanel = new TableToolPanel();
        tableToolPanel.setDeleteCommand(new DeleteCommand());
        tableToolPanel.setInsertAfterCommand(new InsertAfterCommand());
        tableToolPanel.setInsertBeforeCommand(new InsertBeforeCommand());
        tableToolPanel.setMoveDownCommand(new MoveDownCommand());
        tableToolPanel.setMoveUpCommand(new MoveUpCommand());
        
        tabelToolBar.add(tableToolPanel);
    }
 
    public ImageProperties getImageProperties() {
        return properties;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jMenuBar1 = new javax.swing.JMenuBar();
        jPanel1 = new javax.swing.JPanel();
        tabelToolBar = new javax.swing.JToolBar();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        imagePropertiesTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        
        
        setLayout(new java.awt.BorderLayout());
        
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        
        tabelToolBar.setFont(new java.awt.Font("Dialog", 0, 12));
        jPanel1.add(tabelToolBar);
        
        add(jPanel1, java.awt.BorderLayout.NORTH);
        
        jPanel2.setLayout(new java.awt.BorderLayout());
        
        jPanel2.setBorder(new javax.swing.border.TitledBorder(null, "Image-Properties", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12)));
        imagePropertiesTable.setModel(new ImagePropertiesTableModel());
        imagePropertiesTable.setToolTipText("Imageproperties");
        imagePropertiesTable.setDragEnabled(true);
        imagePropertiesTable.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(imagePropertiesTable);
        
        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        
        add(jPanel2, java.awt.BorderLayout.CENTER);
        
        okButton.setFont(new java.awt.Font("Dialog", 0, 12));
        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        
        jPanel3.add(okButton);
        
        add(jPanel3, java.awt.BorderLayout.SOUTH);
        
        jPanel4.setLayout(new java.awt.GridLayout(3, 1));
        
        add(jPanel4, java.awt.BorderLayout.EAST);
        
        add(jPanel5, java.awt.BorderLayout.WEST);
        
    }//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (properties.size() < 0) {
            imagePropertiesTable.removeRowSelectionInterval(0, properties.size() - 1);
        }

        imageProperties.clear();
        Map.Entry entry;
        for (Iterator it = properties.iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            imageProperties.setProperty((String)entry.getKey(), (String)entry.getValue());
        }
    }//GEN-LAST:event_okButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable imagePropertiesTable;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JButton okButton;
    private javax.swing.JToolBar tabelToolBar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
    
}
