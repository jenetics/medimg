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
