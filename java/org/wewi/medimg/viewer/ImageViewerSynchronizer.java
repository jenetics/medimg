/**
 * Created on 11.09.2002
 *
 */
package org.wewi.medimg.viewer;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.wewi.medimg.viewer.image.ImageViewer;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageViewerSynchronizer implements ImageViewerListener {
    private Vector viewerVector;
    private Vector viewerLocation;
    private Vector viewerSize;
    
    private int slice;

	/**
	 * Constructor for ImageViewerGlue.
	 */
	public ImageViewerSynchronizer() {
		super();
        viewerVector = new Vector();
        viewerLocation = new Vector();
        viewerSize = new Vector();
        slice = 0;
	}
    
    public synchronized void addImageViewer(ImageViewer viewer) {
        addImageViewer(viewer, new Point(0, 0), new Dimension(300, 300));   
    }
    
    public synchronized void removeImageViewer(ImageViewer viewer) {
        int index = viewerVector.indexOf(viewer);
        viewerVector.remove(index);
        viewerLocation.remove(index);
        viewerSize.remove(index);
        viewer.removeImageViewerListener(this);    
    }
    
    public synchronized void addImageViewer(ImageViewer viewer, Point pos, Dimension size) {
        viewerVector.add(viewer);
        viewerLocation.add(pos);
        viewerSize.add(size);  
        viewer.addImageViewerListener(this);
    }
    
    public int size() {
        synchronized (viewerVector) {
            return viewerVector.size();    
        }    
    }
    
    public List getImageViewer() {
        Vector v;
        synchronized (viewerVector) {
            v = (Vector)viewerVector.clone();    
        }
        return v;
    }
    
    public synchronized ImageViewer[] getImageViewer(ImageViewer[] viewer) {
        synchronized (viewerVector) {
            return (ImageViewer[])viewerVector.toArray(viewer);         
        }
    }
    
    public List getImageViewerLocation() {
        Vector v;
        synchronized (viewerLocation) {
            v = (Vector)viewerLocation.clone();
        }
        return v;
    }
    
    public synchronized Point[] getImageViewerLocation(Point[] point) {
        synchronized (viewerLocation) {
            return (Point[])viewerLocation.toArray(point);    
        }    
    }
    
    public List getImageViewerSize() {
        Vector v;
        synchronized (viewerSize) {
            v = (Vector)viewerSize.clone();    
        }    
        return v;
    }
    
    public synchronized Dimension[] getImageViewerSize(Dimension[] size) {
        synchronized (viewerSize) {
            return (Dimension[])viewerSize.toArray(size);    
        }    
    }
    
    
    public synchronized void repaintImages() {
        Vector v;
        synchronized (viewerVector) {
            v = (Vector)viewerVector.clone();    
        }    
        ImageViewer viewer;
        for (Iterator it = v.iterator(); it.hasNext();) {
            viewer = (ImageViewer)it.next();
            viewer.repaintImage();    
        }
    }
    
    /**
     * Methode zur Synchronisierung der angemeldeten Viewer.
     * 
     * @param slice alle Viewer werden auf diese Schicht synchronisiert.
     */
    private void synchronize(int slice) {
        Vector vv;
        synchronized (viewerVector) {
            vv = (Vector)viewerVector.clone();
        }
        ImageViewer viewer = null;
        for (Iterator it = vv.iterator(); it.hasNext();) {
            viewer = (ImageViewer)it.next();
            if (viewer == null) {
                continue;
            }
            if (viewer.getSlice() != slice) {
                viewer.setSlice(slice);    
            }
        }    
    }
    
    public synchronized void setSlice(int slice) {
        synchronize(slice);    
    }
    
    public int getSlice() {
        return slice;    
    }
    
    private void closeImageViewer(ImageViewer source) {
        Vector v;
        synchronized (viewerVector) {
            v = (Vector)viewerVector.clone();    
        }   
        ImageViewer viewer; 
        for (Iterator it = v.iterator(); it.hasNext();) {
            viewer = (ImageViewer)it.next();
            removeImageViewer(viewer);
            if (viewer != source && viewer != null) {
                viewer.dispose();    
            }
        }
    }

	/**
	 * @see org.wewi.medimg.viewer.ImageViewerListener#update(ImageViewerEvent)
	 */
	public void update(ImageViewerEvent event) {
        if (event.isClosed()) {
            closeImageViewer((ImageViewer)event.getSource());
            return;    
        }
        
        slice = event.getSlice();
        
        synchronize(event.getSlice());
	}

}




