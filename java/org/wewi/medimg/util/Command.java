/**
 * Command.java
 *
 * Created on 11. J�nner 2002, 20:14
 */

package org.wewi.medimg.util;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public abstract class Command {
    protected Object argument = null;
    protected Object result = null;
    
    public Command() {
    }
    
    public Command(Object argument) {
        this.argument = argument;    
    }
    
    public abstract void execute();
    
    public final void Break(Switch s) {
        s.Break();
    }
}

