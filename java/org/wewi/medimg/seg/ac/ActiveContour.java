/**
 * ActiveContour.java
 *
 * Created on 20. Februar 2002, 17:21
 */

package org.wewi.medimg.seg.ac;

import java.util.List;

import org.wewi.medimg.image.geom.Point;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ActiveContour extends Cloneable {
    
    //public ImagePanel.ImageCanvas getImageCanvas();
    
    public void addBasePoint(Point point);
    
    public void removeBasePoint(Point point);
    
    public void replaceBasePoint(Point oldPoint, Point newPoint);

    public List getBasePoints();
    
    public int getNBasePoints();
    
    public List getContourPoints();
    
    public Object clone();
    
}
