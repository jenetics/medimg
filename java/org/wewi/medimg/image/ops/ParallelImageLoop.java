/**
 * ParallelImageLoop.java
 * 
 * Created on 17.02.2003, 18:45:52
 *
 */
package org.wewi.medimg.image.ops;

import java.util.Comparator;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;
import org.wewi.medimg.util.Sort;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ParallelImageLoop extends ImageLoop {
    
    public static interface TaskFactory {
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
            loop(roi, strideX, strideY, strideZ);
            ready();
		}
    }
    
    
    private TaskFactory factory;
    private int threads = 2;
    private int threadsReady = 0;
    
    public  ParallelImageLoop(Image image, TaskFactory factory) {
        this.image = image;    
    }
    
    public ParallelImageLoop(Image image, TaskFactory factory, int threads) {
        this(image, factory);
        this.threads = threads;
    }
    
    private ROI[] split(ROI roi, int parts) {
        ROI[] result = new ROI[parts];
        
        ROI[] temp = new ROI[2];
        temp[1] = roi;
        for (int i = parts; i > 1; i--) {
            temp = splitr(temp[1], i);
            result[parts - 1] = temp[0];
        }
        result[0] = temp[1];
        
        return result;
    }
    
    private ROI[] splitr(ROI roi, int parts) {
        ROI[] result = new ROI[2];
        
        int[] pivot = new int[3];
        int[] size = new int[3];
        for (int i = 0; i < 3; i++) {
            size[i] = roi.getSize(i);
        }
        Sort.sort(size, pivot);
        
        int part = roi.size()/(parts*roi.getSize(pivot[0])*roi.getSize(pivot[1]));
        
        int[] min = new int[3];
        int[] max = new int[3];
        
        min[pivot[0]] = roi.getMin(pivot[0]);
        max[pivot[0]] = roi.getMax(pivot[0]);
        min[pivot[1]] = roi.getMin(pivot[1]);
        max[pivot[1]] = roi.getMax(pivot[1]);
        min[pivot[2]] = roi.getMin(pivot[2]);
        max[pivot[2]] = roi.getMin(pivot[2]) + part - 1;          
        result[0] = new ROI(min[0], max[0], min[1], max[1], min[2], max[2]);
        
        min[pivot[2]] = roi.getMin(pivot[2]) + part;
        max[pivot[2]] = roi.getMax(pivot[2]);          
        result[1] = new ROI(min[0], max[0], min[1], max[1], min[2], max[2]);        

        return result;
    }
    
    
    private class ObjectComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			return 0;
		}
    }
    
    
    public synchronized void loop(ROI roi, int strideX, int strideY, int strideZ) {
        threadsReady = 0;
        
        Thread[] t = new Thread[threads];
        ROI[] rois = split(roi, threads);
        for (int i = 0; i < threads; i++) { 
            t[i] = new Thread(new LoopThread(image, factory.create(), 
                              rois[i], strideX, strideY, strideZ));      
        }
        
        for (int i = 0; i < threads; i++) {
            t[i].start();
        }
        
        while (threadsReady < threads) {
            try {
				wait();
			} catch (InterruptedException e) {
                System.err.println(e);
			}
        }
    } 
    
    private synchronized void ready() {
        threadsReady++;
    }
       

}









