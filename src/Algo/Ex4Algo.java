package Algo;

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
	
	public Ex4Algo(Meta_Data_Analyze _mda) {
		this.mda = _mda;
		box = mda.box();
		fruit = mda.fruit();
		mPac = mda.mPac();
		dis = new double[fruit.size()];
		G = new Graph();
		for (Fruit f : fruit) {
			pathBetween2Nodes(f.getCoords());
			
		}
	}
	
	private void pathBetween2Nodes(Point3D f) {
		G.add(new Node("s"));
		G.add(new Node("d"));
		int i = 0;
		sd = new Line(mPac, f);
		for (Box b : box) {
			Point3D br = b.bR();
			Point3D tl = b.tL();
			double y = sd.f(tl.x());
			if (y >= br.y() && y <= tl.y()) {	//box in the way
				
				G.add(new Node(""+i));
				if (mPac.x() < tl.x()) {
					//s -> 0
					G.addEdge("s", ""+i, mPac.distance2D(tl));
					G.add(new Node(""+(++i)));
					//0 -> 1
					G.addEdge(""+i, ""+(i++), br.x() - tl.x());
					//s -> 2
					G.addEdge("s", ""+i, mPac.distance2D(new Point3D(tl.x(), br.y())));
					//2 -> 3
					G.addEdge(""+i, ""+(++i), br.x() - tl.x());
					
				}
				else {
					//s -> 0
					G.addEdge("s", ""+i, mPac.distance2D(br));
					G.add(new Node(""+(++i)));
					//0 -> 1
					G.addEdge(""+i, ""+(i++), tl.x() - br.x());
					//s -> 2
					G.addEdge("s", ""+i, mPac.distance2D(new Point3D(br.x(), tl.y())));
					//2 -> 3
					G.addEdge(""+i, ""+(++i), tl.x() - br.x());
				}
				 
				
					
				
			}
		}
		
	}
	
	private boolean inWay() {
		
		return false;
	}
	
	

}
