/* 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.    See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

/**
 * ErrorListPlot.java
 *
 * Created on 14. Oktober 2002, 16:14
 */

package org.wewi.medimg.seg.validation;

import java.awt.Color;

import com.jrefinery.chart.ChartPanel;
import com.jrefinery.chart.HorizontalCategoryAxis;
import com.jrefinery.chart.JFreeChart;
import com.jrefinery.chart.LineAndShapeRenderer;
import com.jrefinery.chart.OverlaidVerticalCategoryPlot;
import com.jrefinery.chart.SeriesShapeFactory;
import com.jrefinery.chart.VerticalCategoryPlot;
import com.jrefinery.chart.VerticalIntervalBarRenderer;
import com.jrefinery.chart.VerticalNumberAxis;
import com.jrefinery.data.DefaultCategoryDataset;
import com.jrefinery.data.DefaultIntervalCategoryDataset;
import com.jrefinery.data.IntervalCategoryDataset;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class ErrorListPlot extends javax.swing.JFrame {
    private String title;
    private double[] k;
    private double[] mean;
    private double[] var;
    
    private ChartPanel chartPanel;
    private JFreeChart freeChart;
    
    
    public ErrorListPlot() {
        initComponents();
        init();
    }
    
    /** Creates new form ErrorListPlot */
    public ErrorListPlot(String title, double[] k, double[] mean, double[] var) {
        this.title = title;
        this.k = k;
        this.mean = mean;
        this.var = var;
        initComponents();
        init();
    }
    /*
    public ErrorListPlot(String title, double[] b, double[] mean, double[] var) {
        this.title = title;
        this.b = b;
        this.mean = mean;
        this.var = var;
        initComponents();
        init();            
    }
    */
    public void init() { 
        //Erzeugen der Datensätze
        String[] categories = new String[mean.length];
        double[][] starts = new double[1][mean.length];
        double[][] ends = new double[1][mean.length];
        for (int i =  0; i < mean.length; i++) {
            starts[0][i] = mean[i] - var[i];
            ends[0][i] = mean[i] + var[i];  
            categories[i] = Double.toString(k[i]);  
        } 
        IntervalCategoryDataset intervalData = new DefaultIntervalCategoryDataset(starts, ends);   
        double[][] m = new double[1][];
        m[0] = mean;
        DefaultCategoryDataset lineData = new DefaultCategoryDataset(m);            
        
        String xTitle = "Gibbs Potential";
        String yTitle = "Mutual Information"; 
        HorizontalCategoryAxis xAxis = new HorizontalCategoryAxis(xTitle);
        VerticalNumberAxis yAxis = new VerticalNumberAxis(yTitle);        
        OverlaidVerticalCategoryPlot plot = new 
                   OverlaidVerticalCategoryPlot(xAxis, yAxis, categories);
        plot.setBackgroundPaint(Color.lightGray);
        plot.setOutlinePaint(Color.black);
        
        VerticalIntervalBarRenderer renderer = new VerticalIntervalBarRenderer();
        VerticalCategoryPlot bar = new VerticalCategoryPlot(intervalData, null, null, renderer); 
        bar.setSeriesPaint(new Color[]{Color.LIGHT_GRAY});
        plot.add(bar);  
       
        LineAndShapeRenderer lineRenderer = null;
        lineRenderer = new LineAndShapeRenderer(LineAndShapeRenderer.SHAPES_AND_LINES);
        VerticalCategoryPlot lines = null;
        lines = new VerticalCategoryPlot(lineData, null, null, lineRenderer);
        lines.setSeriesPaint(new Color[]{Color.BLUE});
        lines.setShapeFactory(new SeriesShapeFactory());
        plot.add(lines); 

        freeChart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, false);       
        freeChart.setBackgroundPaint(Color.WHITE);
        chartPanel = new ChartPanel(freeChart);
        getContentPane().add(chartPanel); 
        
        for (int i = 0; i < k.length; i++) {
            System.out.println("k: " + k[i] + ", mean: " + mean[i] + ", var: " + var[i]);        
        }                
                          
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
        double[] k = new double[15];
        double[] mean = new double[k.length];
        double[] var = new double[k.length];
        for (int i = 0; i < k.length; i++) {
            k[i] = i+3;
            mean[i] = Math.random()*10;
            var[i] = Math.random();    
        }
        
        new ErrorListPlot("Titel", k, mean, var).show();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
