/**
 * GridVectorFieldLoop.java
 * 
 * Created on 10.03.2003, 10:27:17
 *
 */
package org.wewi.medimg.math.vec.ops;

import org.wewi.medimg.math.vec.GridVectorField;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class GridVectorFieldLoop {
    
    public abstract static class Task {
        private GridVectorField field;
        
        private void setVectorField(GridVectorField field) {
            this.field = field;
        }
        
        public GridVectorField getVectorField() {
            return field;
        }
        
        public abstract void execute(int gridX, int gridY, int gridZ);
        
    }
    
    
    private GridVectorField field;
    private Task task;
    
    public GridVectorFieldLoop(GridVectorField field, Task task) {
        this.field = field;
        this.task = task;
        
        task.setVectorField(field);
    }
    
    
    public void loop() {
        for (int k = 0, l = field.getGridsZ(); k < l; k++) {
            for (int j = 0, m = field.getGridsY(); j < m; j++) {
                for (int i = 0, n = field.getGridsX(); i < n; i++) {
                    task.execute(i, j, k);          
                }    
            }    
        }         
    }
}
