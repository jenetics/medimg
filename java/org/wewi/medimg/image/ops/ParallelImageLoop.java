/**
 * ParallelImageLoop.java
 * 
 * Created on 17.02.2003, 18:45:52
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ParallelImageLoop extends ImageLoop {
    
    /**
     * Factory for the <code>ImageLoop.Task</code> class.
     * 
     * @author Franz Wilhelmstötter
     * @version 0.1
     */
    public static interface TaskFactory {
        
        /**
         * Factory method.
         * 
         * @return Task
         */
        public Task create();
    }    
    
    private final class LoopThread implements Runnable {
        private ImageLoop loop;
        private ROI roi;
        private int strideX;
        private int strideY;
        private int strideZ;
        
        public LoopThread(Image image, Task task, ROI roi, int strideX, int strideY, int strideZ) {
            loop = new ImageLoop(image, task);
            this.roi = roi;
            this.strideX = strideX;
            this.strideY = strideY;
            this.strideZ = strideZ;
        }

        public void run() {
            loop.loop(roi, strideX, strideY, strideZ);
            ready();
        }
    }
    
    
    private TaskFactory factory;
    private int threads = 2;
    
    /**
     * 
     * @param image
     * @param factory
     */
    public  ParallelImageLoop(Image image, TaskFactory factory) {
        this.image = image; 
        this.factory = factory;   
    }
    
    /**
     * 
     * @param image
     * @param factory
     * @param threads
     */
    public ParallelImageLoop(Image image, TaskFactory factory, int threads) {
        this(image, factory);
        this.threads = threads;
    }
    
    private int threadsReady = 0;
    
    private synchronized void ready() { 
        threadsReady++; 
        notifyAll();
    }    
    
    /**
     * Overides the method in the <code>ImageLoop</code> class by a parallel
     * one.
     * 
     * @see org.wewi.medimg.image.ops.ImageLoop#loop(ROI, int, int, int)
     */
    public synchronized void loop(ROI roi, int strideX, int strideY, int strideZ) {
        threadsReady = 0;
        
        Thread[] t = new Thread[threads];
        ROI[] rois = roi.split(threads);

        for (int i = 0; i < threads; i++) { 
            t[i] = new Thread(
                   new LoopThread(image, factory.create(), rois[i], 
                                   strideX, strideY, strideZ));      
        }
        
        for (int i = 0; i < threads; i++) {
            t[i].start();
        }
        
        //Waiting, until all Loop Threads are ready.
        while (threadsReady < threads) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }
    }     

}









