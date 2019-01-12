package Geom;

public class Line {
	Point3D a;
	Point3D b;
	double m;
	
	public Line(Point3D a, Point3D b) {
	this.a = a;
	this.b = b;
	m = (a.y() - b.y())/(a.x() - b.x());
	}
	
	public double f(double x) {
		double y = m*(x - a.x()) + a.y();
		return y;
	}
	
	public boolean pointOnLine(Point3D p) {
		double y = f(p.x());
		if (Math.abs(y - p.y()) < 0.00001)
			return true;
		return false;
	}
}
