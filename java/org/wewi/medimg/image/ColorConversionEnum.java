package org.wewi.medimg.image;

import org.wewi.medimg.util.Enumeration;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ColorConversionEnum extends Enumeration {
    public final static ColorConversionEnum GREY_COLOR_CONVERSION = new ColorConversionEnum(1);
    public final static ColorConversionEnum RGB_COLOR_CONVERSION = new ColorConversionEnum(2);
    
    public final static ColorConversionEnum[] TYPES = {GREY_COLOR_CONVERSION,
                                                         RGB_COLOR_CONVERSION};

	/**
	 * Constructor for ColorConversionEnumeration.
	 * @param t
	 */
	private ColorConversionEnum(int t) {
		super(t);
	}

}
