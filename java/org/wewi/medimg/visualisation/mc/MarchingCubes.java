/*
 * MarchingCubes.java
 *
 * Created on 20. März 2002, 14:32
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

import org.wewi.medimg.image.*;
import org.wewi.medimg.image.io.*;
import org.wewi.medimg.seg.*;
import org.wewi.medimg.seg.statistic.*;
import java.io.*;


/*

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
 */

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class MarchingCubes  {                        
    private CubeIterator cubeIterator;

    /** Creates new MarchingCubes */
    public MarchingCubes(CubeIterator ci) {
        cubeIterator = ci;
    }
    
    protected TriangleArray compactation(TriangleArray ta) {
        return ta;
    }
    
    public TriangleArray march() {
        TriangleArray ta = new TriangleArray();
        TriangleFactory tf = new TriangleFactory();
        
        Cube cube;
        while (cubeIterator.hasNext()) {
            cube = (Cube)cubeIterator.next();
            for (Iterator it = tf.createTriangles(cube); it.hasNext();) {
                ta.add((Triangle)it.next());
            }
        }
        ta = compactation(ta);        
        
        return ta;
    }
    
    
    
    
    /*
    
    
    
    private SimpleUniverse u = null;
    
    public BranchGroup createSceneGraph(javax.media.j3d.TriangleArray triangleArray) {
	// Create the root of the branch graph
	BranchGroup objRoot = new BranchGroup();

	// Create the TransformGroup node and initialize it to the
	// identity. Enable the TRANSFORM_WRITE capability so that
	// our behavior code can modify it at run time. Add it to
	// the root of the subgraph.
	TransformGroup objTrans = new TransformGroup();
	objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
	objRoot.addChild(objTrans);

	// Create a simple Shape3D node; add it to the scene graph.
	objTrans.addChild(new ColorCube(0.4));
        //objTrans.addChild(triangleArray);

	// Create a new Behavior object that will perform the
	// desired operation on the specified transform and add
	// it into the scene graph.
	Transform3D yAxis = new Transform3D();
	Alpha rotationAlpha = new Alpha(-1, 4000);

	RotationInterpolator rotator =
	    new RotationInterpolator(rotationAlpha, objTrans, yAxis,
				     0.0f, (float) Math.PI*2.0f);
	BoundingSphere bounds =
	    new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	rotator.setSchedulingBounds(bounds);
	objRoot.addChild(rotator);

        // Have Java 3D perform optimizations on this scene graph.
        objRoot.compile();

	return objRoot;
    }    
    */
    
    
    public static void main(String[] args) {
        ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                            new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
        try {
            reader.read();
        } catch (IOException ioe) {
            System.out.println("Fehler: " + ioe);
        }
        Image image = reader.getImage();
        
        ImageSegmentationStrategy iss = new MLSegmentation(image, 4);
        iss.doSegmentation();
        
        FeatureImage fi = iss.getFeatureImage();
        
        CubeIterator cit = new CubeIterator(fi);
        MarchingCubes mc = new MarchingCubes(cit);
        TriangleArray ta = mc.march();
        
        //javax.media.j3d.TriangleArray triangleArray = new javax.media.j3d.TriangleArray(3*ta.size(), 0);
        float[] coord = new float[9];
        Triangle tri;
        Point A, B, C;
        int counter = 0;
        for (Iterator it = ta.iterator(); it.hasNext();) {
            tri = (Triangle)it.next();
            A = tri.getA();
            B = tri.getB();
            C = tri.getC();
            coord[0] = A.getX();
            coord[1] = A.getY();
            coord[2] = A.getZ();
            coord[3] = B.getX();
            coord[4] = B.getY();
            coord[5] = B.getZ();
            coord[6] = C.getX();
            coord[7] = C.getY();
            coord[8] = C.getZ();
            
            //triangleArray.setCoordinates(counter, coord);
            ++counter;
        }
    }
    
    
}





