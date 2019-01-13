package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import Algo.Ex4Algo;
import DataBase.DataBase;
import GameComponents.Game;
import GameComponents.Meta_Data_Analyze;
import Geom.Point2D;
import Geom.Point3D;
import Resourses.Map;
import Robot.Play;
/**
 * Main GUI class.
 * 
 *
 */
public class Ex4GameFrame extends JFrame implements MouseListener {
	Map m;
	Point2D mouseClick;
	JPanelWithBG _panel;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenu runMenu;
	JMenuItem Open;
	JMenuItem PlayGame;
	Play ex4Game;
	static Dimension d;
	boolean shouldDrawFigures;
	double angle;
	Point2D mPacman;
	/**
	 * set main pacman pointer
	 * @param pac = pointer to main pacman
	 */
	public void setPac(Point2D pac) {
		mPacman = pac;
	}
	/**
	 * initialize class
	 */
	public Ex4GameFrame() {
		initFrame();
	}
	/**
	 * initialize frame: map, panel, menu...
	 */
	private void initFrame() {
		m = new Map();
		d = new Dimension(1433, 642);
		this.setPreferredSize(d);
		createMenu();
		createPanel();
	}
	/**
	 * initialize menu bar: file, run...
	 */
	private void createMenu() {
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		runMenu = new JMenu("Run");
		fileMenu.setMnemonic(KeyEvent.VK_A);
		runMenu.setMnemonic(KeyEvent.VK_A);
		createFileMenu();
		createRunMenu();
		this.setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(runMenu);
	}
	/**
	 * Create 'Run' menu
	 */
	private void createRunMenu() {
		JMenuItem runGame = new JMenuItem("Run Game");
		runGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//start game
				ex4Game.start();
				//start paint
				_panel.setShouldPlay(true);
				//Thread waiting for game to finish, than call database compare.
				new Thread() {
					public void run() {
						while (ex4Game.isRuning()) {
							
						}
						DataBase dataBase = new DataBase();
				    	dataBase.CompareToAll();
					}
				}.start();

			}
		});
		runMenu.add(runGame);
		JMenuItem autoRunGame = new JMenuItem("Auto Run");
		autoRunGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//start game and paint
				ex4Game.start();
				_panel.setShouldPlay(true);
				//Thread to play automatic, using Ex4Algo
				new Thread()
				{
				    public void run() {
				    	while(ex4Game.isRuning()) {
							Meta_Data_Analyze mda = new Meta_Data_Analyze(ex4Game);
							Ex4Algo algo = new Ex4Algo(mda, _panel.frameSizePixels);
							algo.setFSize(_panel.frameSizePixels);
							_panel.setAngle(algo.WhereToMove());
							System.out.println(ex4Game.getStatistics());
							try {
								sleep(100);
							}catch (Exception e) {
								// TODO: handle exception
							}
						}
				    	DataBase dataBase = new DataBase();
				    	dataBase.CompareToAll();
				    }
				}.start();
				
				
			}
		});
		runMenu.add(autoRunGame);
	}
	/**
	 * Create 'File' Menu
	 */
	private void createFileMenu() {
		JMenuItem open = new JMenuItem("Open File");
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
					_panel.setPlay(ex4Game);
					_panel.setDrawFigures(true);
				}
				//				shouldDrawFigures = true;
			}

		});
		fileMenu.add(open);
	}

	/**
	 * Initialize panel with back ground image
	 */
	private void createPanel() {
		_panel = new JPanelWithBG(this);
		_panel.setLayout(new BorderLayout());
		_panel.addMouseListener(this);
		this.add(_panel);
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	//right click to place main pacman
	//left click to 'rotate' game
	@Override
	public void mousePressed(MouseEvent e) {
		mouseClick = new Point2D(e.getX(), e.getY());
		Point3D mouseCoords = m.PixelToCoords(mouseClick, _panel.frameSizePixels);
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
			_panel.setAngle(angle);
			//			shouldPlay = true;

		}

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		Ex4GameFrame mainGameFrame = new Ex4GameFrame();
		mainGameFrame.setSize(d);
		mainGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainGameFrame.setVisible(true);

	}

}
