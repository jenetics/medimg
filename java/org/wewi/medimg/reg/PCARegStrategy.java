/*
 * PCARegStrategy.java
 *
 * Created on 26. März 2002, 15:36
 */

package org.wewi.medimg.reg;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.EigenvalueDecomposition;
import cern.colt.matrix.linalg.LUDecomposition;

import org.wewi.medimg.util.Timer;

import org.wewi.medimg.image.Tissue;

import org.wewi.medimg.reg.interpolation.InterpolStrategy;

import org.wewi.medimg.reg.metric.AffinityMetric;

import org.wewi.medimg.reg.metric.TissueIterator;
import org.wewi.medimg.reg.metric.TissueData;

import org.wewi.medimg.image.geom.transform.AffineTransform3D;
import org.wewi.medimg.image.geom.transform.Transform;
import org.wewi.medimg.image.geom.transform.TransformVector;

import org.wewi.medimg.reg.wizard.RegistrationEvent;

/**
 *
 * @author  werner weiser
 * @version 
 */
public class PCARegStrategy implements RegStrategy {

    
    private InterpolStrategy weightStrategy;

    private AffinityMetric affinityMetric;
    
    private static final double epsilon = 0.05;

    private static final int DIM = 3;
    
    /** Creates new PCARegStrategy */
    public PCARegStrategy(InterpolStrategy strategy, AffinityMetric metric) {
        super();
        weightStrategy = strategy;
        affinityMetric = metric;
    }



    /*private inline int printGSLMatrix(gsl_matrix* m) {

        for (int i = 0; i < m->size1; i++) {
            for (int j = 0; j < m->size2; j++) {
                std::cout << gsl_matrix_get(m, i, j) << "  "; 
            }
            std::cout << std::endl;
        }

        std::cout << std::endl;

        return 0;
    }*/
    
   /**
     * Finden einer ersten Näherung einer rigiden Transformation (Rotation und Translation), welche die Punktmenge points1 in
     * die Punktmenge points2 Überführt. Die Reihenfolge der Punkte ist nicht ausschlaggebend. Bei reine
     * Rotationen und Translationen, wird eine genaue Lösung gefunden. Bei Verzerrungen
     * ist die gefundene Transformation sehr nahe an der tatsächlichen Transformation.
     *
     * @param points1 Punktmenge eines Merkmals aus Bild 1
     * @param points2 Punktmenge eines Merkmals aus Bild 2
     * @param trans Aus points1 und points2 berechnete Transformation.
     *
     * @return Rückgabe des Parameters trans.
     */
    public Transform calculate(RegisterParameter param) throws RegistrationException {
        
        Timer timer1 = new Timer("PCARegistration: calculate: initialize");
        timer1.start();
        int i;
        int count = 0;
        TransformVector transformResult = new TransformVector();
        TissueData it1 = param.getSourceTissueData();
        TissueData it2 = param.getTargetTissueData();

        //it1.goToFirst();
        //it2.goToFirst();
        affinityMetric.setSourceTissueData(it1);
        affinityMetric.setTargetTissueData(it2);

        timer1.stop();
        timer1.print();

        System.out.println("BEGIN TRANSFORMCALC" + '\n');
        Timer timer2 = new Timer("PCARegistration: calculate: getTransforms");
        timer2.start();
        AffineTransform3D transform;
        for (i = 0; i < Tissue.TISSUES.length; i++) {
        //while (it1.hasNext()) {
            TissueIterator ti1 = it1.getTissueIterator(Tissue.TISSUES[i]);
            if (ti1.hasNext()) {
                TissueIterator ti2 = it2.getTissueIterator(Tissue.TISSUES[i]);
                //if (it2.contains(ft)) {
                if (ti2.hasNext()) {
                    Tissue ft = Tissue.TISSUES[i];
                    System.out.println(" Feature " + ft.toString() + " Punkte Bild1 " + ti1.getNPoints() + " Punkte Bild2 " + ti2.getNPoints());
                    try {	
                            transform = getTransform(ti1, ti2); 
                            transformResult.add(ft, transform, ti1.getNPoints());
                            affinityMetric.setTransformation(transformResult.getTransformByTissue(ft));
                            System.out.println(" Abbildung für Tissue " + ft.toString());
                            transformResult.insertFitness(affinityMetric.similarity());				
                    } catch (RegistrationException re) {
                            System.out.println("Abbildung fuer Feature " + ft.toString() + " entfernt");
                            re.printStackTrace();
                            //System.out.println("" + re + '\n');
                    }  
                }
            }
        }
        timer2.stop();
        timer2.print();
        //Durchführen einer Mittelung der Einzeltransformationen
        //zu einer Gesamttransformation.
        Timer timer3 = new Timer("PCARegistration: calculate: interpolate");
        timer3.start();
        AffineTransform3D retVal = (AffineTransform3D)(weightStrategy.interpolate(transformResult));
        timer3.stop();
        timer3.print();
        Timer timer4 = new Timer("PCARegistration: calculate: cleaning");        
        timer4.start();
        System.out.println(retVal);
        System.out.println("END TRANSFORMCALC" + '\n');

        //Cleaning the iterators
        timer4.stop();
        timer4.print();
        return retVal;
    }    

