/*
 * MCCVStatisticWriter.java
 *
 * Created on 6. Juni 2002, 10:47
 */

package org.wewi.medimg.seg.statistic;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class MCCVStatisticWriter {
    private PrintWriter out;
    
    /** Creates a new instance of MCCVStatisticWriter */
    public MCCVStatisticWriter(String file) {
        try {
            out = new PrintWriter(new FileOutputStream(file));
        } catch (Exception ioe) {
            System.out.println("MCCVStatisticWriter: " + ioe);
        }
    }
    
    public void write(int m, int K_MIN, double[] likelihood) {
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append("M: ").append(m).append("\n");
            for (int i = 0; i < likelihood.length; i++) {
                buffer.append(i+K_MIN).append(" ").append(likelihood[i]/(double)m).append("\n");
            }
            out.println(buffer.toString());
            out.flush();
        } catch (Exception e) {
            System.out.println("MCCVStatisticWriter.write: " + e);
        }
    }

    
    public void close() {
        out.close();
    }
}
