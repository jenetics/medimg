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
 * Util.java
 *
 * Created on 14. Januar 2002, 11:31
 */

package org.wewi.medimg.image.io;


/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class Util {

    /** Creates new Util */
    private Util() {
    }

    public static String format(int number, int length) {
        String numberString = Integer.toString(number);
        char[] erg = new char[length];
        for (int i = 0; i < length; i++) {
            erg[i] = '0';
        }
        
        char[] numberStringChars = numberString.toCharArray();
        for (int i = 0; i < numberString.length(); i++) {
            erg[length-1-i] = numberStringChars[numberString.length()-1-i];
        }
        
        return new String(erg);
    }
    
    /*
    public static void main(String[] args) {
        NumberFormat format = NumberFormat.getInstance();
        System.out.println(format(1298,10));
    }
    */
}
