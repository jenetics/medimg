/*
 * SegmentationStrategyThread.java
 *
 * Created on 7. April 2002, 13:17
 */

package org.wewi.medimg.seg;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class SegmentationStrategyThread extends Thread {
    private SegmentationStrategy strategy;
    
    /** Creates a new instance of SegmentationStrategyThread */
    public SegmentationStrategyThread(SegmentationStrategy strategy) {
        this.strategy = strategy;
    }
    
    public SegmentationStrategy getSegmentationStrategy() {
        return strategy;
    }
    
    public void run() {
        strategy.doSegmentation();
    }
    
}
