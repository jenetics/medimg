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
 * NextNextCommand.java
 *
 * Created on 28. März 2002, 21:44
 */

package org.wewi.medimg.viewer.image;

import org.wewi.medimg.viewer.Command;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
final class NextNextCommand implements Command {
    private ImageViewer imageViewer;
    private int stride;

    /** Creates new NextNextCommand */
    public NextNextCommand(ImageViewer imageViewer, int stride) {
        this.imageViewer = imageViewer;
        this.stride = stride;
    }

    public void execute() {
        int slice = imageViewer.getSlice();
        if (imageViewer.getImage().getMaxZ() >= slice+stride) {
            imageViewer.setSlice(slice+stride);
        }
    }
    
}
