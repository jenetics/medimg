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

/**
 * SliceViewerPanelImpl.java
 * 
 * Created on 05.03.2003, 09:54:41
 *
 */
package org.wewi.medimg.viewer.image;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.wewi.medimg.image.Image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class SliceViewerPanelImpl extends JPanel {
    
    private abstract class Indicator {
        /**
         * The value of the indicator.
         */
        protected double value;
        protected int centerY;
        protected Rectangle bounds;
    
        /**
         * The scale reflects the distance in pixels between sucessive markings.
         */
        protected double scale;    
    
        private boolean eraseBackground = true;
    
    
        public Indicator() {
            bounds = new Rectangle();
        }
    
        //Initialize the indicator
        public void init(boolean erase, double Nvalue) {
            eraseBackground = erase;
            value = Nvalue;
        }
        //Set up the bounds
        public void setSize(int startX, int endX, int startY, int endY) {
            bounds.x = startX;
            bounds.width = endX - startX;
            bounds.y = startY;
            bounds.height = endY - startY;
            centerY = bounds.y + bounds.height / 2;
        }



        public void setScale(double Nscale) {
            scale = Nscale;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double v) {
            value = v;
        }

        //Draw the entire indicator to the specified Graphics.
        public void draw(Graphics g) {
            //Erase the background if needed
            g.setColor(Color.BLACK);
            if (eraseBackground) {
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
            //Set up a new graphics clipped to the indicator's bounds
            Graphics newGraf = g.create(0, 0, bounds.x + bounds.width, bounds.y + bounds.height);
            newGraf.clipRect(bounds.x, bounds.y, bounds.width, bounds.height);
            //Call descendants' draw routine
            drawPriv(newGraf);
            //Draw the indicator
            if (eraseBackground) {
                drawIndicator(newGraf, bounds.x, getIndicatorPos());
            }
        }

        abstract void drawPriv(Graphics g);

        abstract int getIndicatorPos();
        
        //External entry to set the position of the indicator (e.g. on mouse down)
        public abstract void setIndicatorPos(int y);
        
        //private routine: display a little white triangle at x,y
        void drawIndicator(Graphics g, int x, int y) {
            g.setColor(Color.WHITE);
            int xPoints[] = { x + 5, x + 20, x + 5 };
            int yPoints[] = { y - 5, y, y + 5 };
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }
    
    
    private final class Ruler extends Indicator {
        int getIndicatorPos() {
            return centerY + (int) (value * scale);
        }

        public void setIndicatorPos(int y) {
            value = (y - centerY) / scale;
        }

        //Draw ruler (-1cm, 0cm, 1cm, etc).
        void drawPriv(Graphics g) {
            g.setColor(Color.white);
            int dist; //Distance from zero, in mm.
            int minMM = (int) ((bounds.y - centerY) / scale);
            int maxMM = (int) ((bounds.y + bounds.height - centerY) / scale);
            int endX = bounds.x + bounds.width;
            for (dist = minMM; dist < maxMM; dist++) {
                int yPos = (int) (scale * dist) + centerY;
                if (dist % 10 == 0) {
                    //A centimeter marking-- draw larger, with a caption
                    g.drawLine(bounds.x + 10, yPos, endX, yPos);
                    g.drawString(Integer.toString(dist / 10) + "cm", bounds.x, yPos - 2);
                } else {
                    //Just a millimeter marking-- draw smaller
                    g.drawLine(endX - 10, yPos, endX, yPos);
                }
            }
        }
    }    
    
    
    private final class SliceState {

        /**
         * rotation angle in radians about X and Y, respectively.
         * These are the amounts of rotation applied to the block 
         * before it hits the screen.
         */
        private double rotAngX = Math.PI/8;
        private double rotAngY = Math.PI/12;

        /**
         * Scale factor for screen--
         * (pixels/inch)/(millimeters/inch)=(pixels/millimeter).
         */
        private double screenScale = 80.0/25.4;

        /**
         * Slicing plane's distance in mm along the Z axis, after rotation.
         */
        private double planePosition = 0.0;

        /**
         * Magnification applied to the screen image.
         */
        private double magnification = 1.0;     
        
        private SliceViewerPanelImpl panel;

        public SliceState(SliceViewerPanelImpl panel) {
            this.panel = panel;
            indicatorsChanged();           
        }

        /**
         * This is called by sliceViewer to indicate a mouse movement.
         * 
         * @param dx
         * @param dy
         */
        public void mouseDelta(int dx, int dy) {
            rotAngY += mouseFunc(dx);
            rotAngX += mouseFunc(dy);
            panel.repaint();
        }

        /**
         * Gently weight a mouse movement, so small movements
         * result in VERY tiny rotations (for fine control).
         * 
         * @param mouseDx
         * @return double
         */
        private double mouseFunc(int mouseDx) {
            int threshold = 5;
            double pixels2radians = Math.PI / 200;
            int absMouseDx = Math.abs(mouseDx);
            
            if (absMouseDx >= threshold) {
                return mouseDx * pixels2radians;
            } else {
                double ret = ((double)absMouseDx) * absMouseDx / threshold * pixels2radians;
                if (mouseDx < 0) {
                    return -ret;
                } else {
                    return ret;
                }
            }
        }

        /**
         * Ruler/Magnifier indicator handling.
         * 
         * @return double
         */
        public double getRulerScale() {
            return screenScale * magnification;
        }
        
        public void setPlanePosition(double pos) {
            planePosition = pos;
        }
        
        public double getPlanePosition() {
            return planePosition;
        }
        
        public void setMagnification(double mag) {
            magnification = mag;    
        }
        
        public double getMagnification() {
            return magnification;
        }

        /**
         * 
         */
        private void indicatorsChanged() {
            panel.repaint();
        }
    }    
    
    
    
    
    private Image image;
    
    private DataBlock dataBlock;
    private SliceState sliceState;
    private Ruler ruler;
    
    
    private int oldMouseX, oldMouseY;
    
    
    //Needed components
    private JToolBar toolBar;  
    private SliceViewerToolPanel toolPanel;
    private JPanel graphicPanel;

    /**
     * Constructor for SliceViewerPanelImpl.
     */
    public SliceViewerPanelImpl(Image image) {
        this.image = image;
        dataBlock = new DataBlock(image);
        
        sliceState = new SliceState(this);
        ruler = new Ruler();
        ruler.init(false, 0);
        
        initComponents();
    }
    
    private void initComponents() {
        //Adding the mouse (motion) listener.
        addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        mouseActionPressed(e.getX(), e.getY());
                    }
                });
                
        addMouseMotionListener(new MouseMotionAdapter() {
                    public void mouseDragged(MouseEvent e) {
                        
                        mouseActionDragged(e.getX(), e.getY());
                    }            
                });
    }  
    
    private void mouseActionDragged(int mx, int my) {
        if ((mx != oldMouseX) || (my != oldMouseY)) {
            sliceState.mouseDelta(mx - oldMouseX, my - oldMouseY);
        }
        
        oldMouseX = mx;
        oldMouseY = my;
    }
 
    /**
     * 
     * @param mx
     * @param my
     */
    private void mouseActionPressed(int mx, int my) {
        oldMouseX = mx;
        oldMouseY = my;
    }
    

    /**
     * This is the main redraw routine-- it calculates the
     * 3D tx matrix from sliceState's variables, and then calls dataBlock.draw.
     * 
     * @param g
     * @param win_g
     * @param w
     * @param h
     */
    protected void fillBackBuffer(Graphics g, Graphics winG, int w, int h) {
        Vector blockCenter = dataBlock.size.scaleBy(0.5);
        
        double scale = sliceState.screenScale * sliceState.magnification;
        
        //Convert dataBlock indicies into mm.
        Matrix3D txMatrix = Matrix3D.scaleMatrix(dataBlock.scale.getX(),
                                        dataBlock.scale.getY(),
                                        dataBlock.scale.getZ());
                                        
        //Center dataBlock on origin.
        txMatrix = txMatrix.postMultBy(
                            Matrix3D.translationMatrix(-blockCenter.getX(),
                                                       -blockCenter.getY(),
                                                       -blockCenter.getZ()));
                                                        
         //rotate about x
        txMatrix = txMatrix.postMultBy(Matrix3D.rotationXMatrix(sliceState.rotAngX));
        
        //rotate about y
        txMatrix = txMatrix.postMultBy(Matrix3D.rotationYMatrix(sliceState.rotAngY));
        
        //translate cutting plane
        txMatrix = txMatrix.postMultBy(
                            Matrix3D.translationMatrix(0,0, -sliceState.planePosition));
        
        //scale dataBlock to screen pixels (& flip y)
        txMatrix = txMatrix.postMultBy(Matrix3D.scaleMatrix(scale, -scale, 1.0));
        
        //center dataBlock on screen.
        txMatrix = txMatrix.postMultBy(Matrix3D.translationMatrix(w / 2.0, h / 2.0, 0));
        
        
        //drawing the image and the ruler
        dataBlock.draw(txMatrix, g, w, h, this);
        ruler.setScale(sliceState.getRulerScale());
        ruler.setSize(w - 45, w - 10, 10, h - 10);
        ruler.draw(g);
    }


    /*Redraw sets up the offscreen buffer and calls fillBackBuffer (above).*/
    Graphics imgG = null;
    java.awt.Image img = null;
    int cachedW = -1, cachedH = -1;

    void redraw(Graphics g) {
        int w = getSize().width;
        int h = getSize().height;
        if (img == null || cachedW != w || cachedH != h) {
            cachedW = w;
            cachedH = h;
            img = createImage(w, h);
            imgG = img.getGraphics();
        }
        fillBackBuffer(imgG, g, w, h); //See above
        g.drawImage(img, 0, 0, this);

    }


    /*Graphics routines.*/
    /*This is key to eliminate flashing.*/
    public void update(Graphics g) {
        redraw(g);
    }

    public void paint(Graphics g) {
        redraw(g);
    }


    public void setPlanePosition(double pos) {
        sliceState.setPlanePosition(pos);
    }
    
    public double getPlanePosition() {
        return sliceState.getPlanePosition();
    }
    
    public void setZoom(double zoom) {
        sliceState.setMagnification(zoom);   
    }
    
    public double getZoom() {
        return sliceState.getMagnification();
    }
    
    public void updateViewer() {
        sliceState.indicatorsChanged();
    }
        

}
