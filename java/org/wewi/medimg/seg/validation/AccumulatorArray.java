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
    private int sizex;
    private int sizey;
    private int[][] accu;

	/**
	 * Constructor for AccumulatorArray.
	 */
	public AccumulatorArray(int sizex, int sizey) {
		this.sizex = sizex;
        this.sizey = sizey;
        accu = new int[sizex][sizey];
        
        for (int i = 0; i < sizex; i++) {
            for (int j = 0; j < sizey; j++) {
                accu[i][j] = 0;   
            }    
        }
	}
    
    public void inc(int x, int y) {
        ++accu[x][y];    
    }

}
