/*
 * Point3D.java
 *
 * Created on 31. Mai 2002, 12:11
 */

package org.wewi.medimg.math.geom;


import java.util.Arrays;


/**
 *
 * @author  werner weiser
 * @version 
 */
public class DoubleDataPoint implements DataPoint {

        protected double[] point;
        
        public DoubleDataPoint(double[] p) {
            int i;
            point = new double[p.length];
            for( i = 0; i < p.length; i++) {
                point[i] = p[i];
            }
        }

        public DoubleDataPoint(int length) {
            int i;
            point = new double[length];
            for( i = 0; i < length; i++) {
                point[i] = 0.0;
            }
        }
        
	    private DoubleDataPoint(DoubleDataPoint p) {
	        this(p.point);
	    }        
        
        public double getValue(int pos) {
            return point[pos];
        }
        
        public void setValue(int pos , double val) {
            point[pos] = val;
        }
        
        public DataPoint add(DataPoint p1) {
        	DoubleDataPoint ddp = (DoubleDataPoint)p1;
            DoubleDataPoint retVal = new DoubleDataPoint(Math.min(point.length, ddp.getDimension()));
            for (int i = 0; i < point.length; i++) {
            	retVal.setValue(i, (point[i] + ddp.getValue(i)));
        	}            
            return retVal;
        }

        public DataPoint sub(DataPoint p1) {
        	DoubleDataPoint ddp = (DoubleDataPoint)p1;
            DoubleDataPoint retVal = new DoubleDataPoint(Math.min(point.length, ddp.getDimension()));
            for (int i = 0; i < point.length; i++) {
            	retVal.setValue(i, (point[i] - ddp.getValue(i)));
        	}
            return retVal;
        }
        
        public DataPoint getDistanceVector(DataPoint p1) {
        	DoubleDataPoint ddp = (DoubleDataPoint)p1;
            DoubleDataPoint retVal = new DoubleDataPoint(Math.min(point.length, ddp.getDimension()));
            for (int i = 0; i < point.length; i++) {
				retVal.setValue(i, Math.abs(point[i] - ddp.getValue(i)));            	
        	}               
            return retVal;
        }   
        
        public DataPoint scale(double scale) {
            DoubleDataPoint retVal = new DoubleDataPoint(point.length);
            for (int i = 0; i < point.length; i++) {
				retVal.setValue(i, (point[i] * scale));            	
        	}          
            return retVal;
        }        

	    public double distance(DataPoint p1) {
	        DoubleDataPoint ip = (DoubleDataPoint)p1;
	        double dist = 0;
	        double diff = 0;
	        for (int i = 0; i < point.length; i++) {
	            diff = (ip.point[i]-point[i]);
	            diff *= diff;
	            dist += diff;
	        }
	        dist = Math.sqrt(dist);
	        return dist;
	    }
	    
	    public double norm() {
	        double n = 0;
	        for (int i = 0; i < point.length; i++) {
	            n += point[i]*point[i];
	        }
	        n = Math.sqrt(n);
	        return n;
	    }	    
            
        public int[] coordinates() {
            int[] retVal = new int[3];
            retVal[0] = (int)Math.floor(point[0]);
            retVal[1] = (int)Math.floor(point[1]);
            retVal[2] = (int)Math.floor(point[2]);
            return retVal;
        } 
        
        public double[] getValue() {
            double[] retVal = new double[point.length];
            for (int i = 0; i < point.length; i++) {
				retVal[i] = point[i];            	
        	} 
            return retVal;        
        }
        
	    public Number getOrdinateNumber(int dim) {
			return new Double(point[dim]);
	    }
	            
        public int getDimension() {
	        return point.length;
	    }
	    
	    public DataPoint getNullInstance() {
	        DoubleDataPoint point = new DoubleDataPoint(this);
	        Arrays.fill(point.point, 0.0);
	        return point;
	    }
	    
	    public DataPoint getOneInstance() {
	        DoubleDataPoint point = new DoubleDataPoint(this);
	        Arrays.fill(point.point, 1.0);
	        return point;        
	    }    
	    
	    public boolean equals(Object p) {
	        if (p == this) {
	            return true;
	        }
	        if (!(p instanceof DoubleDataPoint)) {
	            return false;
	        }
	        DoubleDataPoint ddp = (DoubleDataPoint)p;
	        if (ddp.point.length != point.length) {
	            return false;
	        }
	        for (int i = 0; i < point.length; i++) {
	            if (ddp.point[i] != point[i]) {
	                return false;
	            }
	        }
	        return true;
	    }
	    
	    public int hashCode() {
	        int result = 17;
	        for (int i = 0; i < point.length; i++) {
	            result = result*37 + (int)point[i];
	        }
	        return result;
	    }
	    
	    public Object clone() {
	        return new DoubleDataPoint(this);
	    }
	     
	    public String toString() {
	        StringBuffer buffer = new StringBuffer();
	        buffer.append("{");
	        for (int i = 0; i < point.length; i++) {
	            buffer.append(point[i]);
	            if (i < point.length-1) {
	                buffer.append(",");
	            }
	        }
	        buffer.append("}");
	        return buffer.toString();
	    }
	    
	    public String toMathematicaString() {
	        return toString();
	    }	    
}
