/* 
 * ShortImageFactory.java, created on 18.09.2002
 * 
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

package org.wewi.medimg.image;

import org.wewi.medimg.util.Singleton;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ShortImageFactory implements ImageFactory, Singleton {
    
    private static ShortImageFactory singleton = null;

    private ShortImageFactory() {
    }

    public static synchronized ShortImageFactory getInstance() {
        if (singleton == null) {
            singleton = new ShortImageFactory();
        }
        return singleton;
    }
    
    public synchronized Image createImage(int maxX, int maxY, int maxZ) {
        return new ShortImage(maxX, maxY, maxZ);
    }
    
    public synchronized Image createImage(Dimension dim) {
        return new ShortImage(dim);    
    }

}
