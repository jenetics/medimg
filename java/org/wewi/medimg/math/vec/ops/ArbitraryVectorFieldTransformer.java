package org.wewi.medimg.math.vec.ops;

import org.wewi.medimg.math.vec.ArbitraryVectorField;

/**
 * @author werner weiser
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public final class ArbitraryVectorFieldTransformer {
	
	private ArbitraryVectorField field;
	private VectorFunction function;
	
	public ArbitraryVectorFieldTransformer(ArbitraryVectorField field, VectorFunction function) {
		this.field = field;
		this.function = function;
	}
	

	public void transform() {
		double[] start = new double[3];
		double[] end = new double[3];
		
		for (int i = 0, n = field.size(); i < n; ++i) {
			field.getVector(i, start, end);
			function.transform(start, end);
			field.changeVector(i, start, end);
		}	
	}
}
