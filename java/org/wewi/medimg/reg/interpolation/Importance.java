/*
 * Importance.java
 *
 * Created on 26. März 2002, 09:33
 */

package org.wewi.medimg.reg.interpolation;


import org.wewi.medimg.image.Tissue;
/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class Importance {
    
    private Tissue tissue;
    private double importance;
    
    /** Creates new Importance */
    public Importance() {
    }

    public void setTissue(Tissue tis) {
            tissue = tis;
    }

    public Tissue getTissue() {
            return tissue;
    }

    public double getImportance() {
            return importance;
    }

    public void setImportance(double importance) {
            this.importance = importance;
    }


}
