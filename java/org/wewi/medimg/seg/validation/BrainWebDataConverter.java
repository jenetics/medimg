/**
 * Created on 20.08.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;

import org.wewi.medimg.image.GreyColorConversion;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImage;
import org.wewi.medimg.image.ImageProperties;
import org.wewi.medimg.image.io.ImageWriter;
import org.wewi.medimg.image.io.RawImageWriter;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public final class BrainWebDataConverter {
    private static final int MIN_X = -72;
    private static final int MIN_Y = -126;
    private static final int MIN_Z = -90;
    
    private static final int MAX_X = 108;
    private static final int MAX_Y = 90;
    private static final int MAX_Z = 90;
    

    /**
     * Constructor for BrainWebDataConverter.
     */
    public BrainWebDataConverter() {
        super();
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
    
    private static String[][] parseImageProperties(String file) {
        StringTokenizer tokenizer = new StringTokenizer(file.toLowerCase(), "_");
        
        String dataType;
        if (file.endsWith("b")) {
            dataType = "byte";
        } else {
            dataType = "short";
        }
        
        String modality = tokenizer.nextToken().toUpperCase();
        String protokol = tokenizer.nextToken().toUpperCase();
        String phantomName = tokenizer.nextToken().toUpperCase();
        String sliceThickness = tokenizer.nextToken();
        String noise = tokenizer.nextToken().substring(2);
        String inu = tokenizer.nextToken().toUpperCase();
        inu = inu.substring(0, inu.length()-5);
        
        return new String[][]{{"URL", "CDATA[http://www.bic.mni.mcgill.ca/brainweb/]"},
                               {"Modality", modality},
                               {"Protocol", protokol},
                               {"PhantomName", phantomName},
                               {"SliceThickness", sliceThickness},
                               {"Noise", noise},
                               {"INU", inu},
                               {"ColorDepth", dataType}};
        
    }
    
    private static void readWrite(Reader reader, File out, String[][] imageProperties) {
        try {
            //Reading the image
            System.out.println("Reading the image... (" + reader.toString() + ")");
            Image image = new IntImage(MIN_X, MAX_X, MIN_Y, MAX_Y, MIN_Z, MAX_Z);
            for (int k = MIN_Z; k <= MAX_Z; k++) {
                for (int j = MIN_Y; j <= MAX_Y; j++) {
                    for (int i = MIN_X; i <= MAX_X; i++) {
                        image.setColor(i, j, k, reader.read());
                    }
                } 
            }
            
            //Setting the ImageProperties
            ImageProperties prop = image.getHeader().getImageProperties();
            for (int i = 0; i < imageProperties.length; i++) {
                prop.setProperty(imageProperties[i][0], imageProperties[i][1]);   
            }
            
            if (prop.getProperty("ColorDepth").equals("short")) {
                image.setColorConversion(new GreyColorConversion(12));
            }
            
            //Writing the image
            System.out.println("Writing the image... (" + out.toString() + ")");

            ImageWriter writer = new RawImageWriter(image, out);
            writer.write();
            
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
    
    public static void readBrain(String inDir, String outDir) {
        File[] inFiles = (new File(inDir)).listFiles();
        System.out.println("There are " + inFiles.length + " in the directory \"" + inDir + "\"");
        
        Reader reader = null;
        String[][] properties;
        String outFile;
        for (int i = 0; i < inFiles.length; i++) {
            if (inFiles[i].toString().endsWith("b")) {
                reader = new UnsignedByteReader(inFiles[i]);    
            } else if (inFiles[i].toString().endsWith("s")){
                reader = new SignedShortReader(inFiles[i]);    
            } else {
                reader = null;
            }
            
            properties = parseImageProperties(inFiles[i].getName());
            outFile = outDir + "/";
            for (int j = 1; j < properties.length; j++) {
                outFile += properties[j][1].toLowerCase() + ".";       
            }
            outFile += "rid";
            
            new File(outDir).mkdirs();
            readWrite(reader, new File(outFile), properties);
        }
    }
    
   

    public static void main(String[] args) {
        readBrain(args[0], args[1]);
    }
}
