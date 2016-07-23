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

/**
 * FileExtentionFilter.java
 *
 * Created on 11. Januar 2002, 14:48
 */

package org.wewi.medimg.image.io;

import java.io.File;
import java.io.FileFilter;

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
    
    public String getExtention() {
        return extention;    
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
