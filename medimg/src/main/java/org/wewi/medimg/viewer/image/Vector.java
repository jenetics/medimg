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

/*
 * Java Source File
 * a part of SliceViewer
 * a program written in 2/98 by Orion Lawlor.
 * Public domain source code.
 *
 * Send questions to fsosl@uaf.edu
 */

package org.wewi.medimg.viewer.image;

/**
 * Vector is really quite a simple class-- it's just
 * three cartesian coordinates.  These can be interpreted
 * as a position or a direction, depending on the
 * circumstance.
 *
 * There are public routines to add, subtract, scale,
 * take the dot and cross product, normalize, and
 * return the magnitude of vectors.
 *
 */
final class Vector {
    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void copyFrom(Vector v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector normalize() {
        return scaleBy(1.0 / magnitude());
    }

    public Vector plus(Vector a) {
        return new Vector(x + a.x, y + a.y, z + a.z);
    }

    public Vector minus(Vector a) {
        return new Vector(x - a.x, y - a.y, z - a.z);
    }

    public Vector scaleBy(double scale) {
        return new Vector(scale*x, scale*y, scale*z);
    }

    public double dot(Vector a) {
        return x*a.x + y*a.y + z*a.z;
    }

    /**
     * Return right-handed cross product of this Vector and b
     * 
     * @param b
     * @return Vector
     */
    public Vector cross(Vector b) {
        return new Vector(y*b.z - z*b.y, z*b.x - x*b.z, x*b.y - y*b.x);
    }

    void setX(double x) {
        this.x = x;
    }

    double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }
}

;
