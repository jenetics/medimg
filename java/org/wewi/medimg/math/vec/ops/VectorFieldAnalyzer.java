/**
 * Created on 21.11.2002 13:42:49
 *
 */
package org.wewi.medimg.math.vec.ops;

import org.wewi.medimg.math.vec.VectorField;
import org.wewi.medimg.math.vec.VectorIterator;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class VectorFieldAnalyzer {
    private VectorField field;
    private VectorOperator analyzer;

	/**
	 * Constructor for VectorFieldAnalyzer.
	 */
	public VectorFieldAnalyzer(VectorField field, VectorOperator analyzer) {
		super();
        this.field = field;
        this.analyzer = analyzer;
	}
    
    
    public void analyze() {
        double[] start = new double[3];
        double[] end = new double[3];
        for (VectorIterator it = field.getVectorIterator(); it.hasNext();) {
            it.next(start, end);
            analyzer.process(start, end);        
        }    
    }
    
    public VectorField getVectorField() {
        return field;    
    }
    
    public VectorOperator getVectorOperator() {
        return analyzer;    
    }
}
