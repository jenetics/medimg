/**
 * ImageCanvasDecorator.java
 * 
 * Created on 04.03.2003, 20:01:25
 *
 */
package org.wewi.medimg.viewer;

import java.awt.Graphics;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ImageCanvasDecorator implements ImagePanel.ImageCanvas {
    private ImagePanel.ImageCanvas component;

	/**
	 * Constructor for ImageCanvasDecorator.
	 */
	public ImageCanvasDecorator(ImagePanel.ImageCanvas component) {
		this.component = component;
	}

    
    public void draw(Graphics graph, ImagePanel panel) {
        component.draw(graph, panel);
        drawComponent(graph,panel);
    }

    
    protected abstract void drawComponent(Graphics graph, ImagePanel panel);

}
