/*
 * PCA.java, created on 19.09.2003
 *
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

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.geom.transform.AffineTransformation;
import org.wewi.medimg.image.io.BMPReader;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.io.TIFFWriter;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Sorting;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.EigenvalueDecomposition;
import cern.colt.matrix.linalg.LUDecomposition;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class PCA {


    public PCA() {
    }


	public AffineTransformation getTransformation(Image source, Image target) {
		Algebra alg = new Algebra();
        
		//Berechnung der Schwerpunkte und Hauptachsen und Mediane
		double[] cog1 = new double[3];
		double[] cog2 = new double[3];
              
		centreOfGravity(source, cog1);
		centreOfGravity(target, cog2);   
        
		//Berechnung der Kovarianzmatrix
		double[] eigenValues1 = new double[3];
		double[] eigenValues2 = new double[3];

		double[][] a1 = getHotelingTransformation(source, cog1, eigenValues1);
		double[][] a2 = getHotelingTransformation(target, cog2, eigenValues2);
        
		DoubleMatrix2D A1 = new DenseDoubleMatrix2D(a1);
		DoubleMatrix2D A2 = new DenseDoubleMatrix2D(a2);
        
		double[] scalingFactors = new double[3];
		calculateScaling(scalingFactors, eigenValues1, eigenValues2);
		DoubleMatrix2D As = DoubleFactory2D.dense.make(4, 4);
		for(int i = 0; i < 3; i++) {
			As.setQuick(i, i, scalingFactors[i]);
		} 
		As.setQuick(3, 3, 1.0);
System.out.println("Scalierungsmatrix:");
System.out.println(As);		
        
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

	public void centreOfGravity(Image image, double[] cog) {
		long[] result = new long[3];
		Arrays.fill(result, 0);
		int[] point = new int[3];
		
		long weightSum = 0;
		int weight = 0;
		for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
			weight = it.next(point);
			result[0] += point[0]*weight;
			result[1] += point[1]*weight;
			result[2] += point[2]*weight; 
			weightSum += weight;                       
		}
		
		for (int i = 0; i < 3; i++) {
			cog[i] = (double)result[i]/(double)weightSum;
		}
	}
	
	
	public double[][] getHotelingTransformation(Image image, double[] cog, double[] eigenValues) {
		//Calculating the covariance matrix
		double[] point = new double[3];
		double[][] matrix = new double[3][3];
		for (int i = 0; i < 3; i++) {
			Arrays.fill(matrix[i], 0);
		}
		
		int count = 0;
		for (VoxelIterator data = image.getVoxelIterator(); data.hasNext();) {
			
			int color = data.next(point);
			for (int i = 0; i < 3; i++) {
				//point[i] *= color;
				point[i] -= cog[i];
				//point[i] /= color;
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					matrix[i][j] += point[i]*point[j]*color;
				}
			}
			
			count++;
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

		return A.toArray();
	}
	
	private void calculateScaling(double[] scalingFactors, double[] eigenValues1, double[] eigenValues2) {
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
	
	
	
	
	public static void main(String[] args) {
		String sourceFile = "/home/fwilhelm/Workspace/Projekte/Diplom/code/data/GreyB.bmp";
		String targetFile = "/home/fwilhelm/Workspace/Projekte/Diplom/code/data/GreyA.tif";
		String resultFile = "/home/fwilhelm/Workspace/Projekte/Diplom/code/data/RESULT";
		
		Image source = null;
		Image target = null;
		try {
			System.out.println("Reading images");
			ImageReader reader = new BMPReader(IntImageFactory.getInstance(), sourceFile);
			reader.read();
			source = reader.getImage();
			reader = new TIFFReader(IntImageFactory.getInstance(), targetFile);
			reader.read();
			target = reader.getImage();
			
			System.out.println("Calculating transformation");
			PCA pca = new PCA();
			AffineTransformation trans = pca.getTransformation(source, target);
			
			System.out.println("Performing transformation");
			Image result = trans.transform(source);
			
			System.out.println("Writing images");
			ImageWriter writer = new TIFFWriter(result, resultFile);
			writer.write();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}


















