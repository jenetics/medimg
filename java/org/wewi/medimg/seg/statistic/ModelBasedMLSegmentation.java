/*
 * MLSegmentationModel.java
 *
 * Created on 8. Mai 2002, 15:00
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.math.GaussianDistribution;
import org.wewi.medimg.math.geom.VoronoiDiagram1D;
import org.wewi.medimg.seg.ModelBasedSegmentation;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class ModelBasedMLSegmentation implements ModelBasedSegmentation {
    private final VoronoiDiagram1D vd;
    
    /** Creates a new instance of MLSegmentationModel */
    public ModelBasedMLSegmentation(GaussianDistribution[] fd) {
        double[] mean = new double[fd.length];
        for (int i = 0; i < fd.length; i++) {
            mean[i] = fd[i].getMeanValue();
        }
        vd = new VoronoiDiagram1D(mean);
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
    
    public double quality() {
        return 0;
    }
    
}
