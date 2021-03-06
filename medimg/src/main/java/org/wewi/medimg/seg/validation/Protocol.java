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
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.jdom.DataConversionException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.wewi.medimg.util.AccumulatorArray;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Protocol {
    private File file;
    
    private String algorithmName;
    private int k;
    private double beta = 0;
    private double mutualInformation = 0;
    private Properties imageProperties;
    private long startTime;
    private long stopTime;
    private int iterations;
    private double[] meanValues;
    private AccumulatorArray accu = new AccumulatorArray(1, 1);
    private double overallError;
    private double[] featureError;
    
    

    /**
     * Constructor for Protocol.
     */
    public Protocol(File file) {
        super();
        this.file = file;
        init();
    }
    
    private void init() {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null; 
        try {
            doc = builder.build(file);
        } catch (JDOMException e) {
            System.err.println("Protocol.init: " + e);
            return;
        }
        
        Element root = doc.getRootElement();
        
        //Füllen des Algorithmennamens
        Element alg = root.getChild("Algorithm");
        algorithmName = alg.getAttribute("class").getValue();
        
        //Füllen der Algorithmusparameter
        List parameterList = alg.getChildren("Parameter");
        for (Iterator it = parameterList.iterator(); it.hasNext();) {
            Element param = (Element)it.next();
            String name = param.getAttribute("name").getValue();
            if ("k".equals(name)) {
                k = Integer.parseInt(param.getText());       
            } else if ("b".equals(name)) { 
                beta = Double.parseDouble(param.getText());
            } else if ("image".equals(name)) {
                Element image = param.getChild("ImageHeader");
                List imageParameter = image.getChildren();
                imageProperties = new Properties();
                for (Iterator it2 = imageParameter.iterator(); it2.hasNext();) {
                    Element prop = (Element)it2.next();
                    imageProperties.setProperty(prop.getName(), prop.getText());    
                }    
            }  
        } 
        
        //Füllen der Algorithmusergebnisse
        Element algResult = alg.getChild("Result");
        Element executionTime = algResult.getChild("ExecutionTime");
        try {
            startTime = executionTime.getAttribute("start").getLongValue();
            stopTime = executionTime.getAttribute("stop").getLongValue();
        } catch (DataConversionException e) {
            startTime = 0;
            stopTime = 0;
        }
        Element iter = algResult.getChild("Iterations");
        iterations = Integer.parseInt(iter.getText());
        meanValues = new double[k];
        List meanList = algResult.getChild("MeanValues").getChildren("Value");
        int pos = 0;
        for (Iterator it = meanList.iterator(); it.hasNext();) {
            Element value = (Element)it.next();
            meanValues[pos++] = Double.parseDouble(value.getText());  
        }
        
        //Füllen des AccumulatorArrays
        Element result = root.getChild("Result");
        Element fm = result.getChild("FrequencyMatrix");
        int rows, cols;
        try {
            rows = fm.getAttribute("rows").getIntValue();
            cols = fm.getAttribute("cols").getIntValue();
            
            accu = new AccumulatorArray(rows, cols);
            int posx = 0, posy = 0;
            List rowList = fm.getChildren("Row");
            for (Iterator i = rowList.iterator(); i.hasNext();) {
                posy = 0;
                List colList = ((Element)i.next()).getChildren("ColData");
                for (Iterator j = colList.iterator(); j.hasNext();) {
                    Element data = (Element)j.next();
                    accu.setValue(posx, posy, Integer.parseInt(data.getText())); 
                    ++posy;      
                }
                ++posx;    
            }            
            
        } catch (Exception e) {
            rows = -1; cols = -1;
        }

        
        //Füllen der Fehler
        try {
            Element error = result.getChild("Error");
            overallError = Double.parseDouble(error.getChild("OverallError").getText());
            featureError = new double[k];
            pos = 0;
            List errorList = error.getChildren("FeatureError");
            for (Iterator it = errorList.iterator(); it.hasNext();) {
                Element fe = (Element)it.next();
                featureError[pos++] = Double.parseDouble(fe.getText());   
            }
        } catch (Exception e) {
        }
        
        //MutualInformation
        Element mi = result.getChild("MutualInformation");
        if (mi != null) {
            mutualInformation = Double.parseDouble(mi.getText());
        }
    }

    /**
     * Returns the accu.
     * @return AccumulatorArray
     */
    public AccumulatorArray getAccu() {
        return accu;
    }

    /**
     * Returns the algorithmName.
     * @return String
     */
    public String getAlgorithmName() {
        return algorithmName;
    }

    /**
     * Returns the featureError.
     * @return double[]
     */
    public double[] getFeatureError() {
        return featureError;
    }

    /**
     * Returns the imageProperties.
     * @return Properties
     */
    public Properties getImageProperties() {
        return imageProperties;
    }

    /**
     * Returns the iterations.
     * @return int
     */
    public int getIterations() {
        return iterations;
    }

    /**
     * Returns the k.
     * @return int
     */
    public int getK() {
        return k;
    }
    
    public double getBeta() {
        return beta;    
    }
    
    public double getMutualInformation() {
        return mutualInformation;    
    }

    /**
     * Returns the meanValues.
     * @return double[]
     */
    public double[] getMeanValues() {
        return meanValues;
    }

    /**
     * Returns the overallError.
     * @return double
     */
    public double getOverallError() {
        return overallError;
    }

    /**
     * Returns the startTime.
     * @return long
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Returns the stopTime.
     * @return long
     */
    public long getStopTime() {
        return stopTime;
    }

}





















