package org.wewi.medimg.seg.validation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.wewi.medimg.image.ImageDataFactory;
import org.wewi.medimg.image.io.TIFFReader;
import org.wewi.medimg.util.param.DoubleParameterIterator;
import org.wewi.medimg.util.param.ImageParameterIterator;
import org.wewi.medimg.util.param.IntegerParameterIterator;
import org.wewi.medimg.util.param.Parameter;
import org.wewi.medimg.util.param.ParameterIterator;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ScriptGenerator {
    
    private Validator validator;
    private List parameterIteratorList;
    private int iterations = 1;

	/**
	 * Constructor for ScriptGenerator.
	 */
	public ScriptGenerator(Validator validator) {
		this.validator = validator;
        parameterIteratorList = new ArrayList();
	}
    
    public void addParameterIterator(ParameterIterator parameter) {
        parameterIteratorList.add(parameter);    
    }
    
    public void setIterations(int it) {
        iterations = it;    
    }
    
    public void writeScript(String file) throws IOException {
        XMLOutputter out = new XMLOutputter("   ", true);
        Element root = new Element("Batch");
        
        List pl = parameterIteratorList;
        int[] size = new int[pl.size()];
        Arrays.fill(size, 0);
        
        List params = new ArrayList();
        int pos = 0;
        for (Iterator it = pl.iterator(); it.hasNext();) {
            ParameterIterator pit = (ParameterIterator)it.next(); 
            List p = new ArrayList();
            while (pit.hasNext()) {
                p.add(pit.next());
                size[pos]++;    
            }     
            params.add(p);
            pos++;
        }
        
        Permutation permut = new Permutation(size);
        int[] perm = new int[size.length];
        int t = 0;
        while (permut.hasNext()) {
            Task task = new Task();
            task.setId(t++);
            task.setIterations(iterations);
            task.setValidator(validator);
            
            Element taskElement = task.createTaskElement();
            
            permut.next(perm);
            for (int i = 0; i < perm.length; i++) {
                Element paramElem = ((Parameter)((List)params.get(i)).get(perm[i])).createParameterElement();
                
                taskElement.addContent(paramElem);
            }
            
            root.addContent(taskElement);
        }
          
        Document doc = new Document(root);
        out.output(doc, new FileOutputStream(file));  
    }
    
    
    
    public static void main(String[] args) {
        //Validator validator = new MAPValidator();
        ParameterIterator it1 = new IntegerParameterIterator("k", 6, 6, 1);
        ParameterIterator it2 = new DoubleParameterIterator("BETA", 0.0, 2, 0.2); 
        ParameterIterator it3 = new ImageParameterIterator("source.image", 
                                                            ImageDataFactory.getInstance(),
                                                            TIFFReader.class,
                                                            new String[]{"X:/medimages/nhead/t1.n3.rf20", 
                                                                         "X:/medimages/nhead/t1.n5.rf20", 
                                                                         "X:/medimages/nhead/t1.n7.rf20",
                                                                         "X:/medimages/nhead/t1.n3.rf20"});
        ParameterIterator it4 = new ImageParameterIterator("model.image",
                                                            ImageDataFactory.getInstance(),
                                                            TIFFReader.class,
                                                            new String[]{"X:/medimages/nhead/seg.model"});
                                                            
        //ScriptGenerator gen = new ScriptGenerator(validator);
        //gen.addParameterIterator(it1);  
        //gen.addParameterIterator(it2);
        //gen.addParameterIterator(it3);
        //gen.addParameterIterator(it4);
        
        //try {
		//	gen.writeScript("X:/out.xml");
		//} catch (IOException e) {
        //    System.err.println("ScriptGenerator.main: " + e);
		//}     
        
    }

}






