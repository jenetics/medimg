package org.wewi.medimg.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

interface MovableObjectBorder {
    
	public int getCursor(Component c, final Point p);
    
	public void paintBorder(Component c, Graphics g);
    
	public boolean isPointOverTheBorder(Component c, final Point p);
    
	public Rectangle getRepaintRegion(Component c);
    
}
