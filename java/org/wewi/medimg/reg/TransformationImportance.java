package org.wewi.medimg.reg;

/**
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * 
 * @version 0.1
 */
public interface TransformationImportance {
    public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints);
}
