/*
 * SegmenterArgumentPanel.java
 *
 * Created on 11. August 2002, 21:14
 */

package org.wewi.medimg.seg.wizard;

import org.wewi.medimg.seg.Segmenter;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public abstract class SegmenterArgumentPanel extends javax.swing.JPanel {
    
    /** Creates new form SegmenterArgumentPanel */
    public SegmenterArgumentPanel() { 
    }
    
    public abstract Segmenter getSegmenter();
    
}
