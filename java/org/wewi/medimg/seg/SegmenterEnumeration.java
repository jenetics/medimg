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
public class SegmenterEnumeration extends Enumeration {
    public static final SegmenterEnumeration ML_CLUSTERER = 
                      new SegmenterEnumeration("Maximum-Likelihood Clusterer");
    public static final SegmenterEnumeration MAP_CLUSTERER = 
                      new SegmenterEnumeration("Maximum a posteriori Clusterer");

	public static final SegmenterEnumeration[] ENUMERATION = 
	                          {ML_CLUSTERER, MAP_CLUSTERER};
    
    private String name;
    private static int refCount = 0;  
    private SegmenterEnumeration(String name) { 
        super(++refCount);
        this.name = name;
    }
    
    public String toString() {
    	return name;	
    }
}
