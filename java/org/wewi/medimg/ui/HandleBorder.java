/**
 * HandleBorder.java
 * Created on 03.04.2003
 *
 */
package org.wewi.medimg.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JComponent;


/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class HandleBorder extends JComponent {
    private Component child;
    
    private Color lineColor = Color.BLUE;
    private Color handleColor = Color.BLUE;
    private Stroke lineStroke = new BasicStroke();

    public HandleBorder(Component component) {
        child = component;
        add(child);
    }


    private void paintBorder(Graphics2D g) {
    }
}
