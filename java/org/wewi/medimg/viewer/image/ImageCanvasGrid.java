/**
 * ImageCanvasGrid.java
 * 
 * Created on 04.03.2003, 20:19:48
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.Graphics;

import org.wewi.medimg.viewer.image.ImagePanel.ImageCanvas;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageCanvasGrid extends ImageCanvasDecorator {

	/**
	 * Constructor for ImageCanvasGrid.
	 * @param component
	 */
	public ImageCanvasGrid(ImageCanvas component) {
		super(component);
	}

	/**
	 * @see org.wewi.medimg.viewer.ImageCanvasDecorator#drawComponent(java.awt.Graphics, org.wewi.medimg.viewer.ImagePanel)
	 */
	protected void drawComponent(Graphics graph, ImagePanel panel) {
	}

}
