/**
 * Created on 01.10.2002
 *
 */
package org.wewi.medimg.seg.validation;

import java.io.File;
import java.io.FileFilter;
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
    
    /**
     * Ausgabe als Mathematica-String.
     */
    public String evalKOveralError() {
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
            
    }
    
    
    
    public static void main(String[] args) {
        Evaluate eval = new Evaluate(new File("C:/Workspace/fwilhelm/Projekte/Diplom/validation/protocols")); 
        
        System.out.println(eval.evalKOveralError());  
    }


}

















