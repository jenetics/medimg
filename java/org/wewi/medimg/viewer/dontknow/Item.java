/**
 * Item.java
 * 
 * Created on 02.04.2003, 21:47:13
 *
 */
package org.wewi.medimg.viewer.dontknow;

import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public abstract class Item extends JPanel {
    private boolean selected = false;
    
    protected Blackboard blackboard;
    
	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public Item(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	/**
	 * @param layout
	 */
	public Item(LayoutManager layout) {
		super(layout);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public Item(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}
    
    public Item() {
        super();
    }
    
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    public boolean isSelected() {
        return selected;
    }
    
    void setBlackboard(Blackboard b) {
        blackboard = b;
    }
    
    public Blackboard getBlackboard() {
        return blackboard;
    }
    
}
