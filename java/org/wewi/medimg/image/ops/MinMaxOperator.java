/**
 * Created on 14.11.2002 06:02:01
 *
 */
package org.wewi.medimg.image.ops;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public class MinMaxOperator implements UnaryOperator {
    private boolean firstCall = true;
    
    private int min, max;
    private int calls = 0;

	/**
	 * Constructor for MinMaxOperator.
	 */
	public MinMaxOperator() {
		super();
	}

	/**
	 * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
	 */
	public void process(int color) {
        ++calls;
        if (firstCall) {
            min = max = color; 
            firstCall = false;   
        }
        
        if (min > color) {
            min = color;    
        } else if (max > color) {
            max = color;    
        }
	}
    
    public int getMinimum() {
        return min;    
    }
    
    public int getMaximum() {
        return max;    
    }
    
    public int getCalls() {
        return calls;    
    }

}