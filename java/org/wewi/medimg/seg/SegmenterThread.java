/**
 * Created on 11.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.seg;

import java.util.Iterator;
import java.util.Vector;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.NullImage;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SegmenterThread extends Thread {
	private Vector observer;
	
	private Image mrtImage;
	private Image segImage;
	private Segmenter segmenter;
	
	
	public SegmenterThread(Segmenter segmenter) {
		this.segmenter = segmenter;
		mrtImage = new NullImage();
		segImage = new NullImage();	
	}
	
    public void addSegmenterObserver(SegmenterObserver o) {
        observer.add(o);
    }
    
    public void removeSegmenterObserver(SegmenterObserver o) {
        observer.remove(o);
    }
    
    private void notifySegmenterFinished() {
        Vector o = (Vector)observer.clone();
        SegmenterObserver so;
        for (Iterator it = o.iterator(); it.hasNext();) {
            so = (SegmenterObserver)it.next();
            so.segmenterFinished(new SegmenterEvent(this));
        }
    }
    
    private void notifySegmenterStarted() {
        Vector o = (Vector)observer.clone();
        SegmenterObserver so;
        for (Iterator it = o.iterator(); it.hasNext();) {
            so = (SegmenterObserver)it.next();
            so.segmenterStarted(new SegmenterEvent(this));
        }
    }    	
	

	public void run() {
		segImage = segmenter.segment(mrtImage);
	}
	
	public void setImage(Image mrtImage) {
		this.mrtImage = mrtImage;	
	}
	
	public Image getSegmentedImage() {
		return segImage;	
	}

}
