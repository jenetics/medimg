/*
 * FirstCommand.java
 *
 * Created on 6. April 2002, 21:31
 */

package org.wewi.medimg.viewer.wizard.seg;

import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class FirstCommand implements Command {
    private TwinImageViewer viewer;
    
    /** Creates a new instance of FirstCommand */
    public FirstCommand(TwinImageViewer viewer) {
        this.viewer = viewer;
    }
    
    public void execute() {
        int minSlice = Math.max(viewer.getImage1().getMinZ(), 
                                viewer.getImage2().getMinZ());
        viewer.setSlice(minSlice);
    }
    
}
