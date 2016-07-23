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
 * MAPKMeansClusterer.java
 *
 * Created on 24. Juli 2002, 20:10
 */

package org.wewi.medimg.seg.stat;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageProperties;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MAPKMeansClusterer extends MLKMeansClusterer {
    public static final String SEGMENTER_NAME = "MAP-Kmeans-Clusterer";
    
    private double BETA = 0.35;
    private double BETA_SQRT2 = Math.sqrt(BETA);  
    
    private Image segimgOld;
    private int size;
    private int oldPos = -1;
    private int[] n6 = new int[6];
    private int[] n12 = new int[12];    
    

    public MAPKMeansClusterer(int k) {
        super(k);
        ERROR_LIMIT = 0.2;
    }
    
    public MAPKMeansClusterer(int k, double BETA) {
        this(k);
        this.BETA = BETA;
        BETA_SQRT2 = Math.sqrt(BETA); 
        
        logger.info("k: " + k + ", b: " + BETA);
    }
    
    protected void setImageProperties(Image segimg) {
        ImageProperties segProp = new ImageProperties();
        segProp.setProperty("Segmentation class", getClass().getName());
        segProp.setProperty("k", Integer.toString(k));
        segProp.setProperty("beta", Double.toString(BETA));
        segProp.setProperty("Iterations", Integer.toString(iterationCount));
        for (int i = 0; i < k; i++) {
            segProp.setProperty("mean." + i, Double.toString(mean[i]));    
        }
        
        segimg.getHeader().setImageProperties(segProp);        
    }
    
    protected void createSegimgOld(Image segimg) {
        segimgOld = (Image)segimg.clone();
        size = segimgOld.getNVoxels();
    }
    
    protected void saveOldFeatureColor(int pos, int color) {
        segimgOld.setColor(pos, color);
    }
    
    protected double getCliquesPotential(int pos, int f) {
        double Vc = 0.0;

        if (oldPos != pos) {
            segimgOld.getNeighbor3D6Positions(pos, n6);
            segimgOld.getNeighbor3D12Positions(pos, n12);
            oldPos = pos;
        }
        
        for (int i = 0; i < 6; i++) {
            if (n6[i] >= 0 && n6[i] < size) {
                if (segimgOld.getColor(n6[i]) != f) {
                    Vc += BETA;
                }
            }
        }
        for (int i = 0; i < 12; i++) {
            if (n12[i] >= 0 && n12[i] < size) {
                if (segimgOld.getColor(n12[i]) != f) {
                    Vc += BETA_SQRT2;
                }
            }
        }
        
        return Vc*(double)colorRange.getNColors();
    }
    
    public void setBETA(double b) {
        BETA = b;
        BETA_SQRT2 = Math.sqrt(BETA);
    }
    
    public double getBETA() {
        return BETA;
    }
    
    public String getSegmenterName() {
        return SEGMENTER_NAME;    
    }
    
    public String toString() {
        return SEGMENTER_NAME + " (k:= " + k +")";    
    }
    
}
