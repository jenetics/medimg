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
    
    private long sum;
    
    public AccumulatorArray(int rows, int cols) {
        this(rows, cols, 0);    
    }

	/**
	 * Constructor for AccumulatorArray.
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
    
    public void inc(int row, int col) {
        ++accu[row][col];  
    }
    
    public int getRows() {
        return rows;    
    }
    
    public int getCols() {
        return cols;    
    }
    
    public long getSum() {
        sum = 0;
        
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                sum += getValue(j, i);        
            }    
        }
    
        return sum;    
    }
    
    public int getValue(int row, int col) {
        return accu[row][col];    
    }
    
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

















