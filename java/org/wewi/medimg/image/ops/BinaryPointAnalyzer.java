/**
 * BinaryPointAnalyzer.java
 * 
 * Created on 23.12.2002, 14:48:57
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class BinaryPointAnalyzer {
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
        int minX = Math.max(img1.getMinX(), img2.getMinX());
        int maxX = Math.min(img1.getMaxX(), img2.getMaxX());   
        int minY = Math.max(img1.getMinY(), img2.getMinY());
        int maxY = Math.min(img1.getMaxY(), img2.getMaxY());
        int minZ = Math.max(img1.getMinZ(), img2.getMinZ());
        int maxZ = Math.min(img1.getMaxZ(), img2.getMaxZ());
        
        for (int k = minZ; k <= maxZ; k++) {
            for (int j = minY; j <= maxY; j++) {
                for (int i = minX; i <= maxX; i++) {
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
