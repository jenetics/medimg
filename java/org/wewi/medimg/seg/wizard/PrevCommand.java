/*
 * PevCommand.java
 *
 * Created on 6. April 2002, 21:23
 */

package org.wewi.medimg.seg.wizard;

import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class PrevCommand implements Command {
    private TwinImageViewer viewer;
    
    /** Creates a new instance of PevCommand */
    public PrevCommand(TwinImageViewer viewer) {
        this.viewer = viewer;
    }
    
    public void execute() {
        int slice = viewer.getSlice();
        int minSlice = Math.max(viewer.getImage1().getMinZ(), 
                                viewer.getImage2().getMinZ());
        if (minSlice <= slice-1) {
            viewer.setSlice(slice-1);
        }
    }
    
}
