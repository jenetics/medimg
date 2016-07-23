package org.wewi.medimg.reg.pca;

import java.util.Arrays;

import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.geom.transform.InterpolateableTransformation;
import org.wewi.medimg.reg.LocalRegistrator;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Sorting;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
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
public abstract class LocalPCARegistration extends LocalRegistrator {
    
    private static final double epsilon = 0.05;
    

    /**
     * Constructor for PCARegistration.
     */
    public LocalPCARegistration() {
        super();
    }

    /**
     * @see org.wewi.medimg.reg.MultipleFeatureRegistrator#getTransformation(VoxelIterator, VoxelIterator)
     */
    protected InterpolateableTransformation getTransformation(VoxelIterator source, VoxelIterator target) {
        Algebra alg = new Algebra();
        
        //Berechnung der Schwerpunkte und Hauptachsen und Mediane
        double[] cog1 = new double[3];
        double[] cog2 = new double[3];
              
        centreOfGravity((VoxelIterator)source.clone(), cog1);
        centreOfGravity((VoxelIterator)target.clone(), cog2);   
        
        //Berechnung der Kovarianzmatrix
        double[] eigenValues1 = new double[3];
        double[] eigenValues2 = new double[3];

        double[][] a1 = getHotellingTransform((VoxelIterator)source.clone(), cog1, eigenValues1);
        double[][] a2 = getHotellingTransform((VoxelIterator)target.clone(), cog2, eigenValues2);
        
        DoubleMatrix2D A1 = new DenseDoubleMatrix2D(a1);
        DoubleMatrix2D A2 = new DenseDoubleMatrix2D(a2);
        
        double[] scalingFactors = new double[3];
        calculateScaling(scalingFactors, eigenValues1, eigenValues2);
        DoubleMatrix2D As = DoubleFactory2D.dense.make(4, 4);
        for(int i = 0; i < 3; i++) {
            As.setQuick(i, i, scalingFactors[i]);
        } 
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
    
    protected abstract void calculateScaling(double[] scalingFactors, double[] eigenValues1, double[] eigenValues2);      

    
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
        VoxelIterator vit = (VoxelIterator)data.clone();
        double[] point = new double[3];
        double[][] matrix = new double[3][3];
        for (int i = 0; i < 3; i++) {
            Arrays.fill(matrix[i], 0);
        }
        int count = 0;
        while (data.hasNext()) {
            count++;
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
        covarianceMatrix.assign(cern.jet.math.Functions.mult(1.0 / count));        
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
        for (int i = 0; i < 3; i++) {
            eigenValues[i] = sortMatrix.getQuick((2 - i), 3);
        }        
        
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
        
        //Korrektur der Hauptachsen
        double[] median = new double[3];
        getMedian(vit, A, median);
        //Bestimmen der richtigen Ausrichtung der Hauptachse,
        //durch Vorzeichenvergleich der Komponenten des Medians
        for (int i = 0; i < 3; i++) {
            if (median[i] < -epsilon) {
                for (int j = 0; j < 4; j++) {
                    A.setQuick(i, j, -A.getQuick(i, j));
                }
            }
        }               

        return A.toArray();
    }    
    
    /**
     * Berechnen des Schwerpunktes
     * @param data Daten, aus denen ein Schwerpunkt berechnet werden soll.
     * Die einzelnen Datenpunkte sind reihenweise angeordnet; in einer n * 3 Matrix
     * @return Schwerpunkt der Datenpunkte
     */
    private void centreOfGravity(VoxelIterator data, double[] cog) {
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
    
    /**
     * Berechnung des Medians der Punkte in der Matrix data, nach
     * der Transformation durch die Matrix transform. Die Transformation
     * ist eine 4*4 Matrix. Die Datenpunkte in data sind Zeilenweise angeordnet, mit 3 Spalten für die 3 Dimensionen
     * @param data Datenmatrix, aus dem der Median berechnet werden soll (n * 3)
     * @param transform Mit dieser Transformation werden die Datenpunkte vor
     * der Berechnung des Medians transformiert. transform ist eine 4*4 Matrix. Bei transform == 0 wird keine
     * Transformation durchgeführt.
     * @return der Median der Transformierten Datenmatrix.
     */
    private void getMedian(VoxelIterator data, DoubleMatrix2D transform, double[] median) {
        int rows = data.size();
        Algebra alg = new Algebra();
        double[] point = new double[4];
        DoubleMatrix2D tempMatrix = DoubleFactory2D.dense.make(rows, 3);
        DoubleMatrix1D row;
        DoubleMatrix1D transRow;
        int count = 0;

        //Transformation der Datenpunkte, falls Transformationsmatrix
        //vorhanden
        if (transform != null) {
            while (data.hasNext()) {
                data.next(point);
                point[3] = 1.0;
                row = new DenseDoubleMatrix1D(point);
                transRow = alg.mult(transform, row);
                for (int i = 0; i < 3; i++) {
                    tempMatrix.setQuick(count, i, transRow.getQuick(i));
                }
                count++;
            }
        } else {
            while (data.hasNext()) {
                data.next(point);
                for (int i = 0; i < 3; i++) {
                    tempMatrix.setQuick(count, i, point[i]);
                }
                count++;
            }            
        }
        double erg;
        for (int i = 0; i < 3; i++) {
            DoubleMatrix1D sortVec = tempMatrix.viewColumn(i);
            DoubleMatrix1D sortVec1 = sortVec.viewSorted();
            if (rows % 2 == 0) {
                erg = sortVec1.getQuick((rows / 2) - 1)
                      + sortVec1.getQuick((rows / 2)) / 2.0;
            } else {
                erg = sortVec1.getQuick((rows / 2));
            }
            median[i] = erg;        }
    } 
    
 
        

}
