/*
 * MCWarpingRegStrategy.java
 *
 * Created on 16. Mai 2002, 11:34
 */

package org.wewi.medimg.reg;

import java.util.Iterator;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Neighborhood3D18;
import org.wewi.medimg.image.geom.Point3D;
//import org.wewi.medimg.image.geom.transform.DisplacementField3D;
import org.wewi.medimg.image.geom.transform.Transformation;
//import org.wewi.medimg.math.geom.DoubleDataPoint;
import org.wewi.medimg.reg.interpolation.InterpolStrategy;
import org.wewi.medimg.reg.metric.AffinityMetric;

import cern.jet.random.engine.MersenneTwister64;
import cern.jet.random.engine.RandomEngine;
/**
 *
 * @author  werner weiser
 * @version 
 */
public class MCWarpingRegStrategy implements Registrator {
    
    private InterpolStrategy weightStrategy;
    
    public int act = 0;
    public int act1 = 0;
    public int act2 = 0;    
    private AffinityMetric affinityMetric;
    
    private ImageGrid sourceGrid;
    private ImageGrid targetGrid;    
    
    private RegisterParameter param;
    
    private static final double epsilon = 0.05;

    private static final int DIM = 3;
    
    /** Creates new MCWarpingRegStrategy */
    public MCWarpingRegStrategy() {
        super();

    }    
    
    public Transformation calculate(RegisterParameter param) throws RegistrationException {
       /*
        //DisplacementField3D erg = new 
        double[] point1;
        double[] point2;
        GridElement grid1;
        GridElement grid2;        
        this.param = param;
        
        int[] minimal = new int[3];
        int cou = 0;
        
        sourceGrid = new ImageGrid(param.getSourceImage());
        targetGrid = new ImageGrid(param.getTargetImage());
        sourceGrid.setGridSize(25, 25, 8);
        targetGrid.setGridSize(25, 25, 8);
        //sourceGrid.setGridOffset((param.temp % 2) * 19, (param.temp % 2) * 19, 0);
        //targetGrid.setGridOffset((param.temp % 2) * 19, (param.temp % 2) * 19, 0);
        //sourceGrid.setGridOffset(0, 0, 0);
        //targetGrid.setGridOffset(0, 0, 0);        
        sourceGrid.init();
        targetGrid.init();
        sourceGrid.first();
        targetGrid.first();
        //System.out.println(" ********** " + ((param.temp % 2) * 20));
         while (sourceGrid.hasNext()) {
            grid1 = sourceGrid.next();
            minimal = grid1.getLocation();
            System.out.println(" Test minimal " + minimal[0] + " , " + minimal[1] + " , " + minimal[2] );
            System.out.println("grid1 verify " + grid1.getSize(0) + " , " + grid1.getSize(1) + " , " + grid1.getSize(2));
            cou++;
         }
        System.out.println(" Test minimal cou " + cou );
        sourceGrid.first();        
        randomWalk(sourceGrid, targetGrid, 500);
        DisplacementField3D retVal = new DisplacementField3D(param.getSourceImage());
        sourceGrid.first();
        System.out.print("{");
        while (sourceGrid.hasNext()) {
            grid1 = sourceGrid.next();    
            if (grid1.referencePointExists()) {
                grid2 = targetGrid.getElement(sourceGrid.getPosition());
                if ( grid2 != null) {
                    if (grid2.referencePointExists()) {
                        //Displacementvector in Displacementfield eintragen
                        point1 = grid1.getReferencePoint();
                        point2 = grid2.getReferencePoint();
                        point2[0] = point2[0] - point1[0];
                        point2[1] = point2[1] - point1[1];
                        point2[2] = point2[2] - point1[2];
                        System.out.println("{{" + point1[0] + "," + point1[1] + "},{" + point2[0] + "," + point2[1] + "}},");  
                        retVal.addDisplacementVector(new DoubleDataPoint(point2), new DoubleDataPoint(point1));
                        cou++;
                    }
                }
            }
        }
        System.out.println("}");
        System.out.println("grids mit referenzpunkten: " + cou);
        // funktioniert DiscplacemnetField3D ?
        for( int i = 0; i < 500; i = i + 100) {
            for( int j = 0; j < 500; j = j + 100) {
                point2 = new double[3];
                point1 = new double[3];
                point1[0] = 10;
                point1[1] = 20;
                point1[2] = 0;
                point2[0] = i;
                point2[1] = j;
                point2[2] = 0;
                retVal.addDisplacementVector(new DoublePoint3D(point1), new DoublePoint3D(point2));
            }
        }
        System.out.println("ergebnis = " + act + " Vektoren");
        System.out.println("ergebnis = " + act1 + " Vektoren");        
        return retVal;
        */
        return null;
    }
    
