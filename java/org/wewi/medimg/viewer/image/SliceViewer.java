/* Java Source File
a part of SliceViewer
  a program written in 2/98 by Orion Lawlor.
Public domain source code.

Send questions to fsosl@uaf.edu
*/
/*
SliceViewer:
	is a window which maintains a back buffer,
	informs SliceState of mouse motion in the window,
	and calls DataBlock.draw() to render the slice.
*/

package org.wewi.medimg.viewer.image;

import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;

public class SliceViewer extends Frame {
	private Ruler ruler;
	private SliceState state;
	private DataBlock block;

	public SliceViewer(DataBlock Nblock, SliceState Nstate) {
		block = Nblock;
		state = Nstate;
		ruler = new Ruler();
		ruler.init(false, 0.0);
	}

	Matrix3D txMatrix;
	boolean pointSelected = false;
	int pointX, pointY;

	//This is the main redraw routine-- it calculates the
	//  3D tx matrix from state's variables, and then calls block.draw.
	protected void fillBackBuffer(Graphics g, Graphics win_g, int w, int h) {
		Vector blockCenter = block.size.scaleBy(0.5);
		double scale = state.screenScale * state.magnification;
			txMatrix =
				Matrix3D.scaleMatrix(
					block.scale.getX(),
					block.scale.getY(),
					block.scale.getZ()) //convert block indicies into mm.
		.postMultBy(
			Matrix3D.translationMatrix(
				-blockCenter.getX(),
				-blockCenter.getY(),
				-blockCenter.getZ())) //center block on origin
		.postMultBy(Matrix3D.rotationXMatrix(state.rotAngX)) //rotate about x
		.postMultBy(Matrix3D.rotationYMatrix(state.rotAngY)) //rotate about y
		.postMultBy(
			Matrix3D.translationMatrix(
				0,
				0,
				-state.planePosition)) //translate cutting plane
		.postMultBy(
			Matrix3D.scaleMatrix(
				scale,
				-scale,
				1.0)) //scale block to screen pixels (& flip y)
	.postMultBy(Matrix3D.translationMatrix(w / 2.0, h / 2.0, 0));
		//Center block on screen.
		long startTime = System.currentTimeMillis();
		block.draw(txMatrix, g, w, h, this);
		ruler.setScale(state.getRulerScale());
		ruler.setSize(w - 45, w - 10, 10, h - 10);
		ruler.draw(g);
		long endTime = System.currentTimeMillis();
		g.setColor(Color.red);
		g.drawString(
			"FPS:"
				+ Float.toString(
					(float) (1.0 / ((endTime - startTime) / 1000.0))),
			5,
			20);
		if (pointSelected)
			g.fillRect(pointX - 2, pointY - 2, 4, 4);
		pointSelected = false;
	}

	/*Event handling: allow the user to rotate the box by clicking and dragging.*/
	/*OLD AWT 1.0-style Event Handling: AWT 1.1 events are not supported in most browsers.*/
	public boolean handleEvent(Event e) {
		switch (e.id) {
			case Event.MOUSE_DOWN :
				my_mouseDown(e.x, e.y);
				return true;
			case Event.MOUSE_DRAG :
				my_mouseDrag(e.x, e.y);
				return true;
			case Event.MOUSE_UP :
				my_mouseUp(e.x, e.y);
				return true;
			case Event.WINDOW_DESTROY :
				hide();
				return true;
			default :
				return false;
		}
	}

	long clickTime;
	boolean mouseIsDown = false;
	int startMouseX, startMouseY;
	int oldMouseX, oldMouseY;

	void my_mouseDown(int mx, int my) {
		/*This odd "mouseIsDown" construction is needed for running
		under Netscape's AWT-- sometimes, the "MOUSE_DOWN" message is never sent!
		Instead, Netscape just sends us MOUSE_DRAG messages.
		Since we care about mouse down events, we have to carefully trap for this.*/
		mouseIsDown = true;
		startMouseX = oldMouseX = mx;
		startMouseY = oldMouseY = my;
		clickTime = System.currentTimeMillis();
	}

	void my_mouseDrag(int mx, int my) {
		if (!mouseIsDown)
			my_mouseDown(mx, my); //Kludge for Netscape AWT
		if ((mx != oldMouseX) || (my != oldMouseY))
			state.mouseDelta(mx - oldMouseX, my - oldMouseY);
		oldMouseX = mx;
		oldMouseY = my;
	}

	void my_mouseUp(int mx, int my) {
		if (!mouseIsDown)
			my_mouseDown(mx, my); //Kludge for Netscape AWT
		if ((mx != oldMouseX) || (my != oldMouseY))
			my_mouseDrag(mx, my);
		//Check for a single click--
		if ((System.currentTimeMillis() - clickTime < 300)
			&& //if no more than 300 ms have elapsed
		 (
				Math.abs(startMouseX - mx) + Math.abs(startMouseY - my) < 10))
			//and no more than 10 pixels have gone under mouse.
			{ //Output the clicked-on pixel's coordinates in mm.
			pointSelected = true;
			pointX = mx;
			pointY = my;
			Matrix3D txInverse = txMatrix.invert();
			Vector mouse = new Vector(pointX, pointY, 0),
				voxel = new Vector(0, 0, 0);
			txInverse.transformVector(mouse, voxel);
			System.out.println(
				"( "
					+ Double.toString(voxel.getX() * block.scale.getX())
					+ ", "
					+ Double.toString(voxel.getY() * block.scale.getY())
					+ ", "
					+ Double.toString(voxel.getZ() * block.scale.getZ())
					+ ")");

			repaint();
		}
		mouseIsDown = false;
	}

	/*Graphics routines.*/
	/*This is key to eliminate flashing.*/
	public void update(Graphics g) {
		redraw(g);
	}

	public void paint(Graphics g) {
		redraw(g);
	}

	/*Redraw sets up the offscreen buffer and calls fillBackBuffer (above).*/
	Graphics img_g = null;
	Image img = null;
	int cached_w = -1, cached_h = -1;

	void redraw(Graphics g) {
		int w = getSize().width;
		int h = getSize().height;
		if (img == null || cached_w != w || cached_h != h) {
			cached_w = w;
			cached_h = h;
			img = createImage(w, h);
			img_g = img.getGraphics();
		}
		fillBackBuffer(img_g, g, w, h); //See above
		g.drawImage(img, 0, 0, this);

	}
}
