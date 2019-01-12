package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GameComponents.Fruit;
import GameComponents.Game;
import GameComponents.Pacman;
import Geom.Point2D;
import Geom.Point3D;
import Resourses.FigureForPaint;
import Resourses.Map;
import Robot.Play;

public class JPanelWithBG extends JPanel {
	Ex4GameFrame mFrame;
	Map m;
	public static Point2D frameSizePixels;
	boolean shouldRepaint = true;
	Graphics gPanel;
	int w;
	int h;
	private BufferedImage mapImage;
	boolean shouldDrawFigures;
	boolean shouldPlay;
	Play ex4Game;
	File mapFile;
	Point2D mPacman;
	double angle;
	
	public void setPlay(Play game) {
		ex4Game = game;
	}
	public void setDrawFigures(boolean sdf) {
		shouldDrawFigures = sdf;
	}
	public void setShouldPlay(boolean sp) {
		shouldPlay = sp;
	}
	public void setAngle(double a) {
		angle = a;
	}
	public JPanelWithBG(Ex4GameFrame mainFrame){
		mFrame = mainFrame;
		m = new Map();
//		ex4Game = game;
		Dimension d = new Dimension(1433, 642);
		this.setPreferredSize(d);
		try {
			//ImageINITIALIZER

			mapFile = m.getFile();
			mapImage = ImageIO.read(mapFile);
//			System.out.println("GotImage");
		} 
		catch (Exception e) {
			System.err.println("ImageIO: Cant load image");
		}
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		gPanel = g;
		super.paintComponent(g);
		w = this.getWidth();
		h = this.getHeight();
		frameSizePixels = new Point2D(w, h);
		Image img = mapImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		g.drawImage(img, 0, 0, null);
		if (shouldDrawFigures) {
			paintBoard(g);
		}
				if (shouldPlay) {
				ex4Game.rotate(angle);
				if (!ex4Game.isRuning()) shouldPlay = false;
			}
		repaint();
	}
	public void paintBoard(Graphics g) {
		ArrayList<String> board_data = ex4Game.getBoard();
		//			for(int i=0;i<board_data.size();i++) {
		//				System.out.println(board_data.get(i));
		//			}
		for(int i=0;i<board_data.size();i++) {
			FigureForPaint fig = new FigureForPaint(board_data.get(i));
			if (fig.type() == 'B') {
				g.setColor(fig.color());
				Point2D figCoordsTop = m.CoordsToPixel(fig.coords(), frameSizePixels);
				Point2D figCoordsBot = m.CoordsToPixel(fig.bCoords(), frameSizePixels);
				int width = (int) Math.abs((figCoordsTop.x() - figCoordsBot.x()));
				int height = (int) Math.abs((figCoordsTop.y() - figCoordsBot.y()));
				g.fillRect((int)figCoordsTop.x(), (int)figCoordsBot.y(), width, height);
			}
			else {
				g.setColor(fig.color());
				Point2D figCoords = m.CoordsToPixel(fig.coords(), frameSizePixels);
				if (fig.type() == 'M') {
					mPacman = figCoords;
					mFrame.setPac(mPacman);
				}
				g.fillOval((int)figCoords.x() - (fig.size()/2), (int)figCoords.y() - (fig.size()/2), fig.size(), fig.size());
			}

		}
	}
}
