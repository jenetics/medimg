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
 * RegistrationListener.java
 *
 * Created on 19. April 2002, 15:45
 */

package org.wewi.medimg.reg.wizard;

import java.util.EventListener;

/**
 *
 * @author  Werner Weiser
 * @version 0.1
 */
public interface RegistratorListener extends EventListener {
    
    public void registratorStarted(RegistratorEvent event);
    
    public void registratorFinished(RegistratorEvent event);
}
