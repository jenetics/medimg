/*
 * LastCommand.java
 *
 * Created on 28. M�rz 2002, 21:39
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public final class LastCommand implements Command {
    private ImageViewer imageViewer;

    /** Creates new LastCommand */
    public LastCommand(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void execute() {
        int slice = imageViewer.getImage().getMaxZ()-1;
        imageViewer.setSlice(slice);
    }
    
}
