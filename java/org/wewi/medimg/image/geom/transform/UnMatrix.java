/**
 * Unmatrix.java
 * 
 * Created on 19.03.2003, 17:04:41
 *
 */
package org.wewi.medimg.image.geom.transform;

import java.util.Arrays;

import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class Unmatrix {
    private Matrix4d translation;
    private Matrix4d scale;
    private Matrix4d shear;
    private Matrix4d perspective;
    private Matrix4d rotation;
        

	/**
	 * Constructor for Unmatrix.
	 */
	public Unmatrix(Matrix4d matrix) {
		unmatrix(matrix);
	}
    
    private void unmatrix(Matrix4d matrix) {
        final int U_SCALEX = 0;
        final int U_SCALEY = 1;
        final int U_SCALEZ = 2;
        final int U_SHEARXY = 3;
        final int U_SHEARXZ = 4;
        final int U_SHEARYZ = 5;
        final int U_ROTATEX = 6;
        final int U_ROTATEY = 7;
        final int U_ROTATEZ = 8;
        final int U_TRANSX = 9;
        final int U_TRANSY = 10;
        final int U_TRANSZ = 11;
        final int U_PERSPX = 12;
        final int U_PERSPY = 13;
        final int U_PERSPZ = 14;
        final int U_PERSPW = 15;
        double[] tran = new double[16];        
        
        
        //Normalizing the matrix
        matrix.mul(1.0/matrix.getElement(3, 3));
        
        //pmat wird zum Lösen des perspektivischen Anteils verwendet.
        //Es wird hier automatisch die ober 3x3 Komponente
        //auf Singularität getestet.
        Matrix4d pmat = (Matrix4d)matrix.clone();        
        pmat.setRow(3, 0, 0, 0, 1);  
        if (pmat.determinant() == 0.0) {
            System.err.println("Singulär B");
            return; //Singuläre Matrix kann nicht behandelt werden.            
        }
          
                  
        //Isolieren des perspektivischen Anteils
        Vector4d prsh = new Vector4d();
        if (matrix.getElement(3, 0) != 0 || matrix.getElement(3, 1) != 0 || matrix.getElement(3, 2) != 0) {
            //prhs ist die rechte Seite der Gleichung
            matrix.getRow(3, prsh);

            //Lösen der Gleichung durch Invertieren von pmat 
            //und multiplizieren mit prsh
            Matrix4d invpmat = (Matrix4d)pmat.clone();
            invpmat.invert();
            invpmat.transpose();
            Vector4d psol = (Vector4d)prsh.clone();
            invpmat.transform(psol);
            
     
            //Speichern der Lösung
            double[] temp = new double[4];
            psol.get(temp);
            tran[U_PERSPX] = temp[0];
            tran[U_PERSPY] = temp[1];
            tran[U_PERSPZ] = temp[2];
            tran[U_PERSPW] = temp[3];

            //Entfernen des perspektivischen Anteils
            matrix.setRow(3, 0, 0, 0, 1);
        } else {
            tran[U_PERSPX] = tran[U_PERSPY] = tran[U_PERSPZ] = tran[U_PERSPW] = 0;
        }
        
        //Finden der Translation und entfernen der Translation
        double[] temp = new double[4];
        matrix.getColumn(3, temp);
        tran[U_TRANSX] = temp[0];
        tran[U_TRANSY] = temp[1];
        tran[U_TRANSZ] = temp[2];
        matrix.setColumn(3, 0, 0, 0, 1);


        //Finden der Skalierung und Scherung
        Matrix3d rot = new Matrix3d(matrix.m00, matrix.m01, matrix.m02,
                                    matrix.m10, matrix.m11, matrix.m12,
                                    matrix.m20, matrix.m21, matrix.m22);
                                    
        Vector3d a = new Vector3d();
        Vector3d b = new Vector3d();
        Vector3d c = new Vector3d();                                    

        //Berechnen der X-Skalierung und normalisieren der ersten Spalte
        rot.getColumn(0, a);
        tran[U_SCALEX] = a.length();
        a.normalize();
        rot.setColumn(0, a);
              
        //Berechnen der XY-Scherung und orthogonalisieruen der ersten 
        //und zweiten Spalte der rot-Matrix.
        rot.getColumn(0, a);
        rot.getColumn(1, b);
        tran[U_SHEARXY] = a.dot(b);
        a.scale(-tran[U_SHEARXY]);
        b.add(a);
        rot.setColumn(1, b);
           
    
        //Berechnen der Y-Skalierung und normalisieren der zweiten Spalte
        tran[U_SCALEY] = b.length();
        b.scale(1.0/tran[U_SCALEY]);
        rot.setColumn(1, b);
        tran[U_SHEARXY] /= tran[U_SCALEY];
               
    
        //Berechnen der XZ und YZ-Scherungen und orthogonalisieren der dritten Spalte
        rot.getColumn(0, a);
        rot.getColumn(2, c);
        tran[U_SHEARXZ] = a.dot(c);
        a.scale(-tran[U_SHEARXZ]);
        c.add(a);
        rot.setColumn(2, c);
        rot.getColumn(1, b);
        tran[U_SHEARYZ] = b.dot(c);
        b.scale(-tran[U_SHEARYZ]);
        c.add(b);
        rot.setColumn(2, c);              
   
    
        //Berechnen der Z-Skalierung und normalisieren der dritten Spalte.
        rot.getColumn(2, c);
        tran[U_SCALEZ] = c.length();
        c.scale(1.0/tran[U_SCALEZ]);
        rot.setColumn(2, c);
        tran[U_SHEARXZ] /= tran[U_SCALEZ];
        tran[U_SHEARYZ] /= tran[U_SCALEZ];
                          
                          
                          
        /* At this point, the matrix (in rows[]) is orthonormal.
         * Check for a coordinate system flip.  If the determinant
         * is -1, then negate the matrix and the scaling factors.
         */

        double det = rot.determinant();
        if (det < 0) {
            tran[U_SCALEX] = -tran[U_SCALEX];
            tran[U_SCALEY] = -tran[U_SCALEY];
            tran[U_SCALEZ] = -tran[U_SCALEZ];
            rot.mul(-1);
        } 
        
        
        tran[U_ROTATEY] = Math.asin(rot.getElement(0, 2));
        if (Math.cos(tran[U_ROTATEY]) != 0) {
            tran[U_ROTATEX] = Math.atan(-rot.getElement(1, 2)/rot.getElement(2, 2));
            tran[U_ROTATEZ] = Math.atan(-rot.getElement(0, 1)/rot.getElement(0, 0));
        } else {
            tran[U_ROTATEX] = 0;
            tran[U_ROTATEZ] = Math.atan(-rot.getElement(1, 0));               
        }
        
                          
        double[] da = new double[2];
        double[] db = new double[2];
        double[] dc = new double[2];
        
        da[0] = tran[U_ROTATEX];
        da[1] = Math.PI + da[0];
        db[0] = tran[U_ROTATEY];
        db[1] = Math.PI - db[0];
        dc[0] = tran[U_ROTATEZ];
        dc[1] = Math.PI + dc[0];
        
        loop:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    if (testAngles(da[i], db[j], dc[k], rot, 0.1)) {
                        tran[U_ROTATEX] = da[i]; 
                        tran[U_ROTATEY] = db[j];
                        tran[U_ROTATEZ] = dc[k]; 
                        break loop;  
                    }    
                }    
            }    
        }
        
        
        //Creating the translation matrix
        translation = new Matrix4d();
        Vector3d t = new Vector3d(tran[U_TRANSX], tran[U_TRANSY], tran[U_TRANSZ]);
        translation.setTranslation(t);
        
        //Creating the scale matrix
        scale = new Matrix4d();
        scale.setElement(0, 0, tran[U_SCALEX]);
        scale.setElement(1, 1, tran[U_SCALEY]);
        scale.setElement(2, 2, tran[U_SCALEZ]);
        scale.setElement(3, 3, 1.0);
        
        //Creating the shear matrix
        shear = new Matrix4d();
        shear.setElement(0, 1, tran[U_SHEARXY]);
        shear.setElement(0, 2, tran[U_SHEARXZ]);
        shear.setElement(1, 2, tran[U_SHEARYZ]);
        shear.setElement(0, 0, 1.0);
        shear.setElement(1, 1, 1.0);
        shear.setElement(2, 2, 1.0);
        shear.setElement(3, 3, 1.0);
        
        //Creating the perspective matrix
        perspective = new Matrix4d();
        perspective.setElement(3, 0, tran[U_PERSPX]);
        perspective.setElement(3, 1, tran[U_PERSPY]);
        perspective.setElement(3, 2, tran[U_PERSPZ]);
        perspective.setElement(3, 3, tran[U_PERSPW]);   
        perspective.setElement(0, 0, 1.0);
        perspective.setElement(1, 1, 1.0);
        perspective.setElement(2, 2, 1.0);
        
        //Creating the rotation matrix
        double[] m = new double[16];
        Arrays.fill(m, 0);
        m[0] = Math.cos(tran[U_ROTATEY])*Math.cos(tran[U_ROTATEZ]);
        m[1] = -Math.cos(tran[U_ROTATEY])*Math.sin(tran[U_ROTATEZ]);
        m[2] = Math.sin(tran[U_ROTATEY]);
        m[4] = Math.cos(tran[U_ROTATEZ])*Math.sin(tran[U_ROTATEX])*Math.sin(tran[U_ROTATEY]) +
               Math.cos(tran[U_ROTATEX])*Math.sin(tran[U_ROTATEZ]);
        m[5] = -Math.sin(tran[U_ROTATEX])*Math.sin(tran[U_ROTATEY])*Math.sin(tran[U_ROTATEY]) +
               Math.cos(tran[U_ROTATEX])*Math.cos(tran[U_ROTATEZ]);
        m[6] = -Math.cos(tran[U_ROTATEY])*Math.sin(tran[U_ROTATEX]);
        m[8] = -Math.cos(tran[U_ROTATEX])*Math.cos(tran[U_ROTATEZ])*Math.sin(tran[U_ROTATEY]) +
               Math.sin(tran[U_ROTATEX])*Math.sin(tran[U_ROTATEZ]);
        m[9] = Math.cos(tran[U_ROTATEX])*Math.sin(tran[U_ROTATEY])*Math.sin(tran[U_ROTATEZ]) +
               Math.cos(tran[U_ROTATEZ])*Math.sin(tran[U_ROTATEX]);
        m[10] = Math.cos(tran[U_ROTATEX])*Math.cos(tran[U_ROTATEY]);
        m[15] = 1.0;
        rotation = new Matrix4d(m);
        
                 
        
