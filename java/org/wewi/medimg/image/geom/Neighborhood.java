/*
 * Neighborhood.java
 *
 * Created on 18. Februar 2002, 20:43
 */

package org.wewi.medimg.image.geom;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public interface Neighborhood {
    public Iterator getNeighbors(Point p);
}

