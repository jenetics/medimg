/**
 * MutualInformationTest.java
 * 
 * Created on 02.01.2003, 09:08:09
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.util.AccumulatorArray;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MutualInformationTest extends TestCase {

    /**
     * Constructor for MutualInformationTest.
     * @param arg0
     */
    public MutualInformationTest(String arg0) {
        super(arg0);
    }
    
    
    public void testMutualInformation() {
        AccumulatorArray accu = new AccumulatorArray(32, 21, 4);
        MutualInformation mi = new MutualInformation(accu);
        
        assertEquals(0, mi.getMutualInformation(), 0.001);

        System.out.println(mi.getMutualInformation());
            
    }
    
    public void testRandomMutualInformation() {
        final int COLS = 128;
        final int ROWS = 128;
        AccumulatorArray accu = new AccumulatorArray(ROWS, COLS);
        
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                accu.setValue(j, i, (int)(Math.random()*1024d));        
            }    
        }
        
        MutualInformation mi = new MutualInformation(accu);
        System.out.println(mi.getMutualInformation());
            
    }

}
