/*
 * ViewerPreferences.java
 *
 * Created on 1. Juli 2002, 22:06
 */

package org.wewi.medimg.viewer;

import org.wewi.medimg.util.Singleton;

import java.util.prefs.Preferences;

import java.awt.Dimension;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class ViewerPreferences implements Singleton {
    private Preferences userRoot;
    private Preferences viewerNode;
    
    private static ViewerPreferences singleton = null;
    
    //Default Werte
    private static final Dimension DEFAULT_VIEWER_DIMENSION = new Dimension(1024, 748);
    
    /** Creates a new instance of ViewerPreferences */
    private ViewerPreferences() {
        userRoot = Preferences.userRoot();
        try {
            //if (!userRoot.nodeExists("org.wewi.medimg.viewer.Viewer")) {
                viewerNode = userRoot.node("org.wewi.medimg.viewer.Viewer");
            //}
        } catch (Exception e) {
            System.out.println("ViewerPreferences: " + e);
        }
    }
    
    public static ViewerPreferences getInstance() {
        if (singleton == null) {
            singleton = new ViewerPreferences();
        }
        return singleton;
    }
    
    public Dimension getViewerDimension() {
        int width = viewerNode.getInt("VIEWER_DIMENSION_WIDTH", 1024);
        int height = viewerNode.getInt("VIEWER_DIMENSION_HEIGHT", 748);
        
        return new Dimension(width, height);
    }
    
    public void setViewerDimension(Dimension dim) {
        viewerNode.putInt("VIEWER_DIMENSION_WIDTH", (int)dim.getWidth());
        viewerNode.putInt("VIEWER_DIMENSION_HEIGHT", (int)dim.getHeight());
        try {
            viewerNode.sync();  
        } catch (Exception e) {
            System.out.println("ViewerPreferences.setViewerDimension: " + e);
        }
    }
    
}
