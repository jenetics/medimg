/*
 * MLKMeansClustererArgumentPanel.java
 *
 * Created on 11. August 2002, 21:16
 */

package org.wewi.medimg.seg.wizard;

import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.stat.MLKMeansClusterer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MLKMeansClustererArgumentPanel extends SegmenterArgumentPanel {
    private int nfeatures = 4;
    
    /** Creates new form MLKMeansClustererArgumentPanel */
    public MLKMeansClustererArgumentPanel() {
        initComponents();
    }
    
    public ObservableSegmenter getSegmenter() {
        return new MLKMeansClusterer(nfeatures);
    }     
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        propertyLabel = new javax.swing.JLabel();
        propertieTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        setBorder(new javax.swing.border.TitledBorder("ML-Segmenter"));
        propertyLabel.setText("Number of Features: ");
        propertyLabel.setToolTipText("null");
        add(propertyLabel, new java.awt.GridBagConstraints());

        propertieTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        propertieTextField.setText("4");
        propertieTextField.setToolTipText("null");
        propertieTextField.setMinimumSize(new java.awt.Dimension(60, 20));
        propertieTextField.setPreferredSize(new java.awt.Dimension(100, 20));
        propertieTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                propertieTextFieldFocusLost(evt);
            }
        });

        add(propertieTextField, new java.awt.GridBagConstraints());

    }//GEN-END:initComponents

    private void propertieTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_propertieTextFieldFocusLost
        // Add your handling code here:
        try {
            nfeatures = Integer.parseInt(propertieTextField.getText());
        } catch (NumberFormatException nfe) {
            propertieTextField.setText("4");
        }
    }//GEN-LAST:event_propertieTextFieldFocusLost
       
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField propertieTextField;
    private javax.swing.JLabel propertyLabel;
    // End of variables declaration//GEN-END:variables
    
}
