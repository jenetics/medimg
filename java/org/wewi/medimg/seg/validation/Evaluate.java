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

import org.wewi.medimg.image.ops.MutualInformation;
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
    
    private class BetaMeanVar {
        public double beta;
        public double mean;
        public double var;    
    }
    
    private class BetaMeanVarComparator implements Comparator {
        
        public int compare(Object o1, Object o2) {
            if (!(o1 instanceof BetaMeanVar) || !(o2 instanceof BetaMeanVar)) {
                return 0;    
            }    
            
            BetaMeanVar b1 = (BetaMeanVar)o1;
            BetaMeanVar b2 = (BetaMeanVar)o2;
            
            if (b1.beta < b2.beta) {
                return -1;    
            } else {
                return 1;    
            }
        }    
    }
    
    
    
    private File dir;
    private File[] files;

    private int test = 0;
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
                                            return file.toString().toLowerCase().endsWith(".xml"); 
                                        }   
                                  });                           
    }
    

    
    public void evalKOverallError() {
        //Zusammenfassen der Protokolle
        Hashtable ktable = new Hashtable();
        for (int i = 0; i < files.length; i++) {
            Protocol protocol = new Protocol(files[i]);
            
            Integer k = new Integer(protocol.getK());
            MutualInformation mi = new MutualInformation(protocol.getAccu());
            double error = mi.getMutualInformation();            
            //double error = protocol.getOverallError();
            
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
            
            if (Double.isNaN(variance)) {
                variance = 0;    
            }
            
            KMeanVar kmv = new KMeanVar();
            kmv.k = k.intValue();
            kmv.mean = meanError;
            kmv.var = Math.sqrt(variance);
            
            kmvList.add(kmv); 
             
        }  
        
        KMeanVar[] array = new KMeanVar[kmvList.size()];
        kmvList.toArray(array);
        Arrays.sort(array, new KMeanVarComparator());   
        
        double[] k = new double[kmvList.size()];
        double[] mean = new double[kmvList.size()];
        double[] stddev = new double[kmvList.size()]; 
        
        for (int i = 0; i < kmvList.size(); i++) {
            k[i] = array[i].k;
            mean[i] = array[i].mean;
            stddev[i] = array[i].var;    
        } 
        
        new ErrorListPlot("Gesamtfehlerdiagramm", k, mean, stddev).show();    
    }
    
    
    public void evalBETAMutualInformation() {
       //Zusammenfassen der Protokolle
       System.out.println("Files: " + files.length);
        Hashtable betaTable = new Hashtable();
        for (int i = 0; i < files.length; i++) {
            Protocol protocol = new Protocol(files[i]);
            
            
            Double beta = new Double(protocol.getBeta());
            
            
            //System.out.println(protocol.getAccu());
            //MutualInformation mi = new MutualInformation(protocol.getAccu());
            //double error = mi.getMutualInformation();//protocol.getOverallError();
            double error = protocol.getMutualInformation();
            //System.out.println(error);
            
            if (!betaTable.containsKey(beta)) {
                betaTable.put(beta, new Vector());      
            }
            ((Vector)betaTable.get(beta)).add(new Double(error));
        } 
        
        List betaList = new ArrayList();
        for (Enumeration keys = betaTable.keys(); keys.hasMoreElements();) {
            Double b = (Double)keys.nextElement();
            
            List list = (List)betaTable.get(b);
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
            
            if (Double.isNaN(variance)) {
                variance = 0;    
            }
            
            BetaMeanVar beta = new BetaMeanVar();
            beta.beta = b.doubleValue();
            beta.mean = meanError;
            beta.var = Math.sqrt(variance);
            
            betaList.add(beta); 
             
        }  
        
        BetaMeanVar[] array = new BetaMeanVar[betaList.size()];
        betaList.toArray(array);
        Arrays.sort(array, new BetaMeanVarComparator());   
        
        double[] b = new double[betaList.size()];
        double[] mean = new double[betaList.size()];
        double[] stddev = new double[betaList.size()];
        
        for (int i = 0; i < betaList.size(); i++) {
            b[i] = array[i].beta;
            mean[i] = array[i].mean;
            stddev[i] = array[i].var;    
        } 
        
        new ErrorListPlot("Mutual Information Diagramm", b, mean, stddev).show();         
    }    
    
    public void evalBETAOverallError() {
       //Zusammenfassen der Protokolle
        Hashtable betaTable = new Hashtable();
        for (int i = 0; i < files.length; i++) {
            Protocol protocol = new Protocol(files[i]);
            
            Double beta = new Double(protocol.getBeta());
            double error = protocol.getOverallError();
            
            if (!betaTable.containsKey(beta)) {
                betaTable.put(beta, new Vector());      
            }
            ((Vector)betaTable.get(beta)).add(new Double(error));
        } 
        
        List betaList = new ArrayList();
        for (Enumeration keys = betaTable.keys(); keys.hasMoreElements();) {
            Double b = (Double)keys.nextElement();
            
            List list = (List)betaTable.get(b);
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
            
            BetaMeanVar beta = new BetaMeanVar();
            beta.beta = b.doubleValue();
            beta.mean = meanError;
            beta.var = Math.sqrt(variance);
            
            betaList.add(beta); 
             
        }  
        
        BetaMeanVar[] array = new BetaMeanVar[betaList.size()];
        betaList.toArray(array);
        Arrays.sort(array, new BetaMeanVarComparator());   
        
        double[] b = new double[betaList.size()];
        double[] mean = new double[betaList.size()];
        double[] stddev = new double[betaList.size()];
        
        for (int i = 0; i < betaList.size(); i++) {
            b[i] = array[i].beta;
            mean[i] = array[i].mean;
            stddev[i] = array[i].var;    
        } 
        
        new ErrorListPlot("Gesamtfehlerdiagramm", b, mean, stddev).show();        
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
        
        double[] k = new double[kmvList.size()];
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
        Evaluate eval = new Evaluate(new File(
         "C:/Workspace/Projekte/Diplom/validation/map/protocols/t1.n9.rf20"));
        //C:\Workspace\Projekte\Diplom\validation\map\protocols\t1.n3.rf20
        //"/home/fwilhelm/Workspace/Projekte/Diplom/code/data/validation/map/protocols"));        
        eval.evalBETAMutualInformation();
        //eval.evalBETAOverallError(); 
        //eval.evalNoOfIterations();
        //eval.evalKOverallError();
         
    }


}

















