/**
 * CCChooserPanel.java
 *
 * Created on 4. Oktober 2002, 20:20
 */

package org.wewi.medimg.viewer.tools;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelSelectorEvent;
import org.wewi.medimg.image.VoxelSelectorListener;
import org.wewi.medimg.viewer.ImageViewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class CCChooserPanel extends javax.swing.JPanel implements VoxelSelectorListener {
    private ColorConversion colorConversion;
    private ImageViewer imageViewer;
    private Image image;
    
    /** Creates new form CCChooserPanel */
    public CCChooserPanel(ImageViewer imageViewer) {
        super();
        this.imageViewer = imageViewer;
        image = imageViewer.getImage();
        initComponents();
        init();
    }
    
    public void init() {
        imageViewer.addVoxelSelectorListener(this);
    }
    
    public ColorConversion getColorConversion() {
        return colorConversion;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jColorChooser1 = new javax.swing.JColorChooser();
        jPanel1 = new javax.swing.JPanel();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(2, 0));

        jColorChooser1.setBorder(new javax.swing.border.TitledBorder(""));
        jColorChooser1.setDoubleBuffered(true);
        add(jColorChooser1);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jPanel1.add(jTable1, java.awt.BorderLayout.CENTER);

        jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
        jButton1.setText("OK");
        jPanel1.add(jButton1, java.awt.BorderLayout.SOUTH);

        add(jPanel1);

    }//GEN-END:initComponents

    public void voxelSelected(VoxelSelectorEvent event) {
    }    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    
}
