/**
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.wewi.medimg.math.MathUtil;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Evaluate {
    
    private class KOverallError {
        public int k;
        public double error;    
    }
    
    private class KMeanVar {
        public int k;
        public double mean;
        public double var;    
    }
    
    private class KMeanVarComparator implements Comparator {
        
		public int compare(Object obj1, Object obj2) {
            if (!(obj1 instanceof KMeanVar) ||
                !(obj2 instanceof KMeanVar)) {
                return 0;        
            }
            KMeanVar kmv1 = (KMeanVar)obj1;
            KMeanVar kmv2 = (KMeanVar)obj2;
            
			return kmv1.k - kmv2.k;
		}

    }
    
    
    
    private File dir;
    private File[] files;

	/**
	 * Constructor for Evaluate.
	 */
	public Evaluate(File dir) {
        this.dir = dir;
        init();
	}
    
    private void init() {
        files = dir.listFiles(new FileFilter() {
                                        public boolean accept(File file) {
                                            return file.toString().endsWith(".xml"); 
                                        }   
                                  });                           
    }
    
    public void evalKOverallError() {
        //Zusammenfassen der Protokolle
        Hashtable ktable = new Hashtable();
        for (int i = 0; i < files.length; i++) {
            Protocol protocol = new Protocol(files[i]);
            
            Integer k = new Integer(protocol.getK());
            double error = protocol.getOverallError();
            
            if (!ktable.containsKey(k)) {
                ktable.put(k, new Vector());      
            }
            ((Vector)ktable.get(k)).add(new Double(error));
        } 
        
        List kmvList = new ArrayList();
        for (Enumeration keys = ktable.keys(); keys.hasMoreElements();) {
            Integer k = (Integer)keys.nextElement();
            
            List list = (List)ktable.get(k);
            double meanError = 0;
            for (Iterator it = list.iterator(); it.hasNext();) {
                meanError += ((Double)it.next()).doubleValue();            
            }
            meanError /= list.size();
            double variance = 0;
            for (Iterator it = list.iterator(); it.hasNext();) {
                variance += MathUtil.sqr(((Double)it.next()).doubleValue() - meanError);   
            }
            variance /= (list.size()-1);
            
            KMeanVar kmv = new KMeanVar();
            kmv.k = k.intValue();
            kmv.mean = meanError;
            kmv.var = Math.sqrt(variance);
            
            kmvList.add(kmv); 
             
        }  
        
        KMeanVar[] array = new KMeanVar[kmvList.size()];
        kmvList.toArray(array);
        Arrays.sort(array, new KMeanVarComparator());   
        
        int[] k = new int[kmvList.size()];
        double[] mean = new double[kmvList.size()];
        double[] stddev = new double[kmvList.size()]; 
        
        for (int i = 0; i < kmvList.size(); i++) {
            k[i] = array[i].k;
            mean[i] = array[i].mean;
            stddev[i] = array[i].var;    
        } 
        
        new ErrorListPlot("Gesamtfehlerdiagramm", k, mean, stddev).show();    
    }
    
    public void evalNoOfIterations() {
        //Zusammenfassen der Protokolle
        Hashtable ktable = new Hashtable();
        for (int i = 0; i < files.length; i++) {
            Protocol protocol = new Protocol(files[i]);
            
            Integer k = new Integer(protocol.getK());
            double error = protocol.getIterations();
            
            if (!ktable.containsKey(k)) {
                ktable.put(k, new Vector());      
            }
            ((Vector)ktable.get(k)).add(new Double(error));
        } 
        
        List kmvList = new ArrayList();
        for (Enumeration keys = ktable.keys(); keys.hasMoreElements();) {
            Integer k = (Integer)keys.nextElement();
            
            List list = (List)ktable.get(k);
            double meanError = 0;
            for (Iterator it = list.iterator(); it.hasNext();) {
                meanError += ((Double)it.next()).doubleValue();            
            }
            meanError /= list.size();
            double variance = 0;
            for (Iterator it = list.iterator(); it.hasNext();) {
                variance += MathUtil.sqr(((Double)it.next()).doubleValue() - meanError);   
            }
            variance /= (list.size()-1);
            
            KMeanVar kmv = new KMeanVar();
            kmv.k = k.intValue();
            kmv.mean = meanError;
            kmv.var = Math.sqrt(variance);
            
            kmvList.add(kmv); 
             
        }  
        
        KMeanVar[] array = new KMeanVar[kmvList.size()];
        kmvList.toArray(array);
        Arrays.sort(array, new KMeanVarComparator());   
        
        int[] k = new int[kmvList.size()];
        double[] mean = new double[kmvList.size()];
        double[] stddev = new double[kmvList.size()]; 
        
        for (int i = 0; i < kmvList.size(); i++) {
            k[i] = array[i].k;
            mean[i] = array[i].mean;
            stddev[i] = array[i].var;    
        } 
        
        new ErrorListPlot("Iterationsdiagramm", k, mean, stddev).show();    
    }    
    
    /**
     * Ausgabe als Mathematica-String.
     */
    public String evalKOveralError_() {
        //Zusammenfassen der Protokolle
        Hashtable ktable = new Hashtable();
        for (int i = 0; i < files.length; i++) {
            Protocol protocol = new Protocol(files[i]);
            Integer k = new Integer(protocol.getK());
            double error = protocol.getOverallError();
            
            if (!ktable.containsKey(k)) {
                ktable.put(k, new Vector());      
            }
            ((Vector)ktable.get(k)).add(new Double(error));
        }
        
        
        StringBuffer buffer = new StringBuffer();
        buffer.append("ErrorListPlot[{");
        for (Enumeration keys = ktable.keys(); keys.hasMoreElements();) {
            Integer k = (Integer)keys.nextElement();
            buffer.append("{").append(k).append(",");
            
            List list = (List)ktable.get(k);
            double meanError = 0;
            for (Iterator it = list.iterator(); it.hasNext();) {
                meanError += ((Double)it.next()).doubleValue();            
            }
            meanError /= list.size();
            double variance = 0;
            for (Iterator it = list.iterator(); it.hasNext();) {
                variance += MathUtil.sqr(((Double)it.next()).doubleValue() - meanError);   
            }
            variance /= (list.size()-1);
            
            buffer.append(meanError).append(",");
            buffer.append(Math.sqrt(variance)).append("},\n");       
        }
        
        buffer.append("}]");
        
        return buffer.toString();       
    }
    
    
    public String mathematicaFormat(double value) {
        return null;    
    }
    
    
    
    public static void main(String[] args) {
        Evaluate eval = new Evaluate(new File("C:/Workspace/fwilhelm/Projekte/Diplom/validation/ml/protocols/t1.n9.rf20"));
        eval.evalNoOfIterations(); 
         
    }


}

















