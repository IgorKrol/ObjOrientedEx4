package GameComponents;

import java.util.ArrayList;
import java.util.Iterator;

import Geom.Point3D;

public class Path {

	ArrayList<Point3D> pacManPath;
	ArrayList<Double> timePerFruitEatten;
	double time;

	public Path() {
		pacManPath = new ArrayList<Point3D>();
		timePerFruitEatten = new ArrayList<Double>();
		time = 0;
	}
	/**
	 * Initiator with path parameter
	 * @param p
	 */
	public Path(Path p) {
		Iterator<Point3D> ip = p.iterator();
		while(ip.hasNext())
			pacManPath.add(ip.next());
	}
	/**
	 * deep copy method
	 * @return
	 */
	public Path copy() {
		Path newPath = new Path();
		for (Point3D point3d : pacManPath) {
			newPath.add(point3d);
		}
		return newPath;
	}
	/**
	 * Calculates path's distance through all points.
	 * @return double distance
	 */
	public double distance() {
		double distance = 0;
		Iterator<Point3D> ip = pacManPath.iterator();
		if (!ip.hasNext()) return distance;
		
		Point3D a = ip.next();
		while(ip.hasNext()) {
			Point3D b = ip.next();
			distance+= a.distance3D(b);
			a = b;
		}
		return distance;
	}
	/**
	 * adds time to current time value
	 * @param t = time to add
	 */
	public void addTime(double t) {
		time+=t;
	}
	/**
	 * removes point from path
	 * @param index
	 */
	public void removeP(int index) {
		pacManPath.remove(index);
	}
	/**
	 * returns time with index
	 * @param index
	 * @return
	 */
	public double getTime(int index) {
		return timePerFruitEatten.get(index);
	}
	
	public double getTime() {
		return time;
	}
	/**
	 * adds point to path + saves time.
	 * @param point
	 */
	public void add(Point3D point) {
		pacManPath.add(point);
		timePerFruitEatten.add(time);
	}
	public ArrayList<Double> getTimesForEachFruit(){
		return timePerFruitEatten;
	}
	public Iterator<Point3D> iterator(){
		return pacManPath.iterator();
	}
	public ArrayList<Point3D> getPathPoints(){
		return pacManPath;
	}
	/**
	 * toString method
	 * @return String (x,y,z),(x,y,z)...
	 */
	public String toString() {
		Iterator<Point3D> ip = pacManPath.iterator();
		if (!ip.hasNext()) return "";
		String s = ip.next().toString();
		while(ip.hasNext()) {
			s+=","+ ip.next().toString();
		}
		return s;
		
	}


}
