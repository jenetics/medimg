/*
 * PrevPrevCommand.java
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
final class PrevPrevCommand implements Command {
    private TwinImageViewer viewer;
    private int stride;
    
    /** Creates a new instance of PrevPrevCommand */
    public PrevPrevCommand(TwinImageViewer viewer, int stride) {
        this.viewer = viewer;
        this.stride = stride;
    }
    
    public void execute() {
        int slice = viewer.getSlice();
        int minSlice = Math.max(viewer.getImage1().getMinZ(),
                                viewer.getImage2().getMinZ());
        if (minSlice < slice-stride) {
            viewer.setSlice(slice-stride);
        }
    }
    
}
