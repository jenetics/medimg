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
 * ViewerPreferences.java
 *
 * Created on 1. Juli 2002, 22:06
 */

package org.wewi.medimg.viewer;

import java.awt.Dimension;
import java.awt.Point;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.UIManager;

import org.wewi.medimg.util.Singleton;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class ViewerPreferences implements Singleton {
    private Preferences userRoot;
    private Preferences viewerNode;
    
    private static ViewerPreferences singleton = null;
    
    private Logger logger;
    
    /** Creates a new instance of ViewerPreferences */
    private ViewerPreferences() {
        logger = Logger.getLogger(Viewer.class.getName());
        
        userRoot = Preferences.userRoot();
        try {
            //if (!userRoot.nodeExists("org.wewi.medimg.viewer.Viewer")) {
                viewerNode = userRoot.node("org.wewi.medimg.viewer.Viewer");
            //}
        } catch (Exception e) {
            logger.throwing(ViewerPreferences.class.getName(), "ViewerPreferences()", e);
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
            logger.throwing(ViewerPreferences.class.getName(), "setViewerDimension()", e);
        }
    }
    
    public Point getViewerLocation() {
        int x = viewerNode.getInt("VIEWER_LOCATION_X", 0);
        int y = viewerNode.getInt("VIEWER_LOCATION_Y", 0);  
        
        return new Point(x, y);
    }
    
    public void setViewerLocation(Point p) {
        viewerNode.putInt("VIEWER_LOCATION_X", (int)p.getX());
        viewerNode.putInt("VIEWER_LOCATION_Y", (int)p.getY());
        
        try {
            viewerNode.sync();  
        } catch (Exception e) {
            logger.throwing(ViewerPreferences.class.getName(), "setViewerLocation()", e);
        }        
    }
    
    public String getMostRecentFile() {
        return viewerNode.get("VIEWER_MOST_RECENT_FILE", "");
    }
    
    public void setMostRecentFile(String file) {
        viewerNode.put("VIEWER_MOST_RECENT_FILE", file);
        
        try {
            viewerNode.sync();  
        } catch (Exception e) {
            logger.throwing(ViewerPreferences.class.getName(), "getMostRecentFile()", e);
        }         
    }
    
    public String getMostRecentFileType() {
        return viewerNode.get("VIEWER_MOST_RECENT_FILE_TYPE", "");    
    }
    
    public void setMostRecentFileType(String type) {
        viewerNode.put("VIEWER_MOST_RECENT_FILE_TYPE", type);
        
        try {
            viewerNode.sync();  
        } catch (Exception e) {
            logger.throwing(ViewerPreferences.class.getName(), "getMostRecentFileType()", e);
        }         
    }    
    
    public String getLookAndFeelClassName() {
        return viewerNode.get("VIEWER_LOOK_AND_FEEL_CLASS_NAME", 
                               UIManager.getCrossPlatformLookAndFeelClassName());   
    }
    
    public void setLookAndFeelClassName(String name) {
        viewerNode.put("VIEWER_LOOK_AND_FEEL_CLASS_NAME", name);
        
        try {
            viewerNode.sync();  
        } catch (Exception e) {
            logger.throwing(ViewerPreferences.class.getName(), "setLookAndFeelClassName", e);
        }            
    }
    
}











