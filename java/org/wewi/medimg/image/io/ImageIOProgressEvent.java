/**
 * Created on 01.09.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */package org.wewi.medimg.image.io;

import java.util.EventObject;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
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
