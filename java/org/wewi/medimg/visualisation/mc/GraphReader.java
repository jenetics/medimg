/*
 * GraphReader.java
 *
 * Created on 11. Juni 2002, 21:11
 */

package org.wewi.medimg.visualisation.mc;

import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter*/
public interface GraphReader {
    public void read() throws IOException;
    
    public Graph getGraph();
}
