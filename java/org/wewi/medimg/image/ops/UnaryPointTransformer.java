/**
 * Created on 14.11.2002 06:06:39
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class UnaryPointTransformer {
    private Image image;
    private UnaryFunction functor;

	/**
	 * Constructor for UnaryPointTransformer.
	 */
	public UnaryPointTransformer(Image image, UnaryFunction functor) {
		super();
        this.image = image;
        this.functor = functor;
	}
    
    public void transform() {
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            image.setColor(i, functor.process(image.getColor(i)));    
        }    
    }
    
    public Image getImage() {
        return image;    
    }
    
    public UnaryFunction getFunctor() {
        return functor;    
    }

}
