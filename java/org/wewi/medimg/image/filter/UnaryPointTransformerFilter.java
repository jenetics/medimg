/**
 * Created on 14.11.2002 07:44:28
 *
 */
package org.wewi.medimg.image.filter;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.UnaryPointTransformer;
import org.wewi.medimg.image.ops.UnaryPointTransformerFactory;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class UnaryPointTransformerFilter extends ImageFilter {
    private UnaryPointTransformerFactory factory;

	/**
	 * Constructor for UnaryPointTransformerFilter.
	 * @param image
	 */
	public UnaryPointTransformerFilter(Image image, UnaryPointTransformerFactory factory) {
		super(image);
        this.factory = factory;
	}

	/**
	 * Constructor for UnaryPointTransformerFilter.
	 * @param component
	 */
	public UnaryPointTransformerFilter(ImageFilter component, UnaryPointTransformerFactory factory) {
		super(component);
        this.factory = factory;
	}
    
    public void filter() {
        super.filter();
        
           
        UnaryPointTransformer t = factory.createTransformer(image);
        t.transform();
    }

}
