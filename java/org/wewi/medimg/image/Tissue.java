/**
 * Tissue.java
 *
 * Created on 26. März 2002, 16:42
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class Tissue {
    public static final Tissue VENTRICLE = new Tissue(0, "VENTRICLE");
    public static final Tissue BONE = new Tissue(1, "BONE");
    public static final Tissue FAT = new Tissue(2, "FAT");
    public static final Tissue GREY_MATTER = new Tissue(3, "GREY_MATTER");
    public static final Tissue WHITE_MATTER = new Tissue(4, "WHITE_MATTER");
    public static final Tissue SOFT_TISSUE = new Tissue(5, "SOFT_TISSUE");
    public static final Tissue ANGULAR_GYRUS = new Tissue(6, "ANGULAR_GYRUS");
    public static final Tissue DEEP_TISSUE = new Tissue(7, "DEEP_TISSUE");
    public static final Tissue UNDEFINED = new Tissue(10, "UNDEFINED");    
    
    public static final Tissue[] TISSUES = {VENTRICLE, BONE, FAT, GREY_MATTER, WHITE_MATTER, SOFT_TISSUE, ANGULAR_GYRUS, DEEP_TISSUE};
    
    private final int val;
    private final String name;
    
    private Tissue(int val, String name) { 
        this.name = name;
        this.val = val; 
    }
    
    public int intValue() {
        return val; 
    }

    public String toString() {
        return name;
    }

}
