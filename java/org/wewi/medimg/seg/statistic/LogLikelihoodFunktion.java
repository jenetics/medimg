/*
 * LogLikelihoodFunktion.java
 *
 * Created on 10. Mai 2002, 21:08
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.math.RealFunction;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class LogLikelihoodFunktion implements RealFunction {
    private double[] result;
    
    private VoxelIterator imageVoxelIterator;
    private MixtureModelDistribution mmDistribution;
    
    
    /** Creates a new instance of LogLikelihoodFunktion */
    public LogLikelihoodFunktion(Image image, MixtureModelDistribution mmd) {
        result = new double[1];
        
        mmDistribution = mmd;
        imageVoxelIterator = image.getVoxelIterator();
    }
    
    public LogLikelihoodFunktion(VoxelIterator vit, MixtureModelDistribution mmd) {
        result  = new double[1];
        
        mmDistribution = mmd;
        imageVoxelIterator = vit;
    }
    
    public double[] eval(double[] arg) {
        result[0] = eval(arg[0]);
        return result;
    }
    
    public double eval(double arg) {
        double result = 0;
        int color;
        for (VoxelIterator it = (VoxelIterator)imageVoxelIterator.clone(); it.hasNext();) {
            color = it.next();
            result += Math.log(mmDistribution.eval(color));
        }
        
        return result;
    }
    
}
