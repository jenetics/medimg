/**
 * Created on 27.09.2002
 *
 */
package org.wewi.medimg.seg.validation;

import org.jdom.Element;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class Util {

    private Util() {
    }
    
    static Element transform(AccumulatorArray accu) {
        Element table = new Element("FrequencyMatrix");
        
        for (int i = 0, n = accu.getRows(); i < n; i++) {
            for (int j = 0, m = accu.getCols(); j < m; j++) {
            
            }       
        }
        
        
        return null;    
    }

}
