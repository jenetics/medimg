package org.wewi.medimg.seg.validation;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.wewi.medimg.util.param.ParameterIterator;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ScriptGenerator {
    
    private class ParameterRecord {
        private String name;
        private ParameterIterator parameter;
        
        
        public ParameterRecord(String name, ParameterIterator parameter) {
            this.name = name;
            this.parameter = parameter;    
        }
        
        //public ParameterRecord(String name
        
        public String getName() {
            return name;    
        }
        
        public Object getParameter() {
            return parameter;    
        }
    }
    
    private Class algorithm;
    private List parameterList;

	/**
	 * Constructor for ScriptGenerator.
	 */
	public ScriptGenerator(Class algorithm) {
		this.algorithm = algorithm;
        parameterList = new Vector();
	}
    
    public void setParameter(String name, ParameterIterator parameter) {
        parameterList.add(new ParameterRecord(name, parameter));    
    }
    
    public void writeScript() throws IOException {
        
    }

}






