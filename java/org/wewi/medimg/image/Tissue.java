/*
 * Feature.java
 *
 * Created on 02. April 2002, 23:21
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class Tissue {
    public static final Tissue FAT = new Tissue("Fat", 1);
    public static final Tissue BONE = new Tissue("Bone", 2);
    
    
    private String name;
    private int code;
    
    /** Creates new Feature */
    private Tissue(String name, int code) {
        this.name = name;
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
    
    public String toString() {
        return name;
    }

}
