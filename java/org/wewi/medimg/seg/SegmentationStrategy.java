/*
 * SegStrategy.java
 *
 * Created on 22. Februar 2002, 10:06
 */

package org.wewi.medimg.seg;

/**
 * Da nicht nur Bilder segmentiert werden könne, sondern beliebige Datenpunkt,
 * ist dies die Basisschnittstelle aller Segmentierungsmethoden. Dieses
 * Interface schreibt nur die Implementierung der Methode
 * <code>segmentate</code>. Diese Methode startet den Segmentiervorgang.
 *
 *
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface SegmentationStrategy {
    /**
     * Diese Methode startet den Segmentiervorgang.
     */    
    public void segmentate();
}

