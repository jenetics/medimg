/**
 * Created on 11.09.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.image.filter;

import java.util.Hashtable;

/**
 * @author Franz Wilhelmstötter
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
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
