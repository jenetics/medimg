/*
 * FileExtentionFilter.java
 *
 * Created on 11. Januar 2002, 14:48
 */

package org.wewi.medimg.image.io;

import java.io.FileFilter;
import java.io.File;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class FileExtentionFilter implements FileFilter {
    private String extention;
    private int from = Integer.MIN_VALUE;
    private int to = Integer.MAX_VALUE;
    
    public FileExtentionFilter(String ext) {
        extention = ext.toLowerCase();
    }
    
    public FileExtentionFilter(String ext, int from, int to) {
        this(ext);
        this.from = from;
        this.to = to;
    }
    
    /*
    private boolean acceptRange(File file) {
        if (from == Integer.MIN_VALUE && to == Integer.MIN_VALUE) {
            return true;
        }
        String fileName = file.getName();
        int begin = fileName.indexOf(".");
        int end = fileName.indexOf(".", begin+1);
        
        int slice = -1;
        try {
            slice = Integer.parseInt(fileName.substring(begin+1, end-1));
        } catch (NumberFormatException nfe) {
            return false;
        }
        
        return (from <= slice && slice <= to);
    }
    */
    
    public boolean accept(File file) {
        if (file == null) {
            return false;
        }
        if (file.isFile()) {
            return file.getName().toLowerCase().endsWith(extention);
        }
        return file.getName().toLowerCase().endsWith(extention);   // && acceptRange(file);
    }
}