    private void randomWalk(ImageGrid sG, ImageGrid tG, int duration) {
        GridElement grid;
        double[] trip = new double[3];
        int[] tripI = new int[3];
        int[] minimal = new int[3];
        sG.first();
        int cou = 0;
        // default seed
        RandomEngine twister = new MersenneTwister64();
        while (sG.hasNext()) {
            grid = sG.next();
            //grid = sG.getElement(1023);
            minimal = grid.getLocation();
            for (int j = 0; j < duration; j++) {            
                for (int i = 0; i < trip.length; i++) {
                    trip[i] = twister.raw();
                    //System.out.println(" trip[i] 1 " + trip[i] );                    
                    // Lage im GridElement
                    trip[i] = trip[i] * (grid.getSize(i) - 1);
                    //System.out.println(" minimal " + minimal[0] + " , " + minimal[1] + " , " + minimal[2] );                    
                    //System.out.println(" grid.getSize(i) - 1 " + (grid.getSize(i) - 1) + " act " + sG.act );                      
                    //Lage im GesamtGrid
                    trip[i] = minimal[i] + trip[i];
                    //System.out.println(" trip[i] 3 " + trip[i] );                      
                    // kann nur Voxel vergleichen
                     
                    trip[i] = Math.floor(trip[i]);
                    //System.out.println(" trip[i] 4 " + trip[i] ); 
                    tripI[i] = (int)trip[i];
                    //System.out.println(" tripI[i] 5 " + tripI[i] );                     
                }
                if (srcNeighbourhoodControl(tripI, grid)) {
                    grid.addReferencePoint(tripI);
                    //System.out.println(" point with neghbor " + tripI[0] + " , " + tripI[1] + " , " + tripI[2] ); 
                    //System.out.println(" minimal " + minimal[0] + " , " + minimal[1] + " , " + minimal[2] ); 
                    act1++;
                }
            }
            cou++;
            //double[] p;
            if (grid.referencePointExists()) {
                //p = grid.getReferencePoint();
                //tG.addGridElement(p, sG.getPosition());
                randomMeeting(tG, sG.getPosition(), duration);                
            }
        }
        System.out.println(" Grid durchlaufen " + cou );   
        //sG.first();
        //tG.first();
    }
    
    private boolean srcNeighbourhoodControl(int[] pos, GridElement grid) {
        Image image = (Image)param.getSourceImage();
        int[] minimal = new int[3];
        minimal = grid.getLocation();
                   // System.out.println(" minimal " + minimal[0] + " , " + minimal[1] + " , " + minimal[2] );
        Point3D point3D = new Point3D(pos[0], pos[1], pos[2]);
            //System.out.println(" pos " + point3D.getX() + " , " + point3D.getY() + " , " + point3D.getZ());       
        int color = image.getColor(pos[0], pos[1], pos[2]);
            //System.out.println(" grid " + grid.getSize(0) + " , " + grid.getSize(1) + " , " + grid.getSize(2) + " color " + color);        
        Neighborhood3D18 neighbor = new Neighborhood3D18(minimal[0], (minimal[0] + grid.getSize(0) - 1),
                                                         minimal[1], (minimal[1] + grid.getSize(1) - 1),
                                                         minimal[2], (minimal[2] + grid.getSize(2) - 1));
        Iterator nit = neighbor.getNeighbors(point3D);
        while (nit.hasNext()) {
            point3D = (Point3D)nit.next();
            //System.out.println(" neighbour " + point3D.getX() + " , " + point3D.getY() + " , " + point3D.getZ());
            if (image.getColor(point3D.getX(), point3D.getY(), point3D.getZ()) != color) {
                return true;
            }
        }
        return false;    
    }
    
    private boolean trgNeighbourhoodControl(int[] pos, GridElement grid) {
        Image image = (Image)param.getTargetImage();
        int[] minimal = new int[3];
        minimal = grid.getLocation();
        Point3D point3D = new Point3D(pos[0], pos[1], pos[2]);
        int color = image.getColor(pos[0], pos[1], pos[2]);
        Neighborhood3D18 neighbor = new Neighborhood3D18(minimal[0], (minimal[0] + grid.getSize(0) - 1),
                                                         minimal[1], (minimal[1] + grid.getSize(1) - 1),
                                                         minimal[2], (minimal[2] + grid.getSize(2) - 1));
        Iterator nit = neighbor.getNeighbors(point3D);
        while (nit.hasNext()) {
            point3D = (Point3D)nit.next();
            if (image.getColor(point3D.getX(), point3D.getY(), point3D.getZ()) != color) {
                return true;
            }
        }
        return false;    
    }
    
    private void randomMeeting(ImageGrid tG, int position, int duration) {
        GridElement grid;
        double[] trip = new double[3];
        int[] tripI = new int[3];
        int[] minimal = new int[3];
      
        // default seed
        RandomEngine twister = new MersenneTwister64();            
        grid = tG.getElement(position);
        minimal = grid.getLocation();
        for (int j = 0; j < duration; j++) {            
            for (int i = 0; i < trip.length; i++) {
                trip[i] = twister.raw();
                // Lage im GridElement
                trip[i] = trip[i] * (grid.getSize(i) - 1);
                //Lage im GesamtGrid
                trip[i] = minimal[i] + trip[i];
                // kann nur Voxel vergleichen
                trip[i] = Math.rint(trip[i]);
                tripI[i] = (int)trip[i];
            }
            if (trgNeighbourhoodControl(tripI, grid)) {
                grid.addReferencePoint(tripI);
                    //System.out.println(" point with neghbor " + tripI[0] + " , " + tripI[1] + " , " + tripI[2] ); 
                    //System.out.println(" minimal " + minimal[0] + " , " + minimal[1] + " , " + minimal[2] ); 
                act++;
            }
        }        
    }
	/**
	 * @see org.wewi.medimg.reg.Registrator#registrate(Image, Image)
	 */
	public Transformation registrate(Image source, Image target) {
		return null;
	}

}
