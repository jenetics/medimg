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

package org.wewi.medimg.reg.pca;

import java.util.Arrays;

/**
 * @author Werner Weiser
 * 
 * @version 0.1
 */
public class RigidPCARegistration extends PCARegistration{
    
        /**
     * Constructor for RigidPCARegistration.
     */
    public RigidPCARegistration() {
        super();
    }
    
    protected void calculateScaling(double[] scalingFactors, double[] eigenValues1, double[] eigenValues2) {
        //Bei der Transformation A2 wird die Skalierung in
        //Richtung der Hauptachsen berechnet und in die Transformation eingefügt.
        //   As = |sx  0  0 |
        //        |0   sy 0 |
        //        |0   0  sz|
        //
        //   sx = sqrt(eigen1X / eigen2X)
        //   sy = sqrt(eigen1Y / eigen2Y)
        //   sz = sqrt(eigen1Z / eigen2Z)

        //Prüfen auf unzulässige Eigenwerte
        Arrays.fill(scalingFactors, 1);
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Rigides PCA-Verfahren: ");
        buffer.append("Ergebnis : Transformationsmatrix: ");
        //buffer.append(transformation);
        return buffer.toString();        
    }
    
    public String getRegistratorName() {
        return "Rigides PCA-Verfahren";
    
    }     

}
