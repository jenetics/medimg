package org.wewi.medimg.reg.pca;

import java.util.Arrays;

import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.geom.transform.InterpolateableTransformation;
import org.wewi.medimg.reg.MultipleFeatureRegistrator;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Sorting;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.EigenvalueDecomposition;
import cern.colt.matrix.linalg.LUDecomposition;

/**
 * @author Franz Wilhelmstötter
 * @author Werner Weiser
 * 
 * @version 0.1
 */
public class PCARegistration extends MultipleFeatureRegistrator {

	/**
	 * Constructor for PCARegistration.
	 */
	public PCARegistration() {
		super();
	}

	/**
	 * @see org.wewi.medimg.reg.MultipleFeatureRegistrator#getTransformation(VoxelIterator, VoxelIterator)
	 */
	protected InterpolateableTransformation getTransformation(VoxelIterator source, VoxelIterator target) {
        double[] temp = new double[3];
        Algebra alg = new Algebra();
        
        //Berechnung der Schwerpunkte und Hauptachsen und Mediane
        double[] cog1 = new double[3];
        double[] cog2 = new double[3];
        centreOfGravityAndMedian((VoxelIterator)source.clone(), cog1);
        centreOfGravityAndMedian((VoxelIterator)target.clone(), cog2);   
        
        //Berechnung der Kovarianzmatrix
        double[] eigenValues1 = new double[3];
        double[] eigenValues2 = new double[3];

        double[][] a1 = getHotellingTransform((VoxelIterator)source.clone(), cog1, eigenValues1);
        double[][] a2 = getHotellingTransform((VoxelIterator)target.clone(), cog2, eigenValues2);
        
        DoubleMatrix2D A1 = new DenseDoubleMatrix2D(a1);
        DoubleMatrix2D A2 = new DenseDoubleMatrix2D(a2);
       
        //Bei der Transformation A2 wird die Skalierung in
        //Richtung der Hauptachsen berechnet und in die Transformation eingefügt.
        //   As = |sx  0  0 |
        //        |0   sy 0 |
        //        |0   0  sz|
        //
        //   sx = sqrt(eigen1X / eigen2X)
        //   sy = sqrt(eigen1Y / eigen2Y)
        //   sz = sqrt(eigen1Z / eigen2Z)
        DoubleMatrix2D As = DoubleFactory2D.dense.make(4, 4);
        As.setQuick(3, 3, 1);

        //Prüfen auf unzulässige Eigenwerte
        double sx = 1, sy = 1, sz = 1;
        if (eigenValues1[0] == 0 && eigenValues2[0] == 0) {
            sx = 1;
        } else if (eigenValues1[0] != 0 && eigenValues2[0] != 0) {
            sx = Math.sqrt(eigenValues1[0] / eigenValues2[0]);
        } else {
            //throw new RegistrationException("Invalid Transformation: eigenvalue x = zero \n");
        }
        if (eigenValues1[1] == 0 && eigenValues2[1] == 0) {
            sy = 1;
        } else if (eigenValues1[1] != 0 && eigenValues2[1] != 0) {
            sy = Math.sqrt(eigenValues1[1] / eigenValues2[1]);
        } else {
            //throw new RegistrationException("Invalid Transformation: eigenvalue y = zero \n");
        }
        if (eigenValues1[2] == 0 && eigenValues2[2] == 0) {
            sz = 1;
        } else if (eigenValues1[2] != 0 && eigenValues2[2] != 0) {
            sz = Math.sqrt(eigenValues1[2] / eigenValues2[2]);
        } else {
            //throw new RegistrationException("Invalid Transformation: eigenvalue z = zero \n");
        }

        As.setQuick(0, 0, sx);
        As.setQuick(1, 1, sy);
        As.setQuick(2, 2, sz);
        As.setQuick(3, 3, 1.0);
        
        //Die neue Transformationsmatrix A2 ergibt sich folgendermaßen:
        //    A2 = As*A2
        A2 = As.zMult(A2, null);  
           
        //Von A2 wird für die Gesamttransformation die Inverse benötigt
        //A2 orginal wird vernichtet
        LUDecomposition lu = new LUDecomposition(A2);
        DoubleMatrix2D identity = DoubleFactory2D.dense.make(4, 4);
        identity.assign(0.0);
        identity.setQuick(0, 0, 1.0);
        identity.setQuick(1, 1, 1.0);
        identity.setQuick(2, 2, 1.0);
        identity.setQuick(3, 3, 1.0);
        DoubleMatrix2D A2Inv = lu.solve(identity);

        //Die Gesamttransformation ergibt sich dann aus A2^-1*A1
        A2 = alg.mult(A2Inv, A1);
        System.out.println("******************************");
        System.out.println(A2);
        return new AffineTransformation(A2.toArray());
	}
    
