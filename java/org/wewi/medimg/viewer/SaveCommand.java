/*
 * SaveCommand.java
 *
 * Created on 27. Juni 2002, 22:52
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class SaveCommand implements Command {
    private Viewer viewer;
    
    /** Creates a new instance of SaveCommand */
    public SaveCommand(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public void execute() {
    }
    
}
