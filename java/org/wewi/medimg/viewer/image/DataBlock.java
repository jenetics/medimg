/*
 * Java Source File
 * a part of SliceViewer
 * a program written in 2/98 by Orion Lawlor.
 * Public domain source code.
 *
 * Send questions to fsosl@uaf.edu
 */


package org.wewi.medimg.viewer.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;

import org.wewi.medimg.image.Image;

/**
 * Data block-- a 3D array of greyscale data points.
 * This is the class at the very center of SliceViewer--
 * it encapsulates the block of data, and does the "slicing".
 * The external entry points are init() and draw().
 *
 * draw() takes as input a matrix which transforms the block
 * of data to screen space, where the slicing plane lies along z=0.
 * The bounding lines of this block are transformed to screen space,
 * intersected with the slicing plane, and used to determine the
 * intersection polygon.  This polygon is rasterized, and then
 * filled with data.  The data is then copied to the back buffer,
 * for eventual screen display.
 */

final class DataBlock implements ImageObserver {
    private Image image;
    
    public DataBlock(Image image) {
        this.image = image;
        nx = image.getDimension().getSizeX();
        ny = image.getDimension().getSizeY();
        nz = image.getDimension().getSizeZ();
        
        double dx = 1, dy = 1, dz = 1;
        
        scale = new Vector(dx, dy, dz);
        size = new Vector(dx * (nx - 1), dy * (ny - 1), dz * (nz - 1));
        setInterpolationMethod(INTERP_NEAREST);        
    }

	/**
     * DataBlock basically ignores image updates.  Some AWT's keep sending them, tho'.
     */
	public boolean imageUpdate(java.awt.Image img, int infoflags, int x, int y, int width, int height) {
		return true;
	}

	//Universal Globals:
	public static final int INTERP_NEAREST = 0;
    public static final int INTERP_TRILINEAR = 1;

	//Private state variables
	private int interpolation = INTERP_NEAREST;
	private Graphics graphics;
    
    /**
     * Number of voxels in the x, y, and z directions. 
     */
	private int nx, ny, nz; 
    /**
     * Vector from bottom left to top right corner of block, in mm.
     */
	public Vector size;
    /**
     * Scale factors along x, y, and z to convert voxel indices
     * into mm distances.  Invariant: size.x=scale.x*(nx-1), same for y,z.
     * One interpretation of the scale Vector is the size of a single voxel,
     * measured in mm.
     */
	public Vector scale;
    /**
     * Intensity data for block-- 0=black, 255=white
     */
	//private byte data[]; 

	/**
     * Public initializing method.
     * 
	 * @param Nx Number of voxels in x-direction.
	 * @param Ny Number of voxels in y-direction.
	 * @param Nz Number of voxels in z-direction.
	 * @param dx Size of voxels (in mm) along x.
	 * @param dy Size of voxels (in mm) along y.
	 * @param dz Size of voxels (in mm) along z.
	 * @param Ndata Array of data: data length must equal nx*ny*nz
	 */
	/*
    public void init(int nx, int ny, int nz, double dx, double dy, double dz, byte ndata[]) {
		this.nx = nx;
		this.ny = ny;
		this.nz = nz;
		scale = new Vector(dx, dy, dz);
		size = new Vector(dx * (nx - 1), dy * (ny - 1), dz * (nz - 1));
		//data = ndata;
		setInterpolationMethod(INTERP_TRILINEAR);
	}
    */
    /**
     * Setting the interpolation method.
     * 
     * @param method
     */
	public void setInterpolationMethod(int method) {
		interpolation = method;
	}




    private ColorModel colorMap = null;
    private byte cacheBackPixels[];
    private int cacheW = -1;
    private int cacheH = -1;
    private Rastergon lastRaster = null; 
    /**
     * Greyscale Buffer:
     * Since the dataBlock consists of greyscale pixels, it's easiest
     * to render into a greyscale buffer.  Hence here we create "backPixels"
     * to render into.
     * 
     * @param width
     * @param height
     * @return byte[]
     */
	private byte[] allocBackPixels(int width, int height) {
		if (cacheW != width || cacheH != height) {
			cacheW = width;
			cacheH = height;
			byte greyRamp[] = new byte[256];
			for (int i = 0; i < 256; i++) {
				greyRamp[i] = (byte) i;
			}
			colorMap = new IndexColorModel(8, 256, greyRamp, greyRamp, greyRamp, -1);
			cacheBackPixels = new byte[width * height];
			lastRaster = null;
		}
        
		return cacheBackPixels;
	}
 
    
      
