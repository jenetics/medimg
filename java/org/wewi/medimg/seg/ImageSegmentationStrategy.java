/*
 * Segmentation.java
 *
 * Created on 17. Januar 2002, 15:34
 */

package org.wewi.medimg.seg;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.FeatureImage;

import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public abstract class ImageSegmentationStrategy implements SegmentationStrategy {
    private Vector listeners;
    
    protected Image image;
    protected FeatureImage featureImage;
    
    public ImageSegmentationStrategy(Image image) {
        this.image = image;
        
        listeners = new Vector();
    }
    
    public void addSegmentationListener(SegmentationListener listener) {
        listeners.add(listener);
    }
    
    public void removeSegmentationListener(SegmentationListener listener) {
        listeners.remove(listener);
    }
       
    protected void notifyIterationFinished(SegmentationEvent event) {
        Vector lv = (Vector)listeners.clone();
        SegmentationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmentationListener)it.next();
            l.iterationFinished(event);
        }
    }
    
    protected void notifySegmentationStarted(SegmentationEvent event) {
        Vector lv = (Vector)listeners.clone();
        SegmentationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmentationListener)it.next();
            l.segmentationStarted(event);
        }
    }    
    
    protected void notifySegmentationFinished(SegmentationEvent event) {
        Vector lv = (Vector)listeners.clone();
        SegmentationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmentationListener)it.next();
            l.segmentationFinished(event);
        }
    }    
    
    public FeatureImage getFeatureImage() {
        return featureImage;
    }
    
    public Image getImage() {
        return image;
    }
        
}

