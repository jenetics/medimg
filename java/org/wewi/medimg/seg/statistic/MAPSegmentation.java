/*
 * MAPSegmentation.java
 *
 * Created on 26. Jänner 2002, 11:53
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Neighborhood;
import org.wewi.medimg.image.geom.Neighborhood3D6;
import org.wewi.medimg.image.geom.Neighborhood3D12;
import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.image.geom.Point3D;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class MAPSegmentation extends MLSegmentation {
    private static int M1_ITERATIONS;
    private static double BETA = 0.35;
    private static double BETA_SQRT2 = Math.sqrt(BETA);
    
    private Neighborhood n6;
    private Neighborhood n12;
    private int m1Iteration;

    
    public MAPSegmentation(Image image, int nf, int m1It) {
        super(image, nf);
        M1_ITERATIONS = m1It;
        n6 = new Neighborhood3D6(image.getMinX(), image.getMinY(), image.getMinZ(),
                                 image.getMaxX(), image.getMaxY(), image.getMaxZ());
        n12 = new Neighborhood3D12(image.getMinX(), image.getMinY(), image.getMinZ(),
                                   image.getMaxX(), image.getMaxY(), image.getMaxZ());
    }


    protected boolean isM1Ready() {
        return !(++m1Iteration <= M1_ITERATIONS);
    }

    protected double neighbourhoodWeight(int x, int y, int z, int f) {    
        double V = 0.0;
        Point3D p = new Point3D(x, y, z);
        Point3D p3d;
        for (Iterator it = n6.getNeighbors(p); it.hasNext();) {
            p3d = (Point3D)it.next();
            if (featureData.getOldFeature(p3d.getX(), p3d.getY(), p3d.getZ()) != f) {
                V += BETA;
            }
        }
        for (Iterator it = n12.getNeighbors(p); it.hasNext();) {
            p3d = (Point3D)it.next();
            if (featureData.getOldFeature(p3d.getX(), p3d.getY(), p3d.getZ()) != f) {
                V += BETA_SQRT2;
            }
        }        

        return V;
    }

    protected void initM1Iteration() {
        m1Iteration = 0;
    }

}
