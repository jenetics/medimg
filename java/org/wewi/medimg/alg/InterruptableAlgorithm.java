/**
 * Created on 31.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.alg;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface InterruptableAlgorithm {
    
    public void interruptAlgorithm() throws UnsupportedOperationException;
    
    public void resumeAlgorithm() throws UnsupportedOperationException;
    
    public void cancelAlgorithm() throws UnsupportedOperationException;
}
