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

package org.wewi.medimg.util;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * Großteils von java.util.Hashtable
 */

public class IntHashtable extends Dictionary implements Cloneable {
    
    private class IntHashtableEntry {
        int hash;
        int key;
        Object value;
        IntHashtableEntry next;
    
        protected Object clone() {
            IntHashtableEntry entry = new IntHashtableEntry();
            entry.hash = hash;
            entry.key = key;
            entry.value = value;
            entry.next = (next != null) ? (IntHashtableEntry) next.clone() : null;
            return entry;
        }
    }
    
    private class IntHashtableEnumerator implements Enumeration {
        boolean keys;
        int index;
        IntHashtableEntry table[];
        IntHashtableEntry entry;
        
        IntHashtableEnumerator(IntHashtableEntry table[], boolean keys) {
            this.table = table;
            this.keys = keys;
            this.index = table.length;
        }
    
        public boolean hasMoreElements() {
            if (entry != null) {
                return true;
            }
    
            while (index-- > 0) {
                if ((entry = table[index]) != null) {
                    return true;
                }
            }
    
            return false;
        }
    
        public Object nextElement() {
            if (entry == null) {
                while ((index-- > 0) && ((entry = table[index]) == null));
            }
    
            if (entry != null) {
                IntHashtableEntry e = entry;
                entry = e.next;
                return keys ? new Integer(e.key) : e.value;
            }
            throw new NoSuchElementException("IntHashtableEnumerator");
        }
    
    }    

    private IntHashtableEntry table[];
    private int count;
    private int threshold;
    private float loadFactor;

    public IntHashtable(int initialCapacity, float loadFactor) {

        if (initialCapacity <= 0 || loadFactor <= 0.0) {
            throw new IllegalArgumentException();
        }

        this.loadFactor = loadFactor;
        table = new IntHashtableEntry[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);

    }

    public IntHashtable(int initialCapacity) {
        this(initialCapacity, 0.75f);

    }

    public IntHashtable() {
        this(101, 0.75f);
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public synchronized Enumeration keys() {
        return new IntHashtableEnumerator(table, true);
    }

    public synchronized Enumeration elements() {
        return new IntHashtableEnumerator(table, false);
    }

    public synchronized boolean contains(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }

        IntHashtableEntry tab[] = table;
        for (int i = tab.length; i-- > 0;) {
            for (IntHashtableEntry e = tab[i]; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean containsKey(int key) {
        IntHashtableEntry tab[] = table;
        int hash = key;
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (IntHashtableEntry e = tab[index]; e != null; e = e.next) {
            if (e.hash == hash && e.key == key) {
                return true;
            }
        }
        return false;
    }

    public synchronized Object get(int key) {
        IntHashtableEntry tab[] = table;
        int hash = key;
        int index = (hash & 0x7FFFFFFF) % tab.length;
        for (IntHashtableEntry e = tab[index]; e != null; e = e.next) {
            if (e.hash == hash && e.key == key) {
                return e.value;
            }
        }
        return null;
    }

    public Object get(Object okey) {
        if (!(okey instanceof Integer)) {
            throw new InternalError("key is not an Integer");
        }

        Integer ikey = (Integer) okey;
        int key = ikey.intValue();
        return get(key);
    }

    protected void rehash() {
        int oldCapacity = table.length;
        IntHashtableEntry oldTable[] = table;
        int newCapacity = oldCapacity * 2 + 1;
        IntHashtableEntry newTable[] = new IntHashtableEntry[newCapacity];
        threshold = (int)(newCapacity * loadFactor);
        table = newTable;
        
        for (int i = oldCapacity; i-- > 0;) {
            for (IntHashtableEntry old = oldTable[i]; old != null;) {
                IntHashtableEntry e = old;
                old = old.next;
                int index = (e.hash & 0x7FFFFFFF) % newCapacity;
                e.next = newTable[index];
                newTable[index] = e;
            }
        }
    }


    public synchronized Object put(int key, Object value) {
        if (value == null) {
            throw new NullPointerException();
        }

        IntHashtableEntry tab[] = table;
        int hash = key;
        int index = (hash & 0x7FFFFFFF) % tab.length;

        for (IntHashtableEntry e = tab[index]; e != null; e = e.next) {
            if (e.hash == hash && e.key == key) {
                Object old = e.value;
                e.value = value;
                return old;
            }
        }

        if (count >= threshold) {
            rehash();
            return put(key, value);
        }

        IntHashtableEntry e = new IntHashtableEntry();
        e.hash = hash;
        e.key = key;
        e.value = value;
        e.next = tab[index];
        tab[index] = e;
        ++count;

        return null;
    }

    public Object put(Object okey, Object value) {
        if (!(okey instanceof Integer)) {
            throw new InternalError("key is not an Integer");
        }

        Integer ikey = (Integer) okey;
        int key = ikey.intValue();

        return put(key, value);
    }

    public synchronized Object remove(int key) {
        IntHashtableEntry tab[] = table;
        int hash = key;
        int index = (hash & 0x7FFFFFFF) % tab.length;

        for (IntHashtableEntry e = tab[index], prev = null;
            e != null;
            prev = e, e = e.next) {
            if (e.hash == hash && e.key == key) {
                if (prev != null) {
                    prev.next = e.next;
                } else {
                    tab[index] = e.next;
                }
                --count;

                return e.value;
            }
        }
        return null;
    }

    public Object remove(Object okey) {
        if (!(okey instanceof Integer)) {
            throw new InternalError("key is not an Integer");
        }

        Integer ikey = (Integer) okey;
        int key = ikey.intValue();
        return remove(key);
    }

    public synchronized void clear() {
        IntHashtableEntry tab[] = table;
        for (int index = tab.length; --index >= 0;) {
            tab[index] = null;
        }

        count = 0;
    }

    public synchronized Object clone() {
        try {
            IntHashtable t = (IntHashtable) super.clone();
            t.table = new IntHashtableEntry[table.length];

            for (int i = table.length; i-- > 0;) {
                t.table[i] =
                    (table[i] != null)
                        ? (IntHashtableEntry) table[i].clone()
                        : null;
            }

            return t;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    public synchronized String toString() {
        int max = size() - 1;
        StringBuffer buf = new StringBuffer();
        Enumeration k = keys();
        Enumeration e = elements();
        buf.append("{");

        for (int i = 0; i <= max; ++i) {
            String s1 = k.nextElement().toString();
            String s2 = e.nextElement().toString();
            buf.append(s1 + "=" + s2);
            if (i < max) {
                buf.append(", ");
            }
        }
        buf.append("}");

        return buf.toString();
    }
}


