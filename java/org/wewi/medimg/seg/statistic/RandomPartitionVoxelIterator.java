/*
 * VoxelIterator.java
 *
 * Created on 8. Mai 2002, 15:30
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class RandomPartitionVoxelIterator implements VoxelIterator, Cloneable {
    private Image image;
    private double fraction;
    private int seed;
    
    private int counter;
    private int nvoxel;
    private int imageSize;
    
    private RandomEngine random;
    
    /** Creates a new instance of VoxelIterator */
    public RandomPartitionVoxelIterator(Image image, double fraction, int seed) throws IllegalArgumentException {
        if (fraction <= 0 && fraction > 1) {
            throw new IllegalArgumentException("Fraction not between (0, 1]");
        }
        this.image = image;
        this.fraction = fraction;
        this.seed = seed;
        
        counter = 0;
        imageSize = image.getNVoxels();
        nvoxel = (int)((double)imageSize*fraction);
        
        random = new MersenneTwister(seed);
    }
    
    public boolean hasNext() {
        return counter < nvoxel;
    }
    
    public int next() {
        ++counter;
        int pos = (int)Math.round(random.nextFloat()*(imageSize-1));
        return image.getColor(pos);
        //return image.getColor(counter);
    }
    
    public Object clone() {
        return new RandomPartitionVoxelIterator(image, fraction, seed);
    }
    
    public int size() {
        return nvoxel;
    }
    
}
