package Algo;

import java.awt.Frame;
import java.util.ArrayList;

import GameComponents.Box;
import GameComponents.Fruit;
import GameComponents.Meta_Data_Analyze;
import Geom.Line;
import Geom.Point2D;
import Geom.Point3D;
import Resourses.Map;
import graph.Graph;
import graph.Node;

public class Ex4Algo {
	Map m = new Map();
	Meta_Data_Analyze mda;
	Point3D mPac;
	ArrayList<Box> box;
	ArrayList<Fruit> fruit;
	double [] dis;
	Graph G;
	Line sd;
	Point2D frameSizePixels;
	Point2D s;
	Point2D d;
	
	public Ex4Algo(Meta_Data_Analyze _mda, Point2D fsp) {
		frameSizePixels = fsp;
		this.mda = _mda;
		box = mda.box();
		fruit = mda.fruit();
		mPac = mda.mPac();
		dis = new double[fruit.size()];
		G = new Graph();
		int i = 0;
		s = m.CoordsToPixel(mPac, frameSizePixels);
		for (Fruit f : fruit) {
			d = m.CoordsToPixel(f.getCoords(), frameSizePixels);
			dis[i] = m.pixelDistanceMeters(s, d, frameSizePixels).x();
			i++;
		}
		//		pathBetween2Nodes(f.getCoords());
	}
	public void setFSize(Point2D f) {
		frameSizePixels = f;
	}
	public double WhereToMove() {
		double angle;
		int minI=0;
		for(int i = 1; i < dis.length; i++) {
			if (dis[i-1] > dis[i]) minI = i;
		}
		Fruit f = fruit.get(minI);
		sd = new Line(mPac, f.getCoords());
		s = m.CoordsToPixel(mPac, frameSizePixels);
		d = m.CoordsToPixel(f.getCoords(), frameSizePixels);
		angle = m.angle(s,d);
		for (Box b : box) {
			Point3D br = b.bR();
			Point3D tl = b.tL();
			double y = sd.f(tl.x());
			//if something blocking my way, give angle to it's closest corner.
			if (y >= br.y() && y <= tl.y()) {	//box in the way
				if (mPac.x() < tl.x()) {
					if (mPac.distance2D(tl) < mPac.distance2D(new Point3D(tl.x(),br.y()))) {
						d = m.CoordsToPixel(tl, frameSizePixels);
					}
					else {
						d = m.CoordsToPixel(new Point3D(tl.x(),br.y()), frameSizePixels);
					}
				}
				else {
					if (mPac.distance2D(br) < mPac.distance2D(new Point3D(br.x(),tl.y()))) {
						d = m.CoordsToPixel(br, frameSizePixels);
					}
					else {
						d = m.CoordsToPixel(new Point3D(br.x(),tl.y()), frameSizePixels);
					}
				}
			}			
			angle = m.angle(s,d);
		}
		System.out.println(dis[minI]);
		return angle;
	}

//	private void pathBetween2Nodes(Point3D f) {
//		G.add(new Node("s"));
//		G.add(new Node("d"));
//		int i = 0;
//		sd = new Line(mPac, f);
//		for (Box b : box) {
//			Point3D br = b.bR();
//			Point3D tl = b.tL();
//			double y = sd.f(tl.x());
//			if (y >= br.y() && y <= tl.y()) {	//box in the way
//
//				G.add(new Node(""+i));
//				if (mPac.x() < tl.x()) {
//					//s -> 0
//					G.addEdge("s", ""+i, mPac.distance2D(tl));
//					G.add(new Node(""+(++i)));
//					//0 -> 1
//					G.addEdge(""+i, ""+(i++), br.x() - tl.x());
//					//s -> 2
//					G.addEdge("s", ""+i, mPac.distance2D(new Point3D(tl.x(), br.y())));
//					//2 -> 3
//					G.addEdge(""+i, ""+(++i), br.x() - tl.x());
//
//				}
//				else {
//					//s -> 0
//					G.addEdge("s", ""+i, mPac.distance2D(br));
//					G.add(new Node(""+(++i)));
//					//0 -> 1
//					G.addEdge(""+i, ""+(i++), tl.x() - br.x());
//					//s -> 2
//					G.addEdge("s", ""+i, mPac.distance2D(new Point3D(br.x(), tl.y())));
//					//2 -> 3
//					G.addEdge(""+i, ""+(++i), tl.x() - br.x());
//				}
//
//
//
//
//			}
//		}
//
//	}

////	private boolean inWay() {
//
//		return false;
//	}



}
