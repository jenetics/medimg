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
 * Created on 18.09.2002
 *
 */
package org.wewi.medimg.image;

import org.wewi.medimg.util.Singleton;

/**
 * @author Franz Wilhelmst�tter
 * @version 0.1
 */
public class ByteImageFactory implements ImageFactory, Singleton {

    private static ByteImageFactory singleton = null;

    private ByteImageFactory() {
    }

    /**
     * Liefert die einzige Instanz dieser Fabrik zur�ck
     */
    public static synchronized ByteImageFactory getInstance() {
        if (singleton == null) {
            singleton = new ByteImageFactory();
        }
        return singleton;
    }
    
    /**
     * Erzeugen eines neuen ImageData
     */
    public synchronized Image createImage(int maxX, int maxY, int maxZ) {
        return new ByteImage(maxX, maxY, maxZ);
    }
    
    
    /**
     * Erzeugen eines neuen ImageData
     */    
    public synchronized Image createImage(Dimension dim) {
        return new ByteImage(dim);    
    }

}
