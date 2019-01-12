package GUI;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.net.PasswordAuthentication;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Algo.ShortestPathAlgo;
import File_format.Path2KML;
import File_format.gameToCSVWriter;
import GameComponents.Fruit;
import GameComponents.Game;
import GameComponents.Pacman;
import GameComponents.Path;
import Geom.Point2D;
import Geom.Point3D;
import Resourses.FigureForPaint;
import Resourses.Map;
import Robot.Play;
import Threads.AnimationThread;
import sun.java2d.loops.FillRect;

public class MyFrame extends JFrame implements MouseListener, MouseMotionListener{

	private boolean shouldDrawFigures;
	private Game mainGame;
	private Game gameStash;
	private Map m;
	private JPanel _panel;
	private BufferedImage mapImage;
	private File mapFile;
	private static Dimension d = new Dimension(1433/5*4, 642/5*4);
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenu AddMenu;
	private JMenu AlgoMenu;
	private Point2D mouseClick;
	private boolean shouldPaintPacman;
	private ShortestPathAlgo SPA;
	private Point2D frameSizePixels;
	private boolean shouldDrawLines;
	private boolean shouldPlay;
	private double angle;


	//EX4 additions:
	private Point2D mPacman;
	private Play ex4Game;

	public MyFrame() {
		initFrame();
	}
	/**
	 * Frame initiator: initiate image buffer, menu, panel
	 */
	public void initFrame() {
		m = new Map();
		shouldDrawFigures = false;
		shouldDrawLines = false;
		mainGame = new Game();
		gameStash = mainGame;
		this.setPreferredSize(d);
		try {
			//ImageINITIALIZER
			mapFile = m.getFile();
			mapImage = ImageIO.read(mapFile);
		} 
		catch (Exception e) {
			System.err.println("ImageIO: Cant load image");
		}
		createMenu();
		createPanel();

		shouldPaintPacman = true;

	}
	/**
	 * Create Main options menu
	 */
	public void createMenu() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		AddMenu = new JMenu("Add");
		AlgoMenu = new JMenu("Run");
		fileMenu.setMnemonic(KeyEvent.VK_A);
		createFileMenu();
		createAddMenu();
		createAlgoMenu();
		this.setJMenuBar(menuBar);

	}

	/**
	 * Create file menu
	 */
	public void createFileMenu() {
		JMenuItem open = new JMenuItem("Open File");
		JMenuItem save = new JMenuItem("Save File");
		JMenuItem saveKML = new JMenuItem("Save Path2KML");
		//SAVEKML PLATFORM
		saveKML.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File("c"));
				fc.setDialogTitle("Save your Game");
				fc.setFileFilter(new FileNameExtensionFilter("kml", "KML"));
				int value = fc.showSaveDialog(null);
				File f = fc.getSelectedFile();

				if(f != null) {
					String filePath = f.getAbsolutePath();
					Path2KML p2k = new Path2KML();
					p2k.writePath2KML(mainGame.getPacmans(), filePath);
				}
			}
		});

		//OPEN FILE PLATFORM
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {                                 
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("c"));
				chooser.setFileFilter(new FileNameExtensionFilter("csv","CSV"));
				int value = chooser.showOpenDialog(null);
				File f = chooser.getSelectedFile();
				if (f != null) {
					String fileName = f.getAbsolutePath();
					ex4Game = new Play(fileName);
					ex4Game.setIDs(323230102,302499652,0);
				}
				shouldDrawFigures = true;
			}

		});

		///////////////////////////////////////////////////////////////////////////////////////
		//SAVES THE GAME AS A CSV FILE
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File("c:\\"));
				fc.setDialogTitle("Save your Game");
				fc.setFileFilter(new FileNameExtensionFilter(".csv", "CSV File"));
				int value = fc.showSaveDialog(null);
				File f = fc.getSelectedFile();

				if(f != null) {
					String filePath = f.getAbsolutePath();
					gameToCSVWriter saveGame = new gameToCSVWriter();
					saveGame.CSVWrite(mainGame.getPacmans(), mainGame.getFruits(), filePath);
				}

			}
		});
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveKML);
		menuBar.add(fileMenu);
	}

	/**
	 * Create Add menu
	 */

	///////////////////////////////////////////////////////////
	public void createAddMenu() {
		JMenuItem addPacman = new JMenuItem("Add Pacman");
		JMenuItem addFruit = new JMenuItem("Add Fruit");

		addPacman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shouldPaintPacman = true;
				// mouseClicked
			}
		});

		addFruit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shouldPaintPacman = false;

			}
		});
		/////////////////////////////////////////////////////////
		AddMenu.add(addPacman);
		AddMenu.add(addFruit);
		menuBar.add(AddMenu);
	}

	public void createAlgoMenu() {
		JMenuItem findShortPath = new JMenuItem("Find Short Path");
		JMenuItem playGame = new JMenuItem("Play Game");
		JMenuItem reset = new JMenuItem("Reset");

		findShortPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SPA = new ShortestPathAlgo(mainGame);
				Iterator<Pacman> check = mainGame.iteratorP();

				shouldDrawLines = true;

			}
		});


		playGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AnimationThread at = new AnimationThread(mainGame.getPacmans(), mainGame.getFruits(), (JPanelBG)_panel);
				at.start();

			}
		});

		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainGame = new Game();
			}
		});


		AlgoMenu.add(findShortPath);
		AlgoMenu.add(playGame);
		AlgoMenu.add(reset);
		menuBar.add(AlgoMenu);
	}
	/**
	 * Create Main Panel with map image on it
	 */
	public void createPanel() {
		_panel = new JPanelBG();
		_panel.setLayout(new BorderLayout());
		_panel.addMouseListener(this);
		_panel.addMouseMotionListener(this);
		this.add(_panel);
	}
	/**
	 * This subclass is for main panel, draws image
	 * @author Igor
	 *
	 */
	public class JPanelBG extends JPanel{
		boolean shouldRepaint = true;
		Graphics gPanel;
		int w;
		int h;
		@Override
		public void paint(Graphics g) {
			gPanel = g;
			super.paintComponent(g);
			w = MyFrame.this.getWidth();
			h = MyFrame.this.getHeight();
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
					}
					g.fillOval((int)figCoords.x() - (fig.size()/2), (int)figCoords.y() - (fig.size()/2), fig.size(), fig.size());
				}

			}
		}

		public void paintFigure(Graphics g) {
			Point2D frameSizePixels = new Point2D(w,h);
			Iterator<Pacman> pacmanList = mainGame.getPacmans().iterator();
			g.setColor(Color.YELLOW);
			while(pacmanList.hasNext()) {
				Pacman pacman = pacmanList.next();
				Point2D pacPixels = m.CoordsToPixel(pacman.getCoords(), frameSizePixels);
				g.fillOval((int)pacPixels.x()-10, (int)pacPixels.y()-10, 20, 20);

			}

			Iterator<Fruit> fruitList = mainGame.getFruits().iterator();
			g.setColor(Color.RED);
			while(fruitList.hasNext()) {
				Fruit fruit = fruitList.next();
				Point2D fruPixels = m.CoordsToPixel(fruit.getCoords(), frameSizePixels);
				g.fillOval((int)fruPixels.x()-7, (int)fruPixels.y()-7, 14, 14);
				if(fruit.isEaten())
				{
					g.setColor(Color.BLACK);
					g.drawLine((int)fruPixels.x()+8, (int)fruPixels.y()+8, (int)fruPixels.x()-8, (int)fruPixels.y()-8);
					g.drawLine((int)fruPixels.x()-8, (int)fruPixels.y()+8, (int)fruPixels.x()+8, (int)fruPixels.y()-8);
					g.setColor(Color.RED);
				}
			}
			if(shouldDrawLines) paintLines(g);
			else {
				repaint();
			}

		}
		public void paintLines(Graphics g) {
			repaint();
			Iterator<Pacman> pIte = mainGame.getPacmans().iterator();
			while(pIte.hasNext()) {
				Pacman singlePacPath = pIte.next();
				Iterator<Point3D> cPoint = singlePacPath.getPath().iterator();
				Point2D pPoint = null;
				if(cPoint.hasNext()) pPoint = m.CoordsToPixel(cPoint.next(), frameSizePixels);
				g.setColor(Color.BLUE);
				while(cPoint.hasNext()) {
					Point2D startPoint = pPoint;
					pPoint = m.CoordsToPixel(cPoint.next(), frameSizePixels);
					g.drawLine((int)startPoint.x(), (int)startPoint.y(), (int)pPoint.x(), (int)pPoint.y());
				}
			}
		}
	};
