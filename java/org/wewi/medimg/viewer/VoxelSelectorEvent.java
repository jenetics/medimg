/**
 * Created on 11.09.2002
 *
 */
package org.wewi.medimg.viewer;

import java.awt.Point;
import java.util.EventObject;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Point3D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class VoxelSelectorEvent extends EventObject {
    private final Image image;
    private final Point3D imagePoint;
    private final Point mousePoint;
    
    public VoxelSelectorEvent(Object source, Point mousePoint, Image image, Point3D imagePoint) {
        super(source);
        this.image = image;  
        this.imagePoint = imagePoint;
        this.mousePoint = mousePoint;  
    }
    
    public Image getAffectedImage() {
        return image;   
    }
    
    public Point3D getSelectedImagePoint() {
        return imagePoint;    
    }
    
    public Point getSelectedMousePoint() {
        return mousePoint;    
    }
}
