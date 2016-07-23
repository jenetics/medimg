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

/*
 * MeanFilter.java
 *
 * Created on 26. Jänner 2002, 01:04
 */

package org.wewi.medimg.image.filter;

import java.util.Iterator;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.geom.Neighborhood;
import org.wewi.medimg.image.geom.Neighborhood3D18;
import org.wewi.medimg.image.geom.Point3D;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class MeanFilter extends ImageFilter {
    private Neighborhood neighborhood;   

    public MeanFilter(Image image) {
        super(image);
        init();
    }
    
    public MeanFilter(ImageFilter component) {
        super(component);
        init();
    }
    
    private void init() {
        neighborhood = new Neighborhood3D18(image.getMinX(), image.getMinY(), image.getMinZ(),
                                            image.getMaxX(), image.getMaxY(), image.getMaxZ());        
    }

    protected void componentFilter() {
        //Blur
        int maxX = image.getMaxX();
        int maxY = image.getMaxY();
        int maxZ = image.getMaxZ();
        
        Image tempImage = (Image)image.clone();
        
        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                for (int k = 0; k < maxZ; k++) {
                    image.setColor(i, j, k, blurColor(new Point3D(i, j, k), tempImage));
                }
            }
        } 
              
    }
    
    public Image getImage() {
        return super.getImage();
    }
    
    private short blurColor(Point3D p, Image image) {
        Point3D point;
        int nn = 0;
        double color = 0.0;
        for (Iterator it = neighborhood.getNeighbors(p); it.hasNext();) {
            point = (Point3D)it.next();
            color += image.getColor(point.getX(), point.getY(), point.getZ());
            ++nn;
        }
        return (short)(color/(double)nn);
              
    }       
}
