/**
 * Created on 08.10.2002 11:01:25
 *
 */
package org.wewi.medimg.util.param;


import org.wewi.medimg.image.ImageFactory;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageParameterIterator implements ParameterIterator {
    private String name;
    private ImageFactory factory;
    private Class readerClass;
    private String[] file;
    
    private int pos;

	/**
	 * Constructor for ImageParameterIterator.
	 */
	public ImageParameterIterator(String name, ImageFactory factory, Class reader, String[] file) {
		super();
        this.name = name;
        this.factory = factory;
        this.readerClass = reader;
        this.file = file;
        
        pos = 0;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone()  {
		return new ImageParameterIterator(name, factory, readerClass, file);
	}

	/**
	 * @see java.util.Iterator#hasNext() 
	 */
	public boolean hasNext() {
		return pos < file.length;
	}

	/**
	 * @see java.util.Iterator#next()
	 */ 
	public Object next() {
		return new ImageParameter(name, factory, readerClass, file[pos++]);
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
	}

}
