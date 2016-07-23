/* 
 * TissueColorConversion.java, created on 04. April 2002, 13:55
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


package org.wewi.medimg.image;


/**
 * @author  Werner Weiser
 * @version 0.1
 */
public class TissueColorConversion implements ColorConversion {

    public TissueColorConversion() {
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
    
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
