/*
 * MLKMeansClusterer.java
 *
 * Created on 24. Juli 2002, 20:08
 */

package org.wewi.medimg.seg.stat;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.seg.Clusterer;
import org.wewi.medimg.seg.IterationEvent;
import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.SegmenterEvent;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MLKMeansClusterer extends ObservableSegmenter implements Clusterer {
    
    private class IterationThread extends Thread {
        private MLKMeansClusterer parent;
        private Image mrt;
        private Image segimg;
        
        private boolean cancelled = false;
        private boolean interrupted = false;
        
        public IterationThread(MLKMeansClusterer parent, Image mrt, Image segimg) {
            super();
            this.parent = parent;
            this.mrt = mrt;
            this.segimg = segimg;
        } 
        
        public void run() {
            parent.notifySegmenterStarted(new SegmenterEvent(this));
            
            int iterationCount = 0;
            
            parent.createSegimgOld(segimg);
            parent.initMeans(mrt.getColorRange());
            
            synchronized (this) { 
            
            do {
                
                if (interrupted) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        interrupted = false;
                        continue;
                    }    
                }
                
                if (cancelled) {
                    break;
                }
                
                parent.notifyIterationStarted(new IterationEvent(this));
                
                parent.m1Step(mrt, segimg);
                parent.m2Step(mrt, segimg);
                iterationCount++;
                
                parent.notifyIterationFinished(new IterationEvent(this));
                
                /**************************************************************/
                parent.logger.info("" + iterationCount + ": " + formatMeanValues());
                /**************************************************************/
            } while(parent.ERROR_LIMIT < parent.error() &&
                     parent.MAX_ITERATION >= iterationCount); 
                     
            }
                     
            parent.notifySegmenterFinished(new SegmenterEvent(this));  
            parent.notifyAll();          
        } 
        
        public synchronized void resumeIterator() {
            interrupted = false;
            notifyAll();    
        } 
        
        public void interruptIterator() {
            interrupted = true;
        }
        
        public void cancelInterator() {
            cancelled = false;
        }     
            
    }
    
    
    
    
    
    private static final String SEGMENTER_NAME = "ML-Kmeans-Clusterer";
    
    protected final static int MAX_ITERATION = 50;
    protected final static double ERROR_LIMIT = 0.001;
    
    protected final int k;
    protected double[] mean;
    protected double[] meanOld;
    
    private int iterationCount = 0;
    
    protected IterationThread iterationThread = null;
    

	/**
	 *
	 * @param k Anzahl der zu segmentierenden Merkmalen.
	 */
    public MLKMeansClusterer(int k) {
        super();
        this.k = k;
        mean = new double[k];
        meanOld = new double[k];
        Arrays.fill(mean, 0);
        Arrays.fill(meanOld, 0);
    }
    
	/**
	 * Initialisieren der Anfangsmittelwerte.
     * Diese werden zufällig erzeugt und danach sortiert.
     * 
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
        
        /**********************************************************************/
        logger.info("0: " + formatMeanValues());
        /**********************************************************************/      
    }
    
    
    /**
     * Formatiert die aktuellen Mittelwerte für
     * die Ausgabe.
     */
    private String formatMeanValues() {
        NumberFormat format = NumberFormat.getInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(4);
        format.setMinimumFractionDigits(4);
        
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < k; i++) {
            buffer.append("(");
            buffer.append(format.format(mean[i]));
            buffer.append(")");
        }
        
        return buffer.toString();  
    }
    
	/**
	 * Method createSegimgOld.
	 * Diese Methode wird von der Klasse
	 * MAPKMeansClusterer überschrieben. Dies 
	 * ermöglicht der Klasse, vor Begin des 
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
    
    private void iterate(Image mrt, Image segimg) {
        notifySegmenterStarted(new SegmenterEvent(this));
        
        iterationCount = 0;
        
        createSegimgOld(segimg);
        initMeans(mrt.getColorRange());
        
        do {
            notifyIterationStarted(new IterationEvent(this));
            
            m1Step(mrt, segimg);
            m2Step(mrt, segimg);
            iterationCount++;
            
            notifyIterationFinished(new IterationEvent(this));
            
            /**************************************************************/
            logger.info("" + iterationCount + ": " + formatMeanValues());
            /**************************************************************/
        } while(ERROR_LIMIT < error() &&
                MAX_ITERATION >= iterationCount); 
        notifySegmenterFinished(new SegmenterEvent(this));         
    }
    
	/**
     * 
     * 
	 * @see org.wewi.medimg.seg.Segmenter#segment(Image, Image)
	 */
    public void segment(Image mrt, Image segimg) {
        iterate(mrt, segimg);
        
        Properties segProp = new Properties();
        segProp.put("Segmentiermethode", getClass().getName());
        segProp.put("k", Integer.toString(k));
        segProp.put("Iterationen", Integer.toString(iterationCount));
        for (int i = 0; i < k; i++) {
            segProp.put("mean." + i, Double.toString(mean[i]));    
        }
        
        segimg.getHeader().setImageProperties(segProp);
        
        
        /*
        iterationThread = new IterationThread(this, mrt, segimg);
        iterationThread.start();
        try {
			wait();
		} catch (InterruptedException e) {
            logger.throwing("","",e);
		}
        */
    }
    
	/**
	 * Method m1Step, wie im Algorithmus berschrieben.
     * Zuordnung der Bildpunkte zu jenem Bereich,
     * mit dem geringstem Abstand zum Bereichsmittelwert.
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
     * Neuberechnen der Mittelwerte.
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
	 * überschrieben, da bei diesem Algorithmus notwendig ist,
	 * sich das Merkmalsbild der vorigen Iteration zu merken.
	 * 
	 * @param pos
	 * @param color
	 */
    protected void saveOldFeatureColor(int pos, int color) {
    }
    
	/**
	 * Method getCliquesPotential.
	 * Diese Methode liefert in dieser Klasse null zurück.
	 * Wird von der Klasse MAPKMeansClusterer überschrieben.
	 * 
	 * @param pos Position des Bildpunktes
	 * @param f Numer des Merkmals
	 * @return double das berechnete Cliquenpotential Vc.
	 */
    protected double getCliquesPotential(int pos, int f) {
        return 0;
    }
    
    public void interruptSegmenter() {
        if (iterationThread == null) {
            return;    
        }
    }
    
    public void resumeSegmenter() {
        if (iterationThread == null) {
            return;    
        }        
    }
    
    public void cancelSegmenter() {
        if (iterationThread == null) {
            return;    
        }        
    }
    
    public String getSegmenterName() {
        return SEGMENTER_NAME;    
    }
    
    public String toString() {
        return SEGMENTER_NAME + " (k:= " + k + ")";    
    }
    
}
