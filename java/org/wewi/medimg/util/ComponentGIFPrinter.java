/*
 * ComponentGIFPrinter.java
 *
 * Created on 02. Februar 2002, 13:46
 */

package org.wewi.medimg.util;

import java.awt.Component;
import java.awt.Image;
import java.awt.Graphics;

import javax.swing.RepaintManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;

import jas.util.encoder.GifEncoder;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */ 
public class ComponentGIFPrinter extends ComponentPrinter { 

    /** Creates new ComponentGIFPrinter */
    public ComponentGIFPrinter(Component component) {
        super(component);
    }

    public void print(File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        java.awt.Image img = component.createImage(component.getWidth() ,component.getHeight());
        Graphics g = img.getGraphics();

        // TODO: It would be better to use the PrintHelper to do this??
        // TODO: Make sure we get high quality printing for GIF.
        RepaintManager pm = RepaintManager.currentManager(component);
        boolean save = pm.isDoubleBufferingEnabled();
        pm.setDoubleBufferingEnabled(false);
        component.print(g);
        g.dispose();
        pm.setDoubleBufferingEnabled(save);

        // The rest could be done in a separate thread
        GifEncoder encoder = new GifEncoder(img,out,false);
        encoder.encode();
        img.flush();
        out.close();        
    }
    
}
