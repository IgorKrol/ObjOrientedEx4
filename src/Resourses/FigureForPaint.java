package Resourses;

import java.awt.Color;

import Geom.Point3D;

public class FigureForPaint {
	
	char type;
	int size;
	Point3D coords;
	Point3D bCoords;
	Color color;
	
	public FigureForPaint(String fig) {
		String[] splitedFig =  fig.split(",");
		type = splitedFig[0].charAt(0);
		coords = new Point3D(Double.parseDouble(splitedFig[3]), Double.parseDouble(splitedFig[2]));
		if (type == 'M') {
			size = 20;
			color = Color.PINK;
		}
		if (type == 'P') {
			size = 15;
			color = Color.YELLOW;
		}
		if (type == 'G') {
			size = 15;
			color = Color.RED;
		}
		if (type == 'F') {
			size = 10;
			color = Color.GREEN;
		}
		if (type == 'B') {
			bCoords = new Point3D(Double.parseDouble(splitedFig[6]), Double.parseDouble(splitedFig[5]));
			color = Color.BLACK;
		}
	}
	public int size() {
		return size;
	}
	public char type() {
		return type;
	}
	public Point3D coords() {
		return coords;
	}
	public Point3D bCoords() {
		return bCoords;
	}
	public Color color() {
		return color;
	}
	
}
