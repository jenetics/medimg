/*
Rastergon is a rasterized polygon.  This is just a span for each y-line
of the screen.  The polygon gets defined over several calls by passing
different line segments to Rastergon.addLine.
*/

/*A line through three dimentional space*/

/*A polygonal face, specfied by four (coplanar) vertices.*/

/*Span-- an interval of integers.*/

/*Rasterized polygon class-- a class to encapsulated a screen-rasterized polygon.*/

package org.wewi.medimg.viewer.image;


public class Rastergon {
    int xMin,xMax,yMin,yMax;
    public Span spans[];
    public Span spanY;

    public Rastergon(int screenWidth, int screenHeight, int borderWidth) {
        int i;
        yMin = xMin = borderWidth;
        xMax = screenWidth - borderWidth;
        yMax = screenHeight - borderWidth;
        spans = new Span[screenHeight];
        /*Set max less than min-- this is a non-existent span.*/
        for (i = 0; i < screenHeight; i++) {
            spans[i] = new Span();
            spans[i].setMin(xMax);
            spans[i].setMax(xMin);
        }
        spanY = new Span();
    }

    public void addLine(Line3D line) {
        Vector bottom = line.pos,top = line.pos.plus(line.dir);
        if (bottom.getY() == top.getY())
            return;/*Nothing to do for a horizontal line.*/
        else if (bottom.getY() > top.getY()) {
            Vector temp = bottom;
            bottom = top;
            top = temp;
        }/*Swap top and bottom*/
        double slope = (bottom.getX() - top.getX()) / (bottom.getY() - top.getY());
        double intercept = bottom.getX() - bottom.getY() * slope;
        int bottomy = (int) Math.ceil(bottom.getY()),topy = (int) Math.ceil(top.getY());
        if (bottomy < yMin) bottomy = yMin;
        if (bottomy > yMax) bottomy = yMax;
        spanY.addPoint(bottomy);
        if (topy < yMin) topy = yMin;
        if (topy > yMax) topy = yMax;
        spanY.addPoint(topy);
        int y;
        for (y = bottomy; y < topy; y++) {
            int x = (int) Math.ceil(slope * y + intercept);
            if (x < xMin) x = xMin;
            if (x > xMax) x = xMax;
            spans[y].addPoint(x);
        }
    }
}