    /**
     * Berechnet die sogenannte Hotelling Transformation. Diese Transformation
     * dreht die Hauptachsen des Bildes so, dass sie achsenparallel zum Weltkoordinatensystem
     * stehen und verschiebt den Schwerpunkt in Ursprung. Es wird eine 4x4 Matrix zurückgegeben, die Rotation und
     * Translation ausführt.
     * @param data Datenpunkte, zu denen die Hotelling Transformation durchgeführt wird. data ist eine n * 3 Matrix. n
     * ist die Anzahl der Punkte.
     * @param cog Schwerpunkt der Punktmenge. Wird der Methode mitgegeben,
     * um eine zweite Berechnung des Schwerpunktes zu verhindern.
     * @param eigenValues Die von der Methode berechneten Eigenwerte werden gefüllt
     * @return Die Hotelling Transformation y = A(x - mx); der Term A(x - mx) wird in eine 4*4 Matrix zusammengefaßt
     * und zurückgegeben.
     */
    private double[][] getHotellingTransform(VoxelIterator data, double[] cog, double[] eigenValues) {

        //Berechnen der Kovarianzmatrix
        double[] point = new double[3];
        double[][] matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(matrix[i], 0);
        }
        while (data.hasNext()) {
            data.next(point);
            for (int i = 0; i < 3; i++) {
                point[i] -= cog[i];
            }
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    matrix[i][j] += point[i]*point[j];
                }
            }
        
        }
        
        DoubleMatrix2D covarianceMatrix = new DenseDoubleMatrix2D(matrix);
        EigenvalueDecomposition eigen = new EigenvalueDecomposition(covarianceMatrix);        
        DoubleMatrix2D eigenValues2D = eigen.getD();        
        DoubleMatrix2D eigenVectors = eigen.getV();
        
        DoubleMatrix2D sortMatrix = DoubleFactory2D.dense.make(3, 4);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sortMatrix.setQuick(i, j, eigenVectors.getQuick(j, i));    
            }    
        }
        for (int i = 0; i < 3; i++) {
            sortMatrix.setQuick(i, 3, eigenValues2D.getQuick(i, i));    
        }
        eigenVectors = Sorting.quickSort.sort(sortMatrix, 3).viewPart(0, 0, 3, 3).viewRowFlip();

        DoubleMatrix2D A = DoubleFactory2D.dense.make(4, 4);
        A.assign(0); A.setQuick(3, 3, 1);
        A.viewPart(0, 0, 3, 3).assign(eigenVectors);
        
        //Bestimmen der Verschiebungsmatrix       
        DoubleMatrix2D At = DoubleFactory2D.dense.make(4, 4);
        At.assign(0); 
        for (int i = 0; i < 4; i++) {
            At.setQuick(i, i, 1);
        }
        for (int i = 0; i < 3; i++) {
            At.setQuick(i, 3, -cog[i]);
        }        
       
        
        A = A.zMult(At, null);         

        return A.toArray();
    }    
    
    /**
     * Berechnen des Schwerpunktes
     * @param data Daten, aus denen ein Schwerpunkt berechnet werden soll.
     * Die einzelnen Datenpunkte sind reihenweise angeordnet; in einer n * 3 Matrix
     * @return Schwerpunkt der Datenpunkte
     */
    private void centreOfGravityAndMedian(VoxelIterator data, double[] cog) {
        double[] result = new double[3];
        Arrays.fill(result, 0);
        int[] point = new int[3];
        int count = 0;
        while (data.hasNext()) {
            data.next(point);
            result[0] += point[0];
            result[1] += point[1];
            result[2] += point[2]; 
            ++count;                       
        }
        for (int i = 0; i < 3; i++) {
            result[i] /= (double)count;
            cog[i] = result[i];
        }

    }
    
        

}
