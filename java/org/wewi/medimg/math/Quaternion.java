/**
 * Quaternion.java
 * 
 * Created on 17.03.2003, 22:28:46
 *
 */
package org.wewi.medimg.math;


import org.wewi.medimg.util.Immutable;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class Quaternion implements Immutable {
    public double x, y, z, w;

    /**
     * creates a quaternion from x,y,z,w
     */
    public Quaternion(double x, double y, double z, double w) {
        set(x,y,z,w);
    }

    /**
     * creates a null quaternion
     */
    public Quaternion() {
        set(0,0,0,0);
    }

    /**
     * creates a new quaternion from q
     */
    public Quaternion(Quaternion q) {
        set(q.x, q.y, q.z, q.w);
    }

    /**
     * @return a string representation of quaternion
     */
    public String toString() {
        return "["+x+", "+y+", "+z+", "+w+"]";
    }

/* old code
    public void copyInto(Quaternion q) {
        q.x = x;
        q.y = y;
        q.z = z;
        q.w = w;
    }
*/

    /**
     * @return length of quaternion
     */
    public double length() {
        return Math.sqrt(x*x + y*y + z*z + w*w);
    }

    /**
     * @return normalized form of quaternion, or null if length is 0;
     */
    public Quaternion normalize(Quaternion r) {
        if (r == null) r = new Quaternion();
        double f = length();
        if (f == 0.0) {
            r = null;
        } else {
            r.set(x/f, y/f, z/f, w/f);
        }
        return r;
    }


    /**
     * Construct rotation matrix from (possibly non-unit) quaternion.
     * Assumes matrix is used to multiply column vector on the left:
     * vnew = mat vold.  Works correctly for right-handed coordinate system
     * and right-handed rotations.
     */
    private Matrix4 toMatrix(Matrix4 m) {
        if (m == null) { 
            m = new Matrix4();
        }
        double Nq = x*x + y*y + z*z + w*w;
        double s = (Nq > 0.0) ? (2.0 / Nq) : 0.0;
        double xs = x*s,          ys = y*s,       zs = z*s;
        double wx = w*xs,         wy = w*ys,      wz = w*zs;
        double xx = x*xs,         xy = x*ys,      xz = x*zs;
        double yy = y*ys,         yz = y*zs,      zz = z*zs;
        return m.set(
            1.0 - (yy + zz),    xy - wz,            xz + wy,            0.0,
            xy + wz,            1.0 - (xx + zz),    yz - wx,            0.0,
            xz - wy,            yz + wx,            1.0 - (xx + yy),    0.0,
            0.0,                0.0,                0.0,                1.0
        );
    }

    /**
     * @return quaternion product (this * q).  Note: order is important!
     * To combine rotations, use the product this.multiply(q),
     * which gives the effect of rotating by this then q.
     */
    public Quaternion multiply(Quaternion q, Quaternion r) {
        if (r == null) r = new Quaternion();
        return r.set(
            w*q.x + x*q.w + y*q.z - z*q.y,
            w*q.y + y*q.w + z*q.x - x*q.z,
            w*q.z + z*q.w + x*q.y - y*q.x,
            w*q.w - x*q.x - y*q.y - z*q.z
        );
    }

    /**
     * @return conjugate of quaternion.
     */
    public Quaternion conjugate(Quaternion r) {
        if (r == null) r = new Quaternion();
        return r.set(-x, -y, -z, w);
    }

    /**
     * @return the quaternion set to x,y,z,w
     */
    public Quaternion set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * @return the quaternion set to q
     */
    public Quaternion set(Quaternion q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }
}

/**
 * Implementation of a 4x4 matrix.
 * <P>
 * No methods changes the Matrix itself, however, its member variables are public.
 *
 * @author Mark Donszelmann
 * @version $Id$
 */

final class Matrix4 {

    // matrix represented by Mrow-col
    public double m00, m01, m02, m03;
    public double m10, m11, m12, m13;
    public double m20, m21, m22, m23;
    public double m30, m31, m32, m33;

    private static final double EPS = 1E-10;

