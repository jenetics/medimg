/*
 * RegisterParameter.java
 *
 * Created on 26. März 2002, 15:38
 */

package org.wewi.medimg.reg;

import org.wewi.medimg.reg.metric.TissueIterator;
import org.wewi.medimg.reg.metric.TissueData;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.Tissue;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class RegisterParameter {

    //Tissue[] sourceFeatures;
    //Tissue[] targetFeatures;

    //int[] sourceDim; 
    //int[] targetDim;
    //int[] sourceNFeatures;
    //int[] targetNFeatures;
    //private static final int FEATURES = 8;
    
    Image sourceImage;
    Image targetImage;

    TissueData sourceTissueData;
    TissueData targetTissueData;

    /** Creates new RegisterParameter */
    public RegisterParameter() {
        sourceTissueData = null;
        targetTissueData = null;
        /*sourceDim = new int[3];
        targetDim = new int[3];
        sourceNFeatures = new int[FEATURES];
        targetNFeatures = new int[FEATURES];
	int i;
	for (i = 0; i < FEATURES; i++) {
		targetNFeatures[i] = 0;
		sourceNFeatures[i] = 0;
	}*/
    }

    /**
     * Setzen der Target Images
     *
     * @param t Zielbild; ImageData
     */
    public void setTargetImage(Image t) {
        targetImage = t;
        targetTissueData = new TissueData(targetImage);
        /*
        int i = 0;
        int j = 0;
        int k = 0;
        int index = 0;
        targetDim[0] = t.getMaxX();
        targetDim[1] = t.getMaxY();
        targetDim[2] = t.getMaxZ();

        targetFeatures = new Tissue[targetDim[0] * targetDim[1] * targetDim[2]];

        int color;
        for (i = 0; i < targetDim[2]; i++) {
            for (j = 0; j < targetDim[1]; j++) {
                for (k = 0; k < targetDim[0]; k++) {
                        index = (k + targetDim[0]*(j + targetDim[1]*i));
                        color = t.getColor(k, j, i);
                        if ( color == Tissue.VENTRICLE.intValue() ) {
                            // VENTRICLE
                            targetFeatures[index] = Tissue.VENTRICLE;
                            targetNFeatures[Tissue.VENTRICLE.intValue()]++;
                        }  else if (color == Tissue.BONE.intValue()){
                                // BONE
                                targetFeatures[index] = Tissue.BONE;
                                targetNFeatures[Tissue.BONE.intValue()]++;
                        } else if (color == Tissue.FAT.intValue()){
                                // FAT
                                targetFeatures[index] = Tissue.FAT;
                                targetNFeatures[Tissue.FAT.intValue()]++;
                        } else if (color == Tissue.GREY_MATTER.intValue()){
                                // GREY_MATTER
                                targetFeatures[index] = Tissue.GREY_MATTER;
                                targetNFeatures[Tissue.GREY_MATTER.intValue()]++;
                        } else if (color == Tissue.WHITE_MATTER.intValue()){
                                // WHITE_MATTER
                                targetFeatures[index] = Tissue.WHITE_MATTER;
                                targetNFeatures[Tissue.WHITE_MATTER.intValue()]++;
                        } else if (color == Tissue.SOFT_TISSUE.intValue()){
                                // SOFT_TISSUE
                                targetFeatures[index] = Tissue.SOFT_TISSUE;
                                targetNFeatures[Tissue.SOFT_TISSUE.intValue()]++;
                        } else if (color == Tissue.ANGULAR_GYRUS.intValue()){
                                // ANGULAR_GYRUS
                                targetFeatures[index] = Tissue.ANGULAR_GYRUS;
                                targetNFeatures[Tissue.ANGULAR_GYRUS.intValue()]++;
                        } else if (color == Tissue.DEEP_TISSUE.intValue()){
                                // DEEP_TISSUE
                                targetFeatures[index] = Tissue.DEEP_TISSUE;
                                targetNFeatures[Tissue.DEEP_TISSUE.intValue()]++;
                        } else {
                                // nothing  mentionable 
                                targetFeatures[index] = Tissue.UNDEFINED;
                        }
                }
            }
        }	*/

    }

    public Image getTargetImage() {
        return targetImage;
    }

    public void setSourceImage(Image s) {
        sourceImage = s;
        sourceTissueData = new TissueData(sourceImage);
        /*
        int i = 0;
        int j = 0;
        int k = 0;
        int index = 0;
        sourceDim[0] = s.getMaxX();
        sourceDim[1] = s.getMaxY();
        sourceDim[2] = s.getMaxZ();
        //System.out.println("HIer drinnen1" + sourceDim[0] + " , " + sourceDim[1]  + " , " + sourceDim[2]);        
        //System.out.println("X " + sourceDim[0] + " Y " + sourceDim[1] + " Z " + sourceDim[2]);
        sourceFeatures = new Tissue[sourceDim[0] * sourceDim[1] * sourceDim[2]];
        int color;
        //System.out.println("HIer drinnen2");
        for (i = 0; i < sourceDim[2]; i++) {
            for (j = 0; j < sourceDim[1]; j++) {
                for (k = 0; k < sourceDim[0]; k++) {
                    index = (k + sourceDim[0]*(j + sourceDim[1]*i));
                    color = s.getColor(k, j, i);
                    if ( color == Tissue.VENTRICLE.intValue() ) {
                            // VENTRICLE
                            sourceFeatures[index] = Tissue.VENTRICLE;
                            sourceNFeatures[Tissue.VENTRICLE.intValue()]++;
                    }  else if (color == Tissue.BONE.intValue()){
                            // BONE
                            sourceFeatures[index] = Tissue.BONE;
                            sourceNFeatures[Tissue.BONE.intValue()]++;
                    } else if (color == Tissue.FAT.intValue()){
                            // FAT
                            sourceFeatures[index] = Tissue.FAT;
                            sourceNFeatures[Tissue.FAT.intValue()]++;
                    } else if (color == Tissue.GREY_MATTER.intValue()){
                            // GREY_MATTER
                            sourceFeatures[index] = Tissue.GREY_MATTER;
                            sourceNFeatures[Tissue.GREY_MATTER.intValue()]++;
                    } else if (color == Tissue.WHITE_MATTER.intValue()){
                            // WHITE_MATTER
                            sourceFeatures[index] = Tissue.WHITE_MATTER;
                            sourceNFeatures[Tissue.WHITE_MATTER.intValue()]++;
                    } else if (color == Tissue.SOFT_TISSUE.intValue()){
                            // SOFT_TISSUE
                            sourceFeatures[index] = Tissue.SOFT_TISSUE;
                            sourceNFeatures[Tissue.SOFT_TISSUE.intValue()]++;
                    } else if (color == Tissue.ANGULAR_GYRUS.intValue()){
                            // ANGULAR_GYRUS
                            sourceFeatures[index] = Tissue.ANGULAR_GYRUS;
                            sourceNFeatures[Tissue.ANGULAR_GYRUS.intValue()]++;
                    } else if (color == Tissue.DEEP_TISSUE.intValue()){
                            // DEEP_TISSUE
                            sourceFeatures[index] = Tissue.DEEP_TISSUE;
                            sourceNFeatures[Tissue.DEEP_TISSUE.intValue()]++;
                    } else {
                            // nothing  mentionable 
                            sourceFeatures[index] = Tissue.UNDEFINED;
                    }
                }
            }
        }
        //System.out.println("HIer draußen"); 
         **/       
    }

    public TissueData getSourceTissueData() {
            /*if (sourceSFIterator == null) {
                    generateSFIterators();
            }*/
            return sourceTissueData;
    }

    public TissueData getTargetTissueData() {
            /*if (targetSFIterator == null) {
                    generateSFIterators();
            }*/
            return targetTissueData;
    }

    public Image getSourceImage() {
        return sourceImage;
    }


    /*private void generateSFIterators() {
        sourceSFIterator = new SFIterator();
        targetSFIterator = new SFIterator();
        int i, j, k;
        double[] tempPoint = new double[3];
        SegmentationFeature sf;
        //CUniformRandomPointChooser pch(1);
        int index = 0;
        for (i = 0; i < sourceDim[2]; i++) {
            for (j = 0; j < sourceDim[1]; j++) {
                for (k = 0; k < sourceDim[0]; k++) {
                    index = (k + sourceDim[0]*(j + sourceDim[1]*i));
                    //System.out.println("index " + index + " length "  + sourceFeatures.length + " -- " + sourceFeatures[index].intValue() + '\n');
                    if ((sourceFeatures[index].intValue()) != Tissue.UNDEFINED.intValue()) {
                        sf = sourceSFIterator.getFeature(sourceFeatures[index], sourceNFeatures[sourceFeatures[index].intValue()]);
                        tempPoint[0] = k;
                        tempPoint[1] = j;
                        tempPoint[2] = i;
                        sf.addPoint(tempPoint);
                    }
                }
            }
        }
        sourceSFIterator.goToFirst();
        while (sourceSFIterator.hasNext()) {
            sourceSFIterator.next().finish();
        }
        for (i = 0; i < targetDim[2]; i++) {
            for (j = 0; j < targetDim[1]; j++) {
                for (k = 0; k < targetDim[0]; k++) {
                    index = (k + targetDim[0]*(j + targetDim[1]*i));
                    if (targetFeatures[index].intValue() != Tissue.UNDEFINED.intValue()) {
                        sf = targetSFIterator.getFeature(targetFeatures[index], targetNFeatures[targetFeatures[index].intValue()]);
                        tempPoint[0] = k;
                        tempPoint[1] = j;
                        tempPoint[2] = i;
                        sf.addPoint(tempPoint);
                    }
                }
            }
        }
        sourceSFIterator.goToFirst();
        while (sourceSFIterator.hasNext()) {
            sourceSFIterator.next().finish();
        }
    }*/

    /*public int[] getTargetDim() {
        return targetDim;
    }


    public int[] getSourceDim() {
        return sourceDim;
    }


    public Tissue[] getTarget() {
        return targetFeatures;
    }

    public Tissue[] getSource() {
        return sourceFeatures;
    }*/
}
