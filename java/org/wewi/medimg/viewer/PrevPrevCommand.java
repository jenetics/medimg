/*
 * PrevPrevCommand.java
 *
 * Created on 28. März 2002, 21:42
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class PrevPrevCommand implements Command {
    private ImageViewer imageViewer;
    private int stride;

    /** Creates new PrevPrevCommand */
    public PrevPrevCommand(ImageViewer imageViewer, int stride) {
        this.imageViewer = imageViewer;
        this.stride = stride;
    }

    public void execute() {
        int slice = imageViewer.getSlice();
        if (imageViewer.getImage().getMinZ() < slice-stride) {
            imageViewer.setSlice(slice-stride);
        }
    }
    
}
