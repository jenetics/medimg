/**
 * PseudoColorConversion.java
 *
 * Created on 7. August 2002, 16:38
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public class PseudoColorConversion implements ColorConversion {
    
    /** Creates a new instance of PseudoColorConversion */
    public PseudoColorConversion() {
    }
    
    private int redConversion(int grey) {
        double g = ((double)grey/128d)*Math.PI;
        return (int)Math.abs(Math.rint(255d*Math.sin(g + 2.0)));
    }
    
    private int greenConversion(int grey) {
        double g = ((double)grey/128d)*Math.PI;
        return (int)Math.abs(Math.rint(255d*Math.sin(g + 0.0)));
    }
    
    public int blueConversion(int grey) {
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
    	return new PseudoColorConversion();	
    }
    
}
