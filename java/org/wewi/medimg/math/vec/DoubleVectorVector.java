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
public final class DoubleVectorVector {
    
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
            int p = pos*2*dim;
            System.arraycopy(data, p, start, 0, dim);
            System.arraycopy(data, p + dim, end, 0, dim);
            pos++;
		}
    }
    
	private double[] data;
	private int vectorCount = 0;

    private final int dim = 3;
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
        
        data = new double[initialCapacity*2*dim];
	}

	public void add(double[] start, double[] end) {
        ensureCapacity(vectorCount + 1);
        
        int pos = vectorCount*2*dim;
        
        System.arraycopy(start, 0, data, pos, dim);
        System.arraycopy(end, 0, data, pos + dim, dim);

        vectorCount++;
	}

	private void ensureCapacity(int minCapacity) {
		int oldCapacity = data.length/(dim*2);
        
		if (minCapacity > oldCapacity) {
			double oldData[] = data;
			int newCapacity = (capacityIncrement > 0)
					                  ? (oldCapacity + capacityIncrement)
					                  : (oldCapacity*2);
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			data = new double[newCapacity*2*dim];
			System.arraycopy(oldData, 0, data, 0, vectorCount*dim*2);
		}
	}
    
    public void addElementAt(int index, double[] start, double[] end) {
        if (index >= vectorCount + 1) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount+1));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        
        ensureCapacity(vectorCount + 1);
        
        System.arraycopy(data, index*2*dim, data, (index + 1)*2*dim, (vectorCount - index)*2*dim);
        System.arraycopy(start, 0, data, index*2*dim, dim);
        System.arraycopy(end, 0, data, index*2*dim + dim, dim);
        
        vectorCount++;
    }
    
    public void set(int index, double[] start, double[] end) {
        if (index >= vectorCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        } 
        
        int pos = index*2*dim;
        System.arraycopy(start, 0, data, pos, dim);
        System.arraycopy(end, 0,data, pos + dim, dim);       
    }
    
    public void removeElementAt(int index) {
        if (index >= vectorCount + 1) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount+1));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        
        int j = vectorCount - index - 1;
        if (j > 0) {
            System.arraycopy(data, (index + 1)*2*dim, data, index*2*dim, j*2*dim);
        }
        
        vectorCount--;                
    }
    
    public void remove(int index, double[] start, double[] end) {
        if (index >= vectorCount + 1) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + (vectorCount+1));
        } else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        
        int pos = index*2*dim;
        System.arraycopy(data, pos, start, 0, dim);
        System.arraycopy(data, pos + dim, end, 0, dim);
        
        int j = vectorCount - index - 1;
        if (j > 0) {
            System.arraycopy(data, (index + 1)*2*dim, data, index*2*dim, j*2*dim);
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
        int length = vectorCount*2*dim;
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


















