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
                                                         
    private String name;

	/**
	 * Constructor for ColorConversionEnumeration.
	 * @param t
	 */
	private ColorConversionEnum(int t, String name) {
		super(t);
        this.name = name;
	}
    
    public String toString() {
        return name;    
    }

}
