/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.util;

/**
 * 
 * Wird dieses Interface implementiert, so bedeutet dies,
 * da� der gelieferte String eine Darstellung
 * des Objektes in Mathematica erm�glicht. Dies
 * kann ein Diagramm sein, eine Graphik oder
 * auch nur die Darstellung der Datenstruktur
 * in der Syntax von Mathematica.
 *  
 * @author Franz Wilhelmst�tter
 * @version 0.1
 *
 */
public interface MathematicaStringable {
    public String toMathematicaString();
}
