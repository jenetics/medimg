/*
 * RegisterParameter.java
 *
 * Created on 26. März 2002, 15:38
 */

package org.wewi.medimg.reg;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.reg.metric.TissueData;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class RegisterParameter {

   
    private Image sourceImage;
    private Image targetImage;

    private TissueData sourceTissueData;
    private TissueData targetTissueData;
    public int temp = 0;

    /** Creates new RegisterParameter */
    public RegisterParameter() {
        sourceTissueData = null;
        targetTissueData = null;
    }

    /**
     * Setzen der Target Images
     *
     * @param t Zielbild; ImageData
     */
    public void setTargetImage(Image t) {
        targetImage = t;
        targetTissueData = new TissueData(targetImage);
    }

    public Image getTargetImage() {
        return targetImage;
    }

    public void setSourceImage(Image s) {
        sourceImage = s;
        sourceTissueData = new TissueData(sourceImage);
    }

    public TissueData getSourceTissueData() {
            return sourceTissueData;
    }

    public TissueData getTargetTissueData() {
            return targetTissueData;
    }

    public Image getSourceImage() {
        return sourceImage;
    }

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
