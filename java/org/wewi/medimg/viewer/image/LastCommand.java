/*
 * LastCommand.java
 *
 * Created on 28. März 2002, 21:39
 */

package org.wewi.medimg.viewer.image;

import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class LastCommand implements Command {
    private ImageViewer imageViewer;

    /** Creates new LastCommand */
    public LastCommand(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void execute() {
        int slice = imageViewer.getImage().getMaxZ();
        imageViewer.setSlice(slice);
    }
    
}
