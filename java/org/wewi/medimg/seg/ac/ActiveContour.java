/*
 * ActiveContour.java
 *
 * Created on 20. Februar 2002, 17:21
 */

package org.wewi.medimg.seg.ac;

import org.wewi.medimg.image.geom.Point;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface ActiveContour extends Cloneable {
    
    public void replaceBasePoint(Point oldPoint, Point newPoint);

    public Iterator getBasePoints();
    
    public Iterator getContourPoints();
    
    public Object clone();
    
}
