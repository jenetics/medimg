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
 * DoubleImage.java
 * Created on 14.06.2003
 *
 */
package org.wewi.medimg.image;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleImage extends AbstractImage {

    /**
     * 
     */
    DoubleImage() {
        super();
    }

    /**
     * @param id
     */
    DoubleImage(DoubleImage id) {
        super(id);
    }

    /**
     * @param dim
     */
    public DoubleImage(Dimension dim) {
        super(dim);
    }

    /**
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @param minZ
     * @param maxZ
     */
    public DoubleImage(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
        super(minX, maxX, minY, maxY, minZ, maxZ);
    }

    /**
     * @param sizeX
     * @param sizeY
     * @param sizeZ
     */
    public DoubleImage(int sizeX, int sizeY, int sizeZ) {
        super(sizeX, sizeY, sizeZ);
    }

    /**
     * @see org.wewi.medimg.image.AbstractImage#createImageData(int)
     */
    protected ImageData createImageData(int size) {
        return null;
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        return new DoubleImage(this);
    }

}
