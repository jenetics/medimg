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
    
    public static abstract class ParallelTask extends Task {
        public abstract Object clone();
    }    
    
    private class LoopThread implements Runnable {
        
        public LoopThread(Image image, Task task) {
        }

		public void run() {
		}
    }
    
    
    private int threads = 2;
    
    public  ParallelImageLoop(Image image, ParallelTask task) {
        super(image, task);
    }
    
    public ParallelImageLoop(Image image, ParallelTask task, int threads) {
        super(image, task);
        this.threads = threads;
    }
    
    private ROI[] split(ROI roi, int parts) {
        return new ROI[]{roi};
    }
    
    public void loop(ROI roi, int strideX, int strideY, int strideZ) {
        for (int k = roi.getMinZ(), u = roi.getMaxZ(); k <= u; k += strideZ) {
            for (int j = roi.getMinY(), m = roi.getMaxY(); j <= m; j += strideY) {
                for (int i = roi.getMinX(), n = roi.getMaxX(); i <= n; i += strideX) {
                    task.execute(i, j, k);
                }
            }
        }        
    } 
       

}









