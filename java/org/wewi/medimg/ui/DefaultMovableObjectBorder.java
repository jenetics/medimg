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

package org.wewi.medimg.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

public class DefaultMovableObjectBorder implements MovableComponentBorder {

    private Color lineColor;
    private Stroke lineStroke;
    private Color handleColor;
    private final int thickness = 6;
    private final int thick2 = thickness / 2;
    private MLPHandle[] handles = new MLPHandle[8];

    public DefaultMovableObjectBorder(Color lineColor, Stroke lineStroke, Color handleColor) {
            
        this.lineColor = lineColor;
        this.lineStroke = lineStroke;
        this.handleColor = handleColor;
        
        for (int i = 0; i < handles.length; i++) {
            handles[i] = new MLPHandle(thickness);
        }
    }
    
    public DefaultMovableObjectBorder() {
        this(Color.BLUE, new BasicStroke(), Color.BLUE);
    }

    public Color getHandleColor() {
        return handleColor;
    }

    public Color getLineColor() {
        return lineColor;
    }
    
    public Stroke getLineStroke(){
        return lineStroke;
    }
    
    public void setLineStroke(Stroke lineStroke) {
        this.lineStroke = lineStroke;
    }

    public void setHandleColor(Color color) {
        handleColor = color;
    }

    public void setLineColor(Color color) {
        lineColor = color;
    }

    /**
     * Returns the type of cursor to display based on whether the
     * cursor is over a handle (and if so which one), the border
     * (but not a handle), or none of these.
     * 
     * @param p the point that the cursor is over
     */
    public Cursor getCursor(Component c, final Point p) {
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
            return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
        }
        //none of the above
        return Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
    }

    /**
     * Paints the border for the specified component with the 
     * specified position and size.
     * @param c the component for which this border is being painted
     * @param g the paint graphics
     */
    public void paintBorder(Component c, Graphics2D g) {
        if (c == null) {
            return;
        }
        Color oldColor = g.getColor();
        Stroke oldStroke = g.getStroke();
        
        Rectangle r = getRepaintRegion(c);
        Rectangle[] h = getHandles(r.x, r.y, r.width, r.height);
        
        g.setColor(lineColor);
        g.drawRect(r.x, r.y, r.width, r.height);
        
        g.setColor(handleColor);
        for (int i = 0, n = h.length; i < n; i++) {
            g.fillRect(h[i].x, h[i].y, h[i].width, h[i].height);
        }
        
        g.setColor(oldColor);
        g.setStroke(oldStroke);
    }
    
    /**
     * @return true if the cursor is over the border
     */
    public boolean isPointOverTheBorder(Component c, final Point p) {
        final int lineWidth = 6;
        final int lineHeight = 6;
        
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
            new Rectangle(c.getLocation().x, c.getLocation().y, c.getSize().width, c.getSize().height));
    }

 
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
        handles[0].set(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR), xx, yy);
        handles[1].set(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR), xx + ww, yy);
        handles[2].set(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR), xx + ww, yy + hh);
        handles[3].set(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR), xx, yy + hh);
        handles[4].set(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR), xx + ww / 2, yy);
        handles[5].set(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR), xx + ww / 2, yy + hh);
        handles[6].set(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR), xx, yy + hh / 2);
        handles[7].set(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR), xx + ww, yy + hh / 2);
        return handles;
    }
    private Rectangle getEquivalentBorderRectangle(Rectangle r) {
        return new Rectangle(r.x - (thick2 + 1), r.y - (thick2 + 1),
                             r.width + thickness, r.height + thickness);
    }


}
