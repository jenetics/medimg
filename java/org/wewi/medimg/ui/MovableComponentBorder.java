package org.wewi.medimg.ui;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public interface MovableComponentBorder {
    
	public Cursor getCursor(Component c, final Point p);
    
	public void paintBorder(Component c, Graphics2D g);
    
	public boolean isPointOverTheBorder(Component c, final Point p);
    
	public Rectangle getRepaintRegion(Component c);
    
}
