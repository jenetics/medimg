/**
 * Created on 21.11.2002 13:34:15
 *
 */
package org.wewi.medimg.math;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public class GridVectorFieldTransformer {
    private GridVectorField field;
    private VectorFunction transformer;

	/**
	 * Constructor for GridVectorFieldTransformer.
	 */
	public GridVectorFieldTransformer(GridVectorField field, VectorFunction transformer) {
		super();
        this.field = field;
        this.transformer = transformer;
	}
    
    public void transform() {
        double[] v = new double[3];
        for (int k = 0, l = field.getGridsZ(); k < l; k++) {
            for (int j = 0, m = field.getGridsY(); j < m; j++) {
                for (int i = 0, n = field.getGridsX(); i < n; i++) {
                    field.getVector(i, j, k, v);
                    transformer.transform(v);
                    field.setVector(i, j, k, v);           
                }    
            }    
        }    
    }
    
    public GridVectorField getVectorField() {
        return field;    
    }
    
    public VectorFunction getVectorFunction() {
        return transformer;    
    }

}
