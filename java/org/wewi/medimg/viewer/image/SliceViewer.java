
package org.wewi.medimg.viewer.image;


import javax.swing.JFrame;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.TIFFReader;


public class SliceViewer extends JFrame {

	public SliceViewer(Image image) {
        getContentPane().add(new SliceViewerPanel(image));
	}
    

    
    public static void main(String[] args) {
        try {
            ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                              "C:/Workspace/Projekte/Diplom/validation/data/nhead/t1.n3.rf20");
            reader.read();
            
            
            SliceViewer viewer = new SliceViewer(reader.getImage());
            viewer.show();
            viewer.setSize(500, 500);
            viewer.repaint();
        } catch (Exception e) {
            System.err.println("Fehler: " + e);
            e.printStackTrace();
        }
        
        
        
    }

}
