/*
 * NextCommand.java
 *
 * Created on 28. M�rz 2002, 21:17
 */

package org.wewi.medimg.viewer.image;

import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
final class NextCommand implements Command {
    private ImageViewer imageViewer;

    /** Creates new NextCommand */
    public NextCommand(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void execute() {
        int slice = imageViewer.getSlice();
        if (imageViewer.getImage().getMaxZ() >= slice+1) {
            imageViewer.setSlice(slice+1);
        }
    }
    
}
