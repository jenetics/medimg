/**
 * Created on 25.09.2002
 *
 */
package org.wewi.medimg.seg.validation;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class AccumulatorArray {
    private final int rows;
    private final int cols;
    private int[][] accu;

	/**
	 * Constructor for AccumulatorArray.
	 */
	public AccumulatorArray(int rows, int cols) {
		this.rows = rows;
        this.cols = cols;
        accu = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                accu[i][j] = 0;   
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
    
    public int getValue(int row, int col) {
        return accu[row][col];    
    }

}
