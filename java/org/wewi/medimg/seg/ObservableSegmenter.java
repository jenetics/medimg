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
    private Vector segmenterListener;
    private Vector iterationListener;
    protected Logger logger;
    
    public ObservableSegmenter() {
        segmenterListener = new Vector();
        iterationListener = new Vector();
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
    
    public synchronized void addSegmenterListener(SegmenterListener o) {
        segmenterListener.add(o);
    }
    
    public synchronized void removeSegmenterListener(SegmenterListener o) {
        segmenterListener.remove(o);
    }
    
    public synchronized void addIterationListener(IterationListener il) {
        iterationListener.add(il);   
    }
    
    public synchronized void removeIterationListener(IterationListener il) {
        iterationListener.remove(il);    
    }
    
    protected void notifySegmenterStarted(SegmenterEvent event) {
        Vector lv = (Vector)segmenterListener.clone();
        SegmenterListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmenterListener)it.next();
            l.segmenterStarted(event);
        }
    }    
    
    protected void notifySegmenterFinished(SegmenterEvent event) {
        Vector lv = (Vector)segmenterListener.clone();
        SegmenterListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (SegmenterListener)it.next();
            l.segmenterFinished(event);
        }
    } 
    
    protected void notifyIterationStarted(IterationEvent event) {
        Vector lv = (Vector)iterationListener.clone();
        IterationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (IterationListener)it.next();
            l.iterationStarted(event);
        }
    }    
    
    protected void notifyIterationFinished(IterationEvent event) {
        Vector lv = (Vector)iterationListener.clone();
        IterationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (IterationListener)it.next();
            l.iterationFinished(event);
        }
    } 
    
    public abstract void interruptSegmenter() throws UnsupportedOperationException;
    
    public abstract void resumeSegmenter() throws UnsupportedOperationException;
    
    public abstract void cancelSegmenter() throws UnsupportedOperationException;   
    
    public abstract String getSegmenterName();  
    
    public abstract String toString(); 
    
}



