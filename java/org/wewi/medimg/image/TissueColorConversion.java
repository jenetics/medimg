/*
 * TissueColorConversion.java
 *
 * Created on 04. April 2002, 13:55
 */

package org.wewi.medimg.image;

import org.wewi.medimg.image.Tissue;
import org.wewi.medimg.util.Singleton;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class TissueColorConversion extends ColorConversion implements Singleton {
    private static TissueColorConversion singleton = null;
    /** Creates new TissueColorConversion */
    public TissueColorConversion() {
    }
    
    public static TissueColorConversion getInstance() {
        if (singleton == null) {
            singleton = new TissueColorConversion();
        }
        return singleton; 
    }
    
    public void convert(int grey, int[] rgb) {
        if (grey == Tissue.VENTRICLE.intValue()) {
            // VENTRICLE
            rgb[0] = 0;
            rgb[1] = 0;
            rgb[2] = 0;
        }  else if  (grey == Tissue.BONE.intValue()){
            // BONE
            rgb[0] = 64;
            rgb[1] = 64;
            rgb[2] = 255;
        } else if (grey == Tissue.FAT.intValue()){
            // FAT
            rgb[0] = 64;
            rgb[1] = 255;
            rgb[2] = 64;
        } else if (grey == Tissue.GREY_MATTER.intValue()){
            // GREY_MATTER
            rgb[0] = 255;
            rgb[1] = 64;
            rgb[2] = 255;
        } else if (grey == Tissue.WHITE_MATTER.intValue()){
            // WHITE_MATTER
            rgb[0] = 255;
            rgb[1] = 128;
            rgb[2] = 128;
        } else if (grey == Tissue.SOFT_TISSUE.intValue()){
            // SOFT_TISSUE
            rgb[0] = 0;
            rgb[1] = 255;
            rgb[2] = 255;
        } else if (grey == Tissue.ANGULAR_GYRUS.intValue()){
            // ANGULAR_GYRUS
            rgb[0] = 255;
            rgb[1] = 255;
            rgb[2] = 64;
        } else if (grey == Tissue.DEEP_TISSUE.intValue()){
            // DEEP_TISSUE
            rgb[0] = 255;
            rgb[1] = 0;
            rgb[2] = 0;
        } else {
            // nothing  mentionable 
            rgb[0] = 255;
            rgb[1] = 255;
            rgb[2] = 255;
        }        
    }
    
    public int convert(int[] rgb) {
        if ( rgb[0] == 0 && rgb[1] == 0 && rgb[2] == 0 ) {
                // VENTRICLE
                return Tissue.VENTRICLE.intValue();
            }  else if (rgb[0] == 64  && rgb[1] == 64  && rgb[2] == 255){
                // BONE
                return Tissue.BONE.intValue();
            } else if (rgb[0] == 64  && rgb[1] == 255  && rgb[2] == 64){
                // FAT
                return Tissue.FAT.intValue();
            } else if (rgb[0] == 255  && rgb[1] == 64  && rgb[2] == 255){
                // GREY_MATTER
                return Tissue.GREY_MATTER.intValue();
            } else if (rgb[0] == 255  && rgb[1] == 128  && rgb[2] == 128){
                // WHITE_MATTER
                return Tissue.WHITE_MATTER.intValue();
            } else if (rgb[0] == 0  && rgb[1] == 255  && rgb[2] == 255){
                // SOFT_TISSUE
                return Tissue.SOFT_TISSUE.intValue();
            } else if (rgb[0] == 255  && rgb[1] == 255  && rgb[2] == 64){
                // ANGULAR_GYRUS
                return Tissue.ANGULAR_GYRUS.intValue();
            } else if (rgb[0] == 255  && rgb[1] == 0  && rgb[2] == 0){
                // DEEP_TISSUE
                return Tissue.DEEP_TISSUE.intValue();
            } else {
                // nothing  mentionable 
                return Tissue.UNDEFINED.intValue();
            }
    }
}
