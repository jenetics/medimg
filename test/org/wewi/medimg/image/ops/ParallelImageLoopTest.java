/**
 * ParallelImageLoopTest.java
 * 
 * Created on 18.02.2003, 15:27:25
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ByteImageData;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.ops.ImageLoop.Task;

import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ParallelImageLoopTest extends TestCase {

	/**
	 * Constructor for ParallelImageLoopTest.
	 * @param arg0
	 */
	public ParallelImageLoopTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
    
    private static class TestTask extends ImageLoop.Task {
        private int id = 0;
        
        public TestTask(int id) {
            this.id = id;
        }
        
		public void execute(int x, int y, int z) {
            getImage().setColor(x, y, z, 37);
            System.out.print(id);
		} 
    }
    public static class TestTaskFactory implements ParallelImageLoop.TaskFactory {
        private static int id = 0;
		public Task create() {
			return new TestTask(id++);
		}
    }
    
    public void testLoop() {
        Image image = new ByteImageData(15, 25, 15);
        
        ParallelImageLoop loop = new ParallelImageLoop(image, new TestTaskFactory(), 4);  
        loop.loop();
        
        for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
            assertEquals(37, it.next());
        }
    }

}





