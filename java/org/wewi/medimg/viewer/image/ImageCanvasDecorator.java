/**
 * ImageCanvasDecorator.java
 * 
 * Created on 04.03.2003, 20:01:25
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.Graphics;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ImageCanvasDecorator implements ImageCanvas {
    private ImageCanvas component;

    /**
     * Constructor for ImageCanvasDecorator.
     */
    public ImageCanvasDecorator(ImageCanvas component) {
        this.component = component;
    }

    
    public void draw(Graphics graph, ImagePanel panel) {
        component.draw(graph, panel);
        drawComponent(graph,panel);
    }

    
    protected abstract void drawComponent(Graphics graph, ImagePanel panel);

}
