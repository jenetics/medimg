/*
 * SegmentationWizardPreferences.java
 *
 * Created on 1. Juli 2002, 23:19
 */

package org.wewi.medimg.seg.wizard;

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
            swNode = viewerNode.node("segmentationwizard");
        } catch (Exception e) {
            System.out.println("ViewerPreferences: " + e);
        }        
    }
    
    public static SegmentationWizardPreferences getInstance() {
        if (singleton == null) {
            singleton =  new SegmentationWizardPreferences();
        }
        return singleton;
    }
    
}
