package org.wewi.medimg.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

class DefaultMovableObjectBorder implements MovableObjectBorder {

	public DefaultMovableObjectBorder(Color lineColor, int lineWidth, int lineHeight, int insetsFromRealBorder,
		                              Color handleColor, int cornerHandleSideLength, int sideHandleSideLength) {
            
		this.lineColor = lineColor;
		this.lineWidth = lineWidth;
		this.lineHeight = lineHeight;
		this.insetsFromRealBorder = insetsFromRealBorder;
		this.handleColor = handleColor;
		this.cornerHandleSideLength = cornerHandleSideLength;
		this.sideHandleSideLength = sideHandleSideLength;
        
		for (int i = 0; i < handles.length; i++) {
			handles[i] = new MLPHandle(thickness);
		}
	}

	/**
	 * Returns the type of cursor to display based on whether the
	 * cursor is over a handle (and if so which one), the border
	 * (but not a handle), or none of these.
	 * 
	 * @param p the point that the cursor is over
	 */
	public int getCursor(Component c, final Point p) {
		Rectangle r = getRepaintRegion(c);
		MLPHandle[] h = getHandles(r.x, r.y, r.width, r.height);
		for (int i = 0; i < h.length; i++) {
			if (h[i].contains(p)) {
				//return a resize cursor
				return h[i].getCursor();
			}
		}
		if (isPointOverTheBorder(c, p)) {
			//if it was over a handle we wouldn't get here
			return Cursor.MOVE_CURSOR;
		}
		//none of the above
		return Cursor.DEFAULT_CURSOR;
	}

	/**
	 * Paints the border for the specified component with the 
	 * specified position and size.
	 * @param c the component for which this border is being painted
	 * @param g the paint graphics
	 */
	public void paintBorder(Component c, Graphics g) {
		if (c == null) {
			return;
		}
		Color oldColor = g.getColor();
		Rectangle r = getRepaintRegion(c);
		Rectangle[] theHandles = getHandles(r.x, r.y, r.width, r.height);
		g.setColor(lineColor);
		g.drawRect(r.x, r.y, r.width, r.height);
		for (int i = 0; i < theHandles.length; i++) {
			g.fillRect(
				theHandles[i].x,
				theHandles[i].y,
				theHandles[i].width,
				theHandles[i].height);
		}
		g.setColor(oldColor);
	}

	/**
	 * @return true if the cursor is over the border
	 */
	public boolean isPointOverTheBorder(Component c, final Point p) {
		int x = p.x;
		int y = p.y;
		if (c == null) {
			//then there's no border to be over!
			return false;
		}
		int extra = 3;
		Rectangle r = getRepaintRegion(c);
		if ((x >= r.x - extra)
			&& (x <= r.x + r.width + extra)
			&& (y >= r.y - extra)
			&& (y <= r.y + r.height + extra)) {
			//we are within the border area so we have a chance
			if ((x <= r.x + lineWidth)
				|| //left edge
			 (x >= r.x + r.width - lineWidth)
				|| //right edge
			 (y <= r.y + lineHeight)
				|| //top edge
			 (
					y >= r.y + r.height - lineHeight) //bottom edge
			) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Rectangle getRepaintRegion(Component c) {
		return getEquivalentBorderRectangle(
			new Rectangle(
				c.getLocation().x,
				c.getLocation().y,
				c.getSize().width,
				c.getSize().height));
	}

	//
	//private methods
	//

	/**
	 * A utility method for minimum size border rectangle.
	 */
	private Rectangle getMinimumBorderRectangle(Component c) {
		return getEquivalentBorderRectangle(
			new Rectangle(
				c.getLocation().x,
				c.getLocation().y,
				c.getMinimumSize().width,
				c.getMinimumSize().height));
	}
	private MLPHandle[] getHandles(int xx, int yy, int ww, int hh) {
		handles[0].set(Cursor.NW_RESIZE_CURSOR, xx, yy);
		handles[1].set(Cursor.NE_RESIZE_CURSOR, xx + ww, yy);
		handles[2].set(Cursor.SE_RESIZE_CURSOR, xx + ww, yy + hh);
		handles[3].set(Cursor.SW_RESIZE_CURSOR, xx, yy + hh);
		handles[4].set(Cursor.N_RESIZE_CURSOR, xx + ww / 2, yy);
		handles[5].set(Cursor.S_RESIZE_CURSOR, xx + ww / 2, yy + hh);
		handles[6].set(Cursor.W_RESIZE_CURSOR, xx, yy + hh / 2);
		handles[7].set(Cursor.E_RESIZE_CURSOR, xx + ww, yy + hh / 2);
		return handles;
	}
	private Rectangle getEquivalentBorderRectangle(Rectangle r) {
		return new Rectangle(
			r.x - (thick2 + 1),
			r.y - (thick2 + 1),
			r.width + thickness,
			r.height + thickness);
	}

	//
	//member variables
	//
	private Color lineColor;
	private int lineWidth;
	private int lineHeight;
	private int insetsFromRealBorder = 5;
	private Color handleColor;
	private int cornerHandleSideLength;
	private int sideHandleSideLength;
	private final int thickness = 6;
	private final int thick2 = thickness / 2;
	private MLPHandle[] handles = new MLPHandle[8];

} //MovableObjectBorder
