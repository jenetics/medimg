/**
 * ListMap.java
 * 
 * Created on 13.02.2003, 15:47:19
 *
 */
package org.wewi.medimg.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public class ListMap implements Map {
    
    private class ListMapEntry implements Map.Entry {
        private Object key;
        private Object value;
        
        public ListMapEntry(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
		/**
		 * @see java.util.Map.Entry#getKey()
		 */
		public Object getKey() {
			return key;
		}
		/**
		 * @see java.util.Map.Entry#getValue()
		 */
		public Object getValue() {
			return value;
		}
		/**
		 * @see java.util.Map.Entry#setValue(java.lang.Object)
		 */
		public Object setValue(Object value) {
            this.value = value;
			return value;
		}
        
        public int hashCode() {
            return key.hashCode();
        }
    }
    
    /**
     * This List contains the entries in the inserted order.
     */
    private ArrayList entries = new ArrayList();
    
    private Set keys = new HashSet();
    private Collection values = new ArrayList();
    
    public ListMap() {
    }
    
    
	/**
	 * @see java.util.Map#size()
	 */
	public int size() {
		return entries.size();
	}
    
	/**
	 * @see java.util.Map#isEmpty()
	 */
	public boolean isEmpty() {
		return entries.isEmpty();
	}
    
	/**
	 * @see java.util.Map#containsKey(java.lang.Object)
	 */
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}
    
	/**
	 * @see java.util.Map#containsValue(java.lang.Object)
	 */
	public boolean containsValue(Object value) {
        return values.contains(value);
	}
    
	/**
	 * @see java.util.Map#get(java.lang.Object)
	 */
	public Object get(Object key) {
        Map.Entry entry;
        for (Iterator it = entries.iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }        
        
		return null;
	}
    
	/**
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 */
	public Object put(Object key, Object value) {
        Object ret = remove(key);
        entries.add(new ListMapEntry(key, value));
        keys.add(key);
        values.add(value);
        
        return ret;
	}
    
	/**
	 * @see java.util.Map#remove(java.lang.Object)
	 */
	public Object remove(Object key) {
        Map.Entry entry;
        for (Iterator it = entries.iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            if (entry.getKey().equals(key)) {
                entries.remove(entry);
                keys.remove(key);
                values.remove(entry.getValue());
                
                return entry.getValue();
            }
        } 
        
		return null;
	}
    
	/**
	 * @see java.util.Map#putAll(java.util.Map)
	 */
	public void putAll(Map t) {
        Map.Entry entry;
        for (Iterator it = t.entrySet().iterator(); it.hasNext();) {
            entry = (Map.Entry)it.next();
            put(entry.getKey(), entry.getValue());
        }
	}
    
	/**
	 * @see java.util.Map#clear()
	 */
	public void clear() {
        entries.clear();
        keys.clear();
        values.clear();
	}
    
	/**
	 * @see java.util.Map#keySet()
	 */
	public Set keySet() {     
		return keys;
	}
    
	/**
	 * @see java.util.Map#values()
	 */
	public Collection values() {
        return values;
	}
    
	/**
	 * @see java.util.Map#entrySet()
	 */
	public Set entrySet() {
        Set set = new HashSet();
        for (Iterator it = entries.iterator(); it.hasNext();) {
            set.add(it.next());
        }
        
        return set;
	}
    
    public List entryList() {
        return (List)entries.clone();
    }

}