/**
 * Node.java
 * Created on 03.04.2003
 *
 */
package org.wewi.medimg.viewer.dontknow;

import java.awt.LayoutManager;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Node extends Item {

	/**
	 * @param layout
	 * @param isDoubleBuffered
	 */
	public Node(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	/**
	 * @param layout
	 */
	public Node(LayoutManager layout) {
		super(layout);
	}

	/**
	 * @param isDoubleBuffered
	 */
	public Node(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	/**
	 * 
	 */
	public Node() {
		super();
	}

}
