package Resourses;

import java.io.File;

import Geom.Point2D;
import Geom.Point3D;
/**
 * Map class:
 * methods: CoordsToPixel, PixelToCoords, PixelDistanceInMeters, angle
 * contains map data/file.
 * @author Igor_Krol & Amit_Kazado
 *
 */
public class Map{
	

	private static String mapFilePath = "\\src\\Resourses\\Ariel1.png";
	private File mapFile;
	private Point2D topLeftCorner;
	private Point2D topRightCorner;
	private Point2D bottomLeftCorner;
	private Point2D bottomRightCorner;
	
	//initiator
	public Map() {
		
//		ClassLoader classLoader = getClass().getClassLoader();
		 String workingDir = System.getProperty("user.dir");
//		   System.out.println("Current working directory : " + workingDir + mapFilePath);
		   
		topLeftCorner = new Point2D(35.20236, 32.10568);
		topRightCorner = new Point2D(35.21232, 32.10568);
		bottomLeftCorner = new Point2D(35.20236, 32.10190);
		bottomRightCorner = new Point2D(35.21232,32.10190);
		mapFile = new File((workingDir + mapFilePath));
	}
	/**
	 * Computes pixel from coords
	 * @param coords = given GPS point
	 * @param frameSizePixels = current frame Size
	 * @return
	 */
	public Point2D CoordsToPixel(Point3D coords, Point2D frameSizePixels) {
		
//		PIXELS TO MOVE
		double difX = coords.x() - topLeftCorner.x();
		double difY =  topLeftCorner.y() - coords.y();
		
		//CORDINATES PER PIXEL
		Point2D frameSize = topLeftCorner.difference(bottomRightCorner);
		double pixelX = frameSize.x()/frameSizePixels.x();
		double pixelY = frameSize.y()/frameSizePixels.y();
		
		double xP =	difX/pixelX;
		double yP = difY/pixelY;
		return new Point2D(Math.round(xP), Math.round(yP));
	}
	/**
	 * Computes coords from pixel
	 * @param Pixel = given pixel
	 * @param frameSizePixels = current frame size
	 * @return point3D Coords
	 */
	public Point3D PixelToCoords(Point2D Pixel, Point2D frameSizePixels) {
		Point2D frameSize = topLeftCorner.difference(bottomRightCorner);
		//CORDINATES PER PIXEL
		double pixelX = frameSize.x()/frameSizePixels.x();
		double pixelY = frameSize.y()/frameSizePixels.y();
		//PIXELS TO CONVERT TO LAT/LON
		double difX = Pixel.x()*pixelX;
		double difY = Pixel.y()*pixelY;
		//ADD TOP LEFT COORDS
		double xValue = difX + topLeftCorner.x();
		double yValue = topLeftCorner.y() - difY;
		Point3D returnCoords = new Point3D(xValue,yValue);
		
		return returnCoords;

	}
	/**
	 * Computes distance and angel between 2 pixels points
	 * @param pixel1 = pixel point
	 * @param pixel2 = pixel point
	 * @param frameSizePixels = current frame size
	 * @return point2D x=distance, y=angel
	 */
	public Point2D pixelDistanceMeters(Point2D pixel1, Point2D pixel2, Point2D frameSizePixels) {
		Point3D p1 = this.PixelToCoords(pixel1, frameSizePixels);
		Point3D p2 = this.PixelToCoords(pixel2, frameSizePixels);
		double distance = p1.distance3D(p2);
		double angel = p1.angleXY(p2);
		return new Point2D(distance, angel);
		
	}
	/**
	 * Calc angle between 2 points
	 * @param mPacman = source
	 * @param mouseClick = destination
	 * @return
	 */
	public double angle(Point2D mPacman, Point2D mouseClick) {
		return Math.toDegrees(Math.atan2(mouseClick.x() - mPacman.x(),  mPacman.y() - mouseClick.y()));
	}
	public File getFile() {
		return mapFile;
	}

	public static String getMapFilePath() {
		return mapFilePath;
	}

	public Point2D getTopLeftCorner() {
		return topLeftCorner;
	}


	public void setTopLeftCorner(Point2D topLeftCorner) {
		this.topLeftCorner = topLeftCorner;
	}


	public Point2D getTopRightCorner() {
		return topRightCorner;
	}


	public void setTopRightCorner(Point2D topRightCorner) {
		this.topRightCorner = topRightCorner;
	}


	public Point2D getBottomLeftCorner() {
		return bottomLeftCorner;
	}


	public void setBottomLeftCorner(Point2D bottomLeftCorner) {
		this.bottomLeftCorner = bottomLeftCorner;
	}


	public Point2D getBottomRightCorner() {
		return bottomRightCorner;
	}


	public void setBottomRightCorner(Point2D bottomRightCorner) {
		this.bottomRightCorner = bottomRightCorner;
	}
	
	

}
