package org.wewi.medimg.util;

/**
 * Punkte die in den KD Baum eingef�gt werden sollen
 * m�ssen dieses Interface implementieren
 *
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public interface KDTreePoint {
    
    /**
     * Liefert den Wert der entsprechenden Koordinate.
     * @param dimension Die n-te Koordinate die zur�ckgegeben
     * werden soll
     * @return Wert der Koordinate; Comparable, da f�r die Einordnung
     * in den Baum mit den Knoten verglichen werden mu�
     */
   public Comparable getComparableOrdinate(int dimension);
   
   /**
    * Liefert die Anzahl der Dimensionen des Punktes
    * @return Die Anzahl der Dimensionen des Punktes
    */
   public int getNDim();
}
