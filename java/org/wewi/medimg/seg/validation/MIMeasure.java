/**
 * MIMeasure.java
 * 
 * Created on 07.01.2003, 20:51:24
 *
 */
package org.wewi.medimg.seg.validation;

import org.wewi.medimg.image.ColorRange;
import org.wewi.medimg.image.ComplexImage;
import org.wewi.medimg.image.ComplexIndexImage;
import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.ops.AnalyzerUtils;
import org.wewi.medimg.image.ops.BinaryPointAnalyzer;
import org.wewi.medimg.image.ops.MutualInformationOperator;
import org.wewi.medimg.math.fft.ImageDFT;
import org.wewi.medimg.math.geom.Dimension2D;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class MIMeasure implements ValidationMeasure {

    /**
     * Constructor for MIMeasure.
     */
    public MIMeasure() {
        super();
    }

    /**
     * @see org.wewi.medimg.seg.validation.ValidationMeasure#measure(Image, Image)
     */
    public double measure(Image modelImage, Image segmentedImage) {
        ImageDFT dft = new ImageDFT();
        
        ComplexImage temp = dft.transform(modelImage);
        ComplexIndexImage model = new ComplexIndexImage(temp, new Dimension2D(5, 8, -2.8, 2.8));
        
        temp = dft.transform(segmentedImage);
        ComplexIndexImage segimg = new ComplexIndexImage(temp, new Dimension2D(5, 8, -2.8, 2.8));
        temp = null;
        
System.out.println("1:ASDFASDF");
        
        ColorRange modelColorRange = AnalyzerUtils.getColorRange(model);
        ColorRange segimgColorRange = AnalyzerUtils.getColorRange(segimg);
        
System.out.println(modelColorRange);
System.out.println(segimgColorRange);
        
System.out.println("2:ASDFASDF");        
        
        MutualInformationOperator miop = new MutualInformationOperator(modelColorRange, segimgColorRange);
System.out.println("3:ASDFASDF");        
        BinaryPointAnalyzer analyzer = new BinaryPointAnalyzer(model, segimg, miop);
System.out.println("4:ASDFASDF");        
        analyzer.analyze();
        
System.out.println("5:ASDFASDF");        
        
        return miop.getMutialInformation();
    }

}
