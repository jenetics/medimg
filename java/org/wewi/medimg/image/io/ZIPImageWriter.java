/*
 * ZIPImageWriter.java
 *
 * Created on 14. Januar 2002, 16:42
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class ZIPImageWriter extends CompressedImageWriter {
    
    public ZIPImageWriter(ImageWriter imageWriter) {
        super(imageWriter);
    }

    public void write() throws ImageIOException {
        super.write();
        writeZIPFile();
        deleteTempFiles();
    }
    
    private void writeZIPFile() {
        File dir = super.getTarget();
        if (!dir.isDirectory()) {
            return;
        }
        
        File[] files = dir.listFiles();
        Iterator fileIterator = new FileIterator(files);
        File file = null;
        ZipEntry zipEntry = null;
        FileInputStream in = null;
        ZipOutputStream out = null;
        
        try {
            out = new ZipOutputStream(new FileOutputStream(dir.getPath() + ".zip"));
        } catch (IOException ioe) {
            System.out.println("ZIPImageFileWriter.writeZIPFile: " + ioe);
        }
        
        while (fileIterator.hasNext()) {
            file = (File)fileIterator.next();
            zipEntry = new ZipEntry(file.getName());
            try {
                out.putNextEntry(zipEntry);
                in = new FileInputStream(file);
                byte[] data = new byte[1024];
                int length;
                while((length = in.read(data)) != -1) {
                    out.write(data, 0, length);
                }
                in.close();
            } catch (IOException ioe) {
                System.out.println("ZIPImageWriter.writeZIPFile: " + ioe);
            }            
        }
        
        try {
            out.close();
        } catch (IOException ioe) {
            System.out.println("ZIPImageWriter.writeZIPFile: " + ioe);
        }
    }
    
    private void deleteTempFiles() {
        File dir = super.getTarget();
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        dir.delete();
    }
}





