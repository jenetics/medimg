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
 * All the data members (all 3 of them) are public.
 * This is so routines like Matrix3D can directly
 * (i.e. efficiently) read and write vectors.
 * Many OOP-ers disagree with this-- as I would for
 * large or complex classes-- but for tiny classes
 * I see no reason not to allow direct acess to
 * data members.  In Java, a language without structures,
 * this is doubly true.
 *
 */
final class Vector {
    private double x;
    private double y;
    private double z;

    public Vector(double X, double Y, double Z) {
        setX(X);
        setY(Y);
        setZ(Z);
    }

    public void copyFrom(Vector v) {
        setX(v.getX());
        setY(v.getY());
        setZ(v.getZ());
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector normalize() {
        return scaleBy(1.0 / magnitude());
    }

    public Vector plus(Vector a) {
        return new Vector(getX() + a.getX(), getY() + a.getY(), getZ() + a.getZ());
    }

    public Vector minus(Vector a) {
        return new Vector(getX() - a.getX(), getY() - a.getY(), getZ() - a.getZ());
    }

    public Vector scaleBy(double scale) {
        return new Vector(scale * getX(), scale * getY(), scale * getZ());
    }

    public double dot(Vector a) {
        return getX() * a.getX() + getY() * a.getY() + getZ() * a.getZ();
    }

    /**
     * Return right-handed cross product of this Vector and b
     * 
     * @param b
     * @return Vector
     */
    public Vector cross(Vector b) {
        return new Vector(getY() * b.getZ() - getZ() * b.getY(),
                           getZ() * b.getX() - getX() * b.getZ(),
                           getX() * b.getY() - getY() * b.getX());
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
