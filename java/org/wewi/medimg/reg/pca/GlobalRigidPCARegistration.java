package org.wewi.medimg.reg.pca;

import java.util.Arrays;

/**
 * @author Werner Weiser
 * 
 * @version 0.1
 */
public class GlobalRigidPCARegistration extends GlobalPCARegistration{
    
		/**
	 * Constructor for RigidPCARegistration.
	 */
	public GlobalRigidPCARegistration() {
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

