/**
 * ImageGrid.java
 * 
 * Created on 04.03.2003, 20:19:48
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.Graphics;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageGrid implements ImageCanvas {
    private int strideX;
    private int strideY;
    
    
    public ImageGrid(int strideX, int strideY) {
        this.strideX = strideX;
        this.strideY = strideY;
    }
    
    
    /**
     * @see org.wewi.medimg.viewer.image.ImageCanvas#draw(java.awt.Graphics, org.wewi.medimg.viewer.image.ImagePanel)
     */
    public void draw(Graphics graph, ImagePanel panel) {
        
    }


}
