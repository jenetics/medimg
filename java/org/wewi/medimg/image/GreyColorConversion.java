/* 
 * GreyColorConversion.java, created on 09.08.2002
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

    public void convert(int grey, int[] rgb) {
        rgb[0] = grey >> diffBits;
        rgb[1] = rgb[0];
        rgb[2] = rgb[0];        
    }

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
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
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