    /**
     * creates a new zero-ed matrix
     */
    public Matrix4() {
    }

    /**
     * creates a new matrix with initial values from s
     */
    public Matrix4(Matrix4 s) {
        set(s.m00, s.m01, s.m02, s.m03,
            s.m10, s.m11, s.m12, s.m13,
            s.m20, s.m21, s.m22, s.m23,
            s.m30, s.m31, s.m32, s.m33);
    }

    /**
     * creates a new matrix from an 16 value array, col by col
     */
    public Matrix4(double[] a) {
        set (a[0], a[4], a[ 8], a[12],
             a[1], a[5], a[ 9], a[13],
             a[2], a[6], a[10], a[14],
             a[3], a[7], a[11], a[15]);
    }

    /**
     * creates a new matrix from values
     */
    public Matrix4(double a00, double a01, double a02, double a03,
                   double a10, double a11, double a12, double a13,
                   double a20, double a21, double a22, double a23,
                   double a30, double a31, double a32, double a33) {
        set(a00, a01, a02, a03,
            a10, a11, a12, a13,
            a20, a21, a22, a23,
            a30, a31, a32, a33);
    }

    /**
     * sets all values in this matrix
     */
    public Matrix4 set(double a00, double a01, double a02, double a03,
                       double a10, double a11, double a12, double a13,
                       double a20, double a21, double a22, double a23,
                       double a30, double a31, double a32, double a33) {
        m00 = a00; m01 = a01; m02 = a02; m03 = a03;
        m10 = a10; m11 = a11; m12 = a12; m13 = a13;
        m20 = a20; m21 = a21; m22 = a22; m23 = a23;
        m30 = a30; m31 = a31; m32 = a32; m33 = a33;
        return this;
    }

    /**
     * @return an identity matrix
     */
    public static Matrix4 identity() {
        return new Matrix4(
            1.0, 0.0, 0.0, 0.0,
            0.0, 1.0, 0.0, 0.0,
            0.0, 0.0, 1.0, 0.0,
            0.0, 0.0, 0.0, 1.0
        );
    }

    /**
     * multiplies matrix by s and returns result in m
     */
    public Matrix4 multiply(double s, Matrix4 m) {
        if (m == null) m = new Matrix4();
        return m.set(
            m00*s, m01*s, m02*s, m03*s,
            m10*s, m11*s, m12*s, m13*s,
            m20*s, m21*s, m22*s, m23*s,
            m30*s, m31*s, m32*s, m33*s
        );
    }

    /**
     * multiplies matrix by p and returns result in m
     * @return the matrix multiplied by p: using M = C x P
     */
    public Matrix4 multiply(Matrix4 p, Matrix4 m) {
        if (m == null) m = new Matrix4();
        return m.set(
            m00*p.m00 + m01*p.m10 + m02*p.m20 + m03*p.m30,
            m00*p.m01 + m01*p.m11 + m02*p.m21 + m03*p.m31,
            m00*p.m02 + m01*p.m12 + m02*p.m22 + m03*p.m32,
            m00*p.m03 + m01*p.m13 + m02*p.m23 + m03*p.m33,

            m10*p.m00 + m11*p.m10 + m12*p.m20 + m13*p.m30,
            m10*p.m01 + m11*p.m11 + m12*p.m21 + m13*p.m31,
            m10*p.m02 + m11*p.m12 + m12*p.m22 + m13*p.m32,
            m10*p.m03 + m11*p.m13 + m12*p.m23 + m13*p.m33,

            m20*p.m00 + m21*p.m10 + m22*p.m20 + m23*p.m30,
            m20*p.m01 + m21*p.m11 + m22*p.m21 + m23*p.m31,
            m20*p.m02 + m21*p.m12 + m22*p.m22 + m23*p.m32,
            m20*p.m03 + m21*p.m13 + m22*p.m23 + m23*p.m33,

            m30*p.m00 + m31*p.m10 + m32*p.m20 + m33*p.m30,
            m30*p.m01 + m31*p.m11 + m32*p.m21 + m33*p.m31,
            m30*p.m02 + m31*p.m12 + m32*p.m22 + m33*p.m32,
            m30*p.m03 + m31*p.m13 + m32*p.m23 + m33*p.m33
        );
    }

