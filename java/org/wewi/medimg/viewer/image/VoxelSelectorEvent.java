/**
 * Created on 11.09.2002
 *
 */
package org.wewi.medimg.viewer.image;

import java.util.EventObject;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Point2D;
import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class VoxelSelectorEvent extends EventObject {
    private final Point3D imagePoint;
    private final Point2D mousePoint;
    
    public VoxelSelectorEvent(Object source, Point2D mousePoint, Point3D imagePoint) {
        super(source);  
        this.imagePoint = imagePoint;
        this.mousePoint = mousePoint;  
    }
    
    public Image getImage() {
        return ((ImagePanel)getSource()).getImage();   
    }
    
    public Point3D getSelectedImagePoint() {
        return imagePoint;    
    }
    
    public Point2D getSelectedMousePoint() {
        return mousePoint;    
    }
}
