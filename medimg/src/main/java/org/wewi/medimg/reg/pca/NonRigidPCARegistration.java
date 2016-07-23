package org.wewi.medimg.reg.pca;

import java.util.Arrays;

/**
 * @author Werner Weiser
 * 
 * @version 0.1
 */
public class NonRigidPCARegistration extends LocalPCARegistration{
    
        /**
     * Constructor for RigidPCARegistration.
     */
    public NonRigidPCARegistration() {
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
        if (eigenValues1[0] == 0 && eigenValues2[0] == 0) {
            scalingFactors[0] = 1;
        } else if (eigenValues1[0] != 0 && eigenValues2[0] != 0) {
            scalingFactors[0] = Math.sqrt(eigenValues1[0] / eigenValues2[0]);
        } else {
            //throw new RegistrationException("Invalid Transformation: eigenvalue x = zero \n");
        }
        if (eigenValues1[1] == 0 && eigenValues2[1] == 0) {
            scalingFactors[1] = 1;
        } else if (eigenValues1[1] != 0 && eigenValues2[1] != 0) {
            scalingFactors[1] = Math.sqrt(eigenValues1[1] / eigenValues2[1]);
        } else {
            //throw new RegistrationException("Invalid Transformation: eigenvalue y = zero \n");
        }
        if (eigenValues1[2] == 0 && eigenValues2[2] == 0) {
            scalingFactors[2] = 1;
        } else if (eigenValues1[2] != 0 && eigenValues2[2] != 0) {
            scalingFactors[2] = Math.sqrt(eigenValues1[2] / eigenValues2[2]);
        } else {
            //throw new RegistrationException("Invalid Transformation: eigenvalue z = zero \n");
        }        
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Non-Rigides PCA-Verfahren: ");
        buffer.append("Ergebnis : Transformationsmatrix: ");
        //buffer.append(transformation);
        return buffer.toString();        
     }
    
    public String getRegistratorName() {
        return "Non-Rigides PCA-Verfahren";
    
    }        

}
