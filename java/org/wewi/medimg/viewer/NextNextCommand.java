/*
 * NextNextCommand.java
 *
 * Created on 28. März 2002, 21:44
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class NextNextCommand implements Command {
    private ImageViewer imageViewer;
    private int stride;

    /** Creates new NextNextCommand */
    public NextNextCommand(ImageViewer imageViewer, int stride) {
        this.imageViewer = imageViewer;
        this.stride = stride;
    }

    public void execute() {
        int slice = imageViewer.getSlice();
        if (imageViewer.getImage().getMaxZ() >= slice+stride) {
            imageViewer.setSlice(slice+stride);
        }
    }
    
}
