/**
 * FeatureColorConversion.java
 *
 * Created on 21. Februar 2002, 15:55
 */

package org.wewi.medimg.image;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class FeatureColorConversion implements ColorConversion{
    private static Color[] c = {Color.WHITE,
                                  Color.RED,
                                  Color.GREEN,
                                  Color.BLUE,
                                  Color.BLACK,
                                  Color.PINK,
                                  Color.YELLOW,
                                  Color.CYAN,
                                  Color.LIGHT_GRAY,
                                  Color.GRAY,
                                  Color.DARK_GRAY,
                                  Color.ORANGE,
                                  new Color(22, 22, 20),
                                  new Color(20, 0, 20),
                                  new Color(80, 21, 20),
                                  new Color(80, 21, 0),
                                  new Color(20, 101, 20),
                                  new Color(120, 21, 20),
                                  new Color(20, 21, 120),
                                  new Color(50, 21, 20)};
    private static int colors = c.length;
    private static int[][] cc;

    public FeatureColorConversion() {
        cc = new int[colors][3];
        for (int i = 0; i < colors; i++) {
            cc[i][0] = c[i].getRed();
            cc[i][1] = c[i].getGreen();
            cc[i][2] = c[i].getBlue();
        }
    }

    public void convert(int grey, int[] rgb) {
        rgb[0] = cc[grey%colors][0];
        rgb[1] = cc[grey%colors][1];
        rgb[2] = cc[grey%colors][2];
    }
    
    public int convert(int[] rgb) {
        for (int i = 0; i < colors; i++) {
            if (c[i].getRed() != rgb[0]) {
                continue;    
            } else {
                if (c[i].getGreen() != rgb[1]) {
                    continue;    
                } else {
                    if (c[i].getBlue() != rgb[2]) {
                        continue;    
                    } else {
                        return i;    
                    }
                }    
            }    
        }
        
        return 0;
    }
    
    public Object clone() {
    	return new FeatureColorConversion();	
    }
    
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        
        colors = stream.readInt();
        c = new Color[colors];
        int r, g, b;
        for (int i = 0; i < colors; i++) {
            r = stream.readInt();
            g = stream.readInt();
            b = stream.readInt();
            c[i] = new Color(r, g, b);
        }        
        
        cc = new int[colors][3];
        for (int i = 0; i < colors; i++) {
            cc[i][0] = c[i].getRed();
            cc[i][1] = c[i].getGreen();
            cc[i][2] = c[i].getBlue();
        }
    }
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
               
        stream.writeInt(colors);
        for (int i = 0; i < colors; i++) {
            stream.writeInt(c[i].getRed());
            stream.writeInt(c[i].getGreen());
            stream.writeInt(c[i].getBlue());
        }
               
    }   
    
}




















