/**
 * KDTreeTest.java
 * 
 * Created on 21.03.2003, 21:40:44
 *
 */
package org.wewi.medimg.util;


import junit.framework.TestCase;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class KDTreeTest extends TestCase {

	/**
	 * Constructor for KDTreeTest.
	 * @param arg0
	 */
	public KDTreeTest(String arg0) {
		super(arg0);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

    public void testCreate() {
        KDTree tree = new KDTree();
        
        assertEquals("Empty:", true, tree.isEmpty());
        assertEquals("TreeSize:", 0, tree.size());
    }

    public void testAdd() {
        KDTree tree = new KDTree();
        
        int dim1 = 12;
        int dim2 = 12;
        int dim3 = 12;
        int dim4 = 12;
        
        int[] point = new int[4];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                for (int k = 0; k < dim3; k++) {
                    for (int l = 0; l < dim4; l++) {
                        point[0] = i;
                        point[1] = j;
                        point[2] = k;
                        point[3] = l;
                        tree.add(new KDTree.IntPoint(point));
                    }
                }
            }
        }

        int size = tree.size();
        assertEquals("TreeSize", dim1*dim2*dim3*dim4, size);
    }
    
    public void testContains() {
        KDTree tree = new KDTree();
        
        tree.add(new KDTree.IntPoint(new int[]{3, 4, 5, 6, 8}));
        tree.add(new KDTree.IntPoint(new int[]{5, 67, 5, 6, 8}));
        tree.add(new KDTree.IntPoint(new int[]{3, 4, 43, 6, 8}));
        tree.add(new KDTree.IntPoint(new int[]{3, 4, 5, 544, 8}));
        
        assertEquals("Contains Point:", true, tree.contains(new KDTree.IntPoint(new int[]{3, 4, 5, 6, 8})));
        assertEquals("Don't contain Point:", false, tree.contains(new KDTree.IntPoint(new int[]{1, 4, 5, 6, 8})));
    }
    
    public void testRemove() {
        KDTree tree = new KDTree();
        
        int dim1 = 6;
        int dim2 = 6;
        int dim3 = 6;
        int dim4 = 6;
        
        int[] point = new int[4];
        for (int i = 0; i < dim1; i++) {
            for (int j = 0; j < dim2; j++) {
                for (int k = 0; k < dim3; k++) {
                    for (int l = 0; l < dim4; l++) {
                        point[0] = i;
                        point[1] = j;
                        point[2] = k;
                        point[3] = l;
                        tree.add(new KDTree.IntPoint(point));
                    }
                }
            }
        }

        int size = tree.size();
        assertEquals("TreeSize", dim1*dim2*dim3*dim4, size);
        
        assertEquals(true, tree.remove(new KDTree.IntPoint(new int[]{3, 3, 1, 0})));
        
        size = tree.size();
        assertEquals("TreeSize", dim1*dim2*dim3*dim4 - 1, size);
    }
}
















