
package org.wewi.medimg.viewer.image; 

final class Line3D {
    private Vector position;
    private Vector direction;

    public Line3D(Vector p1, Vector p2)/*p1,p2:Endpoints of line. Line goes from p1 to p2.*/ {
        setPosition(p1);
        setDirection(p2.minus(p1));
    }

	public void setPosition(Vector position) {
		this.position = position;
	}

	public Vector getPosition() {
		return position;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
	}

	public Vector getDirection() {
		return direction;
	}

    /**
     * Return if, and where, this line intersects the plane z=0.
     * 
     * @param where
     * @return boolean
     */
    public boolean intersect(Vector where) {
        double t;
        if (direction.getZ() == 0.0) {
            direction.setZ(0.0000001);
        }
        
        t = -position.getZ() / direction.getZ();
        where.copyFrom(position.plus(direction.scaleBy(t)));
        
        if (t >= 0 && t <= 1.0) {
            return true;
        } else {
            return false;
        }
    }
}
