/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.util;

/**
 * 
 * Wird dieses Interface implementiert, so bedeutet dies,
 * daß der gelieferte String eine Darstellung
 * des Objektes in Mathematica ermöglicht. Dies
 * kann ein Diagramm sein, eine Graphik oder
 * auch nur die Darstellung der Datenstruktur
 * in der Syntax von Mathematica.
 *  
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 */
public interface MathematicaStringable {
    public String toMathematicaString();
}
