/*
 * TissueData.java
 *
 * Created on 04. April 2002, 14:35
 */

package org.wewi.medimg.reg.metric;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.Tissue;

import org.wewi.medimg.reg.metric.TissueIterator;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class TissueData {
    
    private Image image;
    
    /** Creates new TissueData */
    public TissueData(Image img) {
        image = img;
    }
    
    public TissueIterator getTissueIterator(Tissue tissue) {
        return new TissueIterator(image, tissue);
    }
    
}
