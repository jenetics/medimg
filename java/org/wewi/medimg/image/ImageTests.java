/**
 * Created on 12.08.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.image;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.swingui.TestRunner;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ImageTests extends TestCase {
    
    /**
     * Constructor for ImageTests.
     * @param arg0
     */
    public ImageTests(String arg0) {
        super(arg0);
    }
    
    public void imageReadWrite() {
        int minX = 23;
        int minY = 23;
        int minZ = 24;
        int maxX = 100;
        int maxY = 100;
        int maxZ = 100;
        Image image = new ImageData(minX, minY, minZ, maxX, maxX, maxZ);
        
        //Schnreiben der Farben
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                for (int k = minZ; k <= maxZ; k++) {
                    image.setColor(i, j, k, i*j+k);
                }
            }
        }
        
        //Lesen
        int[] point = new int[3];
        int size = image.getNVoxels();
        int color = 0;
        for (int i = 0; i < size; i++) {
            image.getCoordinates(i, point);
            color = image.getColor(point[0], point[1], point[2]);
            assertEquals(color, point[0]*point[1]+point[2]);
        }
    }
    
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(new ImageTests("imageReadWrite"));
        return suite;
    }
    
    
    public static void main(String[] args) {
        TestRunner.run(ImageTests.class);
    }
    
}
