/*
 * SegmenterObservable.java
 *
 * Created on 24. Juli 2002, 20:22
 */

package org.wewi.medimg.seg;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public abstract class ObservableSegmenter implements Segmenter { 
    private Vector listeners;
    protected Logger logger;
    
    public ObservableSegmenter() {
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
    
    public void addSegmentationListener(SegmenterObserver o) {
        listeners.add(o);
    }
    
    public void removeSegmentationListener(SegmenterObserver o) {
        listeners.remove(o);
    }
    
    protected void notifySegmenterStarted(SegmenterEvent event) {
        Vector lv = (Vector)listeners.clone();
        SegmenterObserver l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmenterObserver)it.next();
            l.segmenterStarted(event);
        }
    }    
    
    protected void notifySegmenterFinished(SegmenterEvent event) {
        Vector lv = (Vector)listeners.clone();
        SegmenterObserver l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmenterObserver)it.next();
            l.segmenterFinished(event);
        }
    }    
    
}