    /**
     * Liefert die Transformation zu zwei beliebigen Einzelmerkmalen
     * @param sf1 Merkmal 1
     * @param sf2 Merkmal 2
     * @throws ERegistrationException, wenn die Berechnung einer Transformation nicht möglich ist.
     */
    private AffineTransform3D getTransform(TissueIterator ti1, TissueIterator ti2) throws RegistrationException {
        AffineTransform3D trans = new AffineTransform3D();

        //double[] dataVec1 = sf1.getPoints();
        //double[] dataVec2 = sf2.getPoints();
        double[] temp = new double[3];
        DoubleFactory2D factory2D;
        factory2D = DoubleFactory2D.dense;
        DoubleFactory1D factory1D;
        factory1D = DoubleFactory1D.dense;
        Algebra alg = new Algebra();
        //System.out.println("sf1 n " + sf1.getNPoints() + " sf2 n " + sf2.getNPoints());
        DoubleMatrix2D data1 = factory2D.make(ti1.getNPoints(), 3);    
        DoubleMatrix2D data2 = factory2D.make(ti2.getNPoints(), 3);     
        int i, j;
        i = 0;
        ti1.goToFirst();
        while (ti1.hasNext()) {
        //for (i = 0; i < data1.rows(); i++) {
            // int[] temp = tit.next();
            temp = (double[])ti1.next();
            data1.setQuick(i, 0, temp[0]);
            data1.setQuick(i, 1, temp[1]);
            data1.setQuick(i, 2, temp[2]);
            i++;
            /*if ( i % 2000 == 0 ) {
                System.out.println(" i " + i + " x " + temp[0] + " y " + temp[1] + " z " + temp[2]);
            }*/
        }
        i = 0;
        ti2.goToFirst();
        while (ti2.hasNext()) {
        //for (i = 0; i < data1.rows(); i++) {
            // int[] temp = tit.next();
            temp = (double[])ti2.next();
            data2.setQuick(i, 0, temp[0]);
            data2.setQuick(i, 1, temp[1]);
            data2.setQuick(i, 2, temp[2]);
            i++;
        }
        //Berechnung der Schwerpunkte und Hauptachsen und Mediane
        DoubleMatrix1D cog1 = centreOfGravity(data1);
        /*cog1.setQuick(0, 213.293);
        cog1.setQuick(1, 240.521);
        cog1.setQuick(2, 0.0);*/
        DoubleMatrix1D cog2 = centreOfGravity(data2);
        /*cog2.setQuick(0, 195.076);
        cog2.setQuick(1, 242.379);
        cog2.setQuick(2, 0.0);*/
        DoubleMatrix1D eigen1 = factory1D.make(DIM);  
        DoubleMatrix1D eigen2 = factory1D.make(DIM); 
        //System.out.println("cog1" + cog1);
        //System.out.println("cog2" + cog2);

        //Achtung TODO
        DoubleMatrix2D A1 = getHotellingTransform(data1, cog1, eigen1);
        DoubleMatrix2D A2 = getHotellingTransform(data2, cog2, eigen2);
        //System.out.println("A1" + A1);
        //System.out.println("A2" + A2);
        //System.out.println("eigen1" + eigen1);
        //System.out.println("eigen2" + eigen2);           
        //Bei der Transformation A2 wird die Skalierung in
        //Richtung der Hauptachsen berechnet und in die Transformation eingefügt.
        //   As = |sx  0  0 |
        //        |0   sy 0 |
        //        |0   0  sz|
        //
        //   sx = sqrt(eigen1X / eigen2X)
        //   sy = sqrt(eigen1Y / eigen2Y)
        //   sz = sqrt(eigen1Z / eigen2Z)
        DoubleMatrix2D As = factory2D.make(DIM+1, DIM+1); 
        As.setQuick(DIM, DIM, 1);

        //Prüfen auf unzulässige Eigenwerte
        double e1x = eigen1.getQuick(0);
        double e1y = eigen1.getQuick(1);
        double e1z = eigen1.getQuick(2);
        double e2x = eigen2.getQuick(0);
        double e2y = eigen2.getQuick(1);
        double e2z = eigen2.getQuick(2);
        double sx, sy, sz;
        if (e1x == 0 && e2x == 0) {
                    sx = 1;
        } else if (e1x != 0 && e2x != 0) {
           sx = Math.sqrt(e1x / e2x);
        } else {
           throw new RegistrationException("Invalid Transformation: eigenvalue x = zero \n");
        }
        if (e1y == 0 && e2y == 0) {
           sy = 1;
        } else if (e1y != 0 && e2y != 0) {
                    sy = Math.sqrt(e1y / e2y);
        } else {
           throw new RegistrationException("Invalid Transformation: eigenvalue y = zero \n");
        }
        if (e1z == 0 && e2z == 0) {
           sz = 1;
        } else if (e1z != 0 && e2z != 0) {
           sz = Math.sqrt(e1z / e2z);
        } else {
           throw new RegistrationException("Invalid Transformation: eigenvalue z = zero \n");
        }
        
        As.setQuick(0, 0, sx);
        As.setQuick(1, 1, sy);
        As.setQuick(2, 2, sz);
        As.setQuick(3, 3, 1.0);
        //System.out.println("As" + As);
        //Before scaling, storing the rotation
        DoubleMatrix2D R = A2.copy();

        //Die neue Transformationsmatrix A2 ergibt sich folgendermaßen:
        //    A2 = As*A2
        //System.out.println("A2*As" + A2);
        //System.out.println("As" + As);         
        A2 = alg.mult(As, R);
        //System.out.println("A2 nach Skal" + A2);        
        //Ende der Skalierung
        //Von A2 wird für die Gesamttransformation die Inverse benötigt
        //A2 orginal wird vernichtet
        LUDecomposition lu = new LUDecomposition(A2);
        DoubleMatrix2D identity = factory2D.make(DIM+1, DIM+1); 
        identity.assign(0.0);
        identity.setQuick(0, 0, 1.0);
        identity.setQuick(1, 1, 1.0);
        identity.setQuick(2, 2, 1.0);
        identity.setQuick(3, 3, 1.0); 
        //System.out.println("A2*As" + A2); 
        DoubleMatrix2D A2Inv = lu.solve(identity);
        //DoubleMatrix2D A2Inv = alg.inverse(A2);
        //Vorsicht vielleicht LU-Decomposition
        //System.out.println("A2Inv" + A2Inv);
        //Die Gesamttransformation ergibt sich dann aus A2^-1*A1
        A2 = alg.mult(A2Inv, A1);
        //System.out.println("A2gesamt" + A2);
        //The Inverse of R
        DoubleMatrix2D RInv = alg.inverse(R);
        //identity = alg.mult(RInv, R);
        //System.out.println("identity test" + identity);
        R = alg.mult(RInv, A1);

        //Setting the rotation
        double[][] rot = new double[3][3];
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 3; j++) {
                rot[i][j] = R.getQuick(i, j);
            }
        }
        trans.setRotationMatrix(rot);

        //Feststellen des Verschiebungsanteils
        //Dazu muß der erste Schwerpunkt der Gesamttransformation A2
        //transformiert werden; und daraus läßt sich erst die 
        //wirkliche Verschiebung errechnen.
        DoubleMatrix2D t = factory2D.make(DIM, DIM);   
        for (i = 0; i < DIM; i++) {
            for (j = 0; j < DIM; j++) {
                t.setQuick(i, j, A2.getQuick(i, j));
           }
        }
        DoubleMatrix1D _cog1 = alg.mult(t, cog1);
        //System.out.println(" _cog1 " + _cog1);
        _cog1.assign(cog2, cern.jet.math.Functions.minus);
        //System.out.println(" _cog1 " + _cog1);
        //Einbauen der Verschiebung in die Matrix
        for (i = 0; i < DIM; i++) {
           A2.setQuick(i, 3, (-1) * _cog1.getQuick(i));
        }

        //Füllen der Transformationsmatrix
        for (i = 0; i < 4; i++) {
           for (j = 0; j < 4; j++) {
               trans.setElement(i, j, A2.getQuick(i, j));
           }
        }

        //With the rotation part only and the whole transformation
        //we can calculate the scaling/transforming part.
        for (i = 0; i < DIM; i++) {
            R.setQuick(i, 3, 0.0);
        }
        RInv = alg.inverse(R);
        R = alg.mult(A2, RInv);
        double[][] scaleTrans = new double[3][4];
        for (i = 0; i < 3; i++) {
            for (j = 0; j < 4; j++) {
                scaleTrans[i][j] = R.getQuick(i, j);
            }
        }
        trans.setTransScalingMatrix(scaleTrans);
        System.out.println("******************************");
        trans.printRot();
        System.out.println("******************************");
        System.out.println(trans);   
        System.out.println("******************************");
        trans.printScale();
        System.out.println("******************************");
        //trans.print();
        return trans;
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
    private DoubleMatrix2D getHotellingTransform(DoubleMatrix2D data, DoubleMatrix1D cog, DoubleMatrix1D eigenValues) {
           int p = data.columns(); //Dimension
           int n = data.rows(); //Anzahl der Punkte
           //Berechnen der Kovarianzmatrix
           DoubleFactory2D factory2D;
           factory2D = DoubleFactory2D.dense;
           DoubleFactory1D factory1D;
           factory1D = DoubleFactory1D.dense;      
           //System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");           
           DoubleMatrix2D mx1 = factory2D.make(p, 1);  
           DoubleMatrix2D mx2 = factory2D.make(p, 1);            
           DoubleMatrix2D mxt = factory2D.make(p, p);  
           DoubleMatrix2D xi1 = factory2D.make(p, 1);  
           DoubleMatrix2D xi2 = factory2D.make(p, 1);            
           DoubleMatrix2D xit = factory2D.make(p, p);  
           DoubleMatrix2D Cx = factory2D.make(p, p);  
           Algebra alg = new Algebra();
           mx1.viewColumn(0).assign(cog);
           //System.out.println("mx1" + mx1);
           mx2 = alg.transpose(mx1);
           mxt = alg.mult(mx1, mx2);
           //System.out.println("mx2" + mx2);
           //System.out.println("mxt" + mxt);
           int i, j;
           for (i = 0; i < n; i++) {
               for (j = 0; j < p; j++) {
                   xi1.setQuick(j, 0, data.getQuick(i, j));
               }
               xi2 = alg.transpose(xi1);
               xit = alg.mult(xi1, xi2);
               /*if(i % 2000 == 0) {
                   System.out.println("xi1" + xi1);
                   System.out.println("xi2" + xi2);
                   System.out.println("xit" + xit);               
               }*/               
               xit.assign(mxt, cern.jet.math.Functions.minus);
               Cx.assign(xit, cern.jet.math.Functions.plus);
               /*if(i % 2000 == 0) {
                   System.out.println("Cx" + Cx);
                   System.out.println("xit" + xit);               
               }*/                  
           }
           Cx.assign(cern.jet.math.Functions.mult(1.0 / n));
           //Cx.setQuick(0, 1, (-1) * Cx.getQuick(0, 1));
           //Cx.setQuick(1, 0, (-1) * Cx.getQuick(1, 0));
           //System.out.println("Cx" + Cx);
           //Berechnen der Eigenwerte der Kovarianzmatrix
           //DoubleMatrix2D C_temp;            
           //C_temp = Cx.copy();
           DoubleMatrix2D eigenVectors = factory2D.make(p, p);           
           DoubleMatrix2D eigenVectors_us;      
           EigenvalueDecomposition eigen = new EigenvalueDecomposition(Cx);
           //System.out.println("eigen" + eigen);
           DoubleMatrix2D eigenValues2D;
           DoubleMatrix1D eigenValues1D = factory1D.make(p);
           DoubleMatrix1D eigenValues1DSorted;
           eigenVectors_us = eigen.getV();
           eigenValues2D = eigen.getD();
           //System.out.println("eigenVectors_us" + eigenVectors_us);           
           //System.out.println("eigenValues2D" + eigenValues2D);
           for (i = 0; i < p; i++) {
               eigenValues1D.setQuick(i, eigenValues2D.getQuick(i, i));
           }
           eigenValues1DSorted = eigenValues1D.viewSorted();
           eigenValues1DSorted = eigenValues1DSorted.viewFlip();
           for (i = 0; i < p; i++) {
               eigenValues.setQuick(i, eigenValues1DSorted.getQuick(i));
           }
           //System.out.println("eigenValues" + eigenValues);
               //Sortieren der Eigenvektoren nach den größten Eigenwerten.
           //Hauptachse mit dem größten Eigenwert (Varianz) ist der erste Hauptvektor
           int[] indexes = new int[eigenVectors_us.columns()];
           for (i = 0; i < p; i++) {
               indexes[i] = -1;
           }
           for (i = 0; i < p; i++) {
               for (j = 0; j < p; j++) {
                   if (eigenValues1D.getQuick(i) == eigenValues1DSorted.getQuick(j) && indexes[i] == -1) {
                       indexes[i] = j;
                   }
               }
           }
           //for (i = 0; i < p; i++) {
               //System.out.println("Index i " + i + "->" + indexes[i]);
           //}
           for (i = 0; i < p; i++) {
               eigenVectors.viewColumn(i).assign(eigenVectors_us.viewColumn(indexes[i]));
           }
           //Korrektur EigenValueDecomposition
           /*for (i = 0; i < p; i++) {
               if (indexes[i] == 1) {
                    eigenVectors.viewColumn(i).assign(cern.jet.math.Functions.mult(-1.0));
               }
           }*/
           
           //eigenVectors = alg.permuteColumns(eigenVectors_us, indexes, null);
           //System.out.println("eigenVectors" + eigenVectors);            
           //Ergebnistransformation A. Die Eigenwerte sind spaltenweise
           //angeordnet, in die Matrix A müssen sie zeilenweise geschrieben werden.
           DoubleMatrix2D A = factory2D.make(p + 1, p + 1); 
           for (i = 0; i < p; i++) {
               for (j = 0; j < p; j++) {
                   A.setQuick(i, j, eigenVectors.getQuick(j, i));
               }
           }
           A.setQuick(p, p, 1);
           //System.out.println("A in Hotelling" + A);
           //Bestimmen der Verschiebungsmatrix
           DoubleMatrix2D T = factory2D.make(p + 1, p + 1);
           DoubleMatrix2D _A;
           for (i = 0; i < p + 1; i++) {
               T.setQuick(i, i, 1);
           }
           for (i = 0; i < p; i++) {
               T.setQuick(i, 3, (-1)*cog.getQuick(i));
           }
           //System.out.println("T" + T);           
           _A = A.copy();
           A = alg.mult(_A, T);
           //System.out.println("A * T" + A);           

           //Korrektur der Hauptachsen
           DoubleMatrix1D median = getMedian(data, A);
           //System.out.println("median" + median); 
           //Bestimmen der richtigen Ausrichtung der Hauptachse,
           //durch Vorzeichenvergleich der Komponenten des Medians
           for (i = 0; i < p; i++) {
               if (median.getQuick(i) < -epsilon) {
                   for (j = 0; j < p; j++) {
                       A.setQuick(i, j, (-1) * A.getQuick(i, j));
                   }
               }
           }
           //System.out.println("Anach median" + A);
           //System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY");
           return A;
    }    
    
    /**
     * Berechnen des Schwerpunktes
     * @param data Daten, aus denen ein Schwerpunkt berechnet werden soll.
     * Die einzelnen Datenpunkte sind reihenweise angeordnet; in einer n * 3 Matrix
     * @return Schwerpunkt der Datenpunkte
     */
    private DoubleMatrix1D centreOfGravity(DoubleMatrix2D data) {
        int cols = data.columns();
        int rows = data.rows();
        //System.out.println("cols" + cols + " rows " + rows);
        Algebra alg = new Algebra();
        DoubleFactory1D factory1D;
        factory1D = DoubleFactory1D.dense;
        DoubleMatrix1D cog = factory1D.make(cols);
        cog.assign(0.0);
        //DoubleMatrix1D row = factory1D.make(cols);        
        int i;
        for (i = 0; i < rows; i++) {
            DoubleMatrix1D row = data.viewRow(i);
            cog.assign(row, cern.jet.math.Functions.plus);
        }
        //System.out.println("cog" + cog);
        cog.assign(cern.jet.math.Functions.mult(1.0 / rows));
        return cog;
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
    private DoubleMatrix1D getMedian(DoubleMatrix2D data, DoubleMatrix2D transform) {
        int cols = data.columns();
        int rows = data.rows();
        
        DoubleFactory2D factory2D;
        factory2D = DoubleFactory2D.dense;
        DoubleFactory1D factory1D;
        factory1D = DoubleFactory1D.dense;
        Algebra alg = new Algebra();
        //System.out.println("TTTTTTTTTTTTTTEST" + cols + " " + rows);
        DoubleMatrix2D _data = factory2D.make(rows, cols);            
        DoubleMatrix1D row = factory1D.make(cols + 1);
        DoubleMatrix1D _row;

        //Transformation der Datenpunkte, falls Transformationsmatrix
        //vorhanden
        int i, j;
        if (transform != null) {
            //DoubleMatrix1D _row = factory1D.make(cols + 1);
            for (i = 0; i < rows; i++) {
                for (j = 0; j < cols; j++) {
                    row.setQuick(j, data.getQuick(i, j));
                }
                row.setQuick(j, 1.0);
                _row = alg.mult(transform, row);
                for (j = 0; j < cols; j++) {
                    _data.setQuick(i, j, _row.getQuick(j));
                }
            }
        } else {
            _data = data.copy();
        }
        DoubleMatrix1D median = factory1D.make(cols);
        //DoubleMatrix1D sortVec = factory1D.make(rows); 
        double erg;
        for (i = 0; i < cols; i++) {
            DoubleMatrix1D sortVec = _data.viewColumn(i);
            DoubleMatrix1D sortVec1 = sortVec.viewSorted(); 
            //System.out.println("MDEdian i " + i + " little " + sortVec1.getQuick(0) + " middle " + sortVec1.getQuick(rows/2) + " big "  + sortVec1.getQuick(rows - 1));
            if (rows % 2 == 0) {
                erg = sortVec1.getQuick((rows / 2) - 1) + sortVec1.getQuick((rows / 2)) / 2.0;
            } else {
                erg = sortVec1.getQuick((rows / 2));
            }
            median.setQuick(i, erg);
        }
        return median;
    }

    
}
