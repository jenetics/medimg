/**
 * Created on 25.09.2002
 *
 */
package org.wewi.medimg.util;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class AccumulatorArray {
    private final int rows;
    private final int cols;
    private int[][] accu;
    
    /**
     * Constructs an <code>AccumulatorArray</code> with the
     * specified numbers of rows and cols.
     * 
     * @param rows number of rows of the array.
     * @param cols number of columns of the array.
     */
    public AccumulatorArray(int rows, int cols) {
        this(rows, cols, 0);    
    }

    /**
     * Constructs an <code>AccumulatorArray</code> with the
     * specified numbers of rows and cols and initializes
     * with the default value <code>value</code>.
     * 
     * @param rows  number of rows of the array.
     * @param cols  number of columns of the array.
     * @param value default value of the array elements.
     */
	public AccumulatorArray(int rows, int cols, int value) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Rows and cols must be positive!");
        }
        
        
		this.rows = rows;
        this.cols = cols;
        accu = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                accu[i][j] = value;   
            }    
        }
        
	}
    
    /**
     * Incrementing the specified array element by one.
     * 
     * @param row 
     * @param col
     */
    public void inc(int row, int col) {
        ++accu[row][col];  
    }
    
    /**
     * Getting the number of rows.
     * 
     * @return int the number of rows of this array.
     */
    public int getRows() {
        return rows;    
    }
    
    /**
     * Getting the number of cols.
     * 
     * @return int the number of columns of this array.
     */
    public int getCols() {
        return cols;    
    }
    
    /**
     * Calculates the sum of the current element values.
     * 
     * @return long sum of the current element values.
     */
    public long elementSum() {
        long sum = 0;
        
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                sum += getValue(j, i);        
            }    
        }
    
        return sum;    
    }
    
    /**
     * Gets the element value.
     * 
     * @param row
     * @param col
     * @return int current value of the element.
     */
    public int getValue(int row, int col) {
        return accu[row][col];    
    }
    
    /**
     * Sets the element value
     * 
     * @param row
     * @param col
     * @param value value to be set.
     */
    public void setValue(int row, int col, int value) {
        accu[row][col] = value;    
    }
    
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("AccumulatorArray: Rows: " + rows + ", Cols: " + cols);
        buffer.append("\n");
        
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                buffer.append(getValue(j, i));
                buffer.append(", ");        
            }    
            buffer.append("\n");
        }
        
        return buffer.toString();   
    }

}

















