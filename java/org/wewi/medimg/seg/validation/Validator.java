/*
 * Validator.java
 *
 * Created on 7. August 2002, 15:32
 */

package org.wewi.medimg.seg.validation;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageData;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.RawImageWriter;
import org.wewi.medimg.image.io.TIFFWriter;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class Validator {
    
    private class RelationArray {
        private ColorRange colorRange1;
        private ColorRange colorRange2;
        private int[] array;
        
        public RelationArray(ColorRange cr1, ColorRange cr2) {
            colorRange1 = cr1;
            colorRange2 = cr2;
            array = new int[colorRange1.getNColors()*colorRange2.getNColors()];
            Arrays.fill(array, 0);
        }
        

        public void inc(int c1, int c2) {
            int pos = (c2 - colorRange2.getMinColor())*colorRange1.getNColors() +
                      (c1 - colorRange1.getMinColor());
            ++array[pos];    
        }
        
        public int getValue(int c1, int c2) {
            int pos = (c2 - colorRange2.getMinColor())*colorRange1.getNColors() +
                      (c1 - colorRange1.getMinColor());
            return array[pos];                
        }
        
        public int getMaxValue() {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < array.length; i++) {
                if (max < array[i]) {
                    max = array[i];    
                }    
            } 
            return max;   
        }
        
        public int getSum(int y) {
            int val = 0;
            for (int i = colorRange1.getMinColor(); i <= colorRange1.getMaxColor(); i++) {
                val += getValue(i, y);    
            }
            return val;           
        }
        
        
        public int getMaxValue(int y) {
            int max = Integer.MIN_VALUE;
            for (int i = colorRange1.getMinColor(); i <= colorRange1.getMaxColor(); i++) {
                if (max < getValue(i, y)) {
                    max = getValue(i, y);    
                }    
            }    
            return max;
        }
        
        
        public String toMathematicaString() {
            ColorRange cr1 = colorRange1;
            ColorRange cr2 = colorRange2;
            StringBuffer buffer = new StringBuffer();
            double value = 0;
            
            buffer.append("ListDensityPlot[{");
            for (int j = cr2.getMinColor(); j <= cr2.getMaxColor(); j++) {
                buffer.append("{");
                for (int i = cr1.getMinColor(); i <= cr1.getMaxColor(); i++) {
                    //value = (int)(((double)getValue(i, j)/(double)getSum(j))*1000000);
                    value = getValue(i, j);
                    buffer.append(value);
                    if (i < cr1.getMaxColor()) {
                        buffer.append(",");    
                    }
                } 
                buffer.append("}");
                if (j < cr2.getMaxColor()) {
                    buffer.append(",\n");    
                }   
            }
            buffer.append("}];");
            
            return buffer.toString();    
        }
        
        public String toString() {
            ColorRange cr1 = colorRange1;
            ColorRange cr2 = colorRange2;
            StringBuffer buffer = new StringBuffer();
            double value = 0;
            
            for (int j = cr2.getMinColor(); j <= cr2.getMaxColor(); j++) {
                for (int i = cr1.getMinColor(); i <= cr1.getMaxColor(); i++) {
                    //value = (int)(((double)getValue(i, j)/(double)getSum(j))*1000000);
                    value = getValue(i, j);
                    buffer.append(value);
                    if (i < cr1.getMaxColor()) {
                        buffer.append(" ");    
                    }
                } 
                if (j < cr2.getMaxColor()) {
                    buffer.append("\n");    
                }   
            }
            
            return buffer.toString();    
        }
    }

    
    private Image segimg;
    private Image compimg;
    private RelationArray array;
    
    
    /** Creates a new instance of Validator */
    public Validator(Image segimg, Image compimg) throws IllegalArgumentException {
    	if (!(segimg.getDimension().equals(compimg.getDimension()))) {
    		throw new IllegalArgumentException("Different Dimensions: " +
    		                                     segimg.getDimension().toString() + " != " +
    		                                     compimg.getDimension().toString());
    	}
    	
    	this.segimg = segimg;
    	this.compimg = compimg;
    }
    
    public void validate() {
        ColorRange cr1 = segimg.getColorRange();
        ColorRange cr2 = compimg.getColorRange();
        array = new RelationArray(cr1, cr2);
        
    	int c1, c2;
    	long key = 0;
    	
    	for (int k = segimg.getMinZ(); k < segimg.getMaxZ(); k++) {
    		for (int j = segimg.getMinY(); j < segimg.getMaxY(); j++) {
    			for (int i= segimg.getMinX(); i < segimg.getMaxX(); i++) {
    				c1 = segimg.getColor(i, j, k);
    				c2 = compimg.getColor(i, j, k);	
                    array.inc(c1, c2);			
    			}	
    		}	
    	}
    	
    	
    	
    }
    
    public double getError() {
    	return 0;	
    }
    
    public String toString() {
        return array.toString();    
    }
    
    
}







