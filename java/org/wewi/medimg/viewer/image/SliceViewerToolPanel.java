/**
 * SliceViewerToolPanel.java
 * 
 * Created on 05.03.2003, 13:28:00
 *
 */
package org.wewi.medimg.viewer.image;

import java.awt.FlowLayout;
import java.awt.Font;
import java.text.NumberFormat;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class SliceViewerToolPanel extends JPanel {
    
    private final class ZoomToolBar extends JToolBar {
        private final static int MAX_VALUE = 500;
        private final static int MIN_VALUE = 10;
        private final static double DIVISOR = 100;
        
        private final static String NAME = "Zoom: ";
        
        //Components
        private JSlider slider;
        private TitledBorder border;
    
        public ZoomToolBar() {
            initComponents();
        }
    
        private void initComponents() {
            slider = new JSlider();
            slider.setMaximum(MAX_VALUE);
            slider.setMinimum(MIN_VALUE);
            slider.setValue((int)(1.0*DIVISOR));
            slider.setSnapToTicks(true);
            slider.setPaintTicks(true);
            slider.setMajorTickSpacing((int)(MAX_VALUE/DIVISOR*5));
            
            border = new TitledBorder(null, NAME + format(), 
                                      TitledBorder.DEFAULT_JUSTIFICATION, 
                                      TitledBorder.DEFAULT_POSITION, 
                                      new Font("Dialog", 0, 12));
            slider.setBorder(border);
            add(slider);
            
            //Adding the listener
            slider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
                    zoomChanged();   
				}
            });
        }
        
        private String format() {
            NumberFormat f = NumberFormat.getInstance();
            f.setMaximumFractionDigits(2);
            f.setMinimumFractionDigits(2);
            
            return f.format(getZoomValue()*100) + "%";
        }
        
        private double getZoomValue() {
            return (double)slider.getValue()/DIVISOR;
        }
        
        private void zoomChanged() {
            parent.setZoom(getZoomValue());  
            parent.update(); 
            
            border = new TitledBorder(null, NAME + format(), 
                                                  TitledBorder.DEFAULT_JUSTIFICATION, 
                                                  TitledBorder.DEFAULT_POSITION, 
                                                  new Font("Dialog", 0, 12));
            slider.setBorder(border);
        }
    
    }
    
    private final class SliceToolBar extends JToolBar {
        private int MAX_VALUE = 1000;
        private int MIN_VALUE = 0;
        private double DIVISOR = 100;
        
        private final static String NAME = "Slice: ";        
        
        //Components
        private JSlider slider;
        private TitledBorder border;
        
        public SliceToolBar() {
            int sizeZ = parent.getImage().getDimension().getSizeZ();
            DIVISOR = (double)MAX_VALUE/(double)sizeZ;
            
            initComponents();
        }
        
        private void initComponents() {
            slider = new JSlider();
            slider.setMaximum(MAX_VALUE);
            slider.setMinimum(MIN_VALUE);
            slider.setMajorTickSpacing((int)(MAX_VALUE/DIVISOR));
            slider.setSnapToTicks(true);
            slider.setPaintTicks(true);

            border = new TitledBorder(null, NAME +  format(), 
                                      TitledBorder.DEFAULT_JUSTIFICATION, 
                                      TitledBorder.DEFAULT_POSITION, 
                                      new Font("Dialog", 0, 12));
            slider.setBorder(border);
            add(slider); 
            
            //Adding the listener
            slider.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    sliceChanged();
                }
            });
        }
        
        private String format() {
            NumberFormat f = NumberFormat.getInstance();
            f.setMaximumFractionDigits(2);
            f.setMinimumFractionDigits(2);
            
            return f.format(getValue()) + " cm";
        }        
        
        private void sliceChanged() {
            parent.setPlanePosition(getValue());
            parent.update();
            
            border = new TitledBorder(null, NAME + format(), 
                                                  TitledBorder.DEFAULT_JUSTIFICATION, 
                                                  TitledBorder.DEFAULT_POSITION, 
                                                  new Font("Dialog", 0, 12));
            slider.setBorder(border);            
        }
        
        
        public double getValue() {
            int v = slider.getValue();
            
            return (double)v/DIVISOR;    
        }
    }
    
    
    
    private SliceViewerPanel parent;
    
    //Components
    private ZoomToolBar zoomToolBar;
    private SliceToolBar sliceToolBar;
    
    public SliceViewerToolPanel(SliceViewerPanel parent) {
        this.parent = parent;
        
        initComponents();
    }
    
    
    private void initComponents() {
        setLayout(new FlowLayout());  
        
        zoomToolBar = new ZoomToolBar();
        sliceToolBar = new SliceToolBar();
        
        add(zoomToolBar);
        add(sliceToolBar);
    }
    
    
}






