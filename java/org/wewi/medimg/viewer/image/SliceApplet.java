/* Java Source File
a part of SliceViewer
  a program written in 2/98 by Orion Lawlor.
Public domain source code.

Send questions to fsosl@uaf.edu
*/
/*

The delagation of power in SliceViewer is:

SliceApplet & SliceState handle user interaction.
	This includes the rulers; zoom, slicing plane,
	and rotation information; and input/output.

SliceViewer is a window which maintains a back buffer,
	informs SliceState of mouse motion in the window,
	and calls DataBlock.draw() to render the slice.

DataBlock actually draws the slice of data.

*/
/*
SliceApplet: this is the Applet subclass in SliceViewer.
It is responsible for reading in the block of data,
parsing the parameters, and creating all other classes.

It also shares the user interface tasks with SliceState.
*/

package org.wewi.medimg.viewer.image;


import java.applet.Applet;
import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Image;
import java.io.InputStream;
import java.net.URL;

public class SliceApplet extends Applet {
//First, some utility routines used later on in SliceAppler
    //Report a fatal error of some type to the user.
    void failure(String reason) {
        System.out.println("SliceViewer error:");
        System.out.println(reason);
        System.exit(1);
    }

    //Readblock: open "filename" and read nx*ny*nz bytes.
    byte[] readBlock(String filename, int nx, int ny, int nz) {
        //Open the input file.
        InputStream in = null;
        try {
            in = new URL(getDocumentBase(), filename).openStream();
        } catch (Exception e) {
            failure("Couldn't open '" + filename + "'. Remember: it must be in the same directory as SliceViewer.");
        }

        //Try to allocate our block of data.
        int size = nx * ny * nz;
        byte[] ret = {};
        try {
            ret = new byte[size];
            if (ret == null)
                failure("Could not allocate " + Integer.toString(size) + " bytes of memory.");
        } catch (Exception e) {
            failure("Could not allocate " + Integer.toString(size) + " bytes of memory.");
        }

        //Read succesive bytes
        int bytesRead = 0;
        while (bytesRead < size) {
            try {
                int bytesThisTime = in.read(ret, bytesRead, size - bytesRead);
                bytesRead += bytesThisTime;
                if (size - bytesRead <= 0)
                    return ret;//EOF
            } catch (Exception e) {
                failure("Read failed on byte " + Integer.toString(bytesRead) + " of '" + filename + "'.  Are the length parameters: (" +
                        Integer.toString(nx) + "," +
                        Integer.toString(ny) + "," +
                        Integer.toString(nz) + ") correct?");
            }
        }
        return null;
    }

    //Return <param> tag parameter info to the user
    public String[][] getParameterInfo() {
        String[][] info = {
            {"filename", "String", "Name of byte data file."},
            {"lengthx", "int", "number of bytes in the x direction (fastest index)"},
            {"lengthy", "int", "number of bytes in the y direction"},
            {"lengthz", "int", "number of bytes in the z direction (slowest index)"},
            {"deltax", "double", "millimeters per sample in x"},
            {"deltay", "double", "millimeters per sample in y"},
            {"deltaz", "double", "millimeters per sample in z"},
            {"interpolation", "trilinear or nearest", "byte interpolation procedure."}
        };
        return info;
    }

    //Data Members
    SliceViewer sliceView;
    SliceState state;
//init: this is where it all begins-- the various parameters
    //  are parsed, and objects created and initialized.
    public void init() {
        int nx = Integer.parseInt(getParameter("lengthx")),
                ny = Integer.parseInt(getParameter("lengthy")),
                nz = Integer.parseInt(getParameter("lengthz"));
        double dx = Double.valueOf(getParameter("deltax")).doubleValue(),
                dy = Double.valueOf(getParameter("deltay")).doubleValue(),
                dz = Double.valueOf(getParameter("deltaz")).doubleValue();
        DataBlock block = new DataBlock();
        block.init(nx, ny, nz, dx, dy, dz,
                readBlock(getParameter("filename"), nx, ny, nz));
        if ("trilinear".equalsIgnoreCase(getParameter("interpolation")))
            block.setInterpolationMethod(DataBlock.INTERP_TRILINEAR);
        else if ("nearest".equalsIgnoreCase(getParameter("interpolation")))
            block.setInterpolationMethod(DataBlock.INTERP_NEAREST);
        super.init();
        state = new SliceState();
        sliceView = new SliceViewer(block, state);
        state.setSliceViewer(sliceView);
        sliceView.setTitle("SliceViewer");
        sliceView.setSize(400, 400);
    }

    //Start and stop just hide the sliceViewer window.
    public void start() {
        sliceView.show();
    }

    public void stop() {
        sliceView.hide();
    }

//These are the "intra-applet" graphics routine.
    //  It redraws the indicators, using an offscreen buffer.
    //  Also check out sliceViewer, for the slicing window graphics routines.
    Graphics img_g = null;
    Image img = null;
    int cached_w = -1,cached_h = -1;

    public void update(Graphics g) {
        paint(g);
    }

    int magDivider = 75;
    int rulersTop = 50;

    public void paint(Graphics g) {
        int w = getSize().width;
        int h = getSize().height;
        if (img == null || cached_w != w || cached_h != h) {
            cached_w = w;
            cached_h = h;
            img = createImage(w, h);
            img_g = img.getGraphics();
            img_g.setColor(Color.white);
            img_g.fillRect(0, 0, w, h);
            img_g.setColor(Color.black);
            img_g.drawString("Slice Plane", magDivider + 5, 20);
            img_g.drawString("Z Position", magDivider + 10, 40);
            img_g.drawString("Zoom", 10, 20);
            img_g.drawString("Factor", 15, 40);
        }
        drawUI(img_g, w, h);
        g.drawImage(img, 0, 0, this);
    }

    void drawUI(Graphics g, int w, int h) {
        state.ruler.setSize(magDivider + 5, w - 10, rulersTop, h - 10);
        state.ruler.draw(g);
        state.magnifier.setSize(10, magDivider - 5, rulersTop, h - 10);
        state.magnifier.draw(g);
    }

/*Event handling: allow the user to interact
	with the controls to zoom/slice the box.*/
    /*OLD AWT 1.0-style Event Handling: AWT 1.1 events are not supported in many browsers.*/
    public boolean handleEvent(Event e) {
        switch (e.id) {
            case Event.MOUSE_DOWN:
            case Event.MOUSE_DRAG:
            case Event.MOUSE_UP:
                my_mouseDown(e.x, e.y);
                return true;
            default:
                return false;
        }
    }

    void my_mouseDown(int mx, int my) {
        if ((mx < magDivider) && (my > rulersTop)) {
            state.magnifier.setIndicatorPos(my);
        } else {
            state.ruler.setIndicatorPos(my);
        }
        //This is a bit of a kludge--
        //  First, we shouldn't mess with state.*
        //  or else state should have some automatic way of detecting when we do.
        state.indicatorsChanged();
        repaint();
    }

}
