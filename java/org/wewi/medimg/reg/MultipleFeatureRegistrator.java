/**
 * Created on 24.09.2002
 *
 */
package org.wewi.medimg.reg;

import java.util.List;
import java.util.Vector;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.InterpolateableTransformation;
import org.wewi.medimg.image.geom.transform.Transformation;

/**
 * 
 * 
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * @version 0.1
 *
 */
public abstract class MultipleFeatureRegistrator implements Registrator {
    
    /**
     * Interface, welches die gefunden Transformationen interpoliert.
     */
    public static interface TransformationInterpolator {
        public Transformation interpolate();    
    }
    
    public final class TransformationProperties {
           
    } 
    
    protected TransformationInterpolator transformationInterpolator;
    protected AffinityMetric affinityMetric;

	/**
	 * Constructor for MultipleFeatureRegistrator.
	 */
	public MultipleFeatureRegistrator() {
		super();
	}

	/**
	 * @see org.wewi.medimg.reg.Registrator#registrate(Image, Image)
	 */
	public Transformation registrate(Image source, Image target) {
        List transformationList = new Vector();
        
        ColorRange scr = source.getColorRange();
        ColorRange tcr = target.getColorRange();
        int minFeature = Math.max(scr.getMinColor(), tcr.getMinColor());
        int maxFeature = Math.min(scr.getMaxColor(), tcr.getMaxColor());
        
        VoxelIteratorFactory f = new VoxelIteratorFactory(source, target);
        for (int i = minFeature; i <= maxFeature; i++) {
            VoxelIterator sit = f.getSourceVoxelIterator(i);
            VoxelIterator tit = f.getTargetVoxelIterator(i);
            
            //Berechnen der Transformation
            Transformation trans = getTransformation((VoxelIterator)sit.clone(), 
                                                     (VoxelIterator)tit.clone());
            
            //Bestimmen der Qualität der berechneten Transformation
            double similarity = affinityMetric.similarity((VoxelIterator)sit.clone(),
                                                           (VoxelIterator)tit.clone(),
                                                            trans);
                                                        
        }      
       
        //Rückgabe der berechnenten Interpolation der Transformationen.
		return transformationInterpolator.interpolate();
	}
    
    /**
     * Wenn keine Transformation gefunden werden kann, weil z.B. einer
     * der Iteratoren keine Punkte enthält, soll von der Implementierung
     * die identische Transformation zurückgegeben werden.
     * 
     * @param source VoxelIterator der Punkte die transformiert werden sollen
     * @param target VoxelIterator der Punkte, auf die abgebildet wird
     * 
     * @return Die Transformation, die die source-Punkte auf die 
     *          target-Punkte abbildet.
     */
    protected abstract InterpolateableTransformation getTransformation(VoxelIterator source, 
                                                                         VoxelIterator target);

}








