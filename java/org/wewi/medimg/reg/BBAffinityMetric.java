/*
 * BBAffinityMetric.java
 *
 * Created on 26. März 2002, 15:14
 */

package org.wewi.medimg.reg;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.image.ops.ColorRangeOperator;
import org.wewi.medimg.image.ops.UnaryPointAnalyzer;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class BBAffinityMetric implements AffinityMetric {
    
    private Transformation trans;

    public static final BBAffinityMetric INSTANCE = new BBAffinityMetric();
    
    
        /** Creates new BBAffinityMetric */
    public BBAffinityMetric() {
            trans = null;
    }


    public double similarity(Image source, Image target, Transformation trans) {
        
        VoxelIteratorFactory f = new VoxelIteratorFactory(source, target);
        
        ColorRangeOperator op = new ColorRangeOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(source, op);
        analyzer.analyze();
        ColorRange scr = new ColorRange(op.getMinimum(), op.getMaximum());
        
        op = new ColorRangeOperator();
        analyzer = new UnaryPointAnalyzer(target, op);
        analyzer.analyze();
        ColorRange tcr = new ColorRange(op.getMinimum(), op.getMaximum());
        
        
        int minFeature = Math.max(scr.getMinColor(), tcr.getMinColor());
        //Ignorieren des Hintergrundes
        if (minFeature < 1) {
        	minFeature = 1;
        }
        int maxFeature = Math.min(scr.getMaxColor(), tcr.getMaxColor());
        
        double similarity = 1.0;
        double temp = 1.0;
        for (int j = minFeature; j <= maxFeature; j++) {
        	if (f.hasJointVoxelIterator(j)) {
	            VoxelIterator sittemp = f.getSourceVoxelIterator(j);
	            VoxelIterator tittemp = f.getTargetVoxelIterator(j);
				if (sittemp.hasNext()) {
				    if (tittemp.hasNext()) {
				        temp = getFitness((FeatureIterator)sittemp,
                                                           (FeatureIterator)tittemp, 
                                                            trans); 
				    }
				}	            
		        
                if (temp < similarity) {
                	similarity = temp;
                }                                            
        	}  
       }
	   return similarity;
    }

    private double getFitness(FeatureIterator source, FeatureIterator target, Transformation transformation) {
            BoundingBox sourceBB;
            BoundingBox targetBB;
            sourceBB = getBoundingBox(source);
            targetBB = getBoundingBox(target);
            BoundingBox tempSourceBB = new BoundingBox(sourceBB);
            if (transformation != null) {
            	tempSourceBB.transform(transformation); 
            }
            double refVolume = 0.0;
            double volume = 0.0;
            refVolume = targetBB.getVolume();
            volume = targetBB.getIntersectingVolume(tempSourceBB);
            System.out.println("***RefVolume***" + refVolume); 
            System.out.println("***Volume***" + volume); 
            System.out.println("***ergebnis***" + Math.abs(volume/refVolume)); 

            // für Einzelabbildungen
            //return 1.0;
            return Math.abs(volume/refVolume);
    }
    
    private BoundingBox getBoundingBox(FeatureIterator it) {

        // Dimensionen ermitteln
        double[] min = new double[3];
        double[] max = new double[3];
        int i, j;
        for (i = 0; i < 3; i++) {
                min[i] = Double.MAX_VALUE;
                max[i] = Double.MIN_VALUE;
        }
        double[] tempPoint = new double[3];
        it.goToFirst();
        while(it.hasNext()) {
            it.next(tempPoint);
            for (j = 0; j < 3; j++) {
                if (tempPoint[j] < min[j]) {
                    min[j] = tempPoint[j];
                }
                if (tempPoint[j] > max[j]) {
                    max[j] = tempPoint[j];
                }
            }             
        }
        it.goToFirst();
        // für einzelne Slices
        for (i = 0; i < 3; i++) {
                if (Math.abs(min[i] - max[i]) < 0.001) {
                        max[i] = min[i] + 1;
                }
        }
        double[][] bb = new double[8][3];
        //Boden
        bb[0][0] = min[0];
        bb[0][1] = min[1];
        bb[0][2] = min[2];
        bb[1][0] = min[0];
        bb[1][1] = max[1];
        bb[1][2] = min[2];
        bb[2][0] = max[0];
        bb[2][1] = max[1];
        bb[2][2] = min[2];
        bb[3][0] = max[0];
        bb[3][1] = min[1];
        bb[3][2] = min[2];

        bb[4][0] = min[0];
        bb[4][1] = min[1];
        bb[4][2] = max[2];
        bb[5][0] = min[0];
        bb[5][1] = max[1];
        bb[5][2] = max[2];
        bb[6][0] = max[0];
        bb[6][1] = max[1];
        bb[6][2] = max[2];
        bb[7][0] = max[0];
        bb[7][1] = min[1];
        bb[7][2] = max[2];

        BoundingBox boundingBox = new BoundingBox(bb);
        return boundingBox;
    }

}
