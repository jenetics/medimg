/*
 * LastCommand.java
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
final class LastCommand implements Command {
    private TwinImageViewer viewer;
    
    /** Creates a new instance of LastCommand */
    public LastCommand(TwinImageViewer viewer) {
        this.viewer = viewer;
    }
    
    public void execute() {
        int maxSlice = Math.min(viewer.getImage1().getMaxZ(),
                                viewer.getImage2().getMaxZ());
        viewer.setSlice(maxSlice-1);
    }
    
}
