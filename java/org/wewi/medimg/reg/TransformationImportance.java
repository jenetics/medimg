package org.wewi.medimg.reg;

/**
 * @author Franz Wilhelmst�tter
 * @author Werner Weiser
 * 
 * @version 0.1
 */
public interface TransformationImportance {
    public double[] transformationWeights(int[] features, double[] similarity, int[] featureNPoints);
}
