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
 * DataPointGenerator.java
 *
 * Created on 2. August 2002, 14:53
 */

package org.wewi.medimg.seg.kmeans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Random;
import java.util.Vector;

import org.wewi.medimg.math.geom.IntegerDataPoint;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class DataPointGenerator {
    private double[] mean;
    private double[][] var;
    private int k;
    
    private Vector[] data;
    private Random rand;
    
    /** Creates a new instance of DataPointGenerator */
    public DataPointGenerator(double[] mean, double[][] var) {
        this.mean = mean;
        this.var = var;
        
        data = new Vector[mean.length];
        for (int i = 0; i < mean.length; i++) {
            data[i] = new Vector();
        }
        rand = new Random(System.currentTimeMillis());
    }
    
    public void generate(int size) {
        double[] p = new double[mean.length];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < mean.length; j++) {
                p[i] = rand.nextGaussian();
            }
            
            
        }
    }
    
    public static Collection getData(String file, int size) {
        StringBuffer buffer = new StringBuffer();
        String line;
        Vector data = new Vector();
        int[] p = new int[2];
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            in.close();
            
            String temp = buffer.toString();
            temp = temp.substring(1, temp.length()-1);
            String[] n = temp.toString().split("\\{*\\}");
            int max = Math.min(n.length, size);
            for (int i = 0; i < max; i++) {
                int index = n[i].indexOf("{");
                n[i] = n[i].substring(index+1);
                
                index = n[i].indexOf(",");
                p[0] = (int)Math.rint(Double.parseDouble(n[i].substring(0, index)));
                p[1] = (int)Math.rint(Double.parseDouble(n[i].substring(index+1)));
                data.add(new IntegerDataPoint(p));
            }
            
            
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
        return data;
    }
    
    public Collection getData() {
        return null;
    }
    
    public static void main(String[] args) {
        DataPointGenerator.getData("C:/Programme/Wolfram Research/Mathematica/4.0/out.dat",100);
    }
}
