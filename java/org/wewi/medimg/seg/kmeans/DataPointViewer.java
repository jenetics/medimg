/*
 * DataPointViewer.java
 *
 * Created on 31. Juli 2002, 15:48
 */

package org.wewi.medimg.seg.kmeans;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

/**
 *
 * @author  Franz Wilhelmstötter
 */
public class DataPointViewer extends JPanel {
    
    private class PairVector {
        private Vector data = new Vector();
        private Vector center = new Vector();
        
        public PairVector() {
        }
        
        public void add(DataPoint[] d, DataPoint c) {
            data.add(d);
            center.add(c);
        }
        
        public int size() {
            return data.size();
        }
        
        public DataPoint[] getData(int pos) {
            return (DataPoint[])data.get(pos);
        }
        
        public DataPoint getCenter(int pos) {
            return (DataPoint)center.get(pos);
        }
    }
    
    private PairVector cluster;
    
    private static Color[] colors = {Color.red,
                                     Color.green,
                                     Color.blue,
                                     Color.yellow,
                                     Color.pink,
                                     Color.green,
                                     Color.lightGray};
    
    /** Creates a new instance of DataPointViewer */
    public DataPointViewer() {
        cluster = new PairVector();
    }
    
    
    public void addCluster(DataPoint[] data, DataPoint center) {
        cluster.add(data, center);
    }
    
    public boolean removeCluster(DataPoint center) {
        return false;//cluster.remove(center) != null;
    }
    
    public DataPoint[] getCluster(int pos) {
        return cluster.getData(pos);
    }
    
    
    public void paint(Graphics graphics) {
        Graphics2D g = (Graphics2D)graphics;
        int[] point;
        
        DataPoint center;
        DataPoint[] data;
        
        for (int j = 0; j < cluster.size(); j++) {
            g.setColor(colors[j]);
            
            center = cluster.getCenter(j);
            data = cluster.getData(j);
            for (int i = 0; i < data.length; i++) {
                g.drawOval(data[i].getOrdinateNumber(0).intValue(), 
                           data[i].getOrdinateNumber(1).intValue(), 3, 3);
            }
            
            //Zeichnen der Clusterzentren
            g.setPaint(Color.black);
            g.setColor(Color.white);
            g.drawOval(center.getOrdinateNumber(0).intValue(), 
                       center.getOrdinateNumber(1).intValue(), 20, 20);            
        }

    }   
    
    public void getCovarianceMatrix(int pos) {
        DoubleFactory2D factory2D;
        factory2D = DoubleFactory2D.dense;
        DoubleFactory1D factory1D;
        factory1D = DoubleFactory1D.dense;  
        
        DataPoint[] points = cluster.getData(pos);
        DoubleMatrix2D data1 = factory2D.make(points.length, 2);  
        for (int i = 0; i < points.length; i++) {
            data1.setQuick(i, 0, points[i].getOrdinateNumber(0).doubleValue());
            data1.setQuick(i, 1, points[i].getOrdinateNumber(1).doubleValue());
        }        
        int p = data1.columns(); //Dimension
        int n = data1.rows(); //Anzahl der Punkte
        DoubleMatrix1D cog = centreOfGravity(pos);
        //Berechnen der Kovarianzmatrix
        for (int i = 0; i < n; i++) {
            data1.viewRow(i).assign(cog, cern.jet.math.Functions.minus);
        }
        DoubleMatrix2D data2;
        DoubleMatrix2D covarianceMatrix = factory2D.make(p, p);
        data1.zMult(data1, covarianceMatrix, 1.0, 0.0, true, false);
        covarianceMatrix.assign(cern.jet.math.Functions.mult(1.0 / (points.length-1)));
        
        
        
        System.err.println(covarianceMatrix.toString());
    }
    
    private DoubleMatrix1D centreOfGravity(int pos) {
        DoubleMatrix1D cog = DoubleFactory1D.dense.make(2);
        cog.assign(0.0);
        cog.setQuick(0, cluster.getCenter(pos).getOrdinateNumber(0).doubleValue());
        cog.setQuick(1, cluster.getCenter(pos).getOrdinateNumber(1).doubleValue());

        return cog;
    }     
    
