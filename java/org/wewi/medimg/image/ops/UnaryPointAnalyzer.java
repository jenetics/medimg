/**
 * Created on 13.11.2002 19:57:15
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public class UnaryPointAnalyzer {
    private Image image;
    private UnaryOperator operator;

	/**
	 * Constructor for UnaryPointAnalyzer.
	 */
	public UnaryPointAnalyzer(Image image, UnaryOperator operator) {
		super();
        this.image = image;
        this.operator = operator;
	}
    
    public void analyze() {
        for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
            operator.process(it.next());       
        }    
    }
    
    public Image getImage() {
        return image;    
    }
    
    public UnaryOperator getOperator() {
        return operator;    
    }

}