/**
 * UnaryOperatorTaskAdapter.java
 *
 * Created on 23. Jänner 2003, 20:11
 */

package org.wewi.medimg.image.ops;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class UnaryOperatorTaskAdapter extends ImageLoop.Task {
    private UnaryOperator operator;
    
    /** Creates a new instance of UnaryOperatorTaskAdapter */
    public UnaryOperatorTaskAdapter(UnaryOperator operator) {
        this.operator = operator;
    }
    
    public void execute(int x, int y, int z) {
        operator.process(getImage().getColor(x, y, z));
    }
    
}
