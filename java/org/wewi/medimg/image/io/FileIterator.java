/*
 * SliceImageFiles.java
 *
 * Created on 17. Jänner 2002, 22:41
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class FileIterator implements Iterator {
    private static FileNameComparator comparator = new FileNameComparator();
    
    private File[] files;
    private int index;
    private int length;
    
    private final static class FileNameComparator implements Comparator {
        public int compare(Object obj1, Object obj2) {
            if (!(obj1 instanceof File && obj2 instanceof File)) {
                return 0;
            }
            
            File file1 = (File)obj1;
            File file2 = (File)obj2;
            
            return file1.compareTo(file2);
        }
        
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FileNameComparator)) {
                return false;
            }
            return true;
        }
    }

    public FileIterator(File[] files) {
        this.files = files;
        Arrays.sort(files, comparator);
        index = 0;
        length = files.length;
    }

      
    public boolean hasNext() {
        return (index < length);
    }    
    
    public Object next() {
        return files[index++];
    }
    
    public void remove() {
        File[] newFiles = new File[length-1];
        
        int count = 0;
        for (int i = 0; i < length; i++) {
            if (i != index) {
                newFiles[i] = files[count];
            }
            count++;
        }
        
        files = newFiles;
        Arrays.sort(files, comparator);
        index = 0;
        length = files.length;
    }
    
}
