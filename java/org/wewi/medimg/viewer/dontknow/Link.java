/**
 * Link.java
 * Created on 03.04.2003
 *
 */
package org.wewi.medimg.viewer.dontknow;

import java.awt.LayoutManager;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Link extends Item {

    /**
     * @param layout
     * @param isDoubleBuffered
     */
    public Link(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    /**
     * @param layout
     */
    public Link(LayoutManager layout) {
        super(layout);
    }

    /**
     * @param isDoubleBuffered
     */
    public Link(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    /**
     * 
     */
    public Link() {
        super();
    }

}
