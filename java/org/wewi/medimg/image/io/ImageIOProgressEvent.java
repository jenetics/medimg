/**
 * Created on 01.09.2002
 *
 */
package org.wewi.medimg.image.io;

import java.util.EventObject;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageIOProgressEvent extends EventObject {
    private double progress;
    private boolean finished;
    
	/**
	 * Constructor for ProgressEvent.
	 * @param arg0
	 */
	public ImageIOProgressEvent(Object source, double progress, boolean finished) {
		super(source);
        this.progress = progress;
        this.finished = finished;
	}
    
    public double getProgress() {
        return progress;
    }
    
    public boolean isFinished() {
        return finished;    
    }

}
