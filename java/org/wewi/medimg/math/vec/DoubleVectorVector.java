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
 * DoubleVectorVector.java
 * 
 * Created on 12.03.2003, 08:44:54
 *
 */
package org.wewi.medimg.math.vec;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
final class DoubleVectorVector {
    
    private class DoubleVectorIterator implements VectorIterator {
        private int pos = 0;
        
        /**
         * @see org.wewi.medimg.math.vec.VectorIterator#hasNext()
         */
        public boolean hasNext() {
            return pos < vectorCount;
        }
        /**
         * @see org.wewi.medimg.math.vec.VectorIterator#next(double[], double[])
         */
        public void next(double[] start, double[] end) {
            int p = pos*2*DIM;
            System.arraycopy(data, p, start, 0, DIM);
            System.arraycopy(data, p + DIM, end, 0, DIM);
            pos++;
        }
    }
    
    private double[] data;
    private int vectorCount = 0;

    private final int DIM = 3;
    private int initialCapacity = 8;
    private int capacityIncrement = 0;

    /**
     * Constructor for DoubleVectorVector.
     */
    public DoubleVectorVector() {
    }

    public DoubleVectorVector(int initialCapacity) {
        this(initialCapacity, 0);
    }

    public DoubleVectorVector(int initialCapacity, int capacityIncrement) {
        this.initialCapacity = initialCapacity;
        this.capacityIncrement = capacityIncrement;
        
        data = new double[initialCapacity*2*DIM];
    }

    public void add(double[] start, double[] end) {
        ensureCapacity(vectorCount + 1);
        
        int pos = vectorCount*2*DIM;
        
        System.arraycopy(start, 0, data, pos, DIM);
        System.arraycopy(end, 0, data, pos + DIM, DIM);

        vectorCount++;
    }

    private void ensureCapacity(int minCapacity) {
        int oldCapacity = data.length/(DIM*2);
        
        if (minCapacity > oldCapacity) {
            double oldData[] = data;
            int newCapacity = (capacityIncrement > 0)
                                      ? (oldCapacity + capacityIncrement)
                                      : (oldCapacity*2);
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            data = new double[newCapacity*2*DIM];
            System.arraycopy(oldData, 0, data, 0, vectorCount*DIM*2);
        }
    }
    
    public void addElementAt(int index, double[] start, double[] end) {
        if (index >= vectorCount + 1) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount+1));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        
        ensureCapacity(vectorCount + 1);
        
        System.arraycopy(data, index*2*DIM, data, (index + 1)*2*DIM, (vectorCount - index)*2*DIM);
        System.arraycopy(start, 0, data, index*2*DIM, DIM);
        System.arraycopy(end, 0, data, index*2*DIM + DIM, DIM);
        
        vectorCount++;
    }
    
    public void set(int index, double[] start, double[] end) {
        if (index >= vectorCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        } 
        
        int pos = index*2*DIM;
        System.arraycopy(start, 0, data, pos, DIM);
        System.arraycopy(end, 0,data, pos + DIM, DIM);       
    }
    
    public void get(int index, double[] start, double[] end) {
    	if (index >= vectorCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        } 
        
        int pos = index*2*DIM;
        System.arraycopy(data, pos, start, 0, DIM);
        System.arraycopy(data, pos + DIM, end, 0, DIM); 
    }
    
    public void removeElementAt(int index) {
        if (index >= vectorCount + 1) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount+1));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        
        int j = vectorCount - index - 1;
        if (j > 0) {
            System.arraycopy(data, (index + 1)*2*DIM, data, index*2*DIM, j*2*DIM);
        }
        
        vectorCount--;                
    }
    
    public void remove(int index, double[] start, double[] end) {
        if (index >= vectorCount + 1) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount+1));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        
        int pos = index*2*DIM;
        System.arraycopy(data, pos, start, 0, DIM);
        System.arraycopy(data, pos + DIM, end, 0, DIM);
        
        int j = vectorCount - index - 1;
        if (j > 0) {
            System.arraycopy(data, (index + 1)*2*DIM, data, index*2*DIM, j*2*DIM);
        }
        
        vectorCount--;         
    }
    
    public VectorIterator iterator() {
        return new DoubleVectorIterator();
    }
    
    public int size() {
        return vectorCount;
    }

    /**
     * Removes all vetors from the vector and trims the vector
     * to the initial capacity.
     */
    public void clear() {
        data = new double[initialCapacity];
        vectorCount = 0;
    }
    
    /**
     * Removes all vector form the vector, but don't trims the 
     * capacity of the vector.
     */
    public void removeAllElements() {
        vectorCount = 0;
    }
    
    public void trimToSize() {
        int length = vectorCount*2*DIM;
        double[] oldData = data;
        data = new double[length];
        System.arraycopy(oldData, 0, data, 0, length);
    }


/*
    public static void main(String[] args) {
        DoubleVectorVector v = new DoubleVectorVector(2);
        
        double[] start = new double[3];
        double[] end = new double[3];
        for (int i = 0; i <= 10; i++) {
            java.util.Arrays.fill(start, i);
            java.util.Arrays.fill(end, 2*i);
            
            v.addElementAt(i, start, end);    
        }
        
        for (VectorIterator it = v.iterator(); it.hasNext();) {
            it.next(start, end);
            
            System.out.print("(");
            for (int i = 0; i < start.length; i++) {
                System.out.print(start[i]);
                System.out.print(",");
            }
            System.out.print(")");
            
            System.out.print("(");
            for (int i = 0; i < start.length; i++) {
                System.out.print(end[i]);
                System.out.print(",");
            }
            System.out.print(")\n");            
        }
    }
*/

}


















