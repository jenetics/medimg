/* Java Source File
a part of SliceViewer
  a program written in 2/98 by Orion Lawlor.
Public domain source code.

Send questions to fsosl@uaf.edu
*/
/*
Indicator:
	an abstract superclass (I had to have at least ONE in here, after all)
	of Ruler and Magnifier, which display some sort of graduated
	scale in a fixed rectangle of the screen.  The scale has a little
	"indicator" on it, to display the current value of some parameter.
	Subclasses must override drawPriv, getIndicatorPos, and setIndicatorPos.

Ruler:
	Displays a graduated ruler, centered on zero (...-2cm,-1cm,0cm,1cm,2cm...)

Magnifier:
	Displays a magnification selector (0x,1x,2x,...)
*/

package org.wewi.medimg.viewer.image;


import java.awt.Color;
import java.awt.Graphics;

/*Indicator should probably be a subclass of something in
 java.awt... but I don't know what.*/

;

public class Ruler extends Indicator {
    int getIndicatorPos() {
        return centerY + (int) (value * scale);
    }

    public void setIndicatorPos(int y) {
        value = (y - centerY) / scale;
    }

    //Draw ruler (-1cm, 0cm, 1cm, etc).
    void drawPriv(Graphics g) {
        g.setColor(Color.white);
        int dist;//Distance from zero, in mm.
        int minMM = (int) ((bounds.y - centerY) / scale);
        int maxMM = (int) ((bounds.y + bounds.height - centerY) / scale);
        int endX = bounds.x + bounds.width;
        for (dist = minMM; dist < maxMM; dist++) {
            int yPos = (int) (scale * dist) + centerY;
            if (dist % 10 == 0) {	//A centimeter marking-- draw larger, with a caption
                g.drawLine(bounds.x + 10, yPos, endX, yPos);
                g.drawString(Integer.toString(dist / 10) + "cm", bounds.x, yPos - 2);
            } else //Just a millimeter marking-- draw smaller
                g.drawLine(endX - 10, yPos, endX, yPos);
        }
    }
}

;

;
