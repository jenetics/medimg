/**
 * Created on 07.10.2002 18:15:38
 *
 */
package org.wewi.medimg.util.param;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleParameterIterator implements ParameterIterator {
    private double start;
    private double stop;
    private double stride;
    private String name;
    
    private double current;

	/**
	 * Constructor for DoubleParameterIterator.
	 */
	public DoubleParameterIterator(String name, double start, double stop, double stride) {
		this.start = start;
        this.stop = stop;
        this.stride = stride;
        
        current = start;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return current <= stop;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
        DoubleParameter value = new DoubleParameter(name, current);
        current += stride;
		return value;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
        throw new UnsupportedOperationException();
	}

}
