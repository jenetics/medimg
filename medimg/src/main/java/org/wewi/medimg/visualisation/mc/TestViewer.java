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
 * Viewer3D.java
 *
 * Created on 25. März 2002, 16:34
 */

package org.wewi.medimg.visualisation.mc;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.io.File;
import java.util.Iterator;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TriangleArray;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import org.wewi.medimg.image.Image;
import org.wewi.medimg.image.IntImageFactory;
import org.wewi.medimg.image.io.ImageReader;
import org.wewi.medimg.image.io.RawImageReader;
import org.wewi.medimg.util.Timer;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.picking.behaviors.PickRotateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickZoomBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

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
        pa.setPolygonMode(PolygonAttributes.POLYGON_LINE);
        pa.setCullFace(PolygonAttributes.CULL_NONE);
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
        //String file = "C:/Workspace/fwilhelm/Projekte/Diplom/data/cylinder";
        //ImageReader reader = new TIFFReader(ImageDataFactory.getInstance(), new File(file));
        
        String file = "X:/images/nbrain.model.greymatter.rid";
        ImageReader reader = new RawImageReader(IntImageFactory.getInstance(), new File(file));
        
        try {
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
        for (int i = 0; i < 2; i++) {
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
