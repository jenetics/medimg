/*
 * Viewer3D.java
 *
 * Created on 25. März 2002, 16:34
 */

package org.wewi.medimg.visualisation;



import javax.media.j3d.Canvas3D;
import javax.swing.event.InternalFrameEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.FocusEvent;

import org.wewi.medimg.viewer.ViewerDesktopFrame;
import org.wewi.medimg.viewer.Viewer;
import org.wewi.medimg.viewer.NavigationPanel;
import org.wewi.medimg.viewer.Command;
import org.wewi.medimg.viewer.NullCommand;
import org.wewi.medimg.viewer.ImageContainer;

import java.awt.GraphicsConfiguration;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.picking.behaviors.PickTranslateBehavior;
import com.sun.j3d.utils.picking.behaviors.PickZoomBehavior;
import com.sun.j3d.utils.picking.behaviors.PickRotateBehavior;
import com.sun.j3d.utils.picking.PickTool;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.Alpha;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.View;
import javax.media.j3d.Group;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Morph;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Geometry;
import javax.media.j3d.Appearance;
import javax.media.j3d.QuadArray;
import javax.media.j3d.Material;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.PointAttributes;


import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Point3d;

import javax.swing.JPanel;


import org.wewi.medimg.viewer.ViewerDesktopFrame;

import javax.media.j3d.Canvas3D;

public class Viewer3D extends ViewerDesktopFrame {

    private Canvas3D canvas;  
    
    private SimpleUniverse u = null; 
    
    //private View view = null;
    private Morph morph;    
    private PickRotateBehavior behavior1;
    private PickZoomBehavior   behavior2;
    private PickTranslateBehavior behavior3;    
    private QuadArray geomMorph[] = new QuadArray[3];

    //Navigation-Commands
    private Command firstCommand;
    private Command lastCommand;
    private Command nextCommand;
    private Command prevCommand;
    private Command nextNextCommand;
    private Command prevPrevCommand;

    public Viewer3D(String frameName) {
        super(frameName, true, true, true, false);
        init();
    }

    public void init() {
	canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
	getContentPane().add("Center", canvas);
	
	// Create a scene and attach it to the virtual universe
	BranchGroup scene = createSceneGraph(canvas);
	u = new SimpleUniverse(canvas);
	
	// This will move the ViewPlatform back a bit so the
	// objects in the scene can be viewed.
	u.getViewingPlatform().setNominalViewingTransform();
	u.addBranchGraph(scene);       
    }

  public BranchGroup createSceneGraph(Canvas3D canvas)
  {
    // Create the root of the branch graph
    BranchGroup objRoot = new BranchGroup();

    // Create a Transformgroup to scale all objects so they
    // appear in the scene.
    TransformGroup objScale = new TransformGroup();
    Transform3D t3d = new Transform3D();
    t3d.setScale(1.0);
    objScale.setTransform(t3d);
    objRoot.addChild(objScale);
    
    // Create a bunch of objects with a behavior and add them
    // into the scene graph.
   
    objScale.addChild(createObject( 0.1, 0, 0));
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);

    // Add a light.
    Color3f lColor = new Color3f(1.0f, 1.0f, 1.0f) ;
    Vector3f lDir  = new Vector3f(0.0f, 0.0f, -1.0f) ;

    DirectionalLight lgt = new DirectionalLight(lColor, lDir) ;
    lgt.setInfluencingBounds(bounds) ;
    objRoot.addChild(lgt) ;


