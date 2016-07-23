/*
 * RawReader.java, created on 13.10.2003
 *
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
package org.wewi.medimg.seg.validation;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImage;
import org.wewi.medimg.image.io.ImageIOException;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.TIFFWriter;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RawReader {
    private File file;
    private File out;
    
    public RawReader(File file, File out) {
        this.file = file;
        this.out = out;
    }


    public void read() throws IOException {
        final int MIN_X = 0;
        final int MAX_X = 255;
        final int MIN_Y = 0;
        final int MAX_Y = 255;
        final int MIN_Z = 0;
        final int MAX_Z = 155;
        
        //Writing the image
        System.out.println("Reading the image... (" + file.toString() + ")");
        
        Reader reader = new SignedShortReader(file);
        Image image = new IntImage(MIN_X, MAX_X, MIN_Y, MAX_Y, MIN_Z, MAX_Z);
        for (int k = MIN_Z; k <= MAX_Z; k++) {
            for (int j = MIN_Y; j <= MAX_Y; j++) {
                for (int i = MIN_X; i <= MAX_X; i++) {
                    image.setColor(i, j, k, reader.read());
                }
            } 
        }
        
        //Writing the image
        System.out.println("Writing the image... (" + out.toString() + ")");

        ImageWriter writer = new TIFFWriter(image, out);
        try {
            writer.write();
        } catch (ImageIOException e) {
            throw new IOException(e.toString());
        }
    }



    public static void main(String[] args) {
        try {
            RawReader reader = new RawReader(
            new File("/home/fwilhelm/Workspace/Projekte/Papers/WSCG2004/data/bruce_July13.raw"),
            new File("/home/fwilhelm/Workspace/Projekte/Papers/WSCG2004/data/bruce_July13"));
            reader.read();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static abstract class Reader {
        protected DataInputStream stream;
        protected String fileName;
        
        public Reader(File file) {
            try {
                stream = new DataInputStream(new FileInputStream(file));
                fileName = file.toString();
            } catch (FileNotFoundException e) {
                System.out.println(e);
            }
        }        
        
        public abstract int read() throws IOException;
    }
    
    private static class UnsignedByteReader extends Reader {       
        public UnsignedByteReader(File file) {
            super(file);
        }
        public int read() throws IOException {
            return stream.readUnsignedByte();
        }
        
        public String toString() {
            return "ByteFile: " + fileName;
        }
    }
    
    private static class SignedShortReader extends Reader {       
        public SignedShortReader(File file) {
            super(file);
        }
        public int read() throws IOException {
            return stream.readShort();
        }
        
        public String toString() {
            return "ShortFile: " + fileName;
        }        
    }




}
