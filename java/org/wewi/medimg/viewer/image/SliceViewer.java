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


package org.wewi.medimg.viewer.image;


import org.wewi.medimg.image.Image;
import org.wewi.medimg.viewer.ImageContainer;
import org.wewi.medimg.viewer.ViewerDesktopFrame;


public class SliceViewer extends ViewerDesktopFrame implements ImageContainer {
    private Image image;
    

    /**
     * Constructor for SliceViewer.
     */
    public SliceViewer(Image image) {
        this(image, "");
    }

    /**
     * Constructor for SliceViewer.
     * @param name
     */
    public SliceViewer(Image image, String name) {
        this(image, name, false);
    }

    /**
     * Constructor for SliceViewer.
     * @param name
     * @param resizeable
     */
    public SliceViewer(Image image, String name, boolean resizeable) {
        this(image, name, resizeable, false);
    }

    /**
     * Constructor for SliceViewer.
     * @param name
     * @param resizeable
     * @param closable
     */
    public SliceViewer(Image image, String name, boolean resizeable, boolean closable) {
        this(image, name, resizeable, closable, false);
    }

    /**
     * Constructor for SliceViewer.
     * @param name
     * @param resizeable
     * @param closable
     * @param maximizable
     */
    public SliceViewer(Image image, String name, boolean resizeable, boolean closable, boolean maximizable) {
        this(image, name, resizeable, closable, maximizable, false);
    }


    public SliceViewer(Image image, String name, boolean resizeable, boolean closable, boolean maximizable, boolean iconable) {
        super(name, resizeable, closable, maximizable, iconable);
        this.image = image;
        getContentPane().add(new SliceViewerPanel(image));
    }
    
    /**
     * @see org.wewi.medimg.viewer.ImageContainer#getImage()
     */
    public Image getImage() {
        return image;
    }

}
