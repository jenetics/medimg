/*
 * BBAffinityMetric.java
 *
 * Created on 26. März 2002, 15:14
 */

package org.wewi.medimg.reg.metric;

import org.wewi.medimg.image.Tissue;
import org.wewi.medimg.image.geom.transform.Transform;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class BBAffinityMetric implements AffinityMetric {
    
    private Transform trans;
    private TissueData trg;
    private TissueData src;
    private static double EPSILON = 0.01;

    /** Creates new BBAffinityMetric */
    public BBAffinityMetric() {
            src = null;
            trg = null;
            trans = null;
    }


    public double similarity() {
        double erg = 1.0;
        double tmp = 0.0;
        int i;
        //int oldPosSource = src.getPosition();
        //int oldPosTarget = trg.getPosition();
        TissueIterator ti1;
        TissueIterator ti2;
        //src.goToFirst();
        //trg.goToFirst();        
        for (i = 0; i < Tissue.TISSUES.length; i++) {
            ti1 = src.getTissueIterator(Tissue.TISSUES[i]);
            if (ti1.hasNext()) {
                Tissue ft = Tissue.TISSUES[i];
                System.out.println("mit Tissue " + ft);                
                ti2 = trg.getTissueIterator(Tissue.TISSUES[i]);
                if (ti2.hasNext()) {
                    tmp = getFitness(ti1, ti2, trans);
                    if (tmp < erg) {
                            erg = tmp;
                    }
                }
            }
        }
        System.out.println(" ergibt " + erg);
        //src.setPosition(oldPosSource);
        //trg.setPosition(oldPosTarget);
        return erg;
    }


    public void setSourceTissueData(TissueData sfi) {
            src = sfi;
    }

    public void setTargetTissueData(TissueData sfi) {
            trg = sfi;
    }

    public void setTransformation(Transform transform) {
            trans = transform;
    }

    private double getFitness(TissueIterator source, TissueIterator target, Transform transformation) {
            BoundingBox sourceBB;
            BoundingBox targetBB;
            sourceBB = source.getBoundingBox();
            targetBB = target.getBoundingBox();
            BoundingBox tempSourceBB = new BoundingBox(sourceBB);
            tempSourceBB.transform(transformation); 
            double refVolume = 0.0;
            double volume = 0.0;
            refVolume = targetBB.getVolume();
            volume = targetBB.getIntersectingVolume(tempSourceBB);
            System.out.println("***RefVolume***" + refVolume); 
            System.out.println("***Volume***" + volume); 
            System.out.println("***ergebnis***" + Math.abs(volume/refVolume)); 

            // für Einzelabbildungen
            //return 1.0;
            return Math.abs(volume/refVolume);
    }

}
