/*
 * Histogram.java
 *
 * Created on 25. Februar 2002, 13:43
 */

package org.wewi.medimg.seg.validation;

import java.io.File;
import java.util.Arrays;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.image.ops.ColorRangeOperator;
import org.wewi.medimg.image.ops.UnaryPointAnalyzer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class Histogram {
    private Image image;
    private ColorRange colorRange;
    private double[] hist;
    
    private String histTitel = "";
    private String axesLabelX = "";
    private String axesLabelY = "";

    /** Creates new Histogram */
    public Histogram(Image image) {
        this.image = image;
        
        ColorRangeOperator op = new ColorRangeOperator();
        UnaryPointAnalyzer analyzer = new UnaryPointAnalyzer(image, op);
        analyzer.analyze();       
        
        colorRange = new ColorRange(op.getMinimum(), op.getMaximum());
    }
    
    public Image getImage() {
        return image;
    }
    
    public void setHistTitel(String s) {
        histTitel = s;
    }
    
    public String getHistTitel() {
        return histTitel;
    }
    
    public void setAxesLabelX(String s) {
        axesLabelX = s;
    }
    
    public String getAxesLabelX() {
        return axesLabelX;
    }
    
    public void setAxesLabelY(String s) {
        axesLabelY = s;
    }
    
    public String getAxesLabelY() {
        return axesLabelY;
    }
    
    public void generate() {
        int min = colorRange.getMinColor();
        int max = colorRange.getMaxColor();
        hist = new double[max-min+1];
        Arrays.fill(hist, 0);
        
        int size = image.getNVoxels();
        int c = 0;
        for (int i = 0; i < size; i++) {
            c = image.getColor(i);
            hist[c-min]++;
        }
        for (int i = min; i <= max; i++) {
            //hist[i] /= (double)size;
        }
    }
    
    private String generateDataString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        for (int i = 0; i < hist.length; i++) {
            buffer.append(hist[i]);
            if (i < hist.length-1) {
                buffer.append(",");
            }
        }
        buffer.append("}");
        return buffer.toString();
    }
    
    private String generateBarLabels() {
        StringBuffer buffer = new StringBuffer();
        final int stride = 20;
        int min = colorRange.getMinColor();
        buffer.append("{");
        for (int i = 0; i < hist.length; i++) {
            if (i % stride == 0) {
                buffer.append("\"").append(i+min).append("\"");
            } else {
                buffer.append("\"\"");
            }
            if (i < hist.length-1) {
                buffer.append(",");
            }
        }
        buffer.append("}");
        return buffer.toString();
    }
    
    public String toMathematicaString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("BarChart[");
        buffer.append(generateDataString());
        buffer.append(",\n    Frame->True");
        buffer.append(",\n    PlotRange->All");
        buffer.append(",\n    FrameLabel->{\"").append(axesLabelX).append("\",\"")
                                               .append(axesLabelY).append("\",\"\",\"\"}");
        buffer.append(",\n    BarGroupSpacing->0");
        buffer.append(",\n    BarLabels->").append(generateBarLabels());
        buffer.append(",\n    PlotLabel->\"").append(histTitel).append("\"");
        buffer.append(",\n    DefaultFont->{\"Arial\", 14}]");
        return buffer.toString();
    }
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) {
        try {
            ImageReader reader = new TIFFReader(IntImageFactory.getInstance(), 
                                                new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
            reader.read();
            Image image = reader.getImage();
            
            Histogram hist = new Histogram(image);
            hist.generate();
            hist.setHistTitel("Grauwerthistogramm");
            hist.setAxesLabelX("Grauwerte");
            hist.setAxesLabelY("Häufigkeit");
            
            System.out.println(hist.toMathematicaString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