    /**
     * normalizes matrix by d and returns result in m
     * @return a normalized the matrix by d
     */
    public Matrix4 normalize(double d, Matrix4 m) {
        if (m == null) m = new Matrix4();
        return m.set(
            m00/d, m01/d, m02/d, m03/d,
            m10/d, m11/d, m12/d, m13/d,
            m20/d, m21/d, m22/d, m23/d,
            m30/d, m31/d, m32/d, m33/d
        );
    }

    /**
     * @return the determinant of the matrix
     */
    public double determinant() {
       return
            (m00*m11 - m01*m10)*(m22*m33 - m23*m32)
           -(m00*m12 - m02*m10)*(m21*m33 - m23*m31)
           +(m00*m13 - m03*m10)*(m21*m32 - m22*m31)
           +(m01*m12 - m02*m11)*(m20*m33 - m23*m30)
           -(m01*m13 - m03*m11)*(m20*m32 - m22*m30)
           +(m02*m13 - m03*m12)*(m20*m31 - m21*m30);
    }

    /**
     * inverts matrix and returns result in m
     * @return an inverted matrix or return null on failure
     */
    public Matrix4 invert(Matrix4 m) {
        if (m == null) m = new Matrix4();
        double d = determinant();
        if (Math.abs(d) < EPS) {
            m = null;
            return null;
        }
        m.set(
            m11*(m22*m33 - m23*m32) + m12*(m23*m31 - m21*m33) + m13*(m21*m32 - m22*m31),
            m21*(m02*m33 - m03*m32) + m22*(m03*m31 - m01*m33) + m23*(m01*m32 - m02*m31),
            m31*(m02*m13 - m03*m12) + m32*(m03*m11 - m01*m13) + m33*(m01*m12 - m02*m11),
            m01*(m13*m22 - m12*m23) + m02*(m11*m23 - m13*m21) + m03*(m12*m21 - m11*m22),

            m12*(m20*m33 - m23*m30) + m13*(m22*m30 - m20*m32) + m10*(m23*m32 - m22*m33),
            m22*(m00*m33 - m03*m30) + m23*(m02*m30 - m00*m32) + m20*(m03*m32 - m02*m33),
            m32*(m00*m13 - m03*m10) + m33*(m02*m10 - m00*m12) + m30*(m03*m12 - m02*m13),
            m02*(m13*m20 - m10*m23) + m03*(m10*m22 - m12*m20) + m00*(m12*m23 - m13*m22),

            m13*(m20*m31 - m21*m30) + m10*(m21*m33 - m23*m31) + m11*(m23*m30 - m20*m33),
            m23*(m00*m31 - m01*m30) + m20*(m01*m33 - m03*m31) + m21*(m03*m30 - m00*m33),
            m33*(m00*m11 - m01*m10) + m30*(m01*m13 - m03*m11) + m31*(m03*m10 - m00*m13),
            m03*(m11*m20 - m10*m21) + m00*(m13*m21 - m11*m23) + m01*(m10*m23 - m13*m20),

            m10*(m22*m31 - m21*m32) + m11*(m20*m32 - m22*m30) + m12*(m21*m30 - m20*m31),
            m20*(m02*m31 - m01*m32) + m21*(m00*m32 - m02*m30) + m22*(m01*m30 - m00*m31),
            m30*(m02*m11 - m01*m12) + m31*(m00*m12 - m02*m10) + m32*(m01*m10 - m00*m11),
            m00*(m11*m22 - m12*m21) + m01*(m12*m20 - m10*m22) + m02*(m10*m21 - m11*m20)
        );
        m.normalize(d, m);
        return m;
    }

