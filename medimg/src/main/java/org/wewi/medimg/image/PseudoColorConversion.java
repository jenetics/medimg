/* 
 * PseudoColorConversion.java, created on 7. August 2002, 16:38
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
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class PseudoColorConversion implements ColorConversion {
    
    public PseudoColorConversion() {
    }
    
    protected int redConversion(int grey) {
        double g = ((double)grey/128d)*Math.PI;
        return (int)Math.abs(Math.rint(255d*Math.sin(g + 2.0)));
    }
    
    protected int greenConversion(int grey) {
        double g = ((double)grey/128d)*Math.PI;
        return (int)Math.abs(Math.rint(255d*Math.sin(g + 0.0)));
    }
    
    protected int blueConversion(int grey) {
        double g = ((double)grey/128d)*Math.PI;
        return (int)Math.abs(Math.rint(255d*Math.sin(g + 1.0)));
    }
    
    public int convert(int[] rgb) {
        return (rgb[0]+rgb[1]+rgb[2])/3;
    }
    
    public void convert(int grey, int[] rgb) {
        rgb[0] = redConversion(grey);
        rgb[1] = greenConversion(grey);
        rgb[2] = blueConversion(grey);
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
