/*
 * Segmentation.java
 *
 * Created on 17. Januar 2002, 15:34
 */

package org.wewi.medimg.seg;

import org.wewi.medimg.QualityMeasure;

import org.wewi.medimg.seg.FeatureImage;

import org.wewi.medimg.image.Image;

import java.util.Vector;
import java.util.Iterator;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;

/**
 * Diese Klasse bildet die Basis für Segmentierungsklassen,
 *
 *
 * @author Franz Wilhelmstötter
 * @version 0.2
 */
public abstract class ImageSegmentationStrategy implements SegmentationStrategy {
    private Vector listeners;
    protected Logger logger;
    
    protected Image image;
    
    protected ImageSegmentationStrategy() {
        listeners = new Vector();
        logger = Logger.getLogger(getClass().getPackage().getName());        
    }
    
    public ImageSegmentationStrategy(Image image) {
        this.image = image;
        listeners = new Vector();
        logger = Logger.getLogger(getClass().getPackage().getName());
    }
    
    public void addLoggerHandler(Handler handler) {
        logger.addHandler(handler);
    }
    
    public void removeLoggerHandler(Handler handler) {
        logger.removeHandler(handler);
    }
    
    public void setLoggerLevel(Level level) {
        logger.setLevel(level);
    }
    
    public Level getLoggerLevel() {
        return logger.getLevel();
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
    
    public abstract ModelBasedSegmentation getModelBasedSegmentation();
    
    public abstract QualityMeasure getQualityMeasure();
    
    public abstract Image getSegmentedImage();
    
    public Image getImage() {
        return image;
    }
    
}

