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
    
    private static final double epsilon = 0.5;

    private static final int DIM = 3;
    
    /** Creates new MCWarpingRegStrategy */
    public MonteCarloWarping() {
        super();

    }    
    
    /*public Transformation registrate(Image source, Image target) {
        Dimension dim1 = source.getDimension();
        Dimension dim2 = target.getDimension();
        int offsetX = 13;
        int offsetY = 13;
        int strideX = 43;
        int strideY = 47;
        int strideZ = 1;
        ROI roi = ROI.create(dim1).intersect(ROI.create(dim2));
        // Durch Ver�ndern von ROI kann der Offset erzielt werden
        
        Dimension shrinked;
        ROI offsetted;
        
        
        
        IrregularDisplacementField field = new IrregularDisplacementField();
        
        ROI[] rois;
        double[] start, end;
        RandomWalk walk;
        //int m = strideX/offsetX;
        int m = 0;
        //int l = strideY/offsetY;
        int l = 0;
        for (int h = 0; h <= 0; h++) {
        for (int j = 0; j <= m; j++) {
            for (int k = 0; k <= l; k++) {
                System.out.println("Hallo" + j + " " + k);
                shrinked = new Dimension(Math.min(roi.getMinX() + j * offsetX, roi.getMaxX()), roi.getMaxX(),
                                          Math.min(roi.getMinY() + k * offsetY, roi.getMaxY()), roi.getMaxY(),
                                          roi.getMinZ(), roi.getMaxZ());
                offsetted = ROI.create(shrinked);                                      
                rois = offsetted.split(43 + h * 10, 47 + h * 10, 1);
                //createVectors(rois, source, target, field, true);
                createVectors(rois, source, target, field, false);
            }
        }
        }
      System.out.println("HAllo " + field.size());  
      return field;
    }*/
    
    private boolean createVectors(ROI[] rois, Image source, Image target, IrregularDisplacementField field, boolean dive, int color) {
        RandomWalk walk;
        double[] start, end;
        ROI[] subrois;
        boolean existence = false;
        boolean temp;
        
                for (int i = 0, n = rois.length; i < n; i++) {
                    walk = new RandomWalk(rois[i], source, 1500);
                    walk.walk(color);
                    if ((start = walk.getReferencePoint()) != null) {
                        walk = new RandomWalk(rois[i], target, 1500);
                        walk.walk(color);
                        if ((end = walk.getReferencePoint()) != null) {
                            
                            
                            //Versuch
                            
                            if (dive) {
                                try {
                                subrois = ((ROI)rois[i]).split(21, 23, 1);
                                temp = createVectors(subrois, source, target, field, false, color);
                                if (!temp) {
                                    field.addVector(start, end);
                                }
                                } catch(Exception e) {
                                    System.out.println(e);
                                }
                            } else {
                                field.addVector(start, end);
                            }
                            existence = true;                            
                            
                            
                            
                            //end Versuch
                        }
                    }
                }
                return existence;    
    }
    
    
    protected InterpolateableTransformation getTransformation(VoxelIterator source, VoxelIterator target) {//throws RegistrationException {
        Image sourceI = ((FeatureIterator)source).getImage();
        Image targetI = ((FeatureIterator)target).getImage();
        int color = ((FeatureIterator)source).getFeatureType();
        Dimension dim1 = sourceI.getDimension();
        Dimension dim2 = targetI.getDimension();
        int offsetX = 13;
        int offsetY = 13;
        int strideX = 43;
        int strideY = 47;
        int strideZ = 1;

        ROI roi = ROI.create(dim1).intersect(ROI.create(dim2));
        // Durch Ver�ndern von ROI kann der Offset erzielt werden
        
        Dimension shrinked;
        ROI offsetted;
        
        
        
        IrregularDisplacementField field = new IrregularDisplacementField();
        
        ROI[] rois;
        double[] start, end;
        int m = 0;
        //m = strideX/offsetX;
        int l = 0;
        //l = strideY/offsetY;
        int split = 0;
        
        for (int h = 0; h <= split; h++) {
        for (int j = 0; j <= m; j++) {
            for (int k = 0; k <= l; k++) {
                System.out.println("Hallo" + j + " " + k);
                shrinked = new Dimension(Math.min(roi.getMinX() + j * offsetX, roi.getMaxX()), roi.getMaxX(),
                                          Math.min(roi.getMinY() + k * offsetY, roi.getMaxY()), roi.getMaxY(),
                                          roi.getMinZ(), roi.getMaxZ());
                offsetted = ROI.create(shrinked);                                      
                rois = offsetted.split(43 + h * 10, 47 + h * 10, 1);
                createVectors(rois, sourceI, targetI, field, true, color);
                //createVectors(rois, sourceI, targetI, field, false, color);
                /*for (int i = 0, n = rois.length; i < n; i++) {
                    walk = new RandomWalk(rois[i], source, 1500);
                    walk.walk();
                    if ((start = walk.getReferencePoint()) != null) {
                        walk = new RandomWalk(rois[i], target, 1500);
                        walk.walk();
                        if ((end = walk.getReferencePoint()) != null) {
                            field.addVector(start, end);
                            //Versuch
                            
                            
                            
                            
                            //end Versuch
                        }
                    }
                }*/
            }
        }
        }
      System.out.println("HAllo " + field.size());  
      field.clean(epsilon);
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
    
    public void walk(int color) {
        double[] point = new double[3];
        double[] rPoint = new double[3];
        Arrays.fill(rPoint,0);
        int count = 0;
        
        for (int i = 0; i < steps; i++) {
            step(point);
            if (isObjectBorder(point, color)) {
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
    
    private boolean isObjectBorder(double[] point, int color) {
        int x = (int)Math.round(point[0]);
        int y = (int)Math.round(point[1]);
        int z = (int)Math.round(point[2]);
        
        int[] neighbours = new int[18];
        image.getNeighbor3D18Positions(image.getPosition(x, y, z), neighbours);
        for (int i = 0; i < neighbours.length; i++) {

            if (neighbours[i] < 0 || neighbours[i] >= image.getNVoxels()) {
                continue;
            }
            
            if ((image.getColor(x , y, z) == color && color != image.getColor(neighbours[i]))|| 
                 (image.getColor(x , y, z) != color && color == image.getColor(neighbours[i]))) {
                return true;
            }
            
        }
        
        return false;
    }    

}











