/*
 * SaveAsCommand.java
 *
 * Created on 27. Juni 2002, 22:53
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class SaveAsCommand implements Command {
    private Viewer viewer;
    
    /** Creates a new instance of SaveAsCommand */
    public SaveAsCommand(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public void execute() {
    }
    
}
