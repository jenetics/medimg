/**
 * Created on 24.09.2002
 *
 */
package org.wewi.medimg.reg;

import java.util.List;
import java.util.Vector;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.InterpolateableTransformation;
import org.wewi.medimg.image.geom.transform.Transformation;
import org.wewi.medimg.image.ops.ColorRangeOperator;
import org.wewi.medimg.image.ops.UnaryPointAnalyzer;
import org.wewi.medimg.reg.wizard.ObservableRegistrator;
import org.wewi.medimg.reg.wizard.RegistratorEvent;

/**
 * 
 * 
 * @author Werner Weiser
 * @version 0.1
 *
 */
public abstract class MultipleFeatureRegistrator extends ObservableRegistrator {
    
    protected AffinityMetric affinityMetric;
    protected TransformationImportance transformationImportance;
    
    private Transformation transformation;

    /**
     * Constructor for MultipleFeatureRegistrator.
     */
    public MultipleFeatureRegistrator() {
        super();
        affinityMetric = ConstantAffinityMetric.INSTANCE;
        transformationImportance = ConstantTransformationImportance.INSTANCE;
    }

    /**
     * @see org.wewi.medimg.reg.Registrator#registrate(Image, Image)
     */
    public Transformation registrate(Image source, Image target) {
        notifyRegistratorStarted(new RegistratorEvent(this));
        List transformationList = new Vector();
        List similarityList = new Vector();
        List featureList = new Vector();
        List sitSizeList = new Vector();
        
        double similarity = 1.0;
        
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
        VoxelIteratorFactory f = new VoxelIteratorFactory(source, target);
        for (int i = minFeature; i <= maxFeature; i++) {
            VoxelIterator sit = f.getSourceVoxelIterator(i);
            VoxelIterator tit = f.getTargetVoxelIterator(i);
            int sitSize = sit.size();
            int titSize = tit.size();
            if (sitSize <= 0 || titSize <= 0) {
                continue;    
            }
            
            //Berechnen der Transformation
            Transformation trans = getTransformation((FeatureIterator)sit.clone(), 
                                                     (FeatureIterator)tit.clone());
                                       
            //Bestimmen der Qualität der berechneten Transformation

            similarity = affinityMetric.similarity(source, target, trans); 
            System.out.println("**ergebnis fuer feature ** " + i + " = " + similarity);         

            transformationList.add(trans);
            similarityList.add(new Double(similarity));
            featureList.add(new Integer(i));
            sitSizeList.add(new Integer(sitSize));
                                                        
        } 
        
        int size = transformationList.size();
        int[] features = new int[size];
        double[] similarities = new double[size];
        int[] featureNPoints = new int[size];
        for (int i = 0; i < size; i++) {
            features[i] = ((Integer)featureList.get(i)).intValue();
            similarities[i] = ((Double)similarityList.get(i)).doubleValue();
            featureNPoints[i] = ((Integer)sitSizeList.get(i)).intValue();
        }
        
        double[] weights = transformationImportance.transformationWeights(features, similarities, featureNPoints);   
        for (int i = 0; i < weights.length; i++) {
            System.out.println("Feature: " + features[i]);
            System.out.println("similarities: " + similarities[i]);
            System.out.println("featureNPoints: " + featureNPoints[i]);
            System.out.println("weights: " + weights[i]);
        }
        //Rückgabe der berechnenten Interpolation der Transformationen.
        InterpolateableTransformation[] trans = new InterpolateableTransformation[size];
        transformationList.toArray(trans);
        transformation = interpolate(trans, weights);
        notifyRegistratorFinished(new RegistratorEvent(this));
        return transformation;
    }
    
    
    private Transformation interpolate(InterpolateableTransformation[] transformation, double[] weight) {
        InterpolateableTransformation trans = transformation[0];
        double relationshipWeight;
        double weightSum = weight[0];
        for (int i = 1; i < weight.length; i++) {
            weightSum += weight[i];
            if (weightSum == 0) {
                relationshipWeight = 1.0;
            } else {
                relationshipWeight = ((weight[i]) / weightSum);
            }
            trans = trans.interpolate(transformation[i], relationshipWeight);
            System.out.println("Nach der  " + i + ". Interpolation" + trans);
        }
        // wenn alle Gewichte 0 waren
        /*if (weightSum == 0) {
            trans = null;
        }*/
        
        
        System.out.println("********************************");
        System.out.println("Ergebnis : Transformationsmatrix: ");
        System.out.println(trans);
        return trans;    
    }
    
    /**
     * Wenn keine Transformation gefunden werden kann, weil z.B. einer
     * der Iteratoren keine Punkte enthält, soll von der Implementierung
     * die identische Transformation zurückgegeben werden.
     * 
     * @param source VoxelIterator der Punkte die transformiert werden sollen
     * @param target VoxelIterator der Punkte, auf die abgebildet wird
     * 
     * @return Die Transformation, die die source-Punkte auf die 
     *          target-Punkte abbildet.
     */
    protected abstract InterpolateableTransformation getTransformation(VoxelIterator source, 
                                                                         VoxelIterator target);

    /**
     * Returns the affinityMetric.
     * @return AffinityMetric
     */
    public AffinityMetric getAffinityMetric() {
        return affinityMetric;
    }

    /**
     * Sets the affinityMetric.
     * @param affinityMetric The affinityMetric to set
     */
    public void setAffinityMetric(AffinityMetric affinityMetric) {
        this.affinityMetric = affinityMetric;
    }

    /**
     * Returns the transformationImportance.
     * @return TransformationImportance
     */
    public TransformationImportance getTransformationImportance() {
        return transformationImportance;
    }

    /**
     * Sets the transformationImportance.
     * @param transformationImportance The transformationImportance to set
     */
    public void setTransformationImportance(TransformationImportance transformationImportance) {
        this.transformationImportance = transformationImportance;
    }

}








