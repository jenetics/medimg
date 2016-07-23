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
 * ListPlot3D.java
 *
 * Created on 1. August 2002, 15:27
 */

package org.wewi.medimg.seg.validation;

import java.io.File;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.TIFFReader;

/**
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ListPlot3D {
    
    /** Creates a new instance of ListPlot3D */
    public ListPlot3D() {
    }
    
    
    public static String mathematicaString(Image image, int slice) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ListPlot3D[{");
        int vc = 0;
        for (int i = image.getMinX(); i <= image.getMaxX(); i+=1) {
            buffer.append("{");
            for ( int j = image.getMinY(); j <= image.getMaxY(); j+=1) {
                //buffer.append(i).append(",");
                //buffer.append(j).append(",");
                buffer.append(image.getColor(i, j, slice));
                if (j < image.getMaxY()-1) {
                    buffer.append(",");
                }
                vc++;
                if (vc < image.getNVoxels()-1) {
                    //buffer.append(",");
                }
            }
            buffer.append("}");
            if (i < image.getMaxX()-1) {
                buffer.append(",");
            }
        }
        buffer.append("}];");
        
        return buffer.toString();
    }
    
    
    public static void main(String[] args) {
    
        try {
            ImageReader reader = new TIFFReader(IntImageFactory.getInstance(), 
                                                new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            reader.read();
            Image image = reader.getImage();
            
            System.out.println(mathematicaString(image, 99));

        } catch (Exception e) {
            e.printStackTrace();
        }   
        
    }
    
    
}
