/**
 * ErrorListPlot.java
 *
 * Created on 14. Oktober 2002, 16:14
 */

package org.wewi.medimg.seg.validation;

import java.awt.Color;
import java.awt.Font;

import com.jrefinery.chart.ChartFactory;
import com.jrefinery.chart.ChartPanel;
import com.jrefinery.chart.HorizontalCategoryAxis;
import com.jrefinery.chart.JFreeChart;
import com.jrefinery.chart.LineAndShapeRenderer;
import com.jrefinery.chart.OverlaidVerticalCategoryPlot;
import com.jrefinery.chart.SeriesShapeFactory;
import com.jrefinery.chart.StandardXYItemRenderer;
import com.jrefinery.chart.VerticalCategoryPlot;
import com.jrefinery.chart.VerticalIntervalBarRenderer;
import com.jrefinery.chart.VerticalNumberAxis;
import com.jrefinery.chart.XYPlot;
import com.jrefinery.data.DefaultCategoryDataset;
import com.jrefinery.data.DefaultIntervalCategoryDataset;
import com.jrefinery.data.DefaultStatisticalCategoryDataset;
import com.jrefinery.data.DefaultXYDataset;
import com.jrefinery.data.IntervalCategoryDataset;
import com.jrefinery.data.StatisticalCategoryDataset;
import com.jrefinery.data.XYDataset;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ErrorListPlot extends javax.swing.JFrame {
    private int[] k;
    private double[] mean;
    private double[] var;
    
    private ChartPanel chartPanel;
    private JFreeChart freeChart;
    
    
    public ErrorListPlot() {
        initComponents();
        init();
    }
    
    /** Creates new form ErrorListPlot */
    public ErrorListPlot(int[] k, double[] mean, double[] var) {
        this.k = k;
        this.mean = mean;
        this.var = var;
        initComponents();
        init();
    }
    
    public void init() { 
        /*
        Object[][][] data = new Object[1][k.length][2];
        //Auffüllen des Datenobjektes
        for (int i = 0; i < k.length; i++) {
            data[0][i][0] = new Integer(k[i]);
            data[0][i][1] = new Double(mean[i]);    
        }
        XYDataset dataSet = new DefaultXYDataset(data);
        
        freeChart = ChartFactory.createXYChart("Titel", "X-Achse", "Y-Achse", dataSet, false);
        freeChart.setBackgroundPaint(Color.WHITE);
        
        chartPanel = new ChartPanel(freeChart);
        getContentPane().add(chartPanel);
        */
        /*
        double[][] m = new double[1][];
        m[0] = mean;
        double[][] v = new double[1][];
        v[0] = var;
        StatisticalCategoryDataset data = new DefaultStatisticalCategoryDataset(m, v);
        freeChart = ChartFactory.createAreaChart("Titel", "x", "y", data, false);
        freeChart.setBackgroundPaint(Color.WHITE);
        chartPanel = new ChartPanel(freeChart);
        getContentPane().add(chartPanel);   
        */  
        /*
        double[][] starts = new double[1][mean.length];
        double[][] ends = new double[1][mean.length];
        for (int i =  0; i < mean.length; i++) {
            starts[0][i] = mean[i] - var[i];
            ends[0][i] = mean[i] + var[i];    
        }
        
        IntervalCategoryDataset data = new DefaultIntervalCategoryDataset(starts, ends);
        String title = "Anzahl der Merkmale Diagramm";
        String xTitle = "Anzahl der Merkmale k";
        String yTitle = "Relative Fehlerhäufigkeit";
        HorizontalCategoryAxis xAxis = new HorizontalCategoryAxis(xTitle);
        VerticalNumberAxis yAxis = new VerticalNumberAxis(yTitle);
        VerticalIntervalBarRenderer renderer = new VerticalIntervalBarRenderer();
        VerticalCategoryPlot plot = new VerticalCategoryPlot(data, xAxis, yAxis, renderer);
        plot.setLabelFormatString("0.##%");
        plot.setBackgroundPaint(Color.lightGray);
        plot.setOutlinePaint(Color.white);
        freeChart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, false);       
        freeChart.setBackgroundPaint(Color.WHITE);
        chartPanel = new ChartPanel(freeChart);
        getContentPane().add(chartPanel); 
        */
        
        //Erzeugen der Datensätze
        String[] categories = new String[mean.length];
        double[][] starts = new double[1][mean.length];
        double[][] ends = new double[1][mean.length];
        for (int i =  0; i < mean.length; i++) {
            starts[0][i] = mean[i] - var[i];
            ends[0][i] = mean[i] + var[i];  
            categories[i] = Integer.toString(i+2);  
        } 
        IntervalCategoryDataset intervalData = new DefaultIntervalCategoryDataset(starts, ends);   
        double[][] m = new double[1][];
        m[0] = mean;
        DefaultCategoryDataset lineData = new DefaultCategoryDataset(m);            
        
        String title = "Anzahl der Merkmale Diagramm";
        String xTitle = "Anzahl der Merkmale k";
        String yTitle = "Relative Fehlerhäufigkeit";
        HorizontalCategoryAxis xAxis = new HorizontalCategoryAxis(xTitle);
        VerticalNumberAxis yAxis = new VerticalNumberAxis(yTitle);        
        OverlaidVerticalCategoryPlot plot = new 
                   OverlaidVerticalCategoryPlot(xAxis, yAxis, categories);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setOutlinePaint(Color.black);
        
        VerticalIntervalBarRenderer renderer = new VerticalIntervalBarRenderer();
        VerticalCategoryPlot bar = new VerticalCategoryPlot(intervalData, null, null, renderer); 
        //bar.setLabelsVisible(true);
        //bar.setLabelFont(JFreeChart.DEFAULT_TITLE_FONT);
        //bar.setLabelFormatString("0.##%");
        plot.add(bar);  
       
        LineAndShapeRenderer lineRenderer = null;
        lineRenderer = new LineAndShapeRenderer(LineAndShapeRenderer.SHAPES_AND_LINES);
        VerticalCategoryPlot lines = null;
        lines = new VerticalCategoryPlot(lineData, null, null, lineRenderer);
        //lines.setSeriesPaint(lineColors);
        lines.setShapeFactory(new SeriesShapeFactory());
        plot.add(lines); 
        
        lines.setParent(bar);

        freeChart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, bar, false);       
        freeChart.setBackgroundPaint(Color.WHITE);
        chartPanel = new ChartPanel(freeChart);
        getContentPane().add(chartPanel);                 
                          
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(new java.awt.Dimension(400, 300));
        setLocation((screenSize.width-400)/2,(screenSize.height-300)/2);
    }//GEN-END:initComponents
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        int[] k = new int[15];
        double[] mean = new double[k.length];
        double[] var = new double[k.length];
        for (int i = 0; i < k.length; i++) {
            k[i] = i+3;
            mean[i] = Math.random()*10;
            var[i] = Math.random();    
        }
        
        new ErrorListPlot(k, mean, var).show();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
