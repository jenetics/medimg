/**
 * Created on 13.11.2002 15:53:02
 *
 */
package org.wewi.medimg.image;



/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MarginImage extends AbstractImage {
    private int slice;
    
    public MarginImage(Image image, int slice, int margin) {
        super(image.getMinX()-margin, image.getMaxX()+margin,
               image.getMinY()-margin, image.getMaxY()+margin, 
               image.getMinZ(), slice);
         
        this.slice = slice;
         
        //Kopieren des Bildinhaltes     
        for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) {
            for (int j = image.getMinY(), m = image.getMaxY(); j <=m; j++) {
                setColor(i, j, slice, image.getColor(i, j, slice));    
            }        
        }
        
        initMargin(image, margin);        
    }
    

    /**
     * Constructor for MarginImage.
     */
    public MarginImage(Image image, int margin) {
        this(image, image.getMinZ(), margin);              
    }

    /**
     * Constructor for MarginImage.
     * @param id
     */
    private MarginImage(MarginImage id) {
        super(id);
    }
    
    protected void initMargin(Image image, int margin) {
        //"Einfärben" des oberen Randes durch Spiegeln an der Kante
        int maxY = image.getMaxY();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) { 
                setColor(i, maxY+1+j, slice, image.getColor(i, maxY-j, slice));           
            }
        }
        
        //"Einfärben" des unteren Randes durch Spiegeln an der Kante
        int minY = image.getMinY();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinX(), n = image.getMaxX(); i <= n; i++) { 
                setColor(i, minY-1-j, slice, image.getColor(i, minY+j, slice));           
            }
        }
        
        //"Einfärben" des linken Randes durch Spiegeln an der Kante
        int minX = image.getMinX();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinY(), n = image.getMaxY(); i <= n; i++) {
                setColor(minX-1-j, i, slice, image.getColor(minX+j, i, slice));    
            }   
        }
        
        //"Einfärben" des rechten Randes durch Spiegeln an der Kante
        int maxX = image.getMaxX();
        for (int j = 0; j < margin; j++) {
            for (int i = image.getMinY(), n = image.getMaxY(); i <= n; i++) {
                setColor(maxX+1+j, i, slice, image.getColor(maxX-j, i, slice));    
            }   
        } 
        
        //Einfärben der Ecken
        for (int i = 0; i < margin; i++) {
            for (int j = 0; j < margin; j++) {
                setColor(minX-1-i, minY-1-j, slice, image.getColor(minX+i, minY+j, slice));
                setColor(maxX+1+i, maxY+1+j, slice, image.getColor(maxX-i, maxY-j, slice));
                setColor(maxX+1+i, minY-1-j, slice, image.getColor(maxX-i, minY+j, slice));
                setColor(minX-1-i, maxY+1+j, slice, image.getColor(minY+i, maxY-j, slice));   
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
    
    
    
    /*
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
    */
    
}
