/**
 * ColorConversion.java
 *
 * Created on 17. J�nner 2002, 19:24
 */

package org.wewi.medimg.image;

import java.io.Serializable;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public interface ColorConversion extends Cloneable, Serializable {
    
    public void convert(int color, int[] pixelComponents);

    public int convert(int[] pixelComponents);
    
    public Object clone();
}
