/**
 * Created on 11.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.seg;

import org.wewi.medimg.util.Enumeration;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
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
