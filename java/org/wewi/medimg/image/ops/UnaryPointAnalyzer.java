/**
 * Created on 13.11.2002 19:57:15
 *
 */
package org.wewi.medimg.image.ops;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.VoxelIterator;
import org.wewi.medimg.image.ROI;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class UnaryPointAnalyzer {
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
    
    public void analyze(ROI roi) {
        ImageLoop loop = new ImageLoop(image, new UnaryOperatorTaskAdapter(operator));
        loop.loop(roi);
    }
    
    public Image getImage() {
        return image;    
    }
    
    public UnaryOperator getOperator() {
        return operator;    
    }

}
