/* 
 * ObservableAlgorithm.java, created on 19.09.2002
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
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
