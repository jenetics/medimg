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
 * RegistrationException.java
 *
 * Created on 27. M�rz 2002, 14:47
 */

package org.wewi.medimg.reg;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public class RegistrationException extends java.lang.Exception {

    /**
     * Creates new <code>RegistrationException</code> without detail message.
     */
    public RegistrationException() {
    }


    /**
     * Constructs an <code>RegistrationException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RegistrationException(String msg) {
        super(msg);
    }
}


