/*
 * Timer.java
 *
 * Created on 17. Jänner 2002, 19:42
 */

package org.wewi.medimg.util;

import java.io.PrintStream;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class Timer {
    private String timerName;
    private long startTime;
    private long stopTime;

    public Timer(String name) {
        timerName = name;
        startTime = System.currentTimeMillis();
        stopTime = startTime;
    }
    
    public void start() {
        startTime = System.currentTimeMillis();
    }
    
    public void stop() {
        stopTime = System.currentTimeMillis();
    }
    
    public long duration() {
        return stopTime - startTime;
    }
    
    public void print(PrintStream out) {       
        out.println(toString());
    }
    
    public void print() {
        print(System.out);
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(timerName).append(": ");
        buffer.append(Double.toString((double)duration()/1000d));
        buffer.append("sec");  
        
        return buffer.toString();
    }

}
