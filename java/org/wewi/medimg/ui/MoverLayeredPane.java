package org.wewi.medimg.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * The MoverLayeredPane does not replace the JLayeredPane of the JRootPane it
 * lives in, it coexists with it.  Although this might seem weird and you might
 * keep slipping into the mindset (like I did) of thinking that the JRootPane
 * manages the MoverLayeredPane just like its own JLayeredPane (making it fill
 * up the whole window, etc), keep in mind that JLayeredPane extends JComponent
 * (just like JPanel does) and so can be used like any other JComponent...
 * 
 * @author Peter Armstrong
 * @author Tony Johnson
 */
public class MoverLayeredPane extends JLayeredPane {
    /**
     * This is the layer that components are added to.
     * This has the same value as JLayeredPane.DEFAULT_LAYER so that
     * components that get added w/o layer specified end up here.
     */
    private static final Integer BOTTOM_LAYER = new Integer(0);
    
    /**
     * This is the layer that borders are drawn on.
     */
    private static final Integer BORDER_LAYER = new Integer(50);
    
    /**
     * This is the subclass of JPanel which gets the components.
     * It is the MoverLayeredPane's job to ensure that this JPanel is the
     * same size as the MoverLayeredPane.
     * Note that the "contentPanel" of the MoverLayeredPane is NOT the same
     * thing as the contentPane of the JLayeredPane owned by the JRootPane.
     * Initially we had named them both the same thing to show the
     * similarity as a hint to the client programmer, but unlike Tony I am
     * too easily confused by such things.  Hence "contentPanel" :P
     */
    private MoverPanel contentPanel;
    
    /**
     * This is the subclass of JPanel which gets the borders.
     * It is the MoverLayeredPane's job to ensure that this JPanel is the
     * same size as the MoverLayeredPane.
     */
    private BorderPanel borderPanel;
    
    /**
     * The cursor state determines the results of mouse events...
     */
    private int cursorState;
    
    /**
     * "pocus" is like focus, except that it indicates which component
     * gets the handle border drawn around it.  This may be the same
     * component as the one with the focus, or it may be an ancestor
     * (on the GUI, not in terms of inheritance) of the component.
     * Specifically, only "top-level" components (i.e. ones with the
     * MoverPanel as their parent) have the "pocus" and the focus at the
     * same time.
     */ 
    private Component pocusComponent;
    
    private MovableComponentBorder selectionBorder;
    private MovableComponentBorder mouseOverBorder;
    
    /**
     * The offsetFromTopLeftCorner is used to adjust the location of
     * the component being MOVED (not resized).  We compute this offset
     * once and store it in this point, and then whenever we move the
     * component we need to adjust it by this offset.  This creates
     * the effect of the cursor staying at the same spot on the
     * component as it was when the user started dragging.
     */
    private Point offsetFromTopLeftCorner;
    

    private Rectangle initialSize = null;
    
    /**
     * Global Cursor constants
     */
    private static abstract class CursorState {
        public static final int OBJECT_SELECTION = 0;
        public static final int OBJECT_CREATION = 1;
    }

    /**
     * Empty constructor
     *
     */
    public MoverLayeredPane() {
        super();
        setLayout(new MLPLayout());
        
        contentPanel = new MoverPanel();
        borderPanel = new BorderPanel();
        
        add(contentPanel, BOTTOM_LAYER);
        add(borderPanel, BORDER_LAYER);
        
        contentPanel.setVisible(true);
        setCursorState(CursorState.OBJECT_SELECTION);
        setSelectionBorder(new DefaultMovableObjectBorder());
    }

    /**
     * Getting the content panel. This is the panel where you can
     * add your movable containers.
     * 
     * @return the content panel
     */
    public JComponent getContentPanel() {
        return contentPanel;
    }
    public JComponent getBorderPanel() {
        return borderPanel;
    }

    public MovableComponentBorder getSelectionBorder() {
        return selectionBorder;
    }
    
    public void setSelectionBorder(MovableComponentBorder border) {
        selectionBorder = border;
    }
    
    public MovableComponentBorder getMouseOverBorder() {
        return mouseOverBorder;
    }
    
    public void setMouseOverBorder(MovableComponentBorder border) {
        mouseOverBorder = border;
    }

    private void setCursorState(int newState) {
        cursorState = newState;
        switch (cursorState) {
            case CursorState.OBJECT_CREATION :
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                break;
            case CursorState.OBJECT_SELECTION :
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                break;
            default :
                }
    }
    //other local vars from glass pane code
    private Point dragOffset;
    private Point dragStart;
    private JPopupMenu pm;
    private int numRepaints = 0;
    private static final int REPAINT_FUDGE_FACTOR = 10;
    private static final int FULL_REPAINT = 5;
    private static int numEvents = 0; //debug



    /** 
     * A custom layout manager that is responsible for the layout of 
     * JPanels inside the MoverLayeredPane--basically, all it does is
     * ensure that they are the size of the MoverLayeredPane.
     */
    private final class MLPLayout implements LayoutManager {
        /**
         * Returns the amount of space the layout would like to have.
         *
         * @param the Container for which this layout manager is being used
         * @return a Dimension object containing the layout's preferred size
         */
        public Dimension preferredLayoutSize(Container parent) {
            return parent.getSize();
        }
        
        /**
         * Returns the minimum amount of space the layout needs.
         *
         * @param the Container for which this layout manager is being used
         * @return a Dimension object containing the layout's minimum size
         */
        public Dimension minimumLayoutSize(Container parent) {
            return parent.getSize();
        }
        
        /**
         * Returns the maximum amount of space the layout can use.
         *
         * @param the Container for which this layout manager is being used
         * @return a Dimension object containing the layout's maximum size
         */
        public Dimension maximumLayoutSize(Container target) {
            return target.getSize();
        }
        
        /**
         * Instructs the layout manager to perform the layout for the specified
         * container.
         *
         * @param the Container for which this layout manager is being used
         */
        public void layoutContainer(Container parent) {
            contentPanel.setSize(parent.getSize());
            contentPanel.setLocation(0, 0);
            borderPanel.setSize(parent.getSize());
            borderPanel.setLocation(0, 0);
        }
        
        public void addLayoutComponent(String name, Component comp) {
        }
        
        public void removeLayoutComponent(Component comp) {
        }
    }

    /**
     * The panel that the borders are drawn on.
     */
    private class BorderPanel extends JPanel {
        private BorderComponent borderComponent;
        
        public BorderPanel() {
            super(new BorderLayout());
            setOpaque(false);
            borderComponent = new BorderComponent();
            borderComponent.setVisible(true);
            add(borderComponent); 
        }


        /**
         * The BorderComponent has 2 jobs:
         * 1. Draw the border for the pocusComponent.
         * 2. Mouse events first go to the BorderComponent's contains() method.
         *    This method should return true IFF the the mouse event was on
         *    the border of the pocusComponent.  If contains() is true, the
         *    BorderComponent needs to process the mouse event accordingly.
         *    If contains() is false, the BorderComponent needs to do
         *    NOTHING (notice the beauty of this approach compared to the
         *    glass pane approach!) as the mouse event will automatically be
         *    passed on to the correct target.  This could result in a transfer
         *    of focus/pocus--the key thing to note is that this will happen
         *    without the BorderComponent doing anything special.  Now, when it
         *    does happen, the pocusComponent will be set and the
         *    BorderComponent will draw the new border!
         */
        private class BorderComponent extends JComponent {
            private Cursor currentCursor;
            private int repaintExtra = 20;
             
            public BorderComponent() {
                enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
            }

            public void paint(Graphics g) {
                selectionBorder.paintBorder(pocusComponent, (Graphics2D)g);
            }

            public boolean contains(int x, int y) {
                return selectionBorder.isPointOverTheBorder(pocusComponent, new Point(x, y));
            }

            protected void processMouseEvent(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    return;
                }
                switch (e.getID()) {
                    case MouseEvent.MOUSE_CLICKED :
                        break;
                    case MouseEvent.MOUSE_ENTERED :
                        break;
                    case MouseEvent.MOUSE_EXITED :
                        break;
                    case MouseEvent.MOUSE_PRESSED :
                        if (getCursor() != null) {
                            switch (getCursor().getType()) {
                                case Cursor.MOVE_CURSOR :
                                    Point topLeftCorner = pocusComponent.getLocation();
                                    offsetFromTopLeftCorner = new Point((e.getPoint().x - topLeftCorner.x),
                                                                        (e.getPoint().y - topLeftCorner.y));
                                    break;
                                case Cursor.E_RESIZE_CURSOR :
                                case Cursor.W_RESIZE_CURSOR :
                                case Cursor.N_RESIZE_CURSOR :
                                case Cursor.S_RESIZE_CURSOR :
                                case Cursor.NE_RESIZE_CURSOR :
                                case Cursor.NW_RESIZE_CURSOR :
                                case Cursor.SE_RESIZE_CURSOR :
                                case Cursor.SW_RESIZE_CURSOR :
                                    initialSize = pocusComponent.getBounds();
                                    break;
                                case Cursor.CROSSHAIR_CURSOR :
                                    dragStart = e.getPoint();
                                    break;
                                default :
                                    break;
                            }
                        }
                        break;
                    case MouseEvent.MOUSE_RELEASED :
                        repaint();
                        if (getCursor() != null) {
                            if (getCursor().getType() == Cursor.MOVE_CURSOR) {
                                moveSelectedComponent(e.getPoint());
                            }
                        }
                        break;
                    default :
                        break;
                }
            }
            
            protected void processMouseMotionEvent(MouseEvent e) {
                switch (e.getID()) {
                    case MouseEvent.MOUSE_MOVED :
                        Cursor newCursor = selectionBorder.getCursor(pocusComponent, e.getPoint());
                        if (!newCursor.equals(currentCursor)) {
                            setCursor(newCursor);
                            currentCursor = newCursor;
                        }
                        break;
                    case MouseEvent.MOUSE_DRAGGED :
                        Rectangle r = selectionBorder.getRepaintRegion(pocusComponent);
                        repaint(r.x - repaintExtra, r.y - repaintExtra, 
                                r.width + repaintExtra * 2, r.height + repaintExtra * 2);
                        if (getCursor().getType() == Cursor.MOVE_CURSOR) {
                            moveSelectedComponent(e.getPoint());
                            break;
                        }
                        switch (getCursor().getType()) {
                            case Cursor.N_RESIZE_CURSOR :
                                resizeVertical(e.getPoint(), true);
                                break;
                            case Cursor.E_RESIZE_CURSOR :
                                resizeHorizontal(e.getPoint(), false);
                                break;
                            case Cursor.S_RESIZE_CURSOR :
                                resizeVertical(e.getPoint(), false);
                                break;
                            case Cursor.W_RESIZE_CURSOR :
                                resizeHorizontal(e.getPoint(), true);
                                break;
                            case Cursor.NE_RESIZE_CURSOR :
                                resizeVertical(e.getPoint(), true);
                                resizeHorizontal(e.getPoint(), false);
                                break;
                            case Cursor.NW_RESIZE_CURSOR :
                                resizeVertical(e.getPoint(), true);
                                resizeHorizontal(e.getPoint(), true);
                                break;
                            case Cursor.SE_RESIZE_CURSOR :
                                resizeVertical(e.getPoint(), false);
                                resizeHorizontal(e.getPoint(), false);
                                break;
                            case Cursor.SW_RESIZE_CURSOR :
                                resizeVertical(e.getPoint(), false);
                                resizeHorizontal(e.getPoint(), true);
                                break;
                            default :
                                return;
                        }
                        validate();
                        
                        break;
                    default :
                        break;
                }
            }
            
            private boolean isPointInWindow(Point thePoint) {
                return (thePoint.x <= getSize().width && thePoint.x > 0 &&
                        thePoint.y <= getSize().height && thePoint.y > 0);
            }
            
            private void moveSelectedComponent(Point currentPoint) {
                Point topLeftCorner = new Point(currentPoint.x - offsetFromTopLeftCorner.x,
                                                currentPoint.y - offsetFromTopLeftCorner.y);
                int buttonLeftEdge = topLeftCorner.x;
                int buttonRightEdge = topLeftCorner.x + pocusComponent.getBounds().width;
                int buttonTopEdge = topLeftCorner.y;
                int buttonBottomEdge = topLeftCorner.y + pocusComponent.getBounds().height;
                if (buttonLeftEdge < 0) {
                    topLeftCorner.x = 0;
                } else if (buttonRightEdge >= getSize().width) {
                    topLeftCorner.x = getSize().width - pocusComponent.getBounds().width;
                }
                if (buttonTopEdge < 0) {
                    topLeftCorner.y = 0;
                } else if (buttonBottomEdge >= getSize().height) {
                    topLeftCorner.y = getSize().height - pocusComponent.getBounds().height;
                }
                pocusComponent.setLocation(topLeftCorner);
            }
            
            /**
             * Transforms the cursor location to the closest location on the
             * content pane.
             * 
             * @param currentPoint the actual cursor location (point)
             * @return the transformed point
             */
            private Point mapToPointInWindow(Point currentPoint) {
                Point retval = new Point();
                if (currentPoint.x > getSize().width) {
                    retval.x = getSize().width;
                } else if (currentPoint.x < 0) {
                    retval.x = 0;
                } else {
                    retval.x = currentPoint.x;
                }
                if (currentPoint.y > getSize().height) {
                    retval.y = getSize().height;
                } else if (currentPoint.y < 0) {
                    retval.y = 0;
                } else {
                    retval.y = currentPoint.y;
                }
                return retval;
            }
            
            /**
             * Resize either north or south.  If and when Java gets a parameterized
             * type mechanism this method should be combined with resizeHorizontal().
             */
            private void resizeVertical(Point currentPoint, boolean isNorthHandle) {
                Point currentWindowPoint = mapToPointInWindow(currentPoint);
                Point initialWindowPoint = new Point(initialSize.x, initialSize.y);
                Rectangle b = pocusComponent.getBounds();
                int newHeight;
                if (isNorthHandle) {
                    newHeight = initialSize.height + initialWindowPoint.y - currentWindowPoint.y;
                } else {
                    newHeight = currentWindowPoint.y - initialWindowPoint.y;
                }
                if (newHeight >= pocusComponent.getMinimumSize().height) {
                    if (isNorthHandle) {
                        b.y = currentWindowPoint.y;
                    }
                    b.height = newHeight;
                } else {
                    if (isNorthHandle) {
                        b.y = new Point( initialWindowPoint.x, initialWindowPoint.y +
                                         initialSize.height - pocusComponent.getMinimumSize().height).y;
                    }
                    b.height = pocusComponent.getMinimumSize().height;
                }
                pocusComponent.setBounds(b);
            }
            
            /**
             * Resize either east or west.  If and when Java gets a parameterized
             * type mechanism this method should be combined with resizeVertical().
             */
            private void resizeHorizontal(Point currentPoint, boolean isWestHandle) {
                Point currentWindowPoint = mapToPointInWindow(currentPoint);
                Point initialWindowPoint = new Point(initialSize.x, initialSize.y);
                Rectangle b = pocusComponent.getBounds();
                int newWidth;
                if (isWestHandle) {
                    newWidth = initialSize.width + initialWindowPoint.x - currentWindowPoint.x;
                } else {
                    newWidth = currentWindowPoint.x - initialWindowPoint.x;
                }
                if (newWidth >= pocusComponent.getMinimumSize().width) {
                    if (isWestHandle) {
                        b.x = currentWindowPoint.x;
                    }
                    b.width = newWidth;
                } else {
                    if (isWestHandle) {
                        b.x = new Point(initialWindowPoint.x + initialSize.width -
                                        pocusComponent.getMinimumSize().width, initialWindowPoint.y).x;
                    }
                    b.width = pocusComponent.getMinimumSize().width;
                }
                pocusComponent.setBounds(b);
            }

        } //BorderComponent
    } //BorderPanel

    private class MoverPanel extends JPanel {
        /**
         * This listener gets added to EVERY component in order to track who
         * has focus.
         */
        private SmartFocusListener smartfl;
        
        /**
         * This listener gets added to EVERY component in order to track the
         * addition and removal of components.
         */
        private SmartContainerListener smartcl;
        
        public MoverPanel() {
            super(null); //initialize with no layout manager
            enableEvents(AWTEvent.CONTAINER_EVENT_MASK | 
                         AWTEvent.MOUSE_EVENT_MASK     | 
                         AWTEvent.MOUSE_MOTION_EVENT_MASK);
                         
            smartfl = new SmartFocusListener();
            smartcl = new SmartContainerListener();
            addContainerListener(smartcl);
        }

        private Component findChildThatContainsPoint(MouseEvent e) {
            Component[] children = getComponents();
            for (int i = 0, n = children.length; i < n; i++) {
                if (children[i].contains(e.getX() - children[i].getLocation().x,
                                         e.getY() - children[i].getLocation().y)) {
                    return children[i];
                }
            }
            return null;
        }
        
        /**
         * @param add true if listeners are being added, false if removed
         * @param comp the component which is getting listeners added to it and its kids
         * @param recursionLevel the level (top = 0) in the hierarchy of calls
         * @param topLevelComponent the top level component (which is actually added to the MoverPanel)
         */
        private void recursiveListenerAddOrRemove(boolean add, Component comp, 
                                                  int recursionLevel, Component topLevelComponent) {
            if (add) {
                comp.addFocusListener(smartfl);
                if (comp instanceof Container) {
                    ((Container)comp).addContainerListener(smartcl);
                }
            } else {
                comp.removeFocusListener(smartfl);
                if (comp instanceof Container) {
                    ((Container)comp).removeContainerListener(smartcl);
                }
            }
            
            if (comp instanceof Container) {
                Component[] grandkids = ((Container) comp).getComponents();
                for (int i = 0; i < grandkids.length; i++) {
                    recursiveListenerAddOrRemove(add, grandkids[i], recursionLevel + 1, topLevelComponent);
                }
            }
        }

        protected void processMouseEvent(MouseEvent e) {
            if (e.isPopupTrigger()) {
                return;
            }
            
            Component child = findChildThatContainsPoint(e);
            switch (e.getID()) {
                case MouseEvent.MOUSE_PRESSED :
                    switch (cursorState) {
                        case CursorState.OBJECT_CREATION :
                            break;
                        case CursorState.OBJECT_SELECTION :
                            if (child != null) {
                                child.dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e, child));
                            }
                            break;
                        default :
                    }
                    break;
                case MouseEvent.MOUSE_CLICKED :
                case MouseEvent.MOUSE_RELEASED :
                    switch (cursorState) {
                        case CursorState.OBJECT_CREATION :
                            break;
                        case CursorState.OBJECT_SELECTION :
                            if (child != null) {
                                child.requestFocus();
                            } else {
                                pocusComponent = null;
                                requestFocus();
                                repaint();
                            }
                            break;
                        default :
                            }
                    break;
                default :
                    super.processMouseEvent(e);
            } //switch

        } //processMouseEvent

        protected void processMouseMotionEvent(MouseEvent e) {
            if (e.getID() == MouseEvent.MOUSE_DRAGGED) {
                switch (cursorState) {
                    case CursorState.OBJECT_CREATION :
                        //theMLPObjectFactory.mouseDragged(e, this);
                        break;
                    case CursorState.OBJECT_SELECTION :
                        break;
                    default :
                }
            }
        } //processMouseMotionEvent


        /**
         * used for both the MoverPanel and all its nested components that are containers
         */
        private class SmartContainerListener extends ContainerAdapter {
            public void componentAdded(ContainerEvent e) {
                recursiveListenerAddOrRemove(true, e.getChild(), 0, e.getChild());
            }
            
            public void componentRemoved(ContainerEvent e) {
                recursiveListenerAddOrRemove( false, e.getChild(), 0, e.getChild());
            }
        } //class SmartContainerListener

        /**
         * used to set the pocus based on the focus...
         */
        private class SmartFocusListener extends FocusAdapter {
            /**
             * sets the pocus to the component which is the direct child of the
             * MoverPanel (and is hence either the component itself or one of
             * its ancestors)
             */
            //!PA this used to show the glass pane, now what should it do???
            public void focusGained(FocusEvent e) {
                Component comp = e.getComponent();
                while (comp.getParent() != MoverPanel.this) {
                    comp = comp.getParent();
                }
                pocusComponent = comp;
                repaint();
            }
            //!PA this used to hide the glass pane, now what should it do???
            public void focusLost(FocusEvent e) {
            }
        } //class SmartFocusListener
    } //class MoverPanel






////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////




    /**
     * Testing-Method
     * @return
     */
    private static MoverLayeredPane getSampleLayeredPane() {
        MoverLayeredPane mlp = new MoverLayeredPane();

        //Now create a bunch of objects (incl. nested objects) to add to the
        //contentPanel of the MoverLayeredPane so we can test the border code.
        JButton b1 = new JButton("Button 1");
        JButton b2 = new JButton("Button 2");
        JButton b3 = new JButton("Button 3");
        JPanel p1 = new JPanel();
        JButton pb1 = new JButton("hi iam a button");
        p1.add(pb1);
        JLabel lab1 = new JLabel("this is a JLabel");
        lab1.setBorder(BorderFactory.createLineBorder(Color.red));
        lab1.setSize(lab1.getPreferredSize());
        lab1.setVisible(true);
        p1.add(lab1);
        p1.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
            }
        });
        JTextField ijtf = new JTextField("inner text field!");
        ijtf.setLocation(50, 50);
        ijtf.setSize(ijtf.getPreferredSize());
        p1.add(ijtf);
        p1.setSize(
            p1.getPreferredSize().width + 40,
            p1.getPreferredSize().height + 40);
        p1.setLocation(250, 250);
        p1.setBorder(BorderFactory.createLineBorder(Color.black));
        JLabel lab2 = new JLabel("a JLabel on its own");
        lab2.setBorder(BorderFactory.createLineBorder(Color.cyan));
        lab2.setSize(lab2.getPreferredSize());
        lab2.setVisible(true);
        lab2.setLocation(300, 100);
        JTextField jtf = new JTextField("Hello world!");
        jtf.setLocation(50, 50);
        jtf.setSize(jtf.getPreferredSize());
        b1.setSize(b1.getPreferredSize());
        b2.setSize(b2.getPreferredSize());
        b3.setSize(b3.getPreferredSize());
        b1.setLocation(100, 100);
        b2.setLocation(200, 200);
        b3.setLocation(300, 300);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });

        //Now add these objects to the contentPanel
        mlp.getContentPanel().add(lab2);
        mlp.getContentPanel().add(new MovableComponent(b1));
        //mlp.getContentPanel().add(b1);
        mlp.getContentPanel().add(b2);
        mlp.getContentPanel().add(b3);
        mlp.getContentPanel().add(jtf);
        return mlp;
    }

    public static void main(String[] args) {
        //Create the JFrame which is the test harness and create the instance
        //of the MoverLayeredPane which we will test.  Put that instance
        //in the middle of the JFrame to show that yes Virginia, the
        //MoverLayeredPane does not have to fill the JFrame.
        MoverLayeredPane mlp = getSampleLayeredPane();
        mlp.setSize(800, 600);
        mlp.setLocation(50, 50);
        JFrame f = new JFrame();
        f.getContentPane().setLayout(null);
        f.getContentPane().add(mlp);
        f.setSize(900, 700);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Window w = e.getWindow();
                w.setVisible(false);
                w.dispose();
                System.exit(0);
            }
        });
        mlp.revalidate();
        mlp.repaint();
    } //main
} //class MoverLayeredPane
