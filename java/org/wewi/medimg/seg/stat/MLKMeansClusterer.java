/* 
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

/**
 * MLKMeansClusterer.java
 *
 * Created on 24. Juli 2002, 20:08
 */

package org.wewi.medimg.seg.stat;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;

import org.wewi.medimg.alg.AlgorithmIterationEvent;
import org.wewi.medimg.alg.InterruptableAlgorithm;
import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.FeatureColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageProperties;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.seg.Clusterer;
import org.wewi.medimg.seg.ObservableSegmenter;
import org.wewi.medimg.seg.SegmenterEvent;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MLKMeansClusterer extends ObservableSegmenter 
                                implements Clusterer, InterruptableAlgorithm {
        
    public static final String SEGMENTER_NAME = "ML-Kmeans-Clusterer";
    
    protected int MAX_ITERATION = 125;
    protected double ERROR_LIMIT = 0.15;
    
    protected ColorRange colorRange;
    
    protected final int k;
    protected double[] mean;
    protected double[] meanOld;
    
    protected int iterationCount = 0;
    private boolean interrupted = false;
    private boolean cancelled = false;
    

    /**
     *
     * @param k number of features.
     */
    public MLKMeansClusterer(int k) {
        super();
        this.k = k;
        mean = new double[k];
        meanOld = new double[k];
        Arrays.fill(mean, 0);
        Arrays.fill(meanOld, 0);
    }
    
    public double[] getMeanValues() {
        double[] mv = new double[k];
        System.arraycopy(mean, 0, mv, 0, k);
        return mv;    
    }
    
    public int getIterations() {
        return iterationCount;    
    }
    
    /**
     * Initializing the feature means randomly.
     * 
     * @param cr ColorRange of the current image
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
     * Private formating method.
     */
    private String formatMeanValues() {
        NumberFormat format = NumberFormat.getInstance();
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
        segimg.setColorConversion(new FeatureColorConversion());
        segimg.resetColor(0);
        segment(mrt, segimg);
        return segimg;
    }
    
    private void iterate(Image mrt, Image segimg) {
        notifySegmenterStarted(new SegmenterEvent(this));
        
        iterationCount = 0;
        
        createSegimgOld(segimg);   
        
        initMeans(colorRange);
        
        do {
            if (cancelled) {
                break;    
            }
            notifyIterationStarted(new AlgorithmIterationEvent(this, iterationCount));
            
            m1Step(mrt, segimg);
            m2Step(mrt, segimg);
            ++iterationCount;
            
            notifyIterationFinished(new AlgorithmIterationEvent(this, iterationCount));
            
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
        //Feststellen des Farbbereiches
        colorRange =AnalyzerUtils.getColorRange(mrt);
        
        iterate(mrt, segimg);
        try {
            setImageProperties(segimg);
        } catch (Exception e) {
            //Verursacht einen Fehler???
        }
    }
    
    protected void setImageProperties(Image segimg) {
        ImageProperties segProp = new ImageProperties();
        segProp.setProperty("Segmentation class", getClass().getName());
        segProp.setProperty("k", Integer.toString(k));
        segProp.setProperty("Iterations", Integer.toString(iterationCount));
        for (int i = 0; i < k; i++) {
            segProp.setProperty("mean." + i, Double.toString(mean[i]));    
        }
        
        segimg.getHeader().setImageProperties(segProp);        
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
            minDistance = Double.MAX_VALUE;
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
            err = Math.max(Math.abs(mean[i] - meanOld[i]), err);
        }
        return err;
    }
    
    /**
     * Method saveOldFeatureColor.
     * Diese Methode wird von der Klasse MAPKMeansClusterer
     * überschrieben, da bei diesem Algorithmus notwendig ist,
     * sich das Merkmalsbild der vorigen Iteration zu merken.
     * 
     * @param position
     * @param color
     */
    protected void saveOldFeatureColor(int pos, int color) {
    }
    
    /**
     * Method getCliquesPotential.
     * Diese Methode liefert in dieser Klasse null zurück.
     * Wird von der Klasse MAPKMeansClusterer überschrieben.
     * 
     * @param position Position des Bildpunktes
     * @param f Numer des Merkmals
     * @return double das berechnete Cliquenpotential Vc.
     */
    protected double getCliquesPotential(int pos, int f) {
        return 0;
    }
    
    public String getSegmenterName() {
        return SEGMENTER_NAME;    
    }
    
    /**
     * @see org.wewi.medimg.alg.InterruptableAlgorithm#cancelAlgorithm()
     */
    public void cancelAlgorithm() throws UnsupportedOperationException {
        cancelled = true;
    }

    /**
     * @see org.wewi.medimg.alg.InterruptableAlgorithm#interruptAlgorithm()
     */
    public void interruptAlgorithm() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("\"" + getClass().getName() + 
                                             ".interruptAlgorithm()\" not implemented");
    }

    /**
     * @see org.wewi.medimg.alg.InterruptableAlgorithm#resumeAlgorithm()
     */
    public void resumeAlgorithm() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("\"" + getClass().getName() + 
                                             ".resumeAlgorithm()\" not implemented");        
    }    
    
    public String toString() {
        return SEGMENTER_NAME + " (k:= " + k + ")";    
    }

}
