/**
 * Created on 14.11.2002 07:42:38
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class UnaryPointTransformerFactory {
    private UnaryFunction functor;
    
    public UnaryPointTransformerFactory(UnaryFunction functor) {
        super();
        this.functor = functor;        
    }
    
    
    public UnaryPointTransformer createTransformer(Image image) {
        return new UnaryPointTransformer(image, functor);   
    }
}
