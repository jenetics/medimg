/*
 * PrevCommand.java
 *
 * Created on 28. März 2002, 21:23
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class PrevCommand implements Command {
    private ImageViewer imageViewer;

    /** Creates new PrevCommand */
    public PrevCommand(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void execute() {
        int slice = imageViewer.getSlice();
        if (imageViewer.getImage().getMinZ() < slice) {
            imageViewer.setSlice(slice-1);
        }
    }
    
}
