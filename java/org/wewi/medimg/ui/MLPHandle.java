package org.wewi.medimg.ui;

import java.awt.Rectangle;

class MLPHandle extends Rectangle {
    private int thick2;
    private int type;
    
	public MLPHandle(int size) {
		setSize(size - 1, size - 1);
		thick2 = size / 2;
	}
	public void set(int type, int x, int y) {
		setLocation(x - thick2, y - thick2);
		this.type = type;
	}
	public int getCursor() {
		return type;
	}
}
