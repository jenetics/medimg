/* 
 * Tissue.java, created on 26. März 2002, 16:42
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * Tissue.java
 *
 * Created on 26. März 2002, 16:42
 */

package org.wewi.medimg.image;

/**
 * @author  Werner Weiser
 * @version 0.1
 */
public final class Tissue {
    public static final Tissue VENTRICLE = new Tissue(0, "VENTRICLE");
    public static final Tissue BONE = new Tissue(1, "BONE");
    public static final Tissue FAT = new Tissue(2, "FAT");
    public static final Tissue GREY_MATTER = new Tissue(3, "GREY_MATTER");
    public static final Tissue WHITE_MATTER = new Tissue(4, "WHITE_MATTER");
    public static final Tissue SOFT_TISSUE = new Tissue(5, "SOFT_TISSUE");
    public static final Tissue ANGULAR_GYRUS = new Tissue(6, "ANGULAR_GYRUS");
    public static final Tissue DEEP_TISSUE = new Tissue(7, "DEEP_TISSUE");
    public static final Tissue UNDEFINED = new Tissue(10, "UNDEFINED");    
    
    public static final Tissue[] TISSUES = {VENTRICLE, 
    	                                    BONE, 
    	                                    FAT, 
    	                                    GREY_MATTER, 
    	                                    WHITE_MATTER, 
    	                                    SOFT_TISSUE, 
    	                                    ANGULAR_GYRUS, 
    	                                    DEEP_TISSUE};
    
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
