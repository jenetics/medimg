/**
 * ImageProperties.java
 * 
 * Created on 13.02.2003, 15:32:55
 *
 */
package org.wewi.medimg.image;

import java.util.Iterator;
import java.util.Map;

import org.jdom.Verifier;
import org.wewi.medimg.util.ListMap;
/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageProperties {
    private ListMap props = new ListMap();

    public ImageProperties() {
    }
    
    public void setProperty(String key, String value) throws IllegalArgumentException {
        String reason = Verifier.checkElementName(key);
        if (reason != null) {
            throw new IllegalArgumentException("Invalid key name: " +  reason);
        }
        
        reason = Verifier.checkCharacterData(value);
        if (reason != null) {
            //Plan B
            reason = Verifier.checkCDATASection(value);
            if (reason != null) {
                throw new IllegalArgumentException("Invalid value: " + reason);
            }
            
            value = "CDATA[" + value + "]";
        }
        
        props.put(key, value);
    }
    
    public boolean containsKey(String key) {
        return props.containsKey(key);
    }
    
    public String remove(String key) {
        return (String)props.remove(key);
    }
    
    public String getProperty(String key){
        return (String)props.get(key);
    }
    
    public String getPorperty(String key, String defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            value = defaultValue;
        }
        
        return value;
    }
    
    /**
     * The Iterator contains as values @see java.util.Map.Entry
     * 
     * @return Iterator of <code>java.util.Map.Entry</code> objects.
     */
    public Iterator iterator() {
        return props.entryList().iterator();
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        Map.Entry entry;
        String key, value;
        for (Iterator it = props.iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            key = (String)entry.getKey();
            value = (String)entry.getValue();
            
            buffer.append(key).append(":").append(value).append("\n");
        }
        
        return buffer.toString();
    }
    

}




