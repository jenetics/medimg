/**
 * ImageData.java
 *
 * Created on 5. Dezember 2001, 14:45
 */

package org.wewi.medimg.image;

import java.util.Arrays;
import java.util.RandomAccess;

import org.wewi.medimg.util.Timer;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.2
 */
public class ImageData implements Image, RandomAccess { 
    
    private final class ImageDataVoxelIterator implements VoxelIterator {
        private int pos;
        
        public ImageDataVoxelIterator() {
            pos = 0;
        }
        
        public boolean hasNext() {
            return pos < size;
        }
        
        public int next() {
            return getData(pos++);
        }
        
        public int next(int[] p) {
            int c = getData(pos);
            getCoordinates(pos, p);
            ++pos;
            return c;
        }
        
        public int size() {
            return size;
        }
        
        public Object clone() {
            return new ImageDataVoxelIterator();
        }
    } 
     
    /* 
    final static class CoordinatesToPosition {
        private Dimension dim;
        private int minX, minY, minZ;
        private int sizeX, sizeXY;
        
        public CoordinatesToPosition(Dimension dim) {
            this.dim = dim; 
            minX = dim.getMinX();
            minY = dim.getMinY();
            minZ = dim.getMinZ();
            sizeX = dim.getMaxX()-dim.getMinX()+1;
            sizeXY = sizeX*(dim.getMaxY()-dim.getMinY()+1);   
        }
        
        public int getPosition(int x, int y, int z) {
            return sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX);
        } 
        
        public void getCoordinates(int pos, int[] coordinate) {
            coordinate[2] = pos / (sizeXY);
            pos = pos - (coordinate[2] * sizeXY);
            coordinate[1] = pos / (sizeX);
            pos = pos - (coordinate[1] * sizeX);
            coordinate[0] = pos; 
            
            coordinate[0] += minX;
            coordinate[1] += minY;
            coordinate[2] += minZ;                
        }           
    }
    */
    /**************************************************************************/
    private int[] data;
    
    
    private int minColor = Integer.MIN_VALUE;
    private int maxColor = Integer.MAX_VALUE;
    private ColorRange colorRange = null;
    protected int maxX, maxY, maxZ;
    protected int minX, minY, minZ;
    protected int sizeX, sizeY, sizeZ;
    protected Dimension dimension;
    private int sizeXY;
    protected int size;
    
    private ImageDataHeader header;
    private ColorConversion colorConversion;
    
    ImageData() {
    	colorConversion = new GreyColorConversion();
    }
    
        
    ImageData(ImageData id) {
        this(id.maxX + 1, id.maxY + 1, id.maxZ + 1);
        System.arraycopy(id.data, 0, data, 0, size);
    }
    
    public ImageData(int sizeX, int sizeY, int sizeZ) {
        init(0, 0, 0, sizeX-1, sizeY-1, sizeZ-1);
    }
    
    public ImageData(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        init(minX, minY, minZ, maxX, maxY, maxZ);
    }
    
    void init(Dimension dim) {
        init(dim.getMinX(), dim.getMinY(), dim.getMinZ(),
             dim.getMaxX(), dim.getMaxY(), dim.getMaxZ());    
    }
    
    void init(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
        sizeX = maxX - minX + 1;
        sizeY = maxY - minY + 1;
        sizeZ = maxZ - minZ + 1;
        size = sizeX*sizeY*sizeZ;
        sizeXY = sizeX*sizeY;        
        dimension = new Dimension(minX, maxX, minY, maxY, minZ, maxZ);
        header = new ImageDataHeader(this);
        colorConversion = new GreyColorConversion();        
        
        initData();
    }    
    
    protected void initData() {
        data = new int[size]; 
        Arrays.fill(data, (int)0);        
    }
    
    protected int getData(int pos) {
        return data[pos];    
    }
    
    protected void setData(int pos, int color) {
        data[pos] = color;    
    }    
   
