/**
 * Created on 19.09.2002
 * 
 */
package org.wewi.medimg.alg;

import java.util.Iterator;
import java.util.Vector;

/**
 * This abstract class implements..
 * 
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class ObservableAlgorithm {
    private Vector iterationListener = new Vector();

	/**
	 * Constructor for ObservableAlgorithm.
	 */
	public ObservableAlgorithm() { 
		super();
	}
    
    public synchronized void addIterationListener(AlgorithmIterationListener il) {
        iterationListener.add(il);   
    }
    
    public synchronized void removeIterationListener(AlgorithmIterationListener il) {
        iterationListener.remove(il);    
    }
    
    protected void notifyIterationStarted(AlgorithmIterationEvent event) {
        Vector lv;
        synchronized (iterationListener) {
           lv = (Vector)iterationListener.clone();
        }
        AlgorithmIterationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (AlgorithmIterationListener)it.next();
            l.iterationStarted(event);
        }
    }    
    
    protected void notifyIterationFinished(AlgorithmIterationEvent event) {
        Vector lv;
        synchronized (iterationListener) {
            lv = (Vector)iterationListener.clone();
        }
        AlgorithmIterationListener l;
        for (Iterator it = lv.iterator(); it.hasNext();) {
            l = (AlgorithmIterationListener)it.next();
            l.iterationFinished(event);
        }
    }     

}
