/**
 * ImageCanvas.java
 * 
 * Created on 04.03.2003, 20:32:52
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.Graphics;

/**
 * This interface defines the canvas you can put on the displayed image.
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface ImageCanvas {
    /**
     * When ever the image panel is repainted (by calling the method
     * <code>paintComponent(Graphics graph)</code>), this method is called
     * 
     * @param graph
     * @param panel the current ImagePanel
     */
    public void draw(Graphics graph, ImagePanel panel);
}