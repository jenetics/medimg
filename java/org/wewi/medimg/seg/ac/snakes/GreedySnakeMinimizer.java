/*
 * SnakeMinimizer.java
 *
 * Created on 20. Februar 2002, 19:30
 */

package org.wewi.medimg.seg.ac.snakes;

import org.wewi.medimg.seg.ac.Minimizer;

import org.wewi.medimg.image.Image;

import org.wewi.medimg.image.filter.ImageFilter;
import org.wewi.medimg.image.filter.SobelFilter;

import org.wewi.medimg.image.geom.Point;
import org.wewi.medimg.image.geom.Point2D;
import org.wewi.medimg.image.geom.Neighborhood;
import org.wewi.medimg.image.geom.Neighborhood2D4;

import org.wewi.medimg.seg.ac.ActiveContour;

import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class GreedySnakeMinimizer implements Minimizer {
    protected Image image;
    protected Image gradImage;
    protected ActiveContour contour;
    private static final double ERROR_LIMIT = 1;

    public GreedySnakeMinimizer(Image image, ActiveContour contour) {
        this.image = image;
        this.contour = contour;
    }
    
    protected double innerEnergy() {
        Point2D pm1;
        Point2D p;
        Point2D pp1;
        Iterator it = contour.getContourPoints();
        pm1 = (Point2D)it.next();
        p = (Point2D)it.next();
        pp1 = (Point2D)it.next();
        while (it.hasNext()) {
            
        }
        return 0;
    }
    
    protected double outerEnergy() {
        double grad = 0;
        Point2D p;
        for (Iterator it = contour.getContourPoints(); it.hasNext();) {
            p = (Point2D)it.next();
            grad += gradImage.getColor(p.getX(), p.getY(), 0);
        }
        return -grad;
    }
      
    public void minimize() {       
        Neighborhood neighbor = new Neighborhood2D4(image.getMaxX(), image.getMaxY());
        gradImage = (Image)image.clone();
        ImageFilter filter = new SobelFilter(gradImage);
        filter.filter();
        
        double energy = 0;
        double oldEnergy = 0;
        double newEnergy = 0;
        Point2D point = null;
        Point2D npoint = null;
        Point2D bestPoint = null;
        
        oldEnergy = Double.MAX_VALUE;
        energy = innerEnergy() + outerEnergy();
        
        System.out.println("Energy: " + energy);
        while (Math.abs(oldEnergy-energy) > ERROR_LIMIT) {
        //for (int k = 0; k < 150; k++) {
            for (Iterator it = contour.getBasePoints(); it.hasNext();) {
                point = (Point2D)it.next();
                bestPoint = point;
                for (Iterator nit = neighbor.getNeighbors(point); nit.hasNext();) {
                    npoint = (Point2D)nit.next();
                    contour.replaceBasePoint(point, npoint);
                    newEnergy = innerEnergy() + outerEnergy();
                    if (newEnergy < energy) {
                        energy = newEnergy;
                        bestPoint = point;
                    }
                    contour.replaceBasePoint(npoint, point);
                }
                contour.replaceBasePoint(point, bestPoint);
            }
            oldEnergy = energy;
            energy = innerEnergy() + outerEnergy();
            System.out.println("Energy: " + energy);
        }       
    }

    public ActiveContour getActiveContour() {
        return contour;
    }
    
}
