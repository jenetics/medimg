/*
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 27, 2002
 * Time: 2:21:25 PM
 * To change this template use Options | File Templates.
 */

package org.wewi.medimg.viewer.image;


import java.awt.Color;
import java.awt.Graphics;

public class Magnifier extends Indicator {
    int getIndicatorPos() {
        return bounds.y + bounds.height - (int) (value * scale);
    }

    public void setIndicatorPos(int y) {
        value = (bounds.y + bounds.height - y) / scale;
    }

    void drawPriv(Graphics g) {//draw magnification scale (1x,2x,3x, etc.)
        int magFactor = 0;
        double magPos = bounds.y + bounds.height;
        g.setColor(Color.white);
        while (magPos > bounds.y) {
            g.drawString(Integer.toString(magFactor) + "x", bounds.x + bounds.width - 30, (int) magPos);
            magFactor++;
            magPos -= scale;
        }
    }
}