    private String mathematicaPoint(DataPoint point) {
        StringBuffer buffer = new StringBuffer();
        int dim = point.getDim();
        buffer.append("Point[{");
        for (int i = 0; i < dim; i++) {
            buffer.append(point.getOrdinateNumber(i).doubleValue());
            if (i < dim-1) {
                buffer.append(",");
            }
        }
        buffer.append("}]");
        return buffer.toString();
    }
    
    private String mathematicaDisc(DataPoint point) {
        StringBuffer buffer = new StringBuffer();
        int dim = point.getDim();
        buffer.append("Disc[{");
        for (int i = 0; i < dim; i++) {
            buffer.append(point.getOrdinateNumber(i).doubleValue());
            if (i < dim-1) {
                buffer.append(",");
            }
        }
        buffer.append("}, 2]");
        return buffer.toString();        
    }
    
    private String mathematicaClusterGraphics(DataPoint[] data, Color c) {
        float[] col = new float[3];
        c.getRGBColorComponents(col);
        StringBuffer buffer = new StringBuffer();
        buffer.append("Graphics[{");
        buffer.append("RGBColor[" + col[0] + "," + col[1] + "," + col[2] + "]");
        buffer.append(",PointSize[0.015],");
        
        for (int i = 0; i < data.length; i++) {
            buffer.append(mathematicaPoint(data[i])).append("\n");
            if (i < data.length-1) {
                buffer.append(",");
            }
        }
        
        buffer.append("}, { Axes -> True, PlotRange -> All}");
        
        buffer.append("]");
        return buffer.toString();
    }
    
    public String toMathematicaString(String prefix) {
        DataPoint[] data;
        DataPoint center;
        StringBuffer buffer = new StringBuffer();
       
        for (int i = 0; i < cluster.size(); i++) {
            center = cluster.getCenter(i);
            data = cluster.getData(i);
            
            buffer.append(prefix + "graph" + (i) + "=");
            buffer.append(mathematicaClusterGraphics(data, colors[i])).append(";\n");
            buffer.append(prefix + "center" + (i) + "=");
            buffer.append("Graphics[{PointSize[0.03]," + mathematicaPoint(center) + "}];\n");
        }

        buffer.append("Show[{");
        for (int j = 0; j < cluster.size(); j++) {
            buffer.append(prefix + "graph" + j + ",");
            buffer.append(prefix + "center" + j);
            if (j < cluster.size()-1) {
                buffer.append(",");
            }
        }
        buffer.append("}];\n");
        
        return buffer.toString();
    }
    
    
    public static void test1() {
        int size = 1500;
        Vector data = new Vector(size);
        Random rand = new Random(System.currentTimeMillis());
        int[] point = new int[2];
        for (int i = 0; i < size; i++) {
            point[0] = (int)(rand.nextDouble()*100.0);
            point[1] = (int)(rand.nextDouble()*100.0);
            data.add(new IntegerDataPoint(point));
        }
        
        DirectClustering cluster = new DirectClustering(data, 6);
        DataPointViewer viewer = new DataPointViewer();
        int j = 0;
        for (Iterator it = cluster.getClusterIterator(); it.hasNext();) {
            it.next();
            DataPoint[] center = cluster.getClusterCenter();
            Collection[] clusters = cluster.getCluster();
            viewer = new DataPointViewer();
            for (int i = 0; i < clusters.length; i++) {
                DataPoint[] d = new DataPoint[clusters[i].size()];
                clusters[i].toArray(d);
                viewer.addCluster(d, center[i]);
            }

             
            
        }  
        System.out.println(viewer.toMathematicaString("it" + (j++) + ""));
    }
    
