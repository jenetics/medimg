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

package org.wewi.medimg.ui;

import java.awt.Cursor;
import java.awt.Rectangle;

final class MLPHandle extends Rectangle {
    private int thick2;
    private Cursor cursor;
    
    public MLPHandle(int size) {
        setSize(size - 1, size - 1);
        thick2 = size / 2;
    }
    public void set(Cursor cursor, int x, int y) {
        setLocation(x - thick2, y - thick2);
        this.cursor = cursor;
    }
    public Cursor getCursor() {
        return cursor;
    }
}