    /**
     * transposes matrix and returns result in m
     * @return a transpose of the matrix
     */
    public Matrix4 transpose(Matrix4 m) {
        if (m == null) m = new Matrix4();
        return m.set(
            m00, m10, m20, m30,
            m01, m11, m21, m31,
            m02, m12, m22, m32,
            m03, m13, m23, m33
        );
    }

    /**
     * negates matrix and returns result in m
     * @return a negate of the matrix
     */
    public Matrix4 negate(Matrix4 m) {
        if (m == null) m = new Matrix4();
        return m.set(
            -m00, -m01, -m02, -m03,
            -m10, -m11, -m12, -m13,
            -m20, -m21, -m22, -m23,
            -m30, -m31, -m32, -m33
        );
    }

    /**
     * converts matrix into d, a 4x4 array
     * @return matrix in a two dimensional array
     */
    public double[][] toArray(double[][] d) {
        if (d == null) d = new double[4][4];
        d[0][0] = m00; d[0][1] = m01; d[0][2] = m02; d[0][3] = m03;
        d[1][0] = m10; d[1][1] = m11; d[1][2] = m12; d[1][3] = m13;
        d[2][0] = m20; d[2][1] = m21; d[2][2] = m22; d[2][3] = m23;
        d[3][0] = m30; d[3][1] = m31; d[3][2] = m32; d[3][3] = m33;
        return d;
    }

    /**
     * converts matrix into d, a 16 entry array
     * @return matrix in a one dimensional array, column by column
     */
    public double[] toArray1D(double[] d) {
        if (d == null) d = new double[16];
        d[0] = m00; d[4] = m01; d[ 8] = m02; d[12] = m03;
        d[1] = m10; d[5] = m11; d[ 9] = m12; d[13] = m13;
        d[2] = m20; d[6] = m21; d[10] = m22; d[14] = m23;
        d[3] = m30; d[7] = m31; d[11] = m32; d[15] = m33;
        return d;
    }

    /**
     * @return true if matrix is equal to object
     */
    public boolean equals(Object object) {
        if ((object != null) && (object instanceof Matrix4)) {
            Matrix4 c = (Matrix4)object;
            return c.m00 == m00 && c.m01 == m01 && c.m02 == m02 && c.m03 == m03
                && c.m10 == m10 && c.m11 == m11 && c.m12 == m12 && c.m13 == m13
                && c.m20 == m20 && c.m21 == m21 && c.m22 == m22 && c.m23 == m23
                && c.m30 == m30 && c.m31 == m31 && c.m32 == m32 && c.m33 == m33;
        }
        return false;
    }

    /**
     * @return hashcode for this matrix
     */
    public int hashCode() {
        long l = 1L;
        l = 31L * l + Double.doubleToLongBits(m00);
        l = 31L * l + Double.doubleToLongBits(m01);
        l = 31L * l + Double.doubleToLongBits(m02);
        l = 31L * l + Double.doubleToLongBits(m03);
        l = 31L * l + Double.doubleToLongBits(m10);
        l = 31L * l + Double.doubleToLongBits(m11);
        l = 31L * l + Double.doubleToLongBits(m12);
        l = 31L * l + Double.doubleToLongBits(m13);
        l = 31L * l + Double.doubleToLongBits(m20);
        l = 31L * l + Double.doubleToLongBits(m21);
        l = 31L * l + Double.doubleToLongBits(m22);
        l = 31L * l + Double.doubleToLongBits(m23);
        l = 31L * l + Double.doubleToLongBits(m30);
        l = 31L * l + Double.doubleToLongBits(m31);
        l = 31L * l + Double.doubleToLongBits(m32);
        l = 31L * l + Double.doubleToLongBits(m33);
        return (int)(l ^ l >> 32);
    }

    /**
     * @return string representation of this matrix
     */
    public String toString() {
        return m00 + ", " + m01 + ", " + m02 + ", "+ m03 + "\n" +
               m10 + ", " + m11 + ", " + m12 + ", "+ m13 + "\n" +
               m20 + ", " + m21 + ", " + m22 + ", "+ m23 + "\n" +
               m30 + ", " + m31 + ", " + m32 + ", "+ m33 + "\n";
    }
}
