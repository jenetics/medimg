/**
 * Created on 13.11.2002 15:53:02
 *
 */
package org.wewi.medimg.image;

import java.io.File;

import org.wewi.medimg.image.io.*;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MarginImage extends AbstractImage {

	/**
	 * Constructor for MarginImage.
	 */
	public MarginImage(Image image, int margin) {
		super(image.getMinX()-margin, image.getMaxX()+margin,
               image.getMinY()-margin, image.getMaxY()+margin, 
               image.getMinZ(), image.getMinZ());
         
        //Kopieren des Bildinhaltes     
        int minZ = image.getMinZ(); 
        for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
            for (int j = image.getMinY(), m = image.getMaxY(); j <=m; j++) {
                setColor(i, j, minZ, image.getColor(i, j, minZ));    
            }        
        }
        
        initMargin(image, margin);

        /*
        int minX = getMinX();
        int maxX = getMaxX();
        int minY = getMinY();
        int maxY = getMaxY();
        int imgMinX = image.getMinX();
        int imgMinY = image.getMinY();
        int imgMinZ = image.getMinZ();
        int imgSizeX = image.getMaxX() - image.getMinX() + 1;
        int imgSizeY = image.getMaxY() - image.getMinY() + 1;
        for (int i = 0, n = maxX-minX; i <= n; i++) {
            for (int j = 0, m = maxY-minY; j <=m; j++) {
                setColor(i+minX, j+minY, 0, image.getColor(((i-minX)%imgSizeX)+imgMinX,
                                                           ((j-minY)%imgSizeY)+imgMinY, imgMinZ));    
            }        
        } 
        */       
                   
	}

	/**
	 * Constructor for MarginImage.
	 * @param id
	 */
	private MarginImage(MarginImage id) {
		super(id);
	}
    
    protected void initMargin(Image image, int margin) {
        int minZ = image.getMinZ();
        //"Einfärben" des oberen Randes durch Spiegeln an der Kante
        int maxY = image.getMaxY();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) { 
                setColor(i, maxY+1+j, minZ, image.getColor(i, maxY-j, minZ));           
            }
        }
        
        //"Einfärben" des unteren Randes durch Spiegeln an der Kante
        int minY = image.getMinY();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) { 
                setColor(i, minY-1-j, minZ, image.getColor(i, minY+j, minZ));           
            }
        }
        
        //"Einfärben" des linken Randes durch Spiegeln an der Kante
        int minX = image.getMinX();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinY(), n = image.getMaxY(); i <= n; i++) {
                setColor(minX-1-j, i, minZ, image.getColor(minX+j, i, minZ));    
            }   
        }
        
        //"Einfärben" des rechten Randes durch Spiegeln an der Kante
        int maxX = image.getMaxX();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinY(), n = image.getMaxY(); i <= n; i++) {
                setColor(maxX+1+j, i, minZ, image.getColor(maxX-j, i, minZ));    
            }   
        } 
        
        //Einfärben der Ecken
        for (int i = 0; i < margin; i++) {
            for (int j = 0; j < margin; j++) {
                setColor(minX-1-i, minY-1-j, minZ, image.getColor(minX+i, minY+j, minZ));
                setColor(maxX+1+i, maxY+1+j, minZ, image.getColor(maxX-i, maxY-j, minZ));
                setColor(maxX+1+i, minY-1-j, minZ, image.getColor(maxX-i, minY+j, minZ));
                setColor(minX-1-i, maxY+1+j, minZ, image.getColor(minY+i, maxY-j, minZ));   
            }    
        }         
    }

	/**
	 * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
	 */
	protected DiscreteData createDiscreteData(int size) {
		return new IntData(size);
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new MarginImage(this);
	}
    
    
    
    
    public static void main(String[] args) {
        ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(),
                                           new File("../../data/nhead/t1.n3.rf20/image.0045.tif")); 
                                           
        try {
			reader.read();
		} catch (ImageIOException e) {
            System.out.println("MarginImage: " + e); 
            return;
		}    
        
        Image image = reader.getImage();
        MarginImage mimage = new MarginImage(image, 50);
        //mimage = new MarginImage(mimage, 50);
        
        ImageWriter writer = new TIFFWriter(mimage, new File("X:/margin.image.tif"));
        try {
			writer.write();
		} catch (ImageIOException e) {
		}
        
        
        
        
    }

}
