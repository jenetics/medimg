/**
 * BinaryPointAnalyzer.java
 * 
 * Created on 23.12.2002, 14:48:57
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.*;
import org.wewi.medimg.image.ops.*;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class BinaryPointAnalyzer {
    private Image img1;
    private Image img2;
    private BinaryOperator operator;

    /**
     * Constructor for BinaryPointTransformer.
     */
    public BinaryPointAnalyzer(Image img1, Image img2, BinaryOperator operator) {
        super();
        this.img1 = img1;
        this.img2 = img2;
        this.operator = operator;
    }
    
    public void analyze() {
        /*
        final int minX = Math.max(img1.getMinX(), img2.getMinX());
        final int maxX = Math.min(img1.getMaxX(), img2.getMaxX());   
        final int minY = Math.max(img1.getMinY(), img2.getMinY());
        final int maxY = Math.min(img1.getMaxY(), img2.getMaxY());
        final int minZ = Math.max(img1.getMinZ(), img2.getMinZ());
        final int maxZ = Math.min(img1.getMaxZ(), img2.getMaxZ());
        */
        ROI roi = ROI.create(img1.getDimension()).intersect(ROI.create(img2.getDimension()));
        
        for (int k = roi.getMinZ(); k <= roi.getMaxZ(); k++) {
            for (int j = roi.getMinY(); j <= roi.getMaxY(); j++) {
                for (int i = roi.getMinX(); i <= roi.getMaxX(); i++) {
                    operator.process(img1.getColor(i, j, k), img2.getColor(i, j, k));    
                }    
            }    
        }         
    }
    
    public void analyze(ROI roi) {
        ROI img1ROI = ROI.create(img1.getDimension());
        ROI img2ROI = ROI.create(img2.getDimension());
        roi = roi.intersect(img1ROI.intersect(img2ROI));
        
        for (int k = roi.getMinZ(); k <= roi.getMaxZ(); k++) {
            for (int j = roi.getMinY(); j <= roi.getMaxY(); j++) {
                for (int i = roi.getMinX(); i <= roi.getMaxX(); i++) {
                    operator.process(img1.getColor(i, j, k), img2.getColor(i, j, k));    
                }    
            }    
        }        
    }

    public Image getImage() {
        return img1;    
    }
    
    public Image getFirstImage() {
        return img1;    
    }
    
    public Image getSecondImage() {
        return img2;    
    }
    
    public BinaryOperator getOperator() {
        return operator;    
    }
}
