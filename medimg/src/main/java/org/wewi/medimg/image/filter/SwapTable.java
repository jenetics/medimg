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
 * Created on 11.09.2002
 *
 */
package org.wewi.medimg.image.filter;

import java.util.Hashtable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class SwapTable {
    
    private Hashtable table;

    /**
     * Constructor for SwapTable.
     */
    public SwapTable() {
        super();
        table = new Hashtable();
    }
    
    public void addSwapColor(int oldColor, int newColor) {
        table.put(new Integer(oldColor), new Integer(newColor));    
    }
    
    public void removeSwapColor(int oldColor) {
        table.remove(new Integer(oldColor));    
    }
    
    public int changeColor(int color) {
        Integer intColor = (Integer)table.get(new Integer(color));
        
        if (intColor != null) {
            return intColor.intValue();    
        }
        
        return color;    
    }

}
