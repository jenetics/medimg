/*
 * PrevCommand.java
 *
 * Created on 28. M�rz 2002, 21:23
 */

package org.wewi.medimg.viewer.image;

import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
final class PrevCommand implements Command {
    private ImageViewer imageViewer;

    /** Creates new PrevCommand */
    public PrevCommand(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void execute() {
        int slice = imageViewer.getSlice();
        if (imageViewer.getImage().getMinZ() <= slice-1) {
            imageViewer.setSlice(slice-1);
        }
    }
    
}