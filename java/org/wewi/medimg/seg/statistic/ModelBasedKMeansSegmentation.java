/*
 * ModelBasedKMeansSegmentation.java
 *
 * Created on 10. Mai 2002, 14:15
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.seg.ModelBasedSegmentation;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;

import org.wewi.medimg.math.geom.VoronoiDiagram1D;

import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ModelBasedKMeansSegmentation implements ModelBasedSegmentation {
    private final VoronoiDiagram1D vd;
    
    /** Creates a new instance of ModelBasedKMeansSegmentation */
    public ModelBasedKMeansSegmentation(double[] center) {
        vd = new VoronoiDiagram1D(center);
    }
        
    
    public void segmentate(Image source, Image target) {
        int size = source.getNVoxels();
        for (int i = 0; i < size; i++) {
            target.setColor(i, vd.getVoronoiCellNo(source.getColor(i)));
        }
    }
    
    public void segmentate(Image sourceTarget) {
        int size = sourceTarget.getNVoxels();
        for (int i = 0; i < size; i++) {
            sourceTarget.setColor(i, vd.getVoronoiCellNo(sourceTarget.getColor(i)));
        }
    }
    
}
