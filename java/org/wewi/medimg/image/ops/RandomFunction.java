/**
 * RandomFunction.java
 * 
 * Created on 12.12.2002, 12:30:52
 *
 */
package org.wewi.medimg.image.ops;

import java.util.Random;

import org.wewi.medimg.image.ColorRange;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class RandomFunction implements UnaryFunction {
    private ColorRange range;
    private Random rand;

    /**
     * Constructor for RandomFunction.
     */
    public RandomFunction() {
        this(new ColorRange(0, 255));
    }
    
    public RandomFunction(ColorRange range) {
        this.range = range;
        rand = new Random(System.currentTimeMillis());    
    }

    /**
     * @see org.wewi.medimg.image.ops.UnaryFunction#process(int)
     */
    public int process(int color) {
        int c = (int)((double)range.getNColors()*rand.nextDouble() + (double)range.getMinColor());
        return c;
    }

}
