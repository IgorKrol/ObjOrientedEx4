package GameComponents;

import java.util.ArrayList;
import java.util.List;

import Geom.Point3D;

public class Pacman extends Figure{

	private double speed;
	private double radius;
	private double orientation = 0;
	private Path path;
	
	/**
	 * Ex4 initializer for pacman
	 * @param p
	 */
	public Pacman(Point3D p) {
		super(p);
	}
	
	public Pacman(Point3D p, int id, double speed, double radius) {
		super(p, id);
		this.speed = speed;
		this.radius = radius;
		path = new Path();
		path.add(p);
	}
	/**
	 * for annonim pacman
	 * @param p
	 * @param speed
	 * @param radius
	 */
	public Pacman(Point3D p, double speed, double radius) {
		super(p);
		this.speed = speed;
		this.radius = radius;
		path = new Path();
		path.add(p);
	}
	public void setCoords(Point3D p) {
		super.setCoords(p);
	}
	
	public Path getPath() {
		return path;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void move(Point3D distanation) {
		
	}

	public double getOrientation() {
		return orientation;
	}
	
	public void setOrientation(double orientation) {
		this.orientation = orientation;
	}
	
	public String toString() {
		String s = null;
		s = "P:" + this.getCoords().toString() + "   SPEED:" + speed;
		return s;
	}
	

}
