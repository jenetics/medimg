/*
 * MAPKMeansClustererArgumentPanel.java
 *
 * Created on 11. August 2002, 21:16
 */

package org.wewi.medimg.seg.wizard;

import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.stat.MAPKMeansClusterer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MAPKMeansClustererArgumentPanel extends SegmenterArgumentPanel {
    private int nfeatures = 1;
    
    /** Creates new form MAPKMeansClustererArgumentPanel */
    public MAPKMeansClustererArgumentPanel() {
        initComponents();
    }
    
    public ObservableSegmenter getSegmenter() {
        return new MAPKMeansClusterer(nfeatures);
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        propertyLabel = new javax.swing.JLabel();
        nfeaturesSlider = new javax.swing.JSlider();

        setLayout(new java.awt.GridLayout(1, 0));

        setBorder(new javax.swing.border.TitledBorder(null, "MAP-Segmentierer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12)));
        propertyLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        propertyLabel.setText("Anzahl der Merkmale:    1");
        add(propertyLabel);

        nfeaturesSlider.setMajorTickSpacing(15);
        nfeaturesSlider.setMaximum(15);
        nfeaturesSlider.setMinimum(1);
        nfeaturesSlider.setMinorTickSpacing(1);
        nfeaturesSlider.setPaintTicks(true);
        nfeaturesSlider.setSnapToTicks(true);
        nfeaturesSlider.setValue(1);
        nfeaturesSlider.setDoubleBuffered(true);
        nfeaturesSlider.setValueIsAdjusting(true);
        nfeaturesSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                nfeaturesSliderStateChanged(evt);
            }
        });

        add(nfeaturesSlider);

    }//GEN-END:initComponents

    private void nfeaturesSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_nfeaturesSliderStateChanged
        nfeatures = nfeaturesSlider.getValue();
        propertyLabel.setText("Anzahl der Merkmale:    " + nfeatures);
    }//GEN-LAST:event_nfeaturesSliderStateChanged
       
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider nfeaturesSlider;
    private javax.swing.JLabel propertyLabel;
    // End of variables declaration//GEN-END:variables
    
}