    // Now create the Alpha object that controls the speed of the
    // morphing operation.
    Alpha morphAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE |
				 Alpha.DECREASING_ENABLE,
				 0, 0,
				 4000, 1000, 500,
				 4000, 1000, 500);
      
        
    behavior1 = new PickRotateBehavior(objRoot, canvas, bounds);
    objRoot.addChild(behavior1);

    behavior2 = new PickZoomBehavior(objRoot, canvas, bounds);
    objRoot.addChild(behavior2);

    behavior3 = new PickTranslateBehavior(objRoot, canvas, bounds);
    objRoot.addChild(behavior3);

    // Let Java 3D perform optimizations on this scene graph.
    objRoot.compile();
 
    return objRoot;
  }    

  private Group createObject(double scale, double xpos, double ypos){
    
    Shape3D shape = null;
    Geometry geom = null;
     
    // Create a transform group node to scale and position the object.
    Transform3D t = new Transform3D();
    t.set(scale, new Vector3d(xpos, ypos, 0.0));
    TransformGroup objTrans = new TransformGroup(t);
    objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    objTrans.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
    
    // Create a second transform group node and initialize it to the
    // identity.  Enable the TRANSFORM_WRITE capability so that
    // our behavior code can modify it at runtime.
    TransformGroup spinTg = new TransformGroup();
    spinTg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    spinTg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    spinTg.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
 
    Appearance appearance = new Appearance();
    geom = new GullCG();
    Material m = new Material() ;
    m.setLightingEnable(true) ;
    appearance.setMaterial(m) ;
    shape = new Shape3D(geom,appearance);
    shape.setCapability(Shape3D.ALLOW_APPEARANCE_READ);
    shape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
    shape.setCapability(Shape3D.ENABLE_PICK_REPORTING);
    PickTool.setCapabilities(shape, PickTool.INTERSECT_FULL);
    spinTg.addChild(shape);
   
    // add it to the scene graph. 
    objTrans.addChild(spinTg);    

    return objTrans;
  }
  
  /*private void setPickMode(int mode) {
      behavior1.setMode(mode);
      behavior2.setMode(mode);
      behavior3.setMode(mode);
  }

  private void setPickTolerance(float tolerance) {
      behavior1.setTolerance(tolerance);
      behavior2.setTolerance(tolerance);
      behavior3.setTolerance(tolerance);
  }

  private void setViewMode(int mode) {
      view.setProjectionPolicy(mode);
  }*/
  
 /* public void actionPerformed(ActionEvent e) {
      String name = ((Component)e.getSource()).getName();
      String value = e.getActionCommand();
      //System.out.println("action: name = " + name + " value = " + value);
      setPickMode(PickCanvas.BOUNDS);
	 } else if (value == geometryString) {
	     setPickMode(PickCanvas.GEOMETRY);
	 } else if (value == geometryIntersectString) {
	     setPickMode(PickCanvas.GEOMETRY_INTERSECT_INFO);
	 } else {
	     System.out.println("Unknown pick mode: " + value); 
	 }
      } else if (name == toleranceString) {
	 if (value == tolerance0String) {
	     setPickTolerance(0.0f);
	 } else if (value == tolerance2String) {
	     setPickTolerance(2.0f);
	 } else if (value == tolerance4String) {
	     setPickTolerance(4.0f);
	 } else if (value == tolerance8String) {
	     setPickTolerance(8.0f);
	 } else {
	     System.out.println("Unknown tolerance: " + value); 
	 }
      } else if (name == viewModeString) {
	 if (value == perspectiveString) {
	     setViewMode(View.PERSPECTIVE_PROJECTION);
	 } else if (value == parallelString) {
	     setViewMode(View.PARALLEL_PROJECTION);
	 } 
      } else {
	 System.out.println("Unknown action name: " + name); 
      }
  }  */
  
    private void setCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        np.setFirstCommand(firstCommand);
        np.setLastCommand(lastCommand);
        np.setNextCommand(nextCommand);
        np.setPrevCommand(prevCommand);
        np.setNextNextCommand(nextNextCommand);
        np.setPrevPrevCommand(prevPrevCommand);
    }
    
    private void setNullCommands() {
        NavigationPanel np = Viewer.getInstance().getNavigationPanel();
        np.setFirstCommand(new NullCommand());
        np.setLastCommand(new NullCommand());
        np.setNextCommand(new NullCommand());
        np.setPrevCommand(new NullCommand());
        np.setNextNextCommand(new NullCommand());
        np.setPrevPrevCommand(new NullCommand());        
    }
    
    public void internalFrameOpened(InternalFrameEvent internalFrameEvent) {
    }
    
    public void componentResized(ComponentEvent componentEvent) {
    }
    
    public void componentMoved(ComponentEvent componentEvent) {
    }
    
    public void internalFrameClosed(InternalFrameEvent internalFrameEvent) {
        setNullCommands();   
	u.removeAllLocales();        
    }
    
    public void mouseEntered(MouseEvent mouseEvent) {
    }
    
    public void mouseMoved(MouseEvent mouseEvent) {
    }
    
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_PAGE_UP:
                nextNextCommand.execute();
                break;
            case KeyEvent.VK_PAGE_DOWN:
                prevPrevCommand.execute();
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_SPACE:
                nextCommand.execute();
                break;               
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_DOWN:
                prevCommand.execute();
                break;
            case KeyEvent.VK_END:
                lastCommand.execute();
                break;
            case KeyEvent.VK_HOME:
                firstCommand.execute();
                break;
            default:
                //Nothing
        }
    }
    
    public void mouseExited(MouseEvent mouseEvent) {
    }
    
    public void mouseClicked(MouseEvent mouseEvent) {
    }
    
    public void mouseReleased(MouseEvent mouseEvent) {
    }
    
    public void internalFrameDeactivated(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameIconified(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameClosing(InternalFrameEvent internalFrameEvent) {
    }
    
    public void keyTyped(KeyEvent keyEvent) {
    }
    
    public void componentShown(ComponentEvent componentEvent) {
    }
    
    public void mousePressed(MouseEvent mouseEvent) {
    }
    
    public void keyReleased(KeyEvent keyEvent) {
    }
    
    public void focusLost(FocusEvent focusEvent) {
    }
    
    public void componentHidden(ComponentEvent componentEvent) {
    }
    
    public void mouseDragged(MouseEvent mouseEvent) {
    }
    
    public void internalFrameDeiconified(InternalFrameEvent internalFrameEvent) {
    }
    
    public void internalFrameActivated(InternalFrameEvent internalFrameEvent) {
        setCommands();
    }
    
    public void focusGained(FocusEvent focusEvent) {
        setCommands();
    }
    
    public static void main(String[] args) {
        
    }    
    
}
