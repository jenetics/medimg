/* 
 * ColorConversionEnum.java
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

import org.wewi.medimg.util.Enumeration;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ColorConversionEnum extends Enumeration {
    public final static ColorConversionEnum GREY_CC = 
                                     new ColorConversionEnum(1, "Grey-ColorConversion");
    public final static ColorConversionEnum RGB_CC = 
                                     new ColorConversionEnum(2, "RGB-ColorConversion");
    public final static ColorConversionEnum PSEUDO_CC = 
                                     new ColorConversionEnum(3, "Pseudo-ColorConversion");
    public final static ColorConversionEnum RGBA_CC = 
                                     new ColorConversionEnum(4, "RGBA-ColorConversion");
    
    public final static ColorConversionEnum[] TYPES = {GREY_CC,
                                                         RGB_CC,
                                                         RGBA_CC,
                                                         PSEUDO_CC};
                                                         
    private final String name;


    private ColorConversionEnum(int t, String name) {
        super(t);
        this.name = name;
    }
    
    public String toString() {
        return name;    
    }

}
