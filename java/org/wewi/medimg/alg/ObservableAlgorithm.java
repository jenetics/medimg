/**
 * Created on 19.09.2002
 * 
 */
package org.wewi.medimg.alg;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ObservableAlgorithm {
    private Vector iterationListener;

	/**
	 * Constructor for ObservableAlgorithm.
	 */
	public ObservableAlgorithm() {
		super();
        iterationListener = new Vector();
	}
    
    public synchronized void addIterationListener(IterationListener il) {
        iterationListener.add(il);   
    }
    
    public synchronized void removeIterationListener(IterationListener il) {
        iterationListener.remove(il);    
    }
    
    protected void notifyIterationStarted(IterationEvent event) {
        Vector lv;
        synchronized (iterationListener) {
           lv = (Vector)iterationListener.clone();
        }
        IterationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (IterationListener)it.next();
            l.iterationStarted(event);
        }
    }    
    
    protected void notifyIterationFinished(IterationEvent event) {
        Vector lv;
        synchronized (iterationListener) {
            lv = (Vector)iterationListener.clone();
        }
        IterationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (IterationListener)it.next();
            l.iterationFinished(event);
        }
    }     

}