/*
        System.out.print( "U_SCALEX : (" + tran[U_SCALEX ] + ")\n");
        System.out.print( "U_SCALEY : (" + tran[U_SCALEY ] + ")\n");
        System.out.print( "U_SCALEZ : (" + tran[U_SCALEZ ] + ")\n");
        System.out.print( "U_SHEARXY: (" + tran[U_SHEARXY] + ")\n");
        System.out.print( "U_SHEARXZ: (" + tran[U_SHEARXZ] + ")\n");
        System.out.print( "U_SHEARYZ: (" + tran[U_SHEARYZ] + ")\n");
        System.out.print( "U_ROTATEX: (" + tran[U_ROTATEX] + ")\n");
        System.out.print( "U_ROTATEY: (" + tran[U_ROTATEY] + ")\n");
        System.out.print( "U_ROTATEZ: (" + tran[U_ROTATEZ] + ")\n");
        System.out.print( "U_TRANSX : (" + tran[U_TRANSX ] + ")\n");
        System.out.print( "U_TRANSY : (" + tran[U_TRANSY ] + ")\n");
        System.out.print( "U_TRANSZ : (" + tran[U_TRANSZ ] + ")\n");
        System.out.print( "U_PERSPX : (" + tran[U_PERSPX ] + ")\n");
        System.out.print( "U_PERSPY : (" + tran[U_PERSPY ] + ")\n");
        System.out.print( "U_PERSPZ : (" + tran[U_PERSPZ ] + ")\n");
        System.out.print( "U_PERSPW : (" + tran[U_PERSPW ] + ")\n");
*/ 
                       
    }
    
    private boolean testAngles(double a, double b, double c, Matrix3d rot, double EPSILON) {
        double[][] m = new double[3][3];
        m[0][0] = Math.cos(b)*Math.cos(c);
        m[0][1] = -Math.cos(b)*Math.sin(c);
        m[0][2] = Math.sin(b);
        m[1][0] = Math.cos(c)*Math.sin(a)*Math.sin(b) +
               Math.cos(a)*Math.sin(c);
        m[1][1] = -Math.sin(a)*Math.sin(b)*Math.sin(c) +
               Math.cos(a)*Math.cos(c);
        m[1][2] = -Math.cos(b)*Math.sin(a);
        m[2][0] = -Math.cos(a)*Math.cos(c)*Math.sin(b) +
               Math.sin(a)*Math.sin(c);
        m[2][1] = Math.cos(a)*Math.sin(b)*Math.sin(c) +
               Math.cos(c)*Math.sin(a);
        m[2][2] = Math.cos(a)*Math.cos(b); 
        
        for (int i = 0; i <3; i++) {
            for (int j = 0; j < 3; j++) {
                if (Math.abs(m[i][j] - rot.getElement(i, j)) > EPSILON) {
                    return false;    
                }    
            }    
        }       
        
        return true;    
    }    
    
    public Matrix4d getTranslateMatrix() {
        return (Matrix4d)translation.clone();
    }
    
    public Matrix4d getScaleMatrix() {
        return (Matrix4d)scale.clone();
    }
    
    public Matrix4d getShearMatrix() {
        return (Matrix4d)shear.clone();
    }
    
    public Matrix4d getRotationMatrix() {
        return (Matrix4d)rotation.clone();
    }
    
    public Matrix4d getPerspectiveMatrix() {
        return (Matrix4d)perspective.clone();
    }
    

}









