/*
 * MLKMeansClusterer.java
 *
 * Created on 24. Juli 2002, 20:08
 */

package org.wewi.medimg.seg.stat;

import java.util.Arrays;
import java.util.Random;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.seg.Clusterer;
import org.wewi.medimg.seg.ObservableSegmenter;


/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
public class MLKMeansClusterer extends ObservableSegmenter implements Clusterer {
    protected final static int MAX_ITERATION = 50;
    protected final static double ERROR_LIMIT = 0.001;
    
    protected final int k;
    protected double[] mean;
    protected double[] meanOld;
    

	/**
	 *
	 * @param k Anzahl der zu segmentierenden Merkmalen.
	 */
    public MLKMeansClusterer(int k) {
        this.k = k;
        mean = new double[k];
        meanOld = new double[k];
        Arrays.fill(mean, 0);
        Arrays.fill(meanOld, 0);
    }
    
	/**
	 * Method initMeans.
	 * @param cr
	 */
    protected void initMeans(ColorRange cr) {
        Random random = new Random(System.currentTimeMillis());
        
        double range = cr.getMaxColor()-cr.getMinColor();
        for (int i = 0; i < k; i++) {
            mean[i] = (random.nextDouble()*range) + (double)cr.getMinColor();
            meanOld[i] = mean[i];
        }
        Arrays.sort(mean);  
        Arrays.sort(meanOld);        
    }
    
	/**
	 * Method createSegimgOld.
	 * Diese Methode wird von der Klasse
	 * MAPKMeansClusterer �berschrieben. Dies 
	 * erm�glicht der Klasse, vor Begin des 
	 * Segmentiervorgangs eine Kopie des segmentierten
	 * Bildes zu erzeugen.
	 * 
	 * @param segimg Image das kopiert wird.
	 */
    protected void createSegimgOld(Image segimg) {
    }
    
	/**
	 * @see org.wewi.medimg.seg.Segmenter#segment(Image)
	 */
    public Image segment(Image mrt) {
        Image segimg = (Image)mrt.clone();
        segimg.resetColor(0);
        segment(mrt, segimg);
        return segimg;
    }
    
	/**
	 * @see org.wewi.medimg.seg.Segmenter#segment(Image, Image)
	 */
    public void segment(Image mrt, Image segimg) {
        int iterationCount = 0;
        
        createSegimgOld(segimg);
        initMeans(mrt.getColorRange());
        do {
            m1Step(mrt, segimg);
            m2Step(mrt, segimg);
            iterationCount++;
        } while(ERROR_LIMIT < error() &&
                MAX_ITERATION >= iterationCount);        
    }
    
	/**
	 * Method m1Step, wie im Algorithmus berschrieben.
	 * 
	 * @param mrt
	 * @param segimg
	 */
    private void m1Step(Image mrt, Image segimg) {
        int size = mrt.getNVoxels();
        int minDistanceFeature;
        int color;
        double cp, distance, minDistance;

        for (int i = 0; i < size; i++) {
            color = mrt.getColor(i);
            minDistance = Integer.MAX_VALUE;
            minDistanceFeature = 0;
            
            //Suchen jenes Merkmals mit geringstem 
            //Abstand zum Merkmalsmittelwert.
            for (int f = 0; f < k; f++) {
                distance = mean[f] - (double)color;
                distance *= distance;
                cp = getCliquesPotential(i, f); 
                distance += cp;
                if (distance < minDistance) {
                    minDistanceFeature = f;
                    minDistance = distance;
                }
            } 
            
            saveOldFeatureColor(i, segimg.getColor(i));
            segimg.setColor(i, minDistanceFeature);
        }            
    }
    
	/**
	 * Method m2Step, wie im Algorithmus beschrieben.
     * 
	 * @param mrt das zu segmentierende Bild
	 * @param segimg das segmentierte Bild
	 */
    private void m2Step(Image mrt, Image segimg) {
        long[] meanSum = new long[k];
        int[] meanNo = new int[k];
        Arrays.fill(meanSum, 0);
        Arrays.fill(meanNo, 0);

        int f = 0;
        int size = mrt.getNVoxels();
        for (int i = 0; i < size; i++) {
            f = segimg.getColor(i);
            meanSum[f] += (long)mrt.getColor(i);
            meanNo[f]++;
        }
        
        System.arraycopy(mean, 0, meanOld, 0, mean.length);

        for (int i = 0; i < k; i++) {
            if (meanNo[i] == 0) {
                mean[i] = 0;
            } else {
                mean[i] = (double)meanSum[i] / (double)(meanNo[i]);
            }
        }
        Arrays.sort(mean);  
    }    
    
	/**
	 * Methode error. Berechnet den Fehler zwischen zwei Iterationen.
     * Dies dient als Abbruchkriterium.
     * 
	 * @return double
	 */
    protected double error() {
        double err = 0;
        for (int i = 0; i < k; i++) {
            err += Math.abs(mean[i] - meanOld[i]);
        }
        return err;
    }
    
	/**
	 * Method saveOldFeatureColor.
	 * Diese Methode wird von der Klasse MAPKMeansClusterer
	 * �berschrieben, da bei diesem Algorithmus notwendig ist,
	 * sich das Merkmalsbild der vorigen Iteration zu merken.
	 * 
	 * @param pos
	 * @param color
	 */
    protected void saveOldFeatureColor(int pos, int color) {
    }
    
	/**
	 * Method getCliquesPotential.
	 * Diese Methode liefert in dieser Klasse null zur�ck.
	 * Wird von der Klasse MAPKMeansClusterer �berschrieben.
	 * 
	 * @param pos Position des Bildpunktes
	 * @param f Numer des Merkmals
	 * @return double das berechnete Cliquenpotential Vc.
	 */
    protected double getCliquesPotential(int pos, int f) {
        return 0;
    }
    
}
