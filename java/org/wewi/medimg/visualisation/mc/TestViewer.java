/*
 * Viewer3D.java
 *
 * Created on 25. März 2002, 16:34
 */

package org.wewi.medimg.visualisation.mc;

import java.util.Iterator;

import org.wewi.medimg.util.*;
import org.wewi.medimg.image.*;
import org.wewi.medimg.image.io.*;
import org.wewi.medimg.seg.*;
import org.wewi.medimg.seg.statistic.*;
import java.io.*;


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.picking.behaviors.*;
import com.sun.j3d.utils.picking.*;

/**
 *
 * @author  Franz Wilhelmstötter
 * @version 0.1
 */
public class TestViewer extends Applet {
    private SimpleUniverse u = null;
    private Graph graph;
    
    public BranchGroup createSceneGraph(javax.media.j3d.TriangleArray triangleArray, Canvas3D canvas) {
        Appearance app = new Appearance();
        // Set up the polygon attributes
        PolygonAttributes pa = new PolygonAttributes();
        pa.setPolygonMode(pa.POLYGON_LINE);
        pa.setCullFace(pa.CULL_NONE);
        app.setPolygonAttributes(pa);        
        
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        // Create a Transformgroup to scale all objects so they
        // appear in the scene.
        TransformGroup objScale = new TransformGroup();
        Transform3D t3d = new Transform3D();
        t3d.setScale(1);
        //t3d.frustum(-0.5, 0.5, -0.5, 0.5, 0, 1);
        objScale.setTransform(t3d);
        objRoot.addChild(objScale);
        
        double scale = 0.006;
        double xpos = -0.65;
        double ypos = -0.65;
        Shape3D shape = null;
        Geometry geom = triangleArray;
        // Create a transform group node to scale and position the object.
        Transform3D t = new Transform3D();
        t.set(scale, new Vector3d(xpos, ypos, 0.0));
        TransformGroup objTrans = new TransformGroup(t);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        objTrans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        objRoot.addChild(objTrans);
        // Create a second transform group node and initialize it to the
        // identity.  Enable the TRANSFORM_WRITE capability so that
        // our behavior code can modify it at runtime.
        TransformGroup spinTg = new TransformGroup();
        spinTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        spinTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        spinTg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        //shape = new ColorCube(0.4);
        shape = new Shape3D(geom);
        shape.setAppearance(app);
	shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
	shape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
	shape.setCapability(Shape3D.ENABLE_PICK_REPORTING);
	PickTool.setCapabilities(shape, PickTool.INTERSECT_FULL);
	spinTg.addChild(shape);
        objTrans.addChild(spinTg);
        
        BoundingSphere bounds =
                new BoundingSphere(new Point3d(0.0,0.0,0.0), 5000.0);        
        PickRotateBehavior behavior1 = new PickRotateBehavior(objRoot, canvas, bounds);
        objRoot.addChild(behavior1);

        PickZoomBehavior behavior2 = new PickZoomBehavior(objRoot, canvas, bounds);
        objRoot.addChild(behavior2);

        PickTranslateBehavior behavior3 = new PickTranslateBehavior(objRoot, canvas, bounds);
        objRoot.addChild(behavior3);
        
        return objRoot;
    }   
    
    private void newMarch() {
        ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(), 
                                            new File("C:/Workspace/fwilhelm/Projekte/Diplom/data/head"));
        try {
            reader.setRange(new Range(100, 120));
            reader.read();
        } catch (Exception ioe) {
            System.out.println("Fehler: " + ioe);
        }
        Image image = reader.getImage();
        
        //System.out.println("Segmentierung begin");
        //ImageSegmentationStrategy iss = new KMeansSegmentation(image, 4);
        //iss.segmentate();
        //ModelBasedSegmentation mbs = iss.getModelBasedSegmentation();
        //mbs.segmentate(image);
        //System.out.println("Segmentierung ende");
        
        MarchingCubes mc = new MarchingCubes(image, 2, 0, 100);
        Timer timer = new Timer("Marching");
        timer.start();
        graph = mc.march();
        System.out.println("Vertices (vor dem Ausduennen): " + graph.getNoOfVertices());
        timer.stop();
        timer.print();
        
        TriangleDecimator sd = new SingleTriangleDecimator();
        sd.decimate(graph);
        TriangleDecimator decimator = new CoplanarTriangleDecimator();
        for (int i = 0; i < 0; i++) {
           decimator.decimate(graph);
        }
        System.out.println("Vertices (nach dem Ausduennen): " + graph.getNoOfVertices());
        
        try {
            GraphWriter gw = new RawGraphWriter(graph, new File("C:/Temp/graph.rawtri"));
            //GraphWriter gw = new FlatGraphWriter(graph, new File("C:/Temp/graph.flat"));
            gw.write();
        } catch (Exception e) {
            System.out.println("" + e);
        }        
    }
    
    private void loadMarch() {
        try {
            GraphReader gr = new RawGraphReader(new File("C:/Temp/graph.rawtri"));
            gr.read();
            graph = gr.getGraph();
        } catch (Exception e) {
            System.out.println("" + e);
            e.printStackTrace();
            System.exit(0);
        }        
    }
    
    public void init() {
        newMarch();
        //loadMarch();
        
        System.out.println("Dreiecke: " + graph.getNoOfTriangles());
        TriangleArray triangleArray = new TriangleArray(3*graph.getNoOfTriangles(), GeometryArray.COORDINATES);
        //TriangleArray triangleArray = new TriangleArray(3, GeometryArray.COORDINATES);
        float[] coord = new float[9];
        Triangle tri;
        Point A, B, C;
        int counter = 0;
        for (Iterator it = graph.getTriangles(); it.hasNext();) {
            tri = (Triangle)it.next();
            A = tri.getA(); B = tri.getB(); C = tri.getC();
            coord[0] = A.x; coord[1] = A.y; coord[2] = A.z;
            coord[3] = B.x; coord[4] = B.y; coord[5] = B.z;
            coord[6] = C.x; coord[7] = C.y; coord[8] = C.z;
            triangleArray.setCoordinates(counter, coord);
            counter += 3;
        }      
        
	setLayout(new BorderLayout());
        GraphicsConfiguration config =
           SimpleUniverse.getPreferredConfiguration();

	Canvas3D c = new Canvas3D(config);
	add("Center", c);

	// Create a simple scene and attach it to the virtual universe
	BranchGroup scene = createSceneGraph(triangleArray, c);
	u = new SimpleUniverse(c);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        u.getViewingPlatform().setNominalViewingTransform();

	u.addBranchGraph(scene);
    }    
    
    public static void main(String[] args) {
        new MainFrame(new TestViewer(), 500, 500);
    }

}
