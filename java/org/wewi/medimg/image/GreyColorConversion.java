/**
 * Created on 09.08.2002
 *
 */
package org.wewi.medimg.image;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class GreyColorConversion implements ColorConversion {
    private int bits = 8;
    private int diffBits = 0;
	
	public GreyColorConversion() {
	}
    
    public GreyColorConversion(int bits) {
        this.bits = bits;
        diffBits = bits - 8;
    }

	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int, int[])
	 */
	public void convert(int grey, int[] rgb) {
        rgb[0] = grey >> diffBits;
        rgb[1] = rgb[0];
        rgb[2] = rgb[0];		
	}

	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int[])
	 */
	public int convert(int[] rgb) {
        //return (rgb[0]+rgb[1]+rgb[2])/3;
        return rgb[0] << diffBits;
	}
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName()).append(":\n");
        buffer.append("Bits: ").append(bits).append("\n");
        buffer.append("DiffBits: ").append(diffBits);
        
        return buffer.toString();
    }

	public Object clone() {
		return new GreyColorConversion(bits);	
	}
    
    //Serialization part
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        
        bits = stream.readInt();
        diffBits = stream.readInt();
    }
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
               
        stream.writeInt(bits);
        stream.writeInt(diffBits);       
    }    
}
