/*
 * Feature.java
 *
 * Created on 11. April 2002, 10:53
 */

package org.wewi.medimg.seg;

import org.wewi.medimg.math.DistributionFunction;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class Feature {
    private final int featureNo;
    private int lowerBound;
    private int upperBound;
    private double featureProbability;
    private DistributionFunction featureDistribution;
    
    /** Creates a new instance of Feature */
    public Feature(int featureNo) {
        this.featureNo = featureNo;
    }
    
    public int getFeatureNo() {
        return featureNo;
    }
    
    public void setDistributionFunction(DistributionFunction df) {
        featureDistribution = df;
    }
    
    public DistributionFunction getDistributionFunction() {
        return featureDistribution;
    }
    
    
    public void setFeatureProbability(double p) {
        featureProbability = p;
    }
    
    /**
     * Liefert die a priori Wahrscheinlichkeit dieses Merkmals
     */
    public double getFeatureProbability() {
        return 0.5;
    }
    
    public void setLowerBound(int b) {
        lowerBound = b;
    }
    
    public int getLowerBound() {
        return lowerBound;
    }
    
    public void setUpperBound(int b) {
        upperBound = b;
    }
    
    public int getUpperBound() {
        return upperBound;
    }
}
