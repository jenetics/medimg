/* 
 * ImageData.java, created on 21.10.2002 23:12:00
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
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
interface ImageData extends Cloneable{
    
    public byte getByteValue(int pos);
    
    public short getShortValue(int pos);
    
    public int getIntValue(int pos);
    
    public long getLongValue(int pos);
    
    public float  getFloatValue(int pos);
    
    public double getDoubleValue(int pos);
    
    public void setValue(int pos, byte value);
    
    public void setValue(int pos, short value);
    
    public void setValue(int pos, int value);
    
    public void setValue(int pos, long value);
    
    public void setValue(int pos, float value);
    
    public void setValue(int pos, double value);
    
    public void fill(byte value);
    
    public void fill(short value);
    
    public void fill(int value);
    
    public void fill(long value);
    
    public void fill(float value);
    
    public void fill(double value);
    
    public void copy(ImageData target);
    
    public Object clone();
}
