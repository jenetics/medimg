/* 
 * ImageProperties.java, created on 13.02.2003, 15:32:55
 * 
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


package org.wewi.medimg.image;

import java.util.Iterator;
import java.util.List;
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
        
        props.put(key.trim(), value);
    }
    
    public void setProperty(int pos, String key, String value) {
        props.add(pos, key, value);
    }
    
    public void replace(String oldKey, String newKey, String newValue) {
        props.replace(oldKey, newKey, newValue);
    }
    
    public void swap(String key1, String key2) {
        String tempKey = key1;
        String tempValue = getProperty(key1);
        
        replace(key1, key2 + "xxx", getProperty(key2));
        replace(key2, tempKey, tempValue);
        replace(key2 + "xxx", key2, getProperty(key2 + "xxx"));
    }
    
    public boolean containsKey(String key) {
        return props.containsKey(key.trim());
    }
    
    public String remove(String key) {
        return (String)props.remove(key);
    }
    
    public void clear() {
        props.clear();
    }
    
    public String getProperty(String key){
        if (key == null) {
            return "";
        }
        return (String)props.get(key.trim());
    }
    
    public String getPorperty(String key, String defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        
        String value = getProperty(key);
        if (value == null) {
            value = defaultValue;
        }
        
        return value;
    }
    
    public String getKey(int index) {
        List list = props.entryList();
        Map.Entry entry = (Map.Entry)list.get(index);
        
        if (entry == null) {
            return null;
        }
        
        return (String)entry.getKey();        
    }
    
    public String getValue(int index) {
        List list = props.entryList();
        Map.Entry entry = (Map.Entry)list.get(index);
        
        return (String)entry.getValue();
    }
    
    public int getIndex(String key) {
        int index = 0;
        Map.Entry entry;
        for (Iterator it = iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            if  (key.equals(entry.getKey())) {
                return index;
            }
            index++;
        }
        
        return index;
    }
    
    /**
     * The Iterator contains as values @see java.util.Map.Entry
     * 
     * @return Iterator of <code>java.util.Map.Entry</code> objects.
     */
    public Iterator iterator() {
        return props.entryList().iterator();
    }
    
    public int size() {
        return props.size();
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
    
    public Object clone() {
        ImageProperties ip = new ImageProperties();
        
        Map.Entry entry;
        for (Iterator it = iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            ip.setProperty((String)entry.getKey(), (String)entry.getValue());
        }
        
        return ip;
    }

}




