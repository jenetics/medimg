/**
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

import org.wewi.medimg.alg.ObservableAlgorithm;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ObservableSegmenter extends ObservableAlgorithm 
                                            implements Segmenter { 
    private Vector segmenterListener;
    protected Logger logger;
    
    public ObservableSegmenter() {
        segmenterListener = new Vector();
        logger = Logger.getLogger(getClass().getPackage().getName());        
    }
    
    public synchronized void addLoggerHandler(Handler handler) {
        logger.addHandler(handler);
    }
    
    public synchronized void removeLoggerHandler(Handler handler) {
        logger.removeHandler(handler);
    }
    
    public synchronized void setLoggerLevel(Level level) {
        logger.setLevel(level);
    }
    
    public Level getLoggerLevel() {
        return logger.getLevel();
    }
    
    public synchronized void addSegmenterListener(SegmenterListener o) {
        segmenterListener.add(o);
    }
    
    public synchronized void removeSegmenterListener(SegmenterListener o) {
        segmenterListener.remove(o);
    }
    
    protected void notifySegmenterStarted(SegmenterEvent event) {
        Vector lv;
        synchronized (segmenterListener) {
            lv = (Vector)segmenterListener.clone();
        }
        SegmenterListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmenterListener)it.next();
            l.segmenterStarted(event);
        }
    }    
    
    protected void notifySegmenterFinished(SegmenterEvent event) {
        Vector lv;
        synchronized (segmenterListener) {
            lv = (Vector)segmenterListener.clone();
        }
        SegmenterListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmenterListener)it.next();
            l.segmenterFinished(event);
        }
    }  
    
    public abstract String getSegmenterName();  
    
    public abstract String toString(); 
    
}



