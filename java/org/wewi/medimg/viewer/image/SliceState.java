/* Java Source File
a part of SliceViewer
  a program written in 2/98 by Orion Lawlor.
Public domain source code.

Send questions to fsosl@uaf.edu
*/
/*SliceState: some user interface odds and ends.
SliceState is the glue that interfaces SliceApplet,
SliceViewer, and the various indicators.
*/

package org.wewi.medimg.viewer.image;

public class SliceState {
	public Ruler ruler;
	public Magnifier magnifier;
	protected SliceViewer sliceView;

	public void setSliceViewer(SliceViewer v) {
		sliceView = v;
		ruler = new Ruler();
		ruler.init(true, 0);
		magnifier = new Magnifier();
		magnifier.init(true, 1.0);
		indicatorsChanged();
	}

	//These are the main purpose of SliceState-- it stores
	// the rotation/slicing/zoom parameters.
	public double rotAngX = Math.PI / 8, rotAngY = Math.PI / 12;
	/*rotation angle in radians about X and Y, respectively.
			These are the amounts of rotation applied to the block before it hits the screen.*/
	public double screenScale = 80.0 / 25.4;
	/*Scale factor for screen--
			(pixels/inch)/(millimeters/inch)=(pixels/millimeter).*/
	public double planePosition = 0.0;
	/*Slicing plane's distance in mm along the Z axis, after rotation.*/
	public double magnification = 1.0;
	/*Magnification applied to the screen image.*/

	//This is called by sliceViewer to indicate a mouse movement.
	public void mouseDelta(int dx, int dy) {
		rotAngY += mouseFunc(dx);
		rotAngX += mouseFunc(dy);
		sliceView.repaint();
	}
	/*Gently weight a mouse movement, so small movements
		result in VERY tiny rotations (for fine control).*/
	double mouseFunc(int mouseDx) {
		int threshold = 5;
		double pixels2radians = Math.PI / 200;
		int absMouseDx = Math.abs(mouseDx);
		if (absMouseDx >= threshold)
			return mouseDx * pixels2radians;
		else {
			double ret =
				((double) absMouseDx) * absMouseDx / threshold * pixels2radians;
			if (mouseDx < 0)
				return -ret;
			else
				return ret;
		}
	}

	/*Ruler/Magnifier indicator handling.*/
	public double getRulerScale() {
		return screenScale * magnification;
	}

	public void indicatorsChanged() {
		double oldPlane = planePosition, oldMag = magnification;
		planePosition = ruler.getValue();
		magnification = magnifier.getValue();
		magnifier.setScale(50.0);
		ruler.setScale(getRulerScale());
		if (oldPlane != planePosition || oldMag != magnification)
			sliceView.repaint();
	}
}
