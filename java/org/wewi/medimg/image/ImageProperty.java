/**
 * Created on 22.10.2002 22:34:01
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class ImageProperty {
    private String name;
    private String property;

	/**
	 * Constructor for ImageProperty.
	 */
	public ImageProperty(String name, String property) {
		this.name = name;
        this.property = property;
	}
    
    public String getName() {
        return name;    
    }
    
    public String getProperty() {
        return property;    
    }

}
