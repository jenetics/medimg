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
 * @version 0.1
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
        
        observer = new Vector();	
	}
	
    public void addSegmenterListener(SegmenterListener o) {
        observer.add(o);
    }
    
    public void removeSegmenterListener(SegmenterListener o) {
        observer.remove(o);
    }
    
    private void notifySegmenterFinished(SegmenterEvent event) {
        Vector o = (Vector)observer.clone();
        SegmenterListener so;
        for (Iterator it = o.iterator(); it.hasNext();) {
            so = (SegmenterListener)it.next();
            so.segmenterFinished(event);
        }
    }
    
    private void notifySegmenterStarted(SegmenterEvent event) {
        Vector o = (Vector)observer.clone();
        SegmenterListener so;
        for (Iterator it = o.iterator(); it.hasNext();) {
            so = (SegmenterListener)it.next();
            so.segmenterStarted(event);
        }
    }   	
	

	public void run() {
        notifySegmenterStarted(new SegmenterEvent(this));
		segmenter.segment(mrtImage, segImage);
        notifySegmenterFinished(new SegmenterEvent(this));
	}
	
	public void setImage(Image mrtImage) {
		this.mrtImage = mrtImage;	
        segImage = (Image)mrtImage.clone();
        segImage.resetColor(0);
	}
	
	public Image getSegmentedImage() {
		return segImage;	
	}

}
