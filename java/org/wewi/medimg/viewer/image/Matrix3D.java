/* Java Source File
a part of SliceViewer
  a program written in 2/98 by Orion Lawlor.
Public domain source code.

Send questions to fsosl@uaf.edu
*/
/*The Matrix3D class is my heavily modified version of
Nicholas Wilt's C++ Matrix class, from his Object Oriented Ray Tracer.
I began using his code back before I had taken Linear Algebra, and
didn't yet know the ways of C++.

The Matrix3D class defines a general 3x4 matrix.  This is a non-square
matrix so translation can be represented without using homogenous coordinates.
Matrix multiplication must then be slightly redefined, but quite similar to
regular matrix multiplication.

The real use of the Matrix3D is to transform vectors, using the
transformVector() call.  The other matrix creation routines should
be self-explanatory.
*/


/*
Entry points:
	public double e[][];
	public Matrix3D()
	public Matrix3D dup()
	public Matrix3D invert()
	public void transformVector(Vector in,Vector out)
	public Matrix3D postMultBy(Matrix3D b)
	public static Matrix3D translationMatrix(double tX,double tY,double tZ)
	public static Matrix3D scaleMatrix(double scaleX,double scaleY,double scaleZ)
	public static Matrix3D rotationXMatrix(double angle)
	public static Matrix3D rotationYMatrix(double angle)
	public static Matrix3D rotationZMatrix(double angle)
*/

package org.wewi.medimg.viewer.image;

class Matrix3D {
    public double e[][];

    public Matrix3D() {
        e = new double[4][];
        e[0] = new double[3];
        e[1] = new double[3];
        e[2] = new double[3];
        e[3] = new double[3];
        e[0][0] = 1.0;
        e[1][1] = 1.0;
        e[2][2] = 1.0;
    }

