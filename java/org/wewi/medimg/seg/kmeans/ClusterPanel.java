/*
 * ClusterPanel.java
 *
 * Created on 5. Februar 2002, 16:09
 */

package org.wewi.medimg.seg.kmeans;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Stroke;
import java.awt.Color;
import java.awt.BasicStroke;

import javax.swing.JPanel;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ClusterPanel extends JPanel {
    private DataPoint[][] data;
    private DataPoint[] center;
    private int nclusters;
    private Stroke[] strokes;
    private Stroke centerStroke;
    private Color[] colors;

    /** Creates new ClusterPanel */
    public ClusterPanel(DataPoint[][] data, DataPoint[] center) {
        this.data = data;
        this.center = center;
        nclusters = data.length;
        setBackground(Color.white);
        
        strokes = new Stroke[nclusters];
        for (int i = 0; i < nclusters; i++) {
            strokes[i] = new BasicStroke(3);
        }
        centerStroke = new BasicStroke(5);

        
        colors = new Color[39];
        colors[0] = Color.red;
        colors[1] = Color.green;
        colors[2] = Color.white;
        colors[3] = Color.red;
        colors[4] = Color.pink;
        colors[5] = Color.green;
        colors[6] = Color.lightGray;
    }
    
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        int[] point;
        
        for (int i = 0; i < data.length; i++) {
            g.setStroke(strokes[i]);
            g.setColor(colors[i]);
            for (int j = 0; j < data[i].length; j++) {
                IntegerDataPoint p = (IntegerDataPoint)data[i][j];
                point = p.getValue();
                g.drawOval(point[0], point[1], 3, 3);
            }
        }
        
        if (center != null) {
            g.setColor(Color.red);
            g.setPaint(Color.black);
            g.setStroke(centerStroke);
            for (int i = 0; i < center.length; i++) {
                IntegerDataPoint p = (IntegerDataPoint)center[i];
                point = p.getValue();
                g.setStroke(centerStroke);
                g.setPaint(Color.black);
                g.fill (new Ellipse2D.Double(point[0], point[1], 20,20));
                g.setColor(Color.white);
                g.drawOval(point[0],point[1], 20, 20);
            }
        }
    } 
}
