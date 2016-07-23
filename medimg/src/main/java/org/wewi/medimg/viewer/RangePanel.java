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
 * RangePanel.java
 *
 * Created on 17. Juni 2002, 19:15
 */

package org.wewi.medimg.viewer;


import java.io.File;

import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.Range;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
class RangePanel extends javax.swing.JPanel {
    private ImageFileChooser fileChooser;
    
    private int min = 0;
    private int max = 1000;
    private int stride = 1;
    
    /** Creates new form RangePanel */
    public RangePanel(ImageFileChooser fileChooser) {
        this.fileChooser = fileChooser;
        initComponents();
        strideReadButton.setVisible(false);
    }
    
    public Range getRange() {
        return new Range(min, max, stride);
    }
    
    public void setRange(Range range) {
        min = range.getMinSlice();
        max = range.getMaxSlice();
        stride = range.getStride();
    }
    
    private boolean testMinSlice(String s) {
        try {
            int minTemp = Integer.parseInt(s);
            if (minTemp <= max) {
                min = minTemp;
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    
    private boolean testMaxSlice(String s) {
        try {
            int maxTemp = Integer.parseInt(s);
            if (maxTemp >= min) {
                max = maxTemp;
                return true;
            } else {
                return false;
            }            
        } catch (NumberFormatException nfe) {
            return false;
        }
    }    
    
    
    private boolean testStride(String s) {
        try {
            int strideTemp = Integer.parseInt(s);
            if (strideTemp <= (max-min)) {
                stride = strideTemp;
                return true;
            } else {
                return false;
            }            
        } catch (NumberFormatException nfe) {
            return false;
        }
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        rangeFromLabel = new javax.swing.JLabel();
        rangeFromTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        rangeToLabel = new javax.swing.JLabel();
        rangeToTextField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        strideLabel = new javax.swing.JLabel();
        strideTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        strideReadButton = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(4, 1));

        setBorder(new javax.swing.border.TitledBorder(null, "Bildbereich", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12)));
        rangeFromLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        rangeFromLabel.setText("Schicht von:");
        jPanel1.add(rangeFromLabel);

        rangeFromTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        rangeFromTextField.setText("0");
        rangeFromTextField.setDoubleBuffered(true);
        rangeFromTextField.setDragEnabled(true);
        rangeFromTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        rangeFromTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                rangeFromTextFieldFocusLost(evt);
            }
        });

        jPanel1.add(rangeFromTextField);

        add(jPanel1);

        rangeToLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        rangeToLabel.setText("Schicht bis:");
        jPanel2.add(rangeToLabel);

        rangeToTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        rangeToTextField.setText("1000");
        rangeToTextField.setDoubleBuffered(true);
        rangeToTextField.setDragEnabled(true);
        rangeToTextField.setPreferredSize(new java.awt.Dimension(75, 20));
        rangeToTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                rangeToTextFieldFocusLost(evt);
            }
        });

        jPanel2.add(rangeToTextField);

        add(jPanel2);

        strideLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        strideLabel.setText("Schrittweite:");
        jPanel3.add(strideLabel);

        strideTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        strideTextField.setText("1");
        strideTextField.setMinimumSize(new java.awt.Dimension(5, 20));
        strideTextField.setPreferredSize(new java.awt.Dimension(70, 20));
        strideTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                strideTextFieldFocusLost(evt);
            }
        });

        jPanel3.add(strideTextField);

        add(jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));

        strideReadButton.setFont(new java.awt.Font("Dialog", 0, 12));
        strideReadButton.setMnemonic('L');
        strideReadButton.setText("Lesen der Schichten");
        strideReadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                strideReadButtonActionPerformed(evt);
            }
        });

        jPanel4.add(strideReadButton);

        add(jPanel4);

    }//GEN-END:initComponents

    private void strideReadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_strideReadButtonActionPerformed
        try {
            fileChooser.actionPerformed(null);
            String fileName = fileChooser.getSelectedFile().getAbsolutePath();
            ImageReader reader = fileChooser.getImageReaderFactory().
                                        createImageReader(IntImageFactory.getInstance(),
                                                               new File(fileName));
            int slices = 0;
            //slices = reader.getSlices();
            min = 0;
            max = Math.max(slices-1, 0);
            stride = 1;

            strideTextField.setText("" + stride);
            rangeFromTextField.setText("" + min);
            rangeToTextField.setText("" + max);
        } catch (Exception e) {
            return;
        }
    }//GEN-LAST:event_strideReadButtonActionPerformed

    private void strideTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_strideTextFieldFocusLost
        if (!testStride(strideTextField.getText())) {
            strideTextField.setText("" + stride);
        }
    }//GEN-LAST:event_strideTextFieldFocusLost

    private void rangeToTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rangeToTextFieldFocusLost
        if (!testMaxSlice(rangeToTextField.getText())) {
            rangeToTextField.setText("" + max);
        }
    }//GEN-LAST:event_rangeToTextFieldFocusLost

    private void rangeFromTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rangeFromTextFieldFocusLost
        if (!testMinSlice(rangeFromTextField.getText())) {
            rangeToTextField.setText("" + min);
        }        
    }//GEN-LAST:event_rangeFromTextFieldFocusLost
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField rangeFromTextField;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton strideReadButton;
    private javax.swing.JTextField rangeToTextField;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel strideLabel;
    private javax.swing.JLabel rangeFromLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField strideTextField;
    private javax.swing.JLabel rangeToLabel;
    // End of variables declaration//GEN-END:variables
    
}