/**
 * MovableComponent.java
 * Created on 03.04.2003
 *
 */
package org.wewi.medimg.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.MenuComponent;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.im.InputContext;
import java.awt.im.InputMethodRequests;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.VolatileImage;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.EventListener;
import java.util.Locale;
import java.util.Set;

import javax.accessibility.AccessibleContext;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;
import javax.swing.border.Border;
import javax.swing.event.AncestorListener;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MovableComponent extends JComponent {
    private JComponent child;
    
    private boolean selected = false;
    
    private MovableComponentBorder selectedBorder;
    private MovableComponentBorder mouseOverBorder;

	public MovableComponent(JComponent child) {
        //super();
        if (child == null) {
            throw new NullPointerException("Child component must not be null.");
        }
        this.child = child;
        /*
        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                selected = !selected;
                System.out.println("selected: " + selected);
            }
        });
        */
	}


    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }
    
    
    public static void main(String[] args) {
        JFrame f = new JFrame();
        
        JComponent b1 = new JButton("Button 1");
        MovableComponent mc = new MovableComponent(b1);
        mc.setSize(mc.getPreferredSize());
        mc.setLocation(100, 100);
        f.getContentPane().add(mc);
        
        /*
        JComponent b2 = new JButton("Button 2");
        b2 = new MovableComponent(b2);
        b2.setSize(b2.getPreferredSize());
        b2.setLocation(200, 200);
        f.getContentPane().add(b2);
        */
        f.setSize(300, 300);
        f.setVisible(true);
        f.pack();
        f.show();
        f.repaint();
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
       
    }
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getDefaultLocale()
	 */
	public static Locale getDefaultLocale() {
		return JComponent.getDefaultLocale();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isLightweightComponent(java.awt.Component)
	 */
	public static boolean isLightweightComponent(Component c) {
		return JComponent.isLightweightComponent(c);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setDefaultLocale(java.util.Locale)
	 */
	public static void setDefaultLocale(Locale l) {
		JComponent.setDefaultLocale(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component)
	 */
	public Component add(Component comp) {
		return child.add(comp);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, int)
	 */
	public Component add(Component comp, int index) {
		return child.add(comp, index);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object)
	 */
	public void add(Component comp, Object constraints) {
		child.add(comp, constraints);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object, int)
	 */
	public void add(Component comp, Object constraints, int index) {
		child.add(comp, constraints, index);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#add(java.awt.PopupMenu)
	 */
	public synchronized void add(PopupMenu popup) {
		child.add(popup);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#add(java.lang.String, java.awt.Component)
	 */
	public Component add(String name, Component comp) {
		return child.add(name, comp);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#addAncestorListener(javax.swing.event.AncestorListener)
	 */
	public void addAncestorListener(AncestorListener listener) {
		child.addAncestorListener(listener);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addComponentListener(java.awt.event.ComponentListener)
	 */
	public synchronized void addComponentListener(ComponentListener l) {
		child.addComponentListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#addContainerListener(java.awt.event.ContainerListener)
	 */
	public synchronized void addContainerListener(ContainerListener l) {
		child.addContainerListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
	 */
	public synchronized void addFocusListener(FocusListener l) {
		child.addFocusListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addHierarchyBoundsListener(java.awt.event.HierarchyBoundsListener)
	 */
	public void addHierarchyBoundsListener(HierarchyBoundsListener l) {
		child.addHierarchyBoundsListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addHierarchyListener(java.awt.event.HierarchyListener)
	 */
	public void addHierarchyListener(HierarchyListener l) {
		child.addHierarchyListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addInputMethodListener(java.awt.event.InputMethodListener)
	 */
	public synchronized void addInputMethodListener(InputMethodListener l) {
		child.addInputMethodListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addKeyListener(java.awt.event.KeyListener)
	 */
	public synchronized void addKeyListener(KeyListener l) {
		child.addKeyListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
	 */
	public synchronized void addMouseListener(MouseListener l) {
		child.addMouseListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addMouseMotionListener(java.awt.event.MouseMotionListener)
	 */
	public synchronized void addMouseMotionListener(MouseMotionListener l) {
		child.addMouseMotionListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#addMouseWheelListener(java.awt.event.MouseWheelListener)
	 */
	public synchronized void addMouseWheelListener(MouseWheelListener l) {
		child.addMouseWheelListener(l);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#addNotify()
	 */
	public void addNotify() {
		child.addNotify();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		child.addPropertyChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public synchronized void addPropertyChangeListener(
		String propertyName,
		PropertyChangeListener listener) {
		child.addPropertyChangeListener(propertyName, listener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#addVetoableChangeListener(java.beans.VetoableChangeListener)
	 */
	public synchronized void addVetoableChangeListener(VetoableChangeListener listener) {
		child.addVetoableChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#applyComponentOrientation(java.awt.ComponentOrientation)
	 */
	public void applyComponentOrientation(ComponentOrientation orientation) {
		child.applyComponentOrientation(orientation);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#areFocusTraversalKeysSet(int)
	 */
	public boolean areFocusTraversalKeysSet(int id) {
		return child.areFocusTraversalKeysSet(id);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#checkImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
	 */
	public int checkImage(
		Image image,
		int width,
		int height,
		ImageObserver observer) {
		return child.checkImage(image, width, height, observer);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#checkImage(java.awt.Image, java.awt.image.ImageObserver)
	 */
	public int checkImage(Image image, ImageObserver observer) {
		return child.checkImage(image, observer);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#computeVisibleRect(java.awt.Rectangle)
	 */
	public void computeVisibleRect(Rectangle visibleRect) {
		child.computeVisibleRect(visibleRect);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#contains(int, int)
	 */
	public boolean contains(int x, int y) {
		return child.contains(x, y);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#contains(java.awt.Point)
	 */
	public boolean contains(Point p) {
		return child.contains(p);
	}


	/* (non-Javadoc)
	 * @see java.awt.Component#createImage(int, int)
	 */
	public Image createImage(int width, int height) {
		return child.createImage(width, height);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#createImage(java.awt.image.ImageProducer)
	 */
	public Image createImage(ImageProducer producer) {
		return child.createImage(producer);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#createToolTip()
	 */
	public JToolTip createToolTip() {
		return child.createToolTip();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#createVolatileImage(int, int)
	 */
	public VolatileImage createVolatileImage(int width, int height) {
		return child.createVolatileImage(width, height);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#createVolatileImage(int, int, java.awt.ImageCapabilities)
	 */
	public VolatileImage createVolatileImage(
		int width,
		int height,
		ImageCapabilities caps)
		throws AWTException {
		return child.createVolatileImage(width, height, caps);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#doLayout()
	 */
	public void doLayout() {
		child.doLayout();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#enableInputMethods(boolean)
	 */
	public void enableInputMethods(boolean enable) {
		child.enableInputMethods(enable);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return child.equals(obj);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#findComponentAt(int, int)
	 */
	public Component findComponentAt(int x, int y) {
		return child.findComponentAt(x, y);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#findComponentAt(java.awt.Point)
	 */
	public Component findComponentAt(Point p) {
		return child.findComponentAt(p);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#firePropertyChange(java.lang.String, byte, byte)
	 */
	public void firePropertyChange(
		String propertyName,
		byte oldValue,
		byte newValue) {
		child.firePropertyChange(propertyName, oldValue, newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#firePropertyChange(java.lang.String, char, char)
	 */
	public void firePropertyChange(
		String propertyName,
		char oldValue,
		char newValue) {
		child.firePropertyChange(propertyName, oldValue, newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#firePropertyChange(java.lang.String, double, double)
	 */
	public void firePropertyChange(
		String propertyName,
		double oldValue,
		double newValue) {
		child.firePropertyChange(propertyName, oldValue, newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#firePropertyChange(java.lang.String, float, float)
	 */
	public void firePropertyChange(
		String propertyName,
		float oldValue,
		float newValue) {
		child.firePropertyChange(propertyName, oldValue, newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#firePropertyChange(java.lang.String, long, long)
	 */
	public void firePropertyChange(
		String propertyName,
		long oldValue,
		long newValue) {
		child.firePropertyChange(propertyName, oldValue, newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#firePropertyChange(java.lang.String, short, short)
	 */
	public void firePropertyChange(
		String propertyName,
		short oldValue,
		short newValue) {
		child.firePropertyChange(propertyName, oldValue, newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getAccessibleContext()
	 */
	public AccessibleContext getAccessibleContext() {
		return child.getAccessibleContext();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getActionForKeyStroke(javax.swing.KeyStroke)
	 */
	public ActionListener getActionForKeyStroke(KeyStroke aKeyStroke) {
		return child.getActionForKeyStroke(aKeyStroke);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getAlignmentX()
	 */
	public float getAlignmentX() {
		return child.getAlignmentX();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getAlignmentY()
	 */
	public float getAlignmentY() {
		return child.getAlignmentY();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getAncestorListeners()
	 */
	public AncestorListener[] getAncestorListeners() {
		return child.getAncestorListeners();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getAutoscrolls()
	 */
	public boolean getAutoscrolls() {
		return child.getAutoscrolls();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getBackground()
	 */
	public Color getBackground() {
		return child.getBackground();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getBorder()
	 */
	public Border getBorder() {
		return child.getBorder();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getBounds()
	 */
	public Rectangle getBounds() {
		return child.getBounds();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getBounds(java.awt.Rectangle)
	 */
	public Rectangle getBounds(Rectangle rv) {
		return child.getBounds(rv);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getColorModel()
	 */
	public ColorModel getColorModel() {
		return child.getColorModel();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getComponent(int)
	 */
	public Component getComponent(int n) {
		return child.getComponent(n);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getComponentAt(int, int)
	 */
	public Component getComponentAt(int x, int y) {
		return child.getComponentAt(x, y);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getComponentAt(java.awt.Point)
	 */
	public Component getComponentAt(Point p) {
		return child.getComponentAt(p);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getComponentCount()
	 */
	public int getComponentCount() {
		return child.getComponentCount();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getComponentListeners()
	 */
	public synchronized ComponentListener[] getComponentListeners() {
		return child.getComponentListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getComponentOrientation()
	 */
	public ComponentOrientation getComponentOrientation() {
		return child.getComponentOrientation();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getComponents()
	 */
	public Component[] getComponents() {
		return child.getComponents();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getConditionForKeyStroke(javax.swing.KeyStroke)
	 */
	public int getConditionForKeyStroke(KeyStroke aKeyStroke) {
		return child.getConditionForKeyStroke(aKeyStroke);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getContainerListeners()
	 */
	public synchronized ContainerListener[] getContainerListeners() {
		return child.getContainerListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getCursor()
	 */
	public Cursor getCursor() {
		return child.getCursor();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getDebugGraphicsOptions()
	 */
	public int getDebugGraphicsOptions() {
		return child.getDebugGraphicsOptions();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getDropTarget()
	 */
	public synchronized DropTarget getDropTarget() {
		return child.getDropTarget();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getFocusCycleRootAncestor()
	 */
	public Container getFocusCycleRootAncestor() {
		return child.getFocusCycleRootAncestor();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getFocusListeners()
	 */
	public synchronized FocusListener[] getFocusListeners() {
		return child.getFocusListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getFocusTraversalKeys(int)
	 */
	public Set getFocusTraversalKeys(int id) {
		return child.getFocusTraversalKeys(id);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getFocusTraversalKeysEnabled()
	 */
	public boolean getFocusTraversalKeysEnabled() {
		return child.getFocusTraversalKeysEnabled();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getFocusTraversalPolicy()
	 */
	public FocusTraversalPolicy getFocusTraversalPolicy() {
		return child.getFocusTraversalPolicy();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getFont()
	 */
	public Font getFont() {
		return child.getFont();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getFontMetrics(java.awt.Font)
	 */
	public FontMetrics getFontMetrics(Font font) {
		return child.getFontMetrics(font);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getForeground()
	 */
	public Color getForeground() {
		return child.getForeground();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getGraphics()
	 */
	public Graphics getGraphics() {
		return child.getGraphics();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getGraphicsConfiguration()
	 */
	public GraphicsConfiguration getGraphicsConfiguration() {
		return child.getGraphicsConfiguration();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getHeight()
	 */
	public int getHeight() {
		return child.getHeight();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getHierarchyBoundsListeners()
	 */
	public synchronized HierarchyBoundsListener[] getHierarchyBoundsListeners() {
		return child.getHierarchyBoundsListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getHierarchyListeners()
	 */
	public synchronized HierarchyListener[] getHierarchyListeners() {
		return child.getHierarchyListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getIgnoreRepaint()
	 */
	public boolean getIgnoreRepaint() {
		return child.getIgnoreRepaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getInputContext()
	 */
	public InputContext getInputContext() {
		return child.getInputContext();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getInputMethodListeners()
	 */
	public synchronized InputMethodListener[] getInputMethodListeners() {
		return child.getInputMethodListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getInputMethodRequests()
	 */
	public InputMethodRequests getInputMethodRequests() {
		return child.getInputMethodRequests();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getInputVerifier()
	 */
	public InputVerifier getInputVerifier() {
		return child.getInputVerifier();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getInsets()
	 */
	public Insets getInsets() {
		return child.getInsets();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getInsets(java.awt.Insets)
	 */
	public Insets getInsets(Insets insets) {
		return child.getInsets(insets);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getKeyListeners()
	 */
	public synchronized KeyListener[] getKeyListeners() {
		return child.getKeyListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#getLayout()
	 */
	public LayoutManager getLayout() {
		return child.getLayout();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getListeners(java.lang.Class)
	 */
	public EventListener[] getListeners(Class listenerType) {
		return child.getListeners(listenerType);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getLocale()
	 */
	public Locale getLocale() {
		return child.getLocale();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getLocation()
	 */
	public Point getLocation() {
		return child.getLocation();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getLocation(java.awt.Point)
	 */
	public Point getLocation(Point rv) {
		return child.getLocation(rv);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getLocationOnScreen()
	 */
	public Point getLocationOnScreen() {
		return child.getLocationOnScreen();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getMaximumSize()
	 */
	public Dimension getMaximumSize() {
		return child.getMaximumSize();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getMinimumSize()
	 */
	public Dimension getMinimumSize() {
		return child.getMinimumSize();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getMouseListeners()
	 */
	public synchronized MouseListener[] getMouseListeners() {
		return child.getMouseListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getMouseMotionListeners()
	 */
	public synchronized MouseMotionListener[] getMouseMotionListeners() {
		return child.getMouseMotionListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getMouseWheelListeners()
	 */
	public synchronized MouseWheelListener[] getMouseWheelListeners() {
		return child.getMouseWheelListeners();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getName()
	 */
	public String getName() {
		return child.getName();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getParent()
	 */
	public Container getParent() {
		return child.getParent();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	public Dimension getPreferredSize() {
		return child.getPreferredSize();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPropertyChangeListeners()
	 */
	public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
		return child.getPropertyChangeListeners();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getPropertyChangeListeners(java.lang.String)
	 */
	public synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
		return child.getPropertyChangeListeners(propertyName);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getRegisteredKeyStrokes()
	 */
	public KeyStroke[] getRegisteredKeyStrokes() {
		return child.getRegisteredKeyStrokes();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getRootPane()
	 */
	public JRootPane getRootPane() {
		return child.getRootPane();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getSize()
	 */
	public Dimension getSize() {
		return child.getSize();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getSize(java.awt.Dimension)
	 */
	public Dimension getSize(Dimension rv) {
		return child.getSize(rv);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getToolkit()
	 */
	public Toolkit getToolkit() {
		return child.getToolkit();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getToolTipLocation(java.awt.event.MouseEvent)
	 */
	public Point getToolTipLocation(MouseEvent event) {
		return child.getToolTipLocation(event);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getToolTipText()
	 */
	public String getToolTipText() {
		return child.getToolTipText();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getToolTipText(java.awt.event.MouseEvent)
	 */
	public String getToolTipText(MouseEvent event) {
		return child.getToolTipText(event);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getTopLevelAncestor()
	 */
	public Container getTopLevelAncestor() {
		return child.getTopLevelAncestor();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getTransferHandler()
	 */
	public TransferHandler getTransferHandler() {
		return child.getTransferHandler();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getUIClassID()
	 */
	public String getUIClassID() {
		return child.getUIClassID();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getVerifyInputWhenFocusTarget()
	 */
	public boolean getVerifyInputWhenFocusTarget() {
		return child.getVerifyInputWhenFocusTarget();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getVetoableChangeListeners()
	 */
	public synchronized VetoableChangeListener[] getVetoableChangeListeners() {
		return child.getVetoableChangeListeners();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getVisibleRect()
	 */
	public Rectangle getVisibleRect() {
		return child.getVisibleRect();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getWidth()
	 */
	public int getWidth() {
		return child.getWidth();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getX()
	 */
	public int getX() {
		return child.getX();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#getY()
	 */
	public int getY() {
		return child.getY();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#grabFocus()
	 */
	public void grabFocus() {
		child.grabFocus();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#hasFocus()
	 */
	public boolean hasFocus() {
		return child.hasFocus();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return child.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#imageUpdate(java.awt.Image, int, int, int, int, int)
	 */
	public boolean imageUpdate(
		Image img,
		int infoflags,
		int x,
		int y,
		int width,
		int height) {
		return child.imageUpdate(img, infoflags, x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#invalidate()
	 */
	public void invalidate() {
		child.invalidate();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#isAncestorOf(java.awt.Component)
	 */
	public boolean isAncestorOf(Component c) {
		return child.isAncestorOf(c);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isBackgroundSet()
	 */
	public boolean isBackgroundSet() {
		return child.isBackgroundSet();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isCursorSet()
	 */
	public boolean isCursorSet() {
		return child.isCursorSet();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isDisplayable()
	 */
	public boolean isDisplayable() {
		return child.isDisplayable();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isDoubleBuffered()
	 */
	public boolean isDoubleBuffered() {
		return child.isDoubleBuffered();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isEnabled()
	 */
	public boolean isEnabled() {
		return child.isEnabled();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isFocusable()
	 */
	public boolean isFocusable() {
		return child.isFocusable();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#isFocusCycleRoot()
	 */
	public boolean isFocusCycleRoot() {
		return child.isFocusCycleRoot();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#isFocusCycleRoot(java.awt.Container)
	 */
	public boolean isFocusCycleRoot(Container container) {
		return child.isFocusCycleRoot(container);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isFocusOwner()
	 */
	public boolean isFocusOwner() {
		return child.isFocusOwner();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#isFocusTraversalPolicySet()
	 */
	public boolean isFocusTraversalPolicySet() {
		return child.isFocusTraversalPolicySet();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isFontSet()
	 */
	public boolean isFontSet() {
		return child.isFontSet();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isForegroundSet()
	 */
	public boolean isForegroundSet() {
		return child.isForegroundSet();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isLightweight()
	 */
	public boolean isLightweight() {
		return child.isLightweight();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isMaximumSizeSet()
	 */
	public boolean isMaximumSizeSet() {
		return child.isMaximumSizeSet();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isMinimumSizeSet()
	 */
	public boolean isMinimumSizeSet() {
		return child.isMinimumSizeSet();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isOpaque()
	 */
	public boolean isOpaque() {
		return child.isOpaque();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isOptimizedDrawingEnabled()
	 */
	public boolean isOptimizedDrawingEnabled() {
		return child.isOptimizedDrawingEnabled();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isPaintingTile()
	 */
	public boolean isPaintingTile() {
		return child.isPaintingTile();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isPreferredSizeSet()
	 */
	public boolean isPreferredSizeSet() {
		return child.isPreferredSizeSet();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isRequestFocusEnabled()
	 */
	public boolean isRequestFocusEnabled() {
		return child.isRequestFocusEnabled();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isShowing()
	 */
	public boolean isShowing() {
		return child.isShowing();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isValid()
	 */
	public boolean isValid() {
		return child.isValid();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#isValidateRoot()
	 */
	public boolean isValidateRoot() {
		return child.isValidateRoot();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#isVisible()
	 */
	public boolean isVisible() {
		return child.isVisible();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#list()
	 */
	public void list() {
		child.list();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#list(java.io.PrintStream)
	 */
	public void list(PrintStream out) {
		child.list(out);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#list(java.io.PrintStream, int)
	 */
	public void list(PrintStream out, int indent) {
		child.list(out, indent);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#list(java.io.PrintWriter)
	 */
	public void list(PrintWriter out) {
		child.list(out);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#list(java.io.PrintWriter, int)
	 */
	public void list(PrintWriter out, int indent) {
		child.list(out, indent);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		child.paint(g);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#paintAll(java.awt.Graphics)
	 */
	public void paintAll(Graphics g) {
		child.paintAll(g);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#paintComponents(java.awt.Graphics)
	 */
	public void paintComponents(Graphics g) {
		child.paintComponents(g);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintImmediately(int, int, int, int)
	 */
	public void paintImmediately(int x, int y, int w, int h) {
		child.paintImmediately(x, y, w, h);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintImmediately(java.awt.Rectangle)
	 */
	public void paintImmediately(Rectangle r) {
		child.paintImmediately(r);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#prepareImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
	 */
	public boolean prepareImage(
		Image image,
		int width,
		int height,
		ImageObserver observer) {
		return child.prepareImage(image, width, height, observer);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#prepareImage(java.awt.Image, java.awt.image.ImageObserver)
	 */
	public boolean prepareImage(Image image, ImageObserver observer) {
		return child.prepareImage(image, observer);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#print(java.awt.Graphics)
	 */
	public void print(Graphics g) {
		child.print(g);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#printAll(java.awt.Graphics)
	 */
	public void printAll(Graphics g) {
		child.printAll(g);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#printComponents(java.awt.Graphics)
	 */
	public void printComponents(Graphics g) {
		child.printComponents(g);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#registerKeyboardAction(java.awt.event.ActionListener, java.lang.String, javax.swing.KeyStroke, int)
	 */
	public void registerKeyboardAction(
		ActionListener anAction,
		String aCommand,
		KeyStroke aKeyStroke,
		int aCondition) {
		child.registerKeyboardAction(
			anAction,
			aCommand,
			aKeyStroke,
			aCondition);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#registerKeyboardAction(java.awt.event.ActionListener, javax.swing.KeyStroke, int)
	 */
	public void registerKeyboardAction(
		ActionListener anAction,
		KeyStroke aKeyStroke,
		int aCondition) {
		child.registerKeyboardAction(anAction, aKeyStroke, aCondition);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#remove(int)
	 */
	public void remove(int index) {
		child.remove(index);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#remove(java.awt.Component)
	 */
	public void remove(Component comp) {
		child.remove(comp);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#remove(java.awt.MenuComponent)
	 */
	public void remove(MenuComponent comp) {
		child.remove(comp);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#removeAll()
	 */
	public void removeAll() {
		child.removeAll();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#removeAncestorListener(javax.swing.event.AncestorListener)
	 */
	public void removeAncestorListener(AncestorListener listener) {
		child.removeAncestorListener(listener);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeComponentListener(java.awt.event.ComponentListener)
	 */
	public synchronized void removeComponentListener(ComponentListener l) {
		child.removeComponentListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#removeContainerListener(java.awt.event.ContainerListener)
	 */
	public synchronized void removeContainerListener(ContainerListener l) {
		child.removeContainerListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeFocusListener(java.awt.event.FocusListener)
	 */
	public synchronized void removeFocusListener(FocusListener l) {
		child.removeFocusListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeHierarchyBoundsListener(java.awt.event.HierarchyBoundsListener)
	 */
	public void removeHierarchyBoundsListener(HierarchyBoundsListener l) {
		child.removeHierarchyBoundsListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeHierarchyListener(java.awt.event.HierarchyListener)
	 */
	public void removeHierarchyListener(HierarchyListener l) {
		child.removeHierarchyListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeInputMethodListener(java.awt.event.InputMethodListener)
	 */
	public synchronized void removeInputMethodListener(InputMethodListener l) {
		child.removeInputMethodListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeKeyListener(java.awt.event.KeyListener)
	 */
	public synchronized void removeKeyListener(KeyListener l) {
		child.removeKeyListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeMouseListener(java.awt.event.MouseListener)
	 */
	public synchronized void removeMouseListener(MouseListener l) {
		child.removeMouseListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeMouseMotionListener(java.awt.event.MouseMotionListener)
	 */
	public synchronized void removeMouseMotionListener(MouseMotionListener l) {
		child.removeMouseMotionListener(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#removeMouseWheelListener(java.awt.event.MouseWheelListener)
	 */
	public synchronized void removeMouseWheelListener(MouseWheelListener l) {
		child.removeMouseWheelListener(l);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#removeNotify()
	 */
	public void removeNotify() {
		child.removeNotify();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		child.removePropertyChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#removePropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public synchronized void removePropertyChangeListener(
		String propertyName,
		PropertyChangeListener listener) {
		child.removePropertyChangeListener(propertyName, listener);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#removeVetoableChangeListener(java.beans.VetoableChangeListener)
	 */
	public synchronized void removeVetoableChangeListener(VetoableChangeListener listener) {
		child.removeVetoableChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#repaint()
	 */
	public void repaint() {
		child.repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#repaint(int, int, int, int)
	 */
	public void repaint(int x, int y, int width, int height) {
		child.repaint(x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#repaint(java.awt.Rectangle)
	 */
	public void repaint(Rectangle r) {
		child.repaint(r);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#repaint(long)
	 */
	public void repaint(long tm) {
		child.repaint(tm);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#repaint(long, int, int, int, int)
	 */
	public void repaint(long tm, int x, int y, int width, int height) {
		child.repaint(tm, x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocus()
	 */
	public void requestFocus() {
		child.requestFocus();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocusInWindow()
	 */
	public boolean requestFocusInWindow() {
		return child.requestFocusInWindow();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#resetKeyboardActions()
	 */
	public void resetKeyboardActions() {
		child.resetKeyboardActions();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#reshape(int, int, int, int)
	 */
	public void reshape(int x, int y, int width, int height) {
		child.reshape(x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#revalidate()
	 */
	public void revalidate() {
		child.revalidate();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#scrollRectToVisible(java.awt.Rectangle)
	 */
	public void scrollRectToVisible(Rectangle aRect) {
		child.scrollRectToVisible(aRect);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setAlignmentX(float)
	 */
	public void setAlignmentX(float alignmentX) {
		child.setAlignmentX(alignmentX);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setAlignmentY(float)
	 */
	public void setAlignmentY(float alignmentY) {
		child.setAlignmentY(alignmentY);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setAutoscrolls(boolean)
	 */
	public void setAutoscrolls(boolean autoscrolls) {
		child.setAutoscrolls(autoscrolls);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
	 */
	public void setBackground(Color c) {
		child.setBackground(c);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setBorder(javax.swing.border.Border)
	 */
	public void setBorder(Border border) {
		child.setBorder(border);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
	public void setBounds(int x, int y, int width, int height) {
		child.setBounds(x, y, width, height);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setBounds(java.awt.Rectangle)
	 */
	public void setBounds(Rectangle r) {
		child.setBounds(r);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setComponentOrientation(java.awt.ComponentOrientation)
	 */
	public void setComponentOrientation(ComponentOrientation o) {
		child.setComponentOrientation(o);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setCursor(java.awt.Cursor)
	 */
	public void setCursor(Cursor cursor) {
		child.setCursor(cursor);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setDebugGraphicsOptions(int)
	 */
	public void setDebugGraphicsOptions(int debugOptions) {
		child.setDebugGraphicsOptions(debugOptions);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setDoubleBuffered(boolean)
	 */
	public void setDoubleBuffered(boolean aFlag) {
		child.setDoubleBuffered(aFlag);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setDropTarget(java.awt.dnd.DropTarget)
	 */
	public synchronized void setDropTarget(DropTarget dt) {
		child.setDropTarget(dt);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	public void setEnabled(boolean b) {
		child.setEnabled(b);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setFocusable(boolean)
	 */
	public void setFocusable(boolean focusable) {
		child.setFocusable(focusable);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#setFocusCycleRoot(boolean)
	 */
	public void setFocusCycleRoot(boolean focusCycleRoot) {
		child.setFocusCycleRoot(focusCycleRoot);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#setFocusTraversalKeys(int, java.util.Set)
	 */
	public void setFocusTraversalKeys(int id, Set keystrokes) {
		child.setFocusTraversalKeys(id, keystrokes);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setFocusTraversalKeysEnabled(boolean)
	 */
	public void setFocusTraversalKeysEnabled(boolean focusTraversalKeysEnabled) {
		child.setFocusTraversalKeysEnabled(focusTraversalKeysEnabled);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#setFocusTraversalPolicy(java.awt.FocusTraversalPolicy)
	 */
	public void setFocusTraversalPolicy(FocusTraversalPolicy policy) {
		child.setFocusTraversalPolicy(policy);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setFont(java.awt.Font)
	 */
	public void setFont(Font f) {
		child.setFont(f);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setForeground(java.awt.Color)
	 */
	public void setForeground(Color c) {
		child.setForeground(c);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setIgnoreRepaint(boolean)
	 */
	public void setIgnoreRepaint(boolean ignoreRepaint) {
		child.setIgnoreRepaint(ignoreRepaint);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setInputVerifier(javax.swing.InputVerifier)
	 */
	public void setInputVerifier(InputVerifier inputVerifier) {
		child.setInputVerifier(inputVerifier);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#setLayout(java.awt.LayoutManager)
	 */
	public void setLayout(LayoutManager mgr) {
		child.setLayout(mgr);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale l) {
		child.setLocale(l);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setLocation(int, int)
	 */
	public void setLocation(int x, int y) {
		child.setLocation(x, y);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setLocation(java.awt.Point)
	 */
	public void setLocation(Point p) {
		child.setLocation(p);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setMaximumSize(java.awt.Dimension)
	 */
	public void setMaximumSize(Dimension maximumSize) {
		child.setMaximumSize(maximumSize);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setMinimumSize(java.awt.Dimension)
	 */
	public void setMinimumSize(Dimension minimumSize) {
		child.setMinimumSize(minimumSize);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	public void setName(String name) {
		child.setName(name);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setOpaque(boolean)
	 */
	public void setOpaque(boolean isOpaque) {
		child.setOpaque(isOpaque);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setPreferredSize(java.awt.Dimension)
	 */
	public void setPreferredSize(Dimension preferredSize) {
		child.setPreferredSize(preferredSize);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setRequestFocusEnabled(boolean)
	 */
	public void setRequestFocusEnabled(boolean requestFocusEnabled) {
		child.setRequestFocusEnabled(requestFocusEnabled);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setSize(int, int)
	 */
	public void setSize(int width, int height) {
		child.setSize(width, height);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#setSize(java.awt.Dimension)
	 */
	public void setSize(Dimension d) {
		child.setSize(d);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setToolTipText(java.lang.String)
	 */
	public void setToolTipText(String text) {
		child.setToolTipText(text);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setTransferHandler(javax.swing.TransferHandler)
	 */
	public void setTransferHandler(TransferHandler newHandler) {
		child.setTransferHandler(newHandler);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setVerifyInputWhenFocusTarget(boolean)
	 */
	public void setVerifyInputWhenFocusTarget(boolean verifyInputWhenFocusTarget) {
		child.setVerifyInputWhenFocusTarget(verifyInputWhenFocusTarget);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setVisible(boolean)
	 */
	public void setVisible(boolean b) {
		child.setVisible(b);
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	public String toString() {
		return child.toString();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#transferFocus()
	 */
	public void transferFocus() {
		child.transferFocus();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#transferFocusBackward()
	 */
	public void transferFocusBackward() {
		child.transferFocusBackward();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#transferFocusDownCycle()
	 */
	public void transferFocusDownCycle() {
		child.transferFocusDownCycle();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#transferFocusUpCycle()
	 */
	public void transferFocusUpCycle() {
		child.transferFocusUpCycle();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#unregisterKeyboardAction(javax.swing.KeyStroke)
	 */
	public void unregisterKeyboardAction(KeyStroke aKeyStroke) {
		child.unregisterKeyboardAction(aKeyStroke);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#update(java.awt.Graphics)
	 */
	public void update(Graphics g) {
		child.update(g);
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#updateUI()
	 */
	public void updateUI() {
		child.updateUI();
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#validate()
	 */
	public void validate() {
		child.validate();
	}

}








