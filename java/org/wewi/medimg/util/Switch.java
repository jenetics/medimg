/**
 * Switch.java
 *
 * Created on 11. Jänner 2002, 20:12
 */

package org.wewi.medimg.util;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class Switch {
    private Enumeration enumeration;
    private boolean doCase = true;
    
    private Object result = null;
    
    public Switch(Enumeration enum) throws IllegalArgumentException {
        if (enum == null) {
            throw new IllegalArgumentException("Enumeration must not be null");
        }
        enumeration = enum;
    }

    public void Case(Enumeration enum, Command cmd) {
        if (!doCase) {
            return;
        }
        if (enumeration.equals(enum)) {
            cmd.execute();
            result = cmd.result;
        }
    } 
    
    public void Default(Command cmd) {
        if (!doCase) {
            return;
        }
        cmd.execute();
        result = cmd.result;
    }
    
    public void Break() {
        doCase = false;
    }
    
    public Object result() {
        return result;
    }
}
