/*
 * ListPlot3D.java
 *
 * Created on 1. August 2002, 15:27
 */

package org.wewi.medimg.image.statistic;

import org.wewi.medimg.image.*;
import org.wewi.medimg.image.io.*;

import java.io.*;

/**
 *
 * @author  Franz Wilhelmstötter
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
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                                new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            reader.read();
            Image image = reader.getImage();
            
            System.out.println(mathematicaString(image, 99));

        } catch (Exception e) {
            e.printStackTrace();
        }   
        
    }
    
    
}
