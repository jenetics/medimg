/**
 * Created on 15.11.2002 11:21:27
 *
 */
package org.wewi.medimg.image.geom;

import org.wewi.medimg.image.AbstractImage;
import org.wewi.medimg.image.Dimension;
import org.wewi.medimg.image.DiscreteData;
import org.wewi.medimg.image.IntData;

/**
 * @author Franz Wilhelmstötter
 * @version 0.1
 */
public class ImageVectorField extends AbstractImage {
    private int[] temp = new int[3];

	/**
	 * Constructor for ImageVectorField.
	 * @param id
	 */
	public ImageVectorField(AbstractImage id) {
		super(id);
	}

	/**
	 * Constructor for ImageVectorField.
	 * @param dim
	 */
	public ImageVectorField(Dimension dim) {
		super(dim);
        initVectorField();
	}

	/**
	 * Constructor for ImageVectorField.
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 * @param minZ
	 * @param maxZ
	 */
	public ImageVectorField(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
		super(minX, maxX, minY, maxY, minZ, maxZ);
        initVectorField();
	}

	/**
	 * Constructor for ImageVectorField.
	 * @param sizeX
	 * @param sizeY
	 * @param sizeZ
	 */
	public ImageVectorField(int sizeX, int sizeY, int sizeZ) {
		super(sizeX, sizeY, sizeZ);
        initVectorField();
	}
    
    private void initVectorField() {
        for (int i = 0, n = getNVoxels(); i < n; i++) {
            setColor(i, i);    
        }    
    }

	/**
	 * @see org.wewi.medimg.image.AbstractImage#createDiscreteData(int)
	 */
	protected DiscreteData createDiscreteData(int size) {
		return new IntData(size);
	}
    
    public void getVector(int pos, int[] vector) {
        getCoordinates(getColor(pos), vector);    
    }
    
    public void getVector(int pos, float[] vector) {
        getCoordinates(getColor(pos), temp);
        
        vector[0] = temp[0];
        vector[1] = temp[1];
        vector[2] = temp[2];    
    }
    
    public void getVector(int x, int y, int z, int[] vector) {
        getCoordinates(getColor(getPosition(x, y, z)), vector);
    }
    
    public void getVector(int x, int y, int z, float[] vector) {
        getCoordinates(getPosition(x, y, z), temp);
        
        vector[0] = temp[0];
        vector[1] = temp[1];
        vector[2] = temp[2];
    }
    
    public void setVector(int pos, int x, int y, int z) {
        setColor(pos, getPosition(x, y, z));
    }
    
    public void setVector(int pos, float x, float y, float z) {
        setColor(pos, getPosition((int)x, (int)y, (int)z));    
    }
    
    public void setVector(int pos, int[] v) {
        setColor(pos, getPosition(v[0], v[1], v[2]));    
    }
    
    public void setVector(int pos, float[] v) {
        setColor(pos, getPosition((int)v[0], (int)v[1], (int)v[2]));    
    }
    
    public void setVector(int[] o, int[] v) {
        setColor(o[0], o[1], o[2], getPosition(v[0], v[1], v[2]));    
    }

	/**
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		return new ImageVectorField(this);
	}

}






