//
//
//	public static void main(String[] args) {
//		Ex4GameFrame mainGameFrame = new Ex4GameFrame();
//		mainGameFrame.setSize(d);
//		mainGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		mainGameFrame.setVisible(true);
//
//	}

	///////////////////////////////////////////////////////////////////////////
	@Override
	public void mouseClicked(MouseEvent e) {


	}
	/**
	 * right click, init main pacman
	 * left click, calculates rotation angel
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mouseClick = new Point2D(e.getX(), e.getY());
		Point3D mouseCoords = m.PixelToCoords(mouseClick, frameSizePixels);
		if (SwingUtilities.isRightMouseButton(e)) {
			ex4Game.setInitLocation(mouseCoords.y(), mouseCoords.x());
			ex4Game.start();
		}
		else if(SwingUtilities.isLeftMouseButton(e)) {
			String info = ex4Game.getStatistics();
			System.out.println(info);
			angle = Math.toDegrees(Math.atan2(mouseClick.x() - mPacman.x(),  mPacman.y() - mouseClick.y()));
			//			System.out.println(e.MOUSE_DRAGGED);
			System.out.println(angle);
			shouldPlay = true;

		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {


	}
	@Override
	public void mouseEntered(MouseEvent e) {


	}
	@Override
	public void mouseExited(MouseEvent e) {


	}
	public Point2D FrameSize() {
		return frameSizePixels;
	}
	public void paintFigure () {
		if(shouldPaintPacman) 
			mainGame.addPacman(mouseClick, frameSizePixels);
		else {
			mainGame.addFruit(mouseClick, frameSizePixels);
		}
		shouldDrawFigures = true;
		_panel.repaint();


	}
	///////////////////////////////////////////////////////////////////////////////////
	@Override
	public void mouseDragged(MouseEvent e) {
		//		String info = ex4Game.getStatistics();
		//		System.out.println(info);
		//		double angel = Math.toDegrees(Math.atan2(mouseClick.x() - mPacman.x(),  mPacman.y() - mouseClick.y()));
		//		System.out.println(angel);
		//		ex4Game.rotate(angel);		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}


