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
 * RegistrationKind.java
 *
 * Created on 7. April 2002, 14:28
 */

package org.wewi.medimg.reg.wizard;

import org.wewi.medimg.util.Enumeration;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */

public class RegistratorEnumeration extends Enumeration {
    public static final RegistratorEnumeration PCA_METHOD_RIGID = 
                      new RegistratorEnumeration("Rigid Principal Component Method");
    public static final RegistratorEnumeration PCA_METHOD_NONRIGID = 
                      new RegistratorEnumeration("Nonrigid Principal Component Method");                      
    public static final RegistratorEnumeration MC_METHOD = 
                      new RegistratorEnumeration("Monte Carlo Method");

    public static final RegistratorEnumeration[] ENUMERATION = 
                              {PCA_METHOD_RIGID, PCA_METHOD_NONRIGID, MC_METHOD};
    
    private String name;
    private static int refCount = 0;  
    private RegistratorEnumeration(String name) { 
        super(++refCount);
        this.name = name;
    }
    
    public String toString() {
        return name;    
    }
}
