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
