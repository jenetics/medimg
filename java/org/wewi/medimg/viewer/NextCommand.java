/*
 * NextCommand.java
 *
 * Created on 28. März 2002, 21:17
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class NextCommand implements Command {
    private ImageViewer imageViewer;

    /** Creates new NextCommand */
    public NextCommand(ImageViewer imageViewer) {
        this.imageViewer = imageViewer;
    }

    public void execute() {
        int slice = imageViewer.getSlice();
        if (imageViewer.getImage().getMaxZ()-1 > slice+1) {
            imageViewer.setSlice(slice+1);
        }
    }
    
}
