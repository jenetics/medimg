/*
 * IntervalTree.java
 *
 * Created on 13. Mai 2002, 22:14
 */

package org.wewi.medimg.seg.statistic;

import org.wewi.medimg.util.Timer;

import java.util.Random;
import java.util.Arrays;

/**
 *
 * @author  Franz Wilhelmst�tter
 * @version 0.1
 */
final class IntervalTree {
    private int ninterval;
    private double[][] interval;
    
    public IntervalTree(double[] center) {
        //Initialisieren der Intervalle; geht sicher noch schneller!
        ninterval = center.length;
        interval = new double[ninterval][2];
        for (int i = 1; i < ninterval-1; i++) {
            interval[i][0] = (center[i-1]+center[i])/2;
            interval[i][1] = (center[i]+center[i+1])/2;
        }
        interval[0][0] = -Double.MAX_VALUE;
        if (ninterval > 1) {
            interval[0][1] = interval[1][0];
        } else {
            interval[0][1] = Double.MAX_VALUE;
            return;
        }
        interval[ninterval-1][1] = Double.MAX_VALUE; 
        interval[ninterval-1][0] = interval[ninterval-2][1];        
    }
    
    public int intervalNumber(double x) {
        return 0;
    }
    
    public int intervalNumber(int x) {
        //return intervalNumberRecursive(x, 0, ninterval-1);
        return intervalNumberIterative(x);
    }
    
    private int intervalNumberRecursive(int x, int l, int u) {
        //int m = (int)Math.ceil((double)(u-l+1)/(double)2.0)+(l-1);
        int m = u-l+1; m = m >> 1; m += l; //Macht das Gleiche wie die obere Zeile
        if (x < interval[m][0]) {
            return intervalNumberRecursive(x, l, m-1);
        }
        if (x >= interval[m][1]) {
            return intervalNumberRecursive(x, m+1, u);
        } 
        return m;
    }
    
    int intervalNumberIterative(int x) {
        int l = 0;
        int u = ninterval-1;
        int m;
        while (true) {
            //m = (int)Math.ceil((double)(u-l+1)/(double)2.0)+(l-1);
            m = u-l+1; m = m >> 1; m += l; //Macht das Gleiche wie die obere Zeile
            if (x < interval[m][0]) {
                u = m-1;
                continue;
            }
            if (x >= interval[m][1]) {
                l = m+1;
                continue;
            }
            return m; 
        }
    }
    
    int intervalNumberBF(int x) {
        for (int i = 0; i < ninterval; i++) {
            if (x >= interval[i][0] && x < interval[i][1]) {
                return i;
            }
        } 
        return -1;
    }
    
    static void test1() {
        int F = 100;
        int M = 1000;
        int color;
        
        int CENTERS = 5;
        Random rand = new Random(System.currentTimeMillis());
        double[] center = new double[CENTERS];
        for (int j = 0; j < CENTERS; j++) {
            center[j] = rand.nextDouble()*CENTERS*1000;
        }
        Arrays.sort(center);
        IntervalTree tree = new IntervalTree(center);        
        
        for (int f = 1; f < F; f++) {
            for (int i = 0; i < M; i++) {
                color = (int)(rand.nextDouble()*1000*100);
                if (tree.intervalNumberIterative(color) != tree.intervalNumberBF(color)) {
                    System.out.println("NICHT OK");
                    return;
                }
            }
        }
        System.out.println("OK");
    }
    
    static void test2() {
        int CENTERS;
        int TURNS = 10000;
        long time1, time2;
        int color;
        
        for (int i = 1; i < 20000; i++) {
            System.out.print("" + i + " ");
            CENTERS = i;
            Random rand = new Random(System.currentTimeMillis());
            double[] center = new double[CENTERS];
            for (int j = 0; j < CENTERS; j++) {
                center[j] = rand.nextDouble()*CENTERS*1000;
            }
            Arrays.sort(center);
            IntervalTree tree = new IntervalTree(center);

            //Brute Force Methode
            time1 = System.currentTimeMillis();
            for (int j = 0; j < TURNS; j++) {
                color = (int)(rand.nextDouble()*1000*CENTERS);
                tree.intervalNumberBF(color);
            }
            time2 = System.currentTimeMillis();
            System.out.print("" + (time2-time1) + " ");
            
            //Rekursive Methode
            time1 = System.currentTimeMillis();
            for (int j = 0; j < TURNS; j++) {
                color = (int)(rand.nextDouble()*1000*CENTERS);
                tree.intervalNumber(color);
            }
            time2 = System.currentTimeMillis();
            System.out.print("" + (time2-time1) + " ");
            
            //Iterative Methode
            time1 = System.currentTimeMillis();
            for (int j = 0; j < TURNS; j++) {
                color = (int)(rand.nextDouble()*1000*CENTERS);
                tree.intervalNumberIterative(color);
            }
            time2 = System.currentTimeMillis();
            System.out.println("" + (time2-time1)); 
        }        
    }
    
    public static void main(String[] args) {
        test2();
    }
}