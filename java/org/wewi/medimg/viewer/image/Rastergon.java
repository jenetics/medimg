/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */


package org.wewi.medimg.viewer.image;


final class Rastergon {
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;
    
    private Span spans[];
    private Span spanY;

    public Rastergon(int screenWidth, int screenHeight, int borderWidth) {
        yMin = xMin = borderWidth;
        xMax = screenWidth - borderWidth;
        yMax = screenHeight - borderWidth;
        setSpans(new Span[screenHeight]);
        
        //Set max less than min-- this is a non-existent span.
        for (int i = 0; i < screenHeight; i++) {
            getSpans()[i] = new Span();
            getSpans()[i].setMin(xMax);
            getSpans()[i].setMax(xMin);
        }
        setSpanY(new Span());
    }

    public void setSpanY(Span spanY) {
        this.spanY = spanY;
    }

    public Span getSpanY() {
        return spanY;
    }

    public void setSpans(Span[] spans) {
        this.spans = spans;
    }

    public Span[] getSpans() {
        return spans;
    }

    public void addLine(Line3D line) {
        Vector bottom = line.getPosition();
        Vector top = line.getPosition().plus(line.getDirection());
        
        if (bottom.getY() == top.getY()) {
            //Nothing to do for a horizontal line.
            return;
        } else if (bottom.getY() > top.getY()) {
            //Swap top and bottom
            Vector temp = bottom;
            bottom = top;
            top = temp;
        }
        
        double slope = (bottom.getX() - top.getX())/(bottom.getY() - top.getY());
        double intercept = bottom.getX() - bottom.getY()*slope;
        int bottomy = (int)Math.ceil(bottom.getY());
        int topy = (int)Math.ceil(top.getY());
        
        if (bottomy < yMin) {
            bottomy = yMin;
        }
        if (bottomy > yMax) { 
            bottomy = yMax;
        }
        
        getSpanY().addPoint(bottomy);
        
        if (topy < yMin) { 
            topy = yMin;
        }
        if (topy > yMax){ 
            topy = yMax;
        }
        
        getSpanY().addPoint(topy);

        for (int y = bottomy; y < topy; y++) {
            int x = (int) Math.ceil(slope*y + intercept);
            if (x < xMin) x = xMin;
            if (x > xMax) x = xMax;
            getSpans()[y].addPoint(x);
        }
    }
}