	private int nPts = 8;
	private Vector pts[] = new Vector[nPts];   //Vertices in block space.
	private Vector txPts[] = new Vector[nPts]; //Vertices in screen space.
    /**
     * Allocate and transform the verticies of the cube.
     * 
     * @param txMatrix
     */
	private void createVertices(Matrix3D txMatrix) {
		for (int i = 0; i < nPts; i++) {
			pts[i] = new Vector((nx - 1) * (i & 0x01),
					             (ny - 1) * (i & 0x02) / 0x02,
					             (nz - 1) * (i & 0x04) / 0x04);
			txPts[i] = new Vector(0, 0, 0);
			txMatrix.transformVector(pts[i], txPts[i]);
		}
	}



	private int nFaces = 6;
	private QuadFace faces[] = new QuadFace[nFaces];
    /**
     * Allocate the face of the cube.
     */
	private void createFaces() {
		faces[0] = new QuadFace(txPts[0], txPts[2], txPts[3], txPts[1]);
		faces[1] = new QuadFace(txPts[0], txPts[1], txPts[5], txPts[4]);
		faces[2] = new QuadFace(txPts[0], txPts[4], txPts[6], txPts[2]);
		faces[3] = new QuadFace(txPts[4], txPts[5], txPts[7], txPts[6]);
		faces[4] = new QuadFace(txPts[1], txPts[3], txPts[7], txPts[5]);
		faces[5] = new QuadFace(txPts[2], txPts[6], txPts[7], txPts[3]);
	}
    

	/**
     * Return the rasterized intersection of this data 
     * cube with the slice plane.
     * 
	 * @param width
	 * @param height
	 * @return Rastergon
	 */
	private Rastergon intersectWithSlice(int width, int height) {
		Rastergon raster = new Rastergon(width, height, 2);
		for (int i = 0; i < 6; i++) {
			//Just intersect each face with the slicing plane...
			Line3D line = faces[i].intersect();
			//...and add this line to the intersection polygon.
			if (line != null)
				raster.addLine(line);
		}
		return raster;
	}

