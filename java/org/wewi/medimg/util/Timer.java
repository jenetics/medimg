/**
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
    
    
    public Timer() {
        this("");    
    }

    /**
     * Constructs a new <code>Timer</code> object with the
     * specified name.
     * 
     * @param name name of the timer.
     */
    public Timer(String name) {
        timerName = name;
        startTime = System.currentTimeMillis();
        stopTime = startTime;
    }
    
    /**
     * Starts the <code>Timer</code>
     */
    public void start() {
        startTime = System.currentTimeMillis();
    }
    
    /**
     * Stops the <code>Timer</code>
     */
    public void stop() {
        stopTime = System.currentTimeMillis();
    }
    
    /**
     * Returns the duration between starting and stopping the <code>Timer</code>
     * 
     * @return long duration between start and stop.
     */
    public long duration() {
        return stopTime - startTime;
    }
    
    /**
     * Prints the duration time to the given <code>PrintStream</code>
     * 
     * @param out the duration time is printed to this <code>PrintStream</code>
     */
    public void print(PrintStream out) {       
        out.println(toString());
    }
    
    /**
     * Prints the duration time to the standard output stream.
     * This is equivalent to
     * <pre>
     *     print(System.out);
     * </pre>
     */
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
