/*
 * ColorSubstitution.java
 *
 * Created on 17. J�nner 2002, 19:24
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.2
 */
public interface ColorConversion extends Cloneable {
    public void convert(int grey, int[] rgb);

    public int convert(int[] rgb);
    
    public Object clone();
}
