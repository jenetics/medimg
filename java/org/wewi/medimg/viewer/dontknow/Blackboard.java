/**
 * Blackboard.java
 * 
 * Created on 02.04.2003, 21:43:45
 *
 */
package org.wewi.medimg.viewer.dontknow;

import java.util.Iterator;
import java.util.Vector;

import org.wewi.medimg.ui.MoverLayeredPane;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Blackboard extends MoverLayeredPane {
    private Vector items = new Vector();


	public Blackboard() {
		super();
	}

    public void addItem(Item item) {
        item.setBlackboard(this);
        items.add(item);
        getContentPanel().add(item);
    }
    
    public void removeItem(Item item) {
        item.setBlackboard(null);
        items.remove(item);
        getContentPanel().remove(item);
    }

    public Iterator itemIterator() {
        return items.iterator();
    }

    public Item[] items() {
        Item[] i = new Item[items.size()];
        items.toArray(i);
        return i;
    }
    
    public static void main(String[] args) {
    }

}





