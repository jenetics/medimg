package org.wewi.medimg.util;

/**
 * Punkte die in den KD Baum eingefügt werden sollen
 * müssen dieses Interface implementieren
 *
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public interface KDTreePoint {
    
    /**
     * Liefert den Wert der entsprechenden Koordinate.
     * @param dimension Die n-te Koordinate die zurückgegeben
     * werden soll
     * @return Wert der Koordinate; Comparable, da für die Einordnung
     * in den Baum mit den Knoten verglichen werden muß
     */
   public Comparable getComparableOrdinate(int dimension);
   
   /**
    * Liefert die Anzahl der Dimensionen des Punktes
    * @return Die Anzahl der Dimensionen des Punktes
    */
   public int getNDim();
}
