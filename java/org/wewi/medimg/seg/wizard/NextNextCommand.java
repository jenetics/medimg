/*
 * NextNextCommand.java
 *
 * Created on 6. April 2002, 21:31
 */

package org.wewi.medimg.seg.wizard;

import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class NextNextCommand implements Command {
    private TwinImageViewer viewer;
    private int stride;
    
    /** Creates a new instance of NextNextCommand */
    public NextNextCommand(TwinImageViewer viewer, int stride) {
        this.viewer = viewer;
        this.stride = stride;
    }
    
    public void execute() {
        int slice = viewer.getSlice();
        int maxSlice = Math.min(viewer.getImage1().getMaxZ(),
                                viewer.getImage2().getMaxZ());
        if (maxSlice > slice+stride) {
            viewer.setSlice(slice+stride);
        }
    }
    
}
