/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 27, 2002
 * Time: 2:21:16 PM
 * To change this template use Options | File Templates.
 */

package org.wewi.medimg.viewer.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

abstract class Indicator {
    /**
     * The value of the indicator.
     */
	protected double value;
	protected int centerY;
	protected Rectangle bounds;
    
    /**
     * The scale reflects the distance in pixels between sucessive markings.
     */
    protected double scale;    
    
	private boolean eraseBackground = true;
    
    
    public Indicator() {
        bounds = new Rectangle();
    }
    
	//Initialize the indicator
	public void init(boolean erase, double Nvalue) {
		eraseBackground = erase;
		value = Nvalue;
	}
	//Set up the bounds
	public void setSize(int startX, int endX, int startY, int endY) {
		bounds.x = startX;
		bounds.width = endX - startX;
		bounds.y = startY;
		bounds.height = endY - startY;
		centerY = bounds.y + bounds.height / 2;
	}



	public void setScale(double Nscale) {
		scale = Nscale;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double v) {
		value = v;
	}

	//Draw the entire indicator to the specified Graphics.
	public void draw(Graphics g) {
		//Erase the background if needed
		g.setColor(Color.black);
		if (eraseBackground)
			g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		//Set up a new graphics clipped to the indicator's bounds
		Graphics newGraf =
			g.create(0, 0, bounds.x + bounds.width, bounds.y + bounds.height);
		newGraf.clipRect(bounds.x, bounds.y, bounds.width, bounds.height);
		//Call descendants' draw routine
		drawPriv(newGraf);
		//Draw the indicator
		if (eraseBackground)
			drawIndicator(newGraf, bounds.x, getIndicatorPos());
	}

	abstract void drawPriv(Graphics g);

	abstract int getIndicatorPos();
	//External entry to set the position of the indicator (e.g. on mouse down)
	public abstract void setIndicatorPos(int y);
	//private routine: display a little white triangle at x,y
	void drawIndicator(Graphics g, int x, int y) {
		g.setColor(Color.white);
		int xPoints[] = { x + 5, x + 20, x + 5 },
			yPoints[] = { y - 5, y, y + 5 };
		g.fillPolygon(xPoints, yPoints, 3);
	}
}
