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
 * DoubleVectorField.java
 * 
 * Created on 12.03.2003, 10:54:31
 *
 */
package org.wewi.medimg.math.vec;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class DoubleVectorField implements ArbitraryVectorField {
    private DoubleVectorVector doubleVector;

    /**
     * Constructor for DoubleVectorField.
     */
    public DoubleVectorField() {
        doubleVector = new DoubleVectorVector(10); 
    }
    
    /**
     * @see org.wewi.medimg.math.vec.VectorField#setVector(double[], double[])
     */
    public void addVector(double[] startPoint, double[] endPoint) {
        doubleVector.add(startPoint, endPoint);
    }
    
    /**
     * @see org.wewi.medimg.math.vec.VectorField#getVectorIterator()
     */
    public VectorIterator getVectorIterator() {
        return doubleVector.iterator();
    }


	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#addVector(int, double[], double[])
	 */
	public void addVector(int index, double[] startPoint, double[] endPoint) {
		doubleVector.addElementAt(index, startPoint, endPoint);
	}

	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#changeVector(int, double[], double[])
	 */
	public void changeVector(int index, double[] startPoint, double[] endPoint) {
		doubleVector.set(index, startPoint, endPoint);
	}

	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#getVector(int, double[], double[])
	 */
	public void getVector(int index, double[] startPoint, double[] endPoint) {
		doubleVector.get(index, startPoint, endPoint);
	}

	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#removeVector(int)
	 */
	public void removeVector(int index) {
		doubleVector.removeElementAt(index);
	}

	/**
	 * @see org.wewi.medimg.math.vec.ArbitraryVectorField#size()
	 */
	public int size() {
		return doubleVector.size();
	}

}
