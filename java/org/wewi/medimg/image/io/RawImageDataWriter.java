/*
 * RawImageDataWriter.java
 *
 * Created on 18. Jänner 2002, 13:55
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class RawImageDataWriter extends ImageWriter {

    public RawImageDataWriter(ImageData image, File target) {
        super(image, target);
    }
    
    public void write() {
        DataOutputStream out = null;
        int size = image.getNVoxels();
        
        try {
            out = new DataOutputStream(new FileOutputStream(target));
            writeHeader(out);
            for (int i = 0; i < size; i++) {
                out.writeShort(image.getColor(i));
            }
            out.close();
        } catch (IOException ioe) {
            System.out.println("RawImageDataWriter.write: " + ioe);
            //Löschen der bereits geschriebenen Datei
        }
    }
    
    private void writeHeader(DataOutputStream out) throws IOException {
        int maxX = image.getMaxX();
        int maxY = image.getMaxY();
        int maxZ = image.getMaxZ();
        out.writeInt(maxX);
        out.writeInt(maxY);
        out.writeInt(maxZ);
    }
   
    
    /*
    public static void main(String[] args) {
        File source = new File("C:/Workspace/fwilhelm/Projects/SRS/data/srsSEG/segTest/head.in.001");
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), source);
        reader.read();
        ImageData data = (ImageData)reader.getImage();
        System.out.println(data);
        
        ImageWriter writer = new RawImageDataWriter(data, new File("c:/temp/out.rid"));
        ZIPImageWriter zipWriter = new ZIPImageWriter(writer);
        zipWriter.write();
    }
    */
}




