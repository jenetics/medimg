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
 * ClusterPanel.java
 *
 * Created on 5. Februar 2002, 16:09
 */

package org.wewi.medimg.seg.kmeans;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JPanel;

import org.wewi.medimg.math.geom.DataPoint;
import org.wewi.medimg.math.geom.IntegerDataPoint;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ClusterPanel extends JPanel {
    private Collection[] cluster;
    private DataPoint[] center;
    private Stroke[] strokes;
    private Stroke centerStroke;
    private Color[] colors;

    /** Creates new ClusterPanel */
    public ClusterPanel(Collection[] cluster, DataPoint[] center) {
        this.cluster = cluster;
        this.center = center;
        setBackground(Color.white);
        
        strokes = new Stroke[center.length];
        for (int i = 0; i < center.length; i++) {
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
        
        for (int i = 0; i < cluster.length; i++) {
            g.setStroke(strokes[i]);
            g.setColor(colors[i]);
            for (Iterator it = cluster[i].iterator(); it.hasNext();) {
                IntegerDataPoint p = (IntegerDataPoint)it.next();
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