    public static void test2() {
        Collection data1 = DataPointGenerator.getData(
                  "C:/Workspace/fwilhelm/Projekte/Diplom/document/chapter/extendedsegmentation/add/I_mv.dat",150);
        Collection data2 = DataPointGenerator.getData(
                  "C:/Workspace/fwilhelm/Projekte/Diplom/document/chapter/extendedsegmentation/add/II_mv.dat",150);
        Collection data3 = DataPointGenerator.getData(
                  "C:/Workspace/fwilhelm/Projekte/Diplom/document/chapter/extendedsegmentation/add/III_mv.dat",150);
        Collection data4 = DataPointGenerator.getData(
                  "C:/Workspace/fwilhelm/Projekte/Diplom/document/chapter/extendedsegmentation/add/IV_mv.dat",150); 
        
        DataPointViewer v = new DataPointViewer();
        int[] p = new int[2];
        DataPoint[] dat = new DataPoint[data3.size()];
        data3.toArray(dat);
        p[0] = 20; p[1] = 20;
        v.addCluster(dat, new IntegerDataPoint(p));    
        
        dat = new DataPoint[data2.size()];
        data2.toArray(dat);
        p[0] = 170; p[1] = 50;
        v.addCluster(dat, new IntegerDataPoint(p));   
        
        dat = new DataPoint[data1.size()];
        data1.toArray(dat);
        p[0] = 100; p[1] = 80;
        v.addCluster(dat, new IntegerDataPoint(p));  
        
        dat = new DataPoint[data4.size()];
        data4.toArray(dat);
        p[0] = 150; p[1] = 130;
        v.addCluster(dat, new IntegerDataPoint(p));  
        
        System.out.println(v.toMathematicaString("real"));
        
        //Mischen der Daten
        Vector data = new Vector();
        data.addAll(data1);
        data.addAll(data2);
        data.addAll(data3);
        data.addAll(data4);
        
        DirectClustering cluster = new DirectClustering(data, 4);
        DataPointViewer viewer = null;
        int j = 0;
        for (Iterator it = cluster.getClusterIterator(); it.hasNext();) {
            it.next();
            DataPoint[] center = cluster.getClusterCenter();
            Collection[] clusters = cluster.getCluster();
            viewer = new DataPointViewer();
            for (int i = 0; i < clusters.length; i++) {
                DataPoint[] d = new DataPoint[clusters[i].size()];
                clusters[i].toArray(d);
                viewer.addCluster(d, center[i]);
            }

            System.out.println(viewer.toMathematicaString("it" + (j++) + "")); 
            
        }   
        
        for (int i = 0; i < 4; i++) {
            viewer.getCovarianceMatrix(i);    
        }
        
        Collection[] kdata = cluster.getCluster();
        DataPoint[] kcenter = cluster.getClusterCenter();
        int err1 = 0, ok1 = 0;
        int size1 = 0;
        for (Iterator it = kdata[0].iterator(); it.hasNext();) {
            DataPoint dp = (DataPoint)it.next();
            if (data3.contains(dp)) {
                ok1++;
            } else {
                err1++;
            }
            size1++;
        }
        System.err.println("Data 1(3): (OK:" + ok1 + ",ERR:" + err1 + ",ALL:" + size1 + ")" + kcenter[0]);
        
        int err2 = 0, ok2 = 0;
        int size2 = 0;
        for (Iterator it = kdata[1].iterator(); it.hasNext();) {
            DataPoint dp = (DataPoint)it.next();
            if (data2.contains(dp)) {
                ok2++;
            } else {
                err2++;
            }
            size2++;
        }
        System.err.println("Data 2(2): (OK:" + ok2 + ",ERR:" + err2 + ",ALL:" + size2 + ")" + kcenter[1]);     
        
        int err3 = 0, ok3 = 0;
        int size3 = 0;
        for (Iterator it = kdata[2].iterator(); it.hasNext();) {
            DataPoint dp = (DataPoint)it.next();
            if (data1.contains(dp)) {
                ok3++;
            } else {
                err3++;
            }
            size3++;
        }
        System.err.println("Data 3(1): (OK:" + ok3 + ",ERR:" + err3 + ",ALL:" + size3 + ")" + kcenter[2]); 
        
        int err4 = 0, ok4 = 0;
        int size4 = 0;
        for (Iterator it = kdata[3].iterator(); it.hasNext();) {
            DataPoint dp = (DataPoint)it.next();
            if (data4.contains(dp)) {
                ok4++;
            } else {
                err4++;
            }
            size4++;
        }
        System.err.println("Data 4(4): (OK:" + ok4 + ",ERR:" + err4 + ",ALL:" + size4 + ")" + kcenter[0]);        
    }
    
    
    public static void main(String[] args) {
        test1();
    }
}
