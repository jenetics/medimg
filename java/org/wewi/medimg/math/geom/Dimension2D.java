/**
 * Dimension2D.java
 * 
 * Created on 07.01.2003, 17:11:52
 *
 */
package org.wewi.medimg.math.geom;

import org.wewi.medimg.util.Immutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Dimension2D implements Cloneable, Immutable {
    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    /**
     * Constructor for Dimension2D.
     */
    public Dimension2D(double minX, double maxX, double minY, double maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }
    
    public Dimension2D(double maxX, double maxY) {
        this(0, maxX, 0, maxY);    
    }
    
    public Dimension2D(Dimension2D dim) {
        this.minX = dim.minX;
        this.maxX = dim.maxX;
        this.minY = dim.minY;
        this.maxY = dim.maxY;            
    }
    
    public double getMinX() {
        return minX;    
    }
    
    public double getMaxX() {
        return maxX;    
    }
    
    public double getMinY() {
        return minY;    
    }
    
    public double getMaxY() {
        return maxY;    
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;    
        }    
        if (!(o instanceof Dimension2D)) {
            return false;    
        }
        
        Dimension2D dim = (Dimension2D)o;
        
        return Double.doubleToLongBits(minX) == Double.doubleToLongBits(dim.minX) &&
                Double.doubleToLongBits(maxX) == Double.doubleToLongBits(dim.maxX) &&
                Double.doubleToLongBits(minY) == Double.doubleToLongBits(dim.minY) &&
                Double.doubleToLongBits(maxY) == Double.doubleToLongBits(dim.maxY);
    }
    
    public int hashCode() {
        int code = 17;
        
        code += (int)Double.doubleToLongBits(minX)*37;
        code += (int)Double.doubleToLongBits(maxX)*37;
        code += (int)Double.doubleToLongBits(minY)*37;
        code += (int)Double.doubleToLongBits(maxY)*37;
            
        return code;
    }
    
    public Object clone() {
        return new Dimension2D(this);    
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer(); 
        
        buffer.append("Dimension2D: (");
        buffer.append(minX).append(", ");
        buffer.append(maxX).append(") x ");
        buffer.append(minY).append(", ");
        buffer.append(maxX).append(")");
        
        return buffer.toString();   
    }

}



















