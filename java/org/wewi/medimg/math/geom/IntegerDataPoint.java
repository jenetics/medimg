/**
 * IntegerDataPoint.java
 *
 * Created on 5. Februar 2002, 13:51
 */

package org.wewi.medimg.math.geom;

import java.util.Arrays;

/**
 * Diese Klasse ist eine konkrete Erweiterung eines Datenpunktes.
 * Aufgenommen werden Integerwerte bzw. Integervektoren.
 * Diese Klasse ist "immutable", d.h. der Integervektor der dem
 * Konstruktor beim Erzeugen mitgegeben wird, wird in den internen
 * Integervektor KOPIERT. Desgleichen wird beim Aufruf der 
 * <pre> getIntegerPoint() </pre> Methode eine Kopie des Integervektors
 * erzeugt und nach Außen gegeben.
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public final class IntegerDataPoint implements DataPoint {

	private class IntegerDataPointNumber extends Number {
		private int number;

		public IntegerDataPointNumber(int number) {
			this.number = number;
		}

		public double doubleValue() {
			return (double) number;
		}

		public float floatValue() {
			return (float) number;
		}

		public int intValue() {
			return number;
		}

		public long longValue() {
			return number;
		}

	}

	private int[] point;
	private static IntegerDataPoint NULL = null;
	private static IntegerDataPoint ONE = null;

	public IntegerDataPoint(int p) {
		point = new int[1];
		point[0] = p;
	}

	public IntegerDataPoint(int[] p) {
		point = new int[p.length];
		for (int i = 0; i < point.length; i++) {
			point[i] = p[i];
		}
	}

	private IntegerDataPoint(IntegerDataPoint p) {
		this(p.point);
	}

	public double distance(DataPoint p) {
		IntegerDataPoint ip = (IntegerDataPoint) p;
		double dist = 0;
		double diff = 0;
		for (int i = 0; i < point.length; i++) {
			diff = (ip.point[i] - point[i]);
			diff *= diff;
			dist += diff;
		}
		dist = Math.sqrt(dist);
		return dist;
	}

	public double norm() {
		double n = 0;
		for (int i = 0; i < point.length; i++) {
			n += point[i] * point[i];
		}
		n = Math.sqrt(n);
		return n;
	}

	public int[] getValue() {
		int[] ret = new int[point.length];
		for (int i = 0; i < point.length; i++) {
			ret[i] = point[i];
		}
		return ret;
	}

	public DataPoint add(DataPoint p) {
		IntegerDataPoint idp = (IntegerDataPoint) p;
		int[] temp = new int[point.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = idp.point[i] + point[i];
		}

		return new IntegerDataPoint(temp);
	}

	public DataPoint sub(DataPoint p) {
		IntegerDataPoint idp = (IntegerDataPoint) p;
		int[] temp = new int[point.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = point[i] - idp.point[i];
		}

		return new IntegerDataPoint(temp);
	}

	public DataPoint div(double d) {
		int[] temp = new int[point.length];
		for (int i = 0; i < point.length; i++) {
			temp[i] = (int) Math.rint(((double) point[i] / d));
		}

		return new IntegerDataPoint(temp);
	}

	public Number getOrdinateNumber(int dim) {
		return new Integer(point[dim]);
	}

	public int getDimension() {
		return point.length;
	}

	public DataPoint getNullInstance() {
		if (NULL == null) {
			IntegerDataPoint NULL = new IntegerDataPoint(this);
			Arrays.fill(NULL.point, 0);
		}
		return NULL;
	}

	public DataPoint getOneInstance() {
		if (ONE == null) {
			IntegerDataPoint point = new IntegerDataPoint(this);
			Arrays.fill(NULL.point, 1);
		}
		return NULL;
	}

	public boolean equals(Object p) {
		if (p == this) {
			return true;
		}
		if (!(p instanceof IntegerDataPoint)) {
			return false;
		}
		IntegerDataPoint idp = (IntegerDataPoint) p;
		if (idp.point.length != point.length) {
			return false;
		}
		for (int i = 0; i < point.length; i++) {
			if (idp.point[i] != point[i]) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		for (int i = 0; i < point.length; i++) {
			result = result * 37 + point[i];
		}
		return result;
	}

	public Object clone() {
		return new IntegerDataPoint(this);
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{");
		for (int i = 0; i < point.length; i++) {
			buffer.append(point[i]);
			if (i < point.length - 1) {
				buffer.append(",");
			}
		}
		buffer.append("}");
		return buffer.toString();
	}

	public String toMathematicaString() {
		return toString();
	}

}