    public int getColor(int x, int y, int z) {
        return getData(sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX));
    }
    
    public int getColor(int pos) {
        return getData(pos);
    }
    
    public void setColor(int x, int y, int z, int color) {
        setData(sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX), color);
    }
    
    public void setColor(int pos, int color) {
        setData(pos, color);
    } 

    public void resetColor(int color) {
        Arrays.fill(data, color);
    }  
    
    protected void updateColorRange() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (min > data[i]) {
                min = data[i];
            } else if (max < data[i]) {
                max = data[i];
            }
        }
        minColor = min;
        maxColor = max;
        colorRange = new ColorRange(minColor, maxColor);
    }
    
    public ColorRange getColorRange() {
        if (colorRange == null) {
            updateColorRange();
        }
        return colorRange;
    }
    
    public int getMaxColor() {
        return maxColor;
    }
    
    public int getMinColor() {
        return minColor;
    }    
    
    public int getMaxX() {
        return maxX;
    }
    
    public int getMaxY() {
        return maxY;
    }
    
    public int getMaxZ() {
        return maxZ;
    }
    
    public int getMinX() {
        return minX;
    }
    
    public int getMinY() {
        return minY;
    }
    
    public int getMinZ() {
        return minZ;
    }   
    
    public Dimension getDimension() {
    	return dimension;	
    } 
    
    public int getNVoxels() {
        return size;
    }
    
    public boolean isNull() {
        return false;
    }
    
    public Object clone() {
        return new ImageData(this);
    }
    
    public synchronized int getPosition(int x, int y, int z) {
        return (sizeXY*(z-minZ) + sizeX*(y-minY) + (x-minX));
    }
    
    public synchronized int[] getCoordinates(int pos) {
        int[] erg = new int[3];
        getCoordinates(pos, erg);
        return erg;
    } 
    
    
    public synchronized void getCoordinates(int pos, int[] coordinate) {
        coordinate[2] = pos / (sizeXY);
        pos = pos - (coordinate[2] * sizeXY);
        coordinate[1] = pos / (sizeX);
        pos = pos - (coordinate[1] * sizeX);
        coordinate[0] = pos; 
        
        coordinate[0] += minX;
        coordinate[1] += minY;
        coordinate[2] += minZ;
    } 
    
    public synchronized void getNeighbor3D12Positions(int pos, int[] n12) {
        n12[0] = pos - 1 - sizeXY;
        n12[1] = pos - 1 + sizeXY;
        n12[2] = pos - 1 - sizeX;
        n12[3] = pos - 1 + sizeX;
        n12[4] = pos + 1 - sizeXY;
        n12[5] = pos + 1 + sizeXY;
        n12[6] = pos + 1 - sizeX;
        n12[7] = pos + 1 + sizeX;
        n12[8] = pos - sizeX - sizeXY;
        n12[9] = pos - sizeX + sizeXY;
        n12[10] = pos + sizeX - sizeXY;
        n12[11] = pos + sizeX + sizeXY;         
    }
    
    public synchronized void getNeighbor3D18Positions(int pos, int[] n18) {
        n18[0] = pos - 1;
        n18[1] = pos + 1;
        n18[2] = pos - sizeX;
        n18[3] = pos + sizeX;
        n18[4] = pos - sizeXY;
        n18[5] = pos + sizeXY;  
        n18[6] = pos - 1 - sizeXY;
        n18[7] = pos - 1 + sizeXY;
        n18[8] = pos - 1 - sizeX;
        n18[9] = pos - 1 + sizeX;
        n18[10] = pos + 1 - sizeXY;
        n18[11] = pos + 1 + sizeXY;
        n18[12] = pos + 1 - sizeX;
        n18[13] = pos + 1 + sizeX;
        n18[14] = pos - sizeX - sizeXY;
        n18[15] = pos - sizeX + sizeXY;
        n18[16] = pos + sizeX - sizeXY;
        n18[17] = pos + sizeX + sizeXY;         
    }
    
    public synchronized void getNeighbor3D6Positions(int pos, int[] n6) {
        n6[0] = pos - 1;
        n6[1] = pos + 1;
        n6[2] = pos - sizeX;
        n6[3] = pos + sizeX;
        n6[4] = pos - sizeXY;
        n6[5] = pos + sizeXY;         
    }    
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("ImageData:\n    ");
        buffer.append("(").append(minX);
        buffer.append(",").append(minY);
        buffer.append(",").append(minZ);
        buffer.append("),(").append(maxX);
        buffer.append(",").append(maxY);
        buffer.append(",").append(maxZ).append(")");
        return buffer.toString();
    }
    
    public ImageHeader getHeader() {
        return header;
    }   
    
    public ColorConversion getColorConversion() {
    	return colorConversion;	
    }
    
    public void setColorConversion(ColorConversion cc) {
    	colorConversion = cc;	
    }

    public VoxelIterator getVoxelIterator() {
        return new ImageDataVoxelIterator();
    }  
    
    
    
    
    public static void main(String[] args) {
        Timer timer = new Timer("ImageData");
        
        Image image = new ImageData(350, 350, 350); 
         
        
        timer.start();
        for (int i = 0; i < image.getNVoxels(); i++) {
            image.getColor(i);    
        }
        timer.stop();
        timer.print();
        
        
        timer.start();
        for (int i = 0, n = image.getNVoxels(); i < n; i++) {
            image.getColor(i);    
        }
        timer.stop();
        timer.print();        
        
        timer.start();
        for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
            it.next();    
        }
        timer.stop();
        timer.print();     
        
        timer.start();
        int[] p = new int[3];
        for (VoxelIterator it = image.getVoxelIterator(); it.hasNext();) {
            it.next(p);    
        }
        timer.stop();
        timer.print();            
    }  
    
}






























