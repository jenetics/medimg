/**
 * Created on 11.09.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.viewer;

import java.util.Iterator;
import java.util.Vector;


/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ImageViewerSynchronizer implements ImageViewerListener {
    private Vector viewerVector;

	/**
	 * Constructor for ImageViewerGlue.
	 */
	public ImageViewerSynchronizer() {
		super();
        viewerVector = new Vector();
	}
    
    public void addImageViewer(ImageViewer viewer) {
        viewerVector.add(viewer); 
        viewer.addImageViewerListener(this);   
    }
    
    public void removeImageViewer(ImageViewer viewer) {
        viewerVector.add(viewer);
        viewer.removeImageViewerListener(this);    
    }
    
    private void synchronize(Object source, int slice) {
        Vector vv = (Vector)viewerVector.clone();
        ImageViewer viewer = null;
        for (Iterator it = vv.iterator(); it.hasNext();) {
            viewer = (ImageViewer)it.next();
            if (viewer != source) {
                viewer.setSlice(slice); 
            }  
        }    
    }

	/**
	 * @see org.wewi.medimg.viewer.ImageViewerListener#update(ImageViewerEvent)
	 */
	public void update(ImageViewerEvent event) {
        int slice = ((ImageViewer)event.getSource()).getSlice();
        synchronize(event.getSource(), slice);
	}

}




