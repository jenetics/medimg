/**
 * Created on 11.09.2002
 *
 * To change this generated comment edit the template variable "filecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of file comments go to
 * Window>Preferences>Java>Code Generation.
 */
package org.wewi.medimg.image;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DefinableColorConversion implements ColorConversion {
    private Hashtable table;
    private Hashtable reverseTable;
    private Color defaultColor;

	/**
	 * Constructor for DefinableColorConversion.
	 */
	public DefinableColorConversion() {
		super();
        table = new Hashtable();
        reverseTable = new Hashtable();
        defaultColor = Color.BLACK; 
	}
    
    public void addColor(int grey, Color color) {
        Integer i = new Integer(grey);
        table.put(i, color);
        reverseTable.put(color, i);    
    }
    
    public void removeColor(int grey) {
        Integer i = new Integer(grey);
        Color c = (Color)table.get(i);
        table.remove(i);
        reverseTable.remove(c);      
    }
    
    public void setDefaultColor(Color c) {
        defaultColor = c;    
    }

	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int, int[])
	 */
	public void convert(int grey, int[] rgb) {
        Integer i = new Integer(grey);
        Color c = (Color)table.get(i);
        if (c != null) {
            rgb[0] = c.getRed();
            rgb[1] = c.getGreen();
            rgb[2] = c.getBlue();    
        } else {
            rgb[0] = defaultColor.getRed();
            rgb[1] = defaultColor.getGreen();
            rgb[2] = defaultColor.getBlue();                
        }
	}

	/**
	 * @see org.wewi.medimg.image.ColorConversion#convert(int[])
	 */
	public int convert(int[] rgb) {
        Color c = new Color(rgb[0], rgb[1], rgb[2]);
        Integer i = (Integer)reverseTable.get(c);
        if (i != null) {
            return i.intValue();    
        }
		return 0;
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
        DefinableColorConversion cc = new DefinableColorConversion();
        cc.table = (Hashtable)table.clone();
        cc.reverseTable = (Hashtable)reverseTable.clone();
        cc.defaultColor = new Color(defaultColor.getRed(), defaultColor.getGreen(), defaultColor.getBlue());
		return new DefinableColorConversion();
	}
    
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        
        defaultColor = (Color)stream.readObject();
        int size = stream.readInt();
        Integer key;
        Color value;
        for (int i = 0; i < size; i++) {
            key = (Integer)stream.readObject();
            value = (Color)stream.readObject();
            addColor(key.intValue(), value);     
        } 
    }
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        
        stream.writeObject(defaultColor);
        stream.writeInt(table.size());
        for (Enumeration keys = table.keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            stream.writeObject(key);
            stream.writeObject(table.get(key));    
        }        
    }
     

}








