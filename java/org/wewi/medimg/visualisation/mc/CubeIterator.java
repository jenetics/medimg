/*
 * CubeIterator.java
 *
 * Created on 16. Mai 2002, 13:30
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
interface CubeIterator {
    boolean hasNext();
    
    Cube next();
}
