/**
 * ROITest.java
 * 
 * Created on 18.02.2003, 15:31:49
 *
 */
package org.wewi.medimg.image;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ROITest extends TestCase {

	/**
	 * Constructor for ROITest.
	 * @param arg0
	 */
	public ROITest(String arg0) {
		super(arg0);
	}
    
    public void testSplitRatio() {
        ROI roi = new ROI(151, 130, 100);
        ROI[] splits = new ROI[2];
        
        roi.split(Math.random(), splits);
        //roi.split(1.0, splits);
        
        assertEquals(roi.size(), splits[0].size() + splits[1].size());
        
        //System.out.println(splits[0]);
        //System.out.println();
        //System.out.println(splits[1]);                
    }
    
    public void testSplitN() {
        ROI roi = new ROI(233, 232, 23);
        ROI[] splits = new ROI[7];
        
        roi.split(splits.length, splits);
        
        int size = 0;
        for (int i = 0; i < splits.length; i++) {
            size += splits[i].size();
        }
        
        assertEquals(roi.size(), size);        
    }

}
