/**
 * Validator.java
 *
 * Created on 7. August 2002, 15:32
 */

package org.wewi.medimg.seg.validation;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.seg.stat.MLKMeansClusterer;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MLValidator {
    private MLKMeansClusterer clusterer;
    private Image source;
    private Image target;
    
    private long startTime;
    private long stopTime;
    
    public MLValidator(MLKMeansClusterer clusterer, Image source) {
        this.clusterer = clusterer;
        this.source = source;
    }      
    
    
    public void validate() {
        startTime = System.currentTimeMillis();
        target = clusterer.segment(source);
        stopTime = System.currentTimeMillis();    
    }
    
    public long getStartTime() {
        return startTime;    
    }
    
    public long getStopTime() {
        return stopTime;    
    }
    
    public Image getSourceImage() {
        return source;    
    }
    
    public Image getTargetImage() {
        return target;    
    }
    
    public Class getAlgorithmClass() {
        return clusterer.getClass();    
    }
    
    public String getAlgorithmName() {
        return clusterer.toString();    
    }
}







