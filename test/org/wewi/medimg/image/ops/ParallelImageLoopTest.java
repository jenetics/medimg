/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * ParallelImageLoopTest.java
 * 
 * Created on 18.02.2003, 15:27:25
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ByteImage;
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
        Image image = new ByteImage(15, 25, 15);
        
        ParallelImageLoop loop = new ParallelImageLoop(image, new TestTaskFactory(), 4);  
        loop.loop();
        
        for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
            assertEquals(37, it.next());
        }
    }

}





