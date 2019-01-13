package Geom;
/**
 * Class for Line function
 * 
 *
 */
public class Line {
	Point3D a;
	Point3D b;
	double m;
	/**
	 * 
	 * @param a point
	 * @param b point
	 */
	public Line(Point3D a, Point3D b) {
	this.a = a;
	this.b = b;
	m = (a.y() - b.y())/(a.x() - b.x());
	}
	/**
	 * clac f(x)
	 * @param x parameter
	 * @return y
	 */
	public double f(double x) {
		double y = m*(x - a.x()) + a.y();
		return y;
	}
	/**
	 * checks if point p on this line
	 * @param p point
	 * @return true: if point on line, false: if point isnt on line.
	 */
	public boolean pointOnLine(Point3D p) {
		double y = f(p.x());
		if (Math.abs(y - p.y()) < 0.00001)
			return true;
		return false;
	}
}
