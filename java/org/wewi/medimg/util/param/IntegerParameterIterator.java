/**
 * Created on 07.10.2002 18:10:17
 *
 */
package org.wewi.medimg.util.param;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class IntegerParameterIterator implements ParameterIterator {
    private int start;
    private int stop;
    private int stride; 
    private String name;
    
    private int current;   

	/**
	 * Constructor for IntParameterIterator.
	 */
	public IntegerParameterIterator(String name, int start, int stop, int stride) {
		this.start = start;
        this.stop = stop;
        this.stride = stride;
        this.name = name;
        
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
        IntegerParameter value = new IntegerParameter(name, current);
        current += stride;
		return value;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
        throw new UnsupportedOperationException();
	}
    
    public Object clone() {
        return new IntegerParameterIterator(name, start, stop, stride);    
    }

}
