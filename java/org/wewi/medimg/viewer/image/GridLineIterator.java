/**
 * GridLineIterator.java
 * 
 * Created on 04.03.2003, 21:27:49
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.Dimension;
import java.awt.Point;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GridLineIterator implements LineIterator {
    private Point origin;
    private Dimension dim;
    private int gridsX;
    private int gridsY;

    /**
     * Constructor for GridLineIterator.
     */
    public GridLineIterator(Point origin, Dimension dim, int gridsX, int gridsY) {
        this.origin = origin;
        this.dim = dim;
        this.gridsX = gridsX;
        this.gridsY = gridsY;                         
    }

    /**
     * @see org.wewi.medimg.viewer.image.LineIterator#hasNext()
     */
    public boolean hasNext() {
        return false;
    }

    /**
     * @see org.wewi.medimg.viewer.image.LineIterator#next(int, int)
     */
    public void next(int[] begin, int[] end) {
    }

}
