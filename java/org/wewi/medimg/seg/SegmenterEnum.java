/**
 * Created on 11.08.2002
 *
 */
package org.wewi.medimg.seg;

import org.wewi.medimg.util.Enumeration;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class SegmenterEnum extends Enumeration {
    public static final SegmenterEnum ML_CLUSTERER = 
                      new SegmenterEnum("Maximum-Likelihood Clusterer");
    public static final SegmenterEnum MAP_CLUSTERER = 
                      new SegmenterEnum("Maximum a posteriori Clusterer");

	public static final SegmenterEnum[] ENUMERATION = 
	                          {ML_CLUSTERER, MAP_CLUSTERER};
    
    private String name;
    private static int refCount = 0;  
    private SegmenterEnum(String name) { 
        super(++refCount);
        this.name = name;
    }
    
    public String toString() {
    	return name;	
    }
}
