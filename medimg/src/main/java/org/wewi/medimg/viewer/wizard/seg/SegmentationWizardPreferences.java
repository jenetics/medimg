/*
 * SegmentationWizardPreferences.java
 *
 * Created on 1. Juli 2002, 23:19
 */

package org.wewi.medimg.viewer.wizard.seg;

import java.awt.Dimension;
import java.awt.Point;
import java.util.prefs.Preferences;

import org.wewi.medimg.util.Singleton;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class SegmentationWizardPreferences implements Singleton {
    private static SegmentationWizardPreferences singleton = null;
    
    private Preferences userRoot;
    private Preferences viewerNode;
    private Preferences swNode;
    
    /** Creates a new instance of SegmentationWizardPreferences */
    private SegmentationWizardPreferences() {
        userRoot = Preferences.userRoot();
        try {
            viewerNode = userRoot.node("org.wewi.medimg.viewer.Viewer");
            swNode = viewerNode.node("SegmentationWizard");
        } catch (Exception e) {
            System.err.println("SegmentationWizardPreferences: " + e);
        }        
    }
    
    public static SegmentationWizardPreferences getInstance() {
        if (singleton == null) {
            singleton =  new SegmentationWizardPreferences();
        }
        return singleton;
    }
    
    public Dimension getWizardDimension() {
        int width = swNode.getInt("WIZARD_DIMENSION_WIDTH", 500);
        int height = swNode.getInt("WIZARD_DIMENSION_HEIGHT", 300);
        
        return new Dimension(width, height);
    }
    
    public void setWizardDimension(Dimension dim) {
        swNode.putInt("WIZARD_DIMENSION_WIDTH", (int)dim.getWidth());
        swNode.putInt("WIZARD_DIMENSION_HEIGHT", (int)dim.getHeight());
        try {
            viewerNode.sync();  
        } catch (Exception e) {
            System.err.println("SegmentationWizardPreferences.setWizardDimension: " + e);
        }
    } 
    
    public Point getWizardLocation() {
        int x = swNode.getInt("WIZARD_LOCATION_X", 0);
        int y = swNode.getInt("WIZARD_LOCATION_Y", 0);  
        
        return new Point(x, y);
    }
    
    public void setWizardLocation(Point p) {
        swNode.putInt("WIZARD_LOCATION_X", (int)p.getX());
        swNode.putInt("WIZARD_LOCATION_Y", (int)p.getY());
        
        try {
            viewerNode.sync();  
        } catch (Exception e) {
            System.out.println("SegmentationWizardPreferences.setWizardLocation: " + e);
        }        
    }       
    
}
