/*
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
