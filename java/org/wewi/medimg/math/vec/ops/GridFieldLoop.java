/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * GridFieldLoop.java
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
public final class GridFieldLoop {
    
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
    
    public GridFieldLoop(GridVectorField field, Task task) {
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
