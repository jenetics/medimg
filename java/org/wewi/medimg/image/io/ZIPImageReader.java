/*
 * ZIPImageReader.java
 *
 * Created on 18. Jänner 2002, 20:49
 */

package org.wewi.medimg.image.io;

import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.ImageData;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;

import java.util.Enumeration;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public final class ZIPImageReader extends CompressedImageReader {

    public ZIPImageReader(ImageReader component) {
        super(component);
    }
    
    public void read() throws ImageIOException {
        File source = getSource();
        File sourceTemp = new File(System.getProperty("TEMP") + "/_temp_");
        if (!sourceTemp.mkdirs()) {
            throw new ImageIOException("ZIPImageReader.read: konte Datei " + 
                                       sourceTemp.getAbsolutePath() + " nicht anlegen!");
        }
        
        FileOutputStream out = null;
        InputStream in = null;
        ZipFile zipFile = null;
        ZipEntry zipEntry = null;
        String componentSource = null;
        byte[] buffer = new byte[1024];
        int length;
        
        try {
            zipFile = new ZipFile(source);
            for (Enumeration enum = zipFile.entries(); enum.hasMoreElements();) {
                zipEntry = (ZipEntry)enum.nextElement();
                in = zipFile.getInputStream(zipEntry);
                componentSource = sourceTemp.getPath() + File.separator + zipEntry.getName();
                out = new FileOutputStream(componentSource);

                while((length = in.read(buffer)) != -1) {
                    out.write(buffer, 0, length);
                }
                out.close();
            }
        } catch (IOException ioe) {
            throw new ImageIOException();
        }
        
        super.setSource(new File(componentSource));
        super.read();
        
        //Löschen des Temporären Verzeichnisses
        File[] files = sourceTemp.listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        sourceTemp.delete();
    }
    
    
    
    
    /*
    public static void main(String[] args) {
        File source = new File("C:/cygwin/home/fwilhelm/diplom/algorithms/data/head.in.001");
        TIFFReader reader = new TIFFReader(ImageDataFactory.getInstance(), source);
        reader.read();
        ImageData data = (ImageData)reader.getImage();
        System.out.println("Eingelesen:");
        System.out.println(data);
        
        ZIPImageWriter writer = new ZIPImageWriter(new TIFFWriter(data, new File("c:/temp/out")));
        writer.write();
        System.out.println("Geschrieben");
        
        ZIPImageReader zipReader = new ZIPImageReader(
                                       new TIFFReader(ImageDataFactory.getInstance(), 
                                           new File("c:/temp/out.zip")));
        zipReader.read();
        data = (ImageData)zipReader.getImage();
        System.out.println("Zweites Einlesen:");
        System.out.println(data);        
    }
    */
}



