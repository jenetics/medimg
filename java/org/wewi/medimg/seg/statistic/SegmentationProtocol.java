/*
 * SegmentationRecord.java
 *
 * Created on 17. April 2002, 20:22
 */

package org.wewi.medimg.seg.statistic;

import java.text.DateFormat;

import java.util.Date;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class SegmentationProtocol {
    private String inputImage = "";
    private String segmentationMethod = "";
    private long segStart;
    private long segStop;
    private double[] meanValues = {};
    private double[] variance = {};
    
    /** Creates a new instance of SegmentationRecord */
    public SegmentationProtocol() {
        segStart = segStop = System.currentTimeMillis();
    }
    
    public void setInputImageName(String name) {
        inputImage = name;
    }
    
    public void setSegmentationMethod(String name) {
        segmentationMethod = name;
    }
    
    public void setStartTime(long time) {
        segStart = time;
    }
    
    public void setStopTime(long time) {
        segStop = time;
    }
    
    public void setMeanValues(double[] mv) {
        meanValues = mv;
    }
    
    public void setVariance(double[] v) {
        variance = v;
    }
    
    public String toXMLString() {
        return null;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        DateFormat format = DateFormat.getTimeInstance();
        buffer.append("\n    ");
        buffer.append(segmentationMethod).append(": ").append(inputImage).append("\n");
        buffer.append("    Segmentationstart: ").append((new Date(segStart)).toString()).append("\n");
        buffer.append("    Segmentationstop:  ").append((new Date(segStop)).toString()).append("\n");
        buffer.append("    Segmentationduration: ").append((double)(segStop-segStart)/1000d).append("\n");
        buffer.append("    ----------------------------------------------------\n");
        buffer.append("    Meanvalues:\n        ");
        for (int i = 0; i < meanValues.length; i++) {
            buffer.append("(").append(meanValues[i]).append(")");
        }
        buffer.append("\n    Variance:\n        ");
        for (int i = 0; i < variance.length; i++) {
            buffer.append("(").append(variance[i]).append(")");
        }
        buffer.append("\n");
        
        return buffer.toString();
    }
}
