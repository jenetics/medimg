/*
 * Enumeration.java
 *
 * Created on 11. Jänner 2002, 19:38
 */

package org.wewi.medimg.util;

import java.util.Hashtable;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class Enumeration {
    protected int type;
    
    private static Hashtable refCounters = new Hashtable();

    protected Enumeration(int t) {
        type = (int)Math.pow(2, t);
    }
    
    protected Enumeration(Enumeration enum1, Enumeration enum2) throws IllegalArgumentException {
        if (enum1 == null || enum2 == null) {
            throw new IllegalArgumentException("Enumeration one or two are null");
        }
        
        if (enum1.equals(enum2)) {
            type = enum1.type;
        } else {
            type = enum1.type + enum2.type;
        }
    }
    
    public int hashCode() {
        int result = 17;
        result = 37*result + type;
        result = 37*result + super.hashCode();
        return result;
    }
    
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(this.getClass().isInstance(obj))) {
            return false;
        }
        
        return type == ((Enumeration)obj).type;
    }
}



