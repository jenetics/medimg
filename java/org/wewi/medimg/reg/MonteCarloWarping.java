/**
 * MCWarpingRegStrategy.java
 *
 * Created on 16. Mai 2002, 11:34
 */

package org.wewi.medimg.reg;

import java.util.Arrays;

import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ROI;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.InterpolateableTransformation;
import org.wewi.medimg.image.geom.transform.IrregularDisplacementField;
import org.wewi.medimg.image.geom.transform.Transformation;

import cern.jet.random.engine.MersenneTwister64;
import cern.jet.random.engine.RandomEngine;
/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class MonteCarloWarping extends MultipleFeatureRegistrator {
    
    //private InterpolStrategy weightStrategy;
    
    public int act = 0;
    public int act1 = 0;
    public int act2 = 0;    
    private AffinityMetric affinityMetric;
    
    private ImageGrid sourceGrid;
    private ImageGrid targetGrid;    
    
    //private RegisterParameter param;
    
    private static final double epsilon = 0.05;

    private static final int DIM = 3;
    
    /** Creates new MCWarpingRegStrategy */
    public MonteCarloWarping() {
        super();

    }    
    
    public Transformation registrate(Image source, Image target) {
        Dimension dim1 = source.getDimension();
        Dimension dim2 = target.getDimension();
        ROI roi = ROI.create(dim1).intersect(ROI.create(dim2));
        // Durch Verändern von ROI kann der Offset erzielt werden
        
        IrregularDisplacementField field = new IrregularDisplacementField();
        
        ROI[] rois = roi.split(43, 47, 1);
        double[] start, end;
        RandomWalk walk;
        
        for (int i = 0, n = rois.length; i < n; i++) {
            walk = new RandomWalk(rois[i], source, 1500);
            walk.walk();
            if ((start = walk.getReferencePoint()) != null) {
                walk = new RandomWalk(rois[i], target, 1500);
                walk.walk();
                if ((end = walk.getReferencePoint()) != null) {
                    field.addVector(start, end);
                }
            }
        }
        
      return field;
    }
    
    
    protected InterpolateableTransformation getTransformation(VoxelIterator source, VoxelIterator target) {//throws RegistrationException {
        Image sourceI = null;
        Image targetI = null;
        Dimension dim1 = ((FeatureIterator)source).getDimension();
        Dimension dim2 = ((FeatureIterator)target).getDimension();
        ROI roi = ROI.create(dim1).intersect(ROI.create(dim2));
        // Durch Verändern von ROI kann der Offset erzielt werden
        
        IrregularDisplacementField field = new IrregularDisplacementField();
        
        ROI[] rois = roi.split(25, 25, 8);
        double[] start, end;
        RandomWalk walk;
        
        for (int i = 0, n = rois.length; i < n; i++) {
            walk = new RandomWalk(rois[i], sourceI, 500);
            walk.walk();
            if ((start = walk.getReferencePoint()) != null) {
                walk = new RandomWalk(rois[i], targetI, 500);
                walk.walk();
                if ((end = walk.getReferencePoint()) != null) {
                    field.addVector(start, end);
                }
            }
        }
        
      return field;
    }
    
    public String toString() {
        return "MC-Warping";
    
    }
    
    public String getRegistratorName() {
        return "MC-Warping";
    
    }    
}

class RandomWalk {
    
    private ROI roi;
    private Image image;
    private double[] referencePoint = null;
    private int steps;
    
    private RandomEngine engine;
    
    public RandomWalk(ROI roi, Image image, int steps) {
        this.roi = roi;
        this.image = image;
        this.steps = steps;
        
        engine = new MersenneTwister64();
    }
    
    public void walk() {
        double[] point = new double[3];
        double[] rPoint = new double[3];
        Arrays.fill(rPoint,0);
        int count = 0;
        
        for (int i = 0; i < steps; i++) {
            step(point);
            if (isObjectBorder(point)) {
                count++;
                rPoint[0] += point[0]; 
                rPoint[1] += point[1]; 
                rPoint[2] += point[2]; 
            }
        }
        
        if (count > 0) {
            rPoint[0] /= (double)count;
            rPoint[1] /= (double)count;
            rPoint[2] /= (double)count;
            referencePoint = rPoint;
        }
    }
    
    public double[] getReferencePoint() {
        return referencePoint;
    }
    
    private void step(double[] point) {
        point[0] = engine.nextDouble() * (roi.getSizeX() - 1) + roi.getMinX();
        point[1] = engine.nextDouble() * (roi.getSizeY() - 1) + roi.getMinY();
        point[2] = engine.nextDouble() * (roi.getSizeZ() - 1) + roi.getMinZ();
    }
    
    private boolean isObjectBorder(double[] point) {
        int x = (int)Math.round(point[0]);
        int y = (int)Math.round(point[1]);
        int z = (int)Math.round(point[2]);
        
        int[] neighbours = new int[18];
        image.getNeighbor3D18Positions(image.getPosition(x, y, z), neighbours);
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i] < 0 || neighbours[i] >= image.getNVoxels()) {
                continue;
            }
            
            if (image.getColor(x , y, z) != image.getColor(neighbours[i])) {
                return true;
            }
            
        }
        
        return false;
    }

}











