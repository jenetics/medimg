/**
 * Created on 19.09.2002
 *
 */
package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.ColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelSelectorEvent;
import org.wewi.medimg.image.VoxelSelectorListener;
import org.wewi.medimg.image.geom.Point3D;
import org.wewi.medimg.viewer.ImageViewer;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public class ActiveContourImageViewer extends ImageViewer
                                        implements VoxelSelectorListener {

    private ActiveContour contour;                                            

	/**
	 * Constructor for ActiveContourImageViewer.
	 */
	public ActiveContourImageViewer(String title, Image image) {
		super(title, image);
        init();
	}
    
    /**
     * Constructor for ActiveContourImageViewer.
     */
    public ActiveContourImageViewer(String title, Image image, ColorConversion cc) {
        super(title, image, cc);
        init();
    }
    
    private void init() {
        addVoxelSelectorListener(this);    
    } 
    
    public void setActiveContour(ActiveContour contour) {
        this.contour = contour; 
        imagePanel.setImageCanvas(contour.getImageCanvas());   
    }
    
    public ActiveContour getActiveContout() {
        return contour;    
    }
    
    
    /**
     * @see org.wewi.medimg.image.VoxelSelectorListener#voxelSelected(VoxelSelectorEvent)
     */
    public void voxelSelected(VoxelSelectorEvent event) {
        Point3D p = event.getSelectedImagePoint();
        contour.addBasePoint(p);
        repaintImage();
    }       

}
