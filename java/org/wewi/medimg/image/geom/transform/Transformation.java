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
 * Transform.java
 *
 * Created on 21. März 2002, 13:27
 */
package org.wewi.medimg.image.geom.transform;

//import org.wewi.medimg.image.Image;

/**
 *
 * @author  Werner Weiser
 * @author Franz Wilhelmstötter
 *
 * @version 0.1
 */
public interface Transformation {
    

    public void transform(int[] source, int[] target);    

    public void transform(float[] source, float[] target);
    
    public void transform(double[] source, double[] target);
    
    public void transformBackward(int[] target, int[] source);
    
    public void transformBackward(float[] target, float[] source);
    
    public void transformBackward(double[] target, double[] source);
    
    //public Image transform(Image source); 
    
    //public void transform(Image source, Image taget);     
    
    public Transformation scale(double alpha);
    
    public Transformation concatenate(Transformation trans);
    
    public Transformation createInverse();// throw TransformationException();
    

}

