package org.wewi.medimg.ui;

import java.awt.Cursor;
import java.awt.Rectangle;

final class MLPHandle extends Rectangle {
    private int thick2;
    private Cursor cursor;
    
	public MLPHandle(int size) {
		setSize(size - 1, size - 1);
		thick2 = size / 2;
	}
	public void set(Cursor cursor, int x, int y) {
		setLocation(x - thick2, y - thick2);
		this.cursor = cursor;
	}
	public Cursor getCursor() {
		return cursor;
	}
}
