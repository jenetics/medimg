/**
 * SliceViewerPanel.java
 * 
 * Created on 05.03.2003, 12:48:18
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class SliceViewerPanel extends JPanel {
    private Image image;
    
    //Components
    private SliceViewerToolPanel toolPanel; 
    private SliceViewerPanelImpl viewerPanel;   
    
    public SliceViewerPanel(Image image) {
        this.image = image;
        
        initComponents();
    }
    
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        //Adding the tool panle (align left)
        toolPanel = new SliceViewerToolPanel(this);
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(toolPanel);
        add(northPanel, BorderLayout.NORTH); 
        
                
        viewerPanel = new SliceViewerPanelImpl(image); 
        add(viewerPanel, BorderLayout.CENTER);     
    }
    
    public Image getImage() {
        return image;
    }
    
    public void setZoom(double zoom) {
        viewerPanel.setZoom(zoom);
    }
    
    public double getZoom() {
        return viewerPanel.getZoom();
    }
    
    public void setPlanePosition(double planePosition){
        viewerPanel.setPlanePosition(planePosition);
    }
    
    public double getPlanePosition() {
        return viewerPanel.getPlanePosition();
    }
    
    public void update() {
        viewerPanel.updateViewer();
    }

}
