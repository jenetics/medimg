/*
 * SimpleImageSimilarity.java
 *
 * Created on 4. Juni 2002, 21:12
 */

package org.wewi.medimg.image;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class SimpleImageSimilarity implements ImageSimilarity {
    private Image imageA;
    private Image imageB;
    
    /** Creates a new instance of SimpleImageSimilarity */
    public SimpleImageSimilarity(Image imageA, Image imageB) {
        this.imageA = imageA;
        this.imageB = imageB;
    }
    
    public double similarity() {
        int minX = Math.max(imageA.getMinX(), imageB.getMinX());
        int minY = Math.max(imageA.getMinY(), imageB.getMinY());
        int minZ = Math.max(imageA.getMinZ(), imageB.getMinZ());
        int maxX = Math.min(imageA.getMaxX(), imageB.getMaxX());
        int maxY = Math.min(imageA.getMaxY(), imageB.getMaxY());
        int maxZ = Math.min(imageA.getMaxZ(), imageB.getMaxZ());
        
        int sizeX = Math.max(imageA.getMaxX(), imageB.getMaxX()) -
                    Math.min(imageA.getMinX(), imageB.getMinX()) + 1;
        int sizeY = Math.max(imageA.getMaxY(), imageB.getMaxY()) -
                    Math.min(imageA.getMinY(), imageB.getMinY()) + 1;
        int sizeZ = Math.max(imageA.getMaxZ(), imageB.getMaxZ()) -
                    Math.min(imageA.getMinZ(), imageB.getMinZ()) + 1; 
        int nvoxel = sizeX*sizeY*sizeZ;
        
        int diff = 0;
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                for (int k = minZ; k <= maxZ; k++) {
                    if (imageA.getColor(i, j, k) != imageB.getColor(i, j, k)) {
                        diff++;
                    }
                }
            }
        }
        
        double similarity = (double)(nvoxel-diff)/(double)nvoxel;
        
        
        return similarity;
    }
    
}
