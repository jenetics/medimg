/*
 * NextCommad.java
 *
 * Created on 6. April 2002, 21:10
 */

package org.wewi.medimg.seg.wizard;

import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class NextCommad implements Command {
    private TwinImageViewer viewer;
    
    /** Creates a new instance of NextCommad */
    public NextCommad(TwinImageViewer viewer) {
        this.viewer = viewer;
    }
    
    public void execute() {
        int slice = viewer.getSlice();
        int maxSlize = Math.min(viewer.getImage1().getMaxZ(),
                                viewer.getImage2().getMaxZ());
        if (maxSlize >= slice+1) {
            viewer.setSlice(slice+1);
        }        
    }
    
}