    public Matrix3D dup() {
        Matrix3D ret = new Matrix3D();
        int i,j;
        for (i = 0; i < 3; i++)
            for (j = 0; j < 4; j++)
                ret.e[j][i] = e[j][i];
        return ret;
    }
/*This is an incredibly ugly 3D matrix inversion routine.
	It's just standard Gaussian elimination, but is complicated by
	checks for zero-valued pivots, row-swapping, and too much
	optimization.

	Of course, it's a pretty odd act to invert a non-square matrix!

	You have my apologies-- this should be rewritten soon.*/
    public Matrix3D invert() {
        int i,j,i3,j3;
        double scratch[] = new double[4 * 3],oute[] = new double[4 * 3],temp;
        oute[0 * 3 + 0] = 1.0;
        oute[1 * 3 + 1] = 1.0;
        oute[2 * 3 + 2] = 1.0;
        //copy ourselves into scratch
        for (j = 0; j < 4; j++)
            for (i = 0; i < 3; i++)
                scratch[j * 3 + i] = e[j][i];
        Matrix3D out = new Matrix3D();
        for (i = 0; i < 3; i++) {
            int pivot = i * 3 + i;
            if (scratch[pivot] == 0.0) {
                //if the pivot is zero, then we have to swap rows so that it ain't.
                int rowToSwap = i + 1;
                while ((rowToSwap < 3) && (scratch[rowToSwap * 3 + i] == 0.0))
                    rowToSwap++;
                if (rowToSwap != 3) {
                    int newPivot = i * 3,oldPivot = rowToSwap * 3;
                    for (j = 0; j < 4; j++) {
                        temp = scratch[newPivot];
                        scratch[newPivot] = scratch[oldPivot];
                        scratch[oldPivot] = temp;
                        temp = oute[newPivot];
                        oute[newPivot] = oute[oldPivot];
                        oute[oldPivot] = temp;
                        newPivot++;
                        oldPivot++;
                    }
                }
            }
            double divby = 1 / scratch[pivot],mulby;
            i3 = i * 3;
            if (divby != 1) {
                scratch[i3] *= divby;
                oute[i3] *= divby;
                scratch[i3 + 1] *= divby;
                oute[i3 + 1] *= divby;
                scratch[i3 + 2] *= divby;
                oute[i3 + 2] *= divby;
            }
            for (j = 0; j < 3; j++) {
                j3 = j * 3;
                if ((j != i) && ((mulby = scratch[j3 + i]) != 0)) {
                    scratch[j3 + 0] -= mulby * scratch[i3 + 0];
                    oute[j3 + 0] -= mulby * oute[i3 + 0];
                    scratch[j3 + 1] -= mulby * scratch[i3 + 1];
                    oute[j3 + 1] -= mulby * oute[i3 + 1];
                    scratch[j3 + 2] -= mulby * scratch[i3 + 2];
                    oute[j3 + 2] -= mulby * oute[i3 + 2];
                }
            }
            oute[3 * 3 + i] = 0;
        }
        out.e[3][0] = 0;
        out.e[3][1] = 0;
        out.e[3][2] = 0;
        for (j = 0; j < 3; j++)
            for (i = 0; i < 3; i++) {
                out.e[j][i] = oute[j * 3 + i];
                out.e[3][i] -= oute[j * 3 + i] * e[3][j];
            }
        return out;
    }
//Here's where the rubber meets the road-- transforming vectors.
    public void transformVector(Vector in, Vector out) {
        out.setX(e[3][0] + in.getX() * e[0][0] + in.getY() * e[1][0] + in.getZ() * e[2][0]);
        out.setY(e[3][1] + in.getX() * e[0][1] + in.getY() * e[1][1] + in.getZ() * e[2][1]);
        out.setZ(e[3][2] + in.getX() * e[0][2] + in.getY() * e[1][2] + in.getZ() * e[2][2]);
    }
/*I've defined A.postMultBy(A) to create a matrix which
	is equivalent to first transforming a vector by A, then transforming
	the result by B.*/
    public Matrix3D postMultBy(Matrix3D b) {
        Matrix3D ret = new Matrix3D();
        int x,y;
        for (y = 0; y < 3; y++) {
            for (x = 0; x < 3; x++)
                ret.e[x][y] = e[x][0] * b.e[0][y] + e[x][1] * b.e[1][y] + e[x][2] * b.e[2][y];
            ret.e[3][y] = e[3][0] * b.e[0][y] + e[3][1] * b.e[1][y] + e[3][2] * b.e[2][y] + b.e[3][y];
        }
        return ret;
    }
/*These routines are essentially just
	gussied-up public constructors for the Matrix3D class.*/
    public static Matrix3D translationMatrix(double tX, double tY, double tZ) {
        Matrix3D m = new Matrix3D();
        m.e[3][0] = tX;
        m.e[3][1] = tY;
        m.e[3][2] = tZ;
        return m;
    }

    public static Matrix3D scaleMatrix(double scaleX, double scaleY, double scaleZ) {
        Matrix3D m = new Matrix3D();
        m.e[0][0] = scaleX;
        m.e[1][1] = scaleY;
        m.e[2][2] = scaleZ;
        return m;
    }
/*These routines just create a matrix which rotates angle radians
	about the specified axis.*/
    public static Matrix3D rotationXMatrix(double angle) {
        Matrix3D m = new Matrix3D();
        double cosine = Math.cos(angle);
        double sine = Math.sin(angle);
        m.e[1][1] = cosine;
        m.e[2][1] = -sine;
        m.e[1][2] = sine;
        m.e[2][2] = cosine;
        return m;
    }

    public static Matrix3D rotationYMatrix(double angle) {
        Matrix3D m = new Matrix3D();
        double cosine = Math.cos(angle);
        double sine = Math.sin(angle);
        m.e[0][0] = cosine;
        m.e[2][0] = -sine;
        m.e[0][2] = sine;
        m.e[2][2] = cosine;
        return m;
    }

    public static Matrix3D rotationZMatrix(double angle) {
        Matrix3D m = new Matrix3D();
        double cosine = Math.cos(angle);
        double sine = Math.sin(angle);
        m.e[0][0] = cosine;
        m.e[1][0] = -sine;
        m.e[0][1] = sine;
        m.e[1][1] = cosine;
        return m;
    }
}
