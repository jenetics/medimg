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
 * @author Franz Wilhelmst�tter
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
