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
 * SaveAsCommand.java
 *
 * Created on 27. Juni 2002, 22:53
 */

package org.wewi.medimg.viewer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class SaveAsCommand implements Command {
    private Viewer viewer;
    
    /** Creates a new instance of SaveAsCommand */
    public SaveAsCommand(Viewer viewer) {
        this.viewer = viewer;
    }
    
    public void execute() {
    }
    
}
