/*
 * FirstCommand.java
 *
 * Created on 28. M�rz 2002, 21:14
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public final class FirstCommand implements Command {
    private ImageViewer imageViewer;

    /** Creates new FirstCommand */
    public FirstCommand(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void execute() {
        int slice = imageViewer.getImage().getMinZ();
        imageViewer.setSlice(slice);
    }
    
}
