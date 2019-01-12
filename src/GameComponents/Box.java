package GameComponents;

import Geom.Point3D;

public class Box{
	
	Point3D topLeft;
	Point3D bottomRight;
	
	public Box(Point3D tl, Point3D br) {
		topLeft = tl;
		bottomRight = br;
	}
	
	public Point3D tL() {
		return topLeft;
	}
	public Point3D bR() {
		return bottomRight;
	}
	
}
