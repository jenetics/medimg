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
 * Importance.java
 *
 * Created on 26. März 2002, 09:33
 */

package org.wewi.medimg.reg.interpolation;


import org.wewi.medimg.image.Tissue;
/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class Importance {
    
    private Tissue tissue;
    private double importance;
    
    /** Creates new Importance */
    public Importance() {
    }

    public void setTissue(Tissue tis) {
            tissue = tis;
    }

    public Tissue getTissue() {
            return tissue;
    }

    public double getImportance() {
            return importance;
    }

    public void setImportance(double importance) {
            this.importance = importance;
    }


}
