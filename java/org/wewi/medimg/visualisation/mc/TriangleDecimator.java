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
 * TriangleDecimator.java
 *
 * Created on 12. Juni 2002, 15:41
 */

package org.wewi.medimg.visualisation.mc;

/**
 *
 * @author  Werner Weiser
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class TriangleDecimator {
    private TriangleDecimator component;
    
    public TriangleDecimator() {
        component = null;
    }
    
    public TriangleDecimator(TriangleDecimator component) {
        this.component = component;
    }
    
    public void decimate(Graph graph) {
        if (component == null) {
            return;
        }
        component.decimate(graph);
    }

}