	/**
     * Render the intersection of the cutting plane and data, 
     * copying grey pixels.
     * 
	 * @param txInverse
	 * @param raster
	 * @param w
	 * @param h
	 * @param creator
	 */
	private void renderIntersection(Matrix3D txInverse, Rastergon raster, int w, int h, Component creator) {
		//Make sure our greyscale buffer is allocated.
		byte[] backPixels = allocBackPixels(w, h);
		if (lastRaster == null) {
			lastRaster = raster;
		}

		//Now fill in each line of the rasterized intersection between 
        //the cube and the slicing plane.
		Span spanY = raster.spanY;
		spanY.addSpan(lastRaster.spanY);
		for (int y = spanY.getMin(); y < spanY.getMax(); y++) {
			int pixelsOffset = y * w;
			Span s = raster.spans[y], lastSpan = lastRaster.spans[y];
			int xmin = s.getMin(), xmax = s.getMax();
			int lastxmin = lastSpan.getMin(), lastxmax = lastSpan.getMax();
            
			//Erase first section of previously drawn frame.
			for (int x = lastxmin; x < xmin; x++) {
				backPixels[pixelsOffset + x] = 0;
			}
            
			//Draw line of new data:
			if (xmin < xmax) {
				renderIntersectSpan(xmin, xmax, y, txInverse, backPixels, pixelsOffset);
			}
                    
			//Erase last section of previously drawn frame.
			for (int x = xmax; x < lastxmax; x++) {
				backPixels[pixelsOffset + x] = 0;
			}
		}
        
		//Save the rasterized polygon so we can erase it on the next frame.
		lastRaster = raster;
		//Copy the used portion of the greyscale buffer to screen
		java.awt.Image backImage = creator.createImage(
                                    new MemoryImageSource(w, spanY.length(),
					                                      colorMap, backPixels,
                                                          spanY.getMin()*w, w));
		graphics.drawImage(backImage, 0, spanY.getMin(), w, spanY.length(), this);
        
		//Fill in sections above and below the rendered intersection
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, w, spanY.getMin());
		graphics.fillRect(0, spanY.getMax(), w, h);
	}

	
    /**
     * Draw a string on the string at the specified 3d point.
     * 
     * @param screen
     * @param text
     */
	private void vecString(Vector screen, String text) {
		graphics.drawString(text, (int)screen.getX(), (int)screen.getY());
	}

	/**
     * Draw a 2-d line from one 3D vector to another.
     * 
	 * @param from
	 * @param to
	 */
	private void vecLine(Vector from, Vector to) {
		graphics.drawLine((int) from.getX(), (int) from.getY(), (int) to.getX(), (int) to.getY());
	}
    
	/**
     * Render the dataBlock into the given Graphics.
     * 
	 * @param txMatrix
	 * @param Ng
	 * @param w
	 * @param h
	 * @param creator
	 */
	public void draw(Matrix3D txMatrix, Graphics Ng, int w, int h, Component creator) {
		graphics = Ng;
		Matrix3D txInverse = txMatrix.invert();
        
		//Compute vertex locations; transform vertices.
		createVertices(txMatrix);
        
		//Compute the 6 polygon faces of the box
		createFaces();
        
		//Compute the intersection of the slicing plane with the faces, 
        //filling out a rasterized polygon.
		graphics.setColor(Color.red);
		Rastergon raster = intersectWithSlice(w, h);
        
		//Render (rasterized) intersection in shades of grey.
		renderIntersection(txInverse, raster, w, h, creator);
        
		//Draw X,Y,Z axis lines in dark grey
		graphics.setColor(Color.darkGray);
		vecLine(txPts[0], txPts[1]);
		vecLine(txPts[0], txPts[2]);
		vecLine(txPts[0], txPts[4]);
        
		//Draw axis labels in green if they are above the 
        //slicing plane; grey if below.
		graphics.setColor(txPts[1].getZ() > 0 ? Color.GREEN : Color.DARK_GRAY);
		vecString(txPts[1], "X");
		graphics.setColor(txPts[2].getZ() > 0 ? Color.GREEN : Color.DARK_GRAY);
		vecString(txPts[2], "Y");
		graphics.setColor(txPts[4].getZ() > 0 ? Color.GREEN : Color.DARK_GRAY);
		vecString(txPts[4], "Z");
	}
    
    
    /**
     * Here's where the rubber meets the road:
     * Render a single line of the intersection, copying grey pixels 
     * to the given buffer.
     * 
     * @param xmin
     * @param xmax
     * @param y
     * @param txInverse
     * @param backPixels
     * @param offset
     */
	private void renderIntersectSpan(
		int xmin,
		int xmax,
		int y,
		Matrix3D txInverse,
		byte[] backPixels,
		int offset) {
		int nxy = nx * ny;
        
		//Compute endpoints of this span in screen 3-space
		Vector start = new Vector(0, 0, 0);
        Vector end = new Vector(0, 0, 0);
		Vector left, right;
		left = new Vector(xmin, y, 0);
		right = new Vector(xmax - 1, y, 0);
        
		//Transform the endpoints back to dataBlock space:
		txInverse.transformVector(left, start);
		txInverse.transformVector(right, end);
        
		//Convert endpoints to 16.16 fixed-point format
		double fixScale = 65536.0;
        double invFixScale = 1.0 / fixScale;
		double deltaScale = fixScale / (xmax - 1 - xmin);
		int dx = (int) ((end.getX() - start.getX()) * deltaScale),
			dy = (int) ((end.getY() - start.getY()) * deltaScale),
			dz = (int) ((end.getZ() - start.getZ()) * deltaScale);
		double finiteOffset = (interpolation == INTERP_NEAREST) ? -0.5 : 0;
		int sx = (int) (fixScale * start.getX() + finiteOffset),
			sy = (int) (fixScale * start.getY() + finiteOffset),
			sz = (int) (fixScale * start.getZ() + finiteOffset);
            
		//Step through the 3D data, copying pixels as we go.
		switch (interpolation) {
			case INTERP_NEAREST :
				for (int x = xmin; x < xmax; x++) {
					backPixels[offset + x] =
						(byte)image.getColor((sx >>> 16)
							+ nx * (sy >>> 16)
							+ nxy * (sz >>> 16));
					sx += dx;
					sy += dy;
					sz += dz;
				}
				break;
			case INTERP_TRILINEAR :
				for (int x = xmin; x < xmax; x++) {
					int frac_x = sx & 0xffff,
						frac_y = sy & 0xffff,
						frac_z = sz & 0xffff;
					int datStart = (sx >>> 16) + nx * (sy >>> 16) + nxy * (sz >>> 16);
                    
					//Read in 8 surrounding voxels as unsigned 8-bit values.
					int a = 0xff & ((int)image.getColor(datStart)),
						b = 0xff & ((int)image.getColor(datStart + 1)),
						c = 0xff & ((int)image.getColor(datStart + ny)),
						d = 0xff & ((int)image.getColor(datStart + ny + 1)),
						e = 0xff & ((int)image.getColor(datStart + nxy)),
						f = 0xff & ((int)image.getColor(datStart + nxy + 1)),
						g = 0xff & ((int)image.getColor(datStart + nxy + ny)),
						h = 0xff & ((int)image.getColor(datStart + nxy + ny + 1));
                        
					//Interpolate these voxels in a trilinear fashion
					int ab = a + ((b - a) * frac_x >> 16),
						cd = c + ((d - c) * frac_x >> 16),
						ef = e + ((f - e) * frac_x >> 16),
						gh = g + ((h - g) * frac_x >> 16);
                        
					int abcd = ab + ((cd - ab) * frac_y >> 16),
						efgh = ef + ((gh - ef) * frac_y >> 16);
					backPixels[offset + x] = (byte)(abcd + ((efgh - abcd) * frac_z >> 16));
					sx += dx;
					sy += dy;
					sz += dz;
				}
				break;
		}
	}
}