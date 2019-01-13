package GameComponents;

import java.awt.Color;
import java.util.ArrayList;

import Geom.Point2D;
import Geom.Point3D;
import Robot.Play;
/**
 * this class responsible for analyzing data from 'Play' and contain all data in objects to use
 * 
 *
 */
public class Meta_Data_Analyze {
	Point3D mPacman;
	ArrayList<Pacman> pac;
	ArrayList<Fruit> fruit;
	ArrayList<Ghost> ghost;
	ArrayList<Box> box;
	
	/**
	 * init
	 * @param game = Play type
	 */
	public Meta_Data_Analyze(Play game) {
		initAll();
		analyze(game.getBoard());
	}
	/**
	 * Arraylist init
	 * @param metaData
	 */
	public Meta_Data_Analyze(ArrayList<String> metaData) {
		initAll();
		analyze(metaData);
	}
	/**
	 * init for all paramaters
	 */
	private void initAll() {
		pac = new ArrayList<Pacman>();
		fruit = new ArrayList<Fruit>();
		ghost = new ArrayList<Ghost>();
		box = new ArrayList<Box>();
	}
	/**
	 * analyzing every 'row'
	 * @param metaData
	 */
	private void analyze(ArrayList<String> metaData) {
		for (String string : metaData) {
			type(string);
		}	

	}
	/**
	 * converts row to the right 'figure': M -> Main, P -> Pacman, F -> Fruit, G -> Ghost, B -> Box
	 * @param fig - row string
	 */
	private void type(String fig) {
		String[] splitedFig =  fig.split(",");
		char type = splitedFig[0].charAt(0);
		Point3D coords = new Point3D(Double.parseDouble(splitedFig[3]), Double.parseDouble(splitedFig[2]));
		if (type == 'M') {
			mPacman = coords;
		}
		if (type == 'P') {
			pac.add(new Pacman(coords));
		}
		if (type == 'G') {
			ghost.add(new Ghost(coords));
		}
		if (type == 'F') {
			fruit.add(new Fruit(coords));
		}
		if (type == 'B') {
			Point3D bCoords = new Point3D(Double.parseDouble(splitedFig[6]), Double.parseDouble(splitedFig[5]));
			box.add(new Box(coords, bCoords));
		}
	}
	
	public ArrayList<Pacman> pac(){
		return pac;
	}
	public ArrayList<Fruit> fruit(){
		return fruit;
	}
	public ArrayList<Ghost> ghost(){
		return ghost;
	}
	public ArrayList<Box> box(){
		return box;
	}
	public Point3D mPac(){
		return mPacman;
	}
	
}
