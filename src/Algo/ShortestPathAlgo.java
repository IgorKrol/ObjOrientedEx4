package Algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Coords.MyCoords;
import GameComponents.Fruit;
import GameComponents.Game;
import GameComponents.Pacman;
import GameComponents.Path;

/**
 * This class responsible for finding the Shortest path calculation for a given game.
 * @author Igor_Krol & Amit_Kazado
 *
 */
public class ShortestPathAlgo {


	private Game game;
	//matrix for pacmans/fruits. pi = pacman, fi = fruits
	//PathBoard.length = pacmans, PathBoard[0].length = fruits
	private double PathBoard[][];


	public ShortestPathAlgo(Game g) {
		this.game = g;
		PathBoard = new double[g.getPacmans().size()][g.getFruits().size()];
		
		Iterator<Pacman> pacIterator = g.getPacmans().iterator();
		for(int pi = 0; pacIterator.hasNext(); pi++) {
			Pacman pac = pacIterator.next();
			Iterator<Fruit> fruIterator = g.getFruits().iterator();
			for(int fi = 0; fruIterator.hasNext(); fi++) {
				Fruit fru = fruIterator.next();
				PathBoard[pi][fi]= timePacmanToFruit(pac, fru);
			}
		}//END PATHBOARD CREATION
		//CHECK
//		for(int i = 0; i < PathBoard.length; i++) {
//			for(int j = 0; j < PathBoard[0].length; j++)
//				System.out.print((int)PathBoard[i][j] + ", ");
//			System.out.println();}
		
		for(int i = 0; i < PathBoard[0].length; i++) {
			int[] minIndex = MatrixMin(PathBoard);
//			System.out.println(minIndex[0] +"," + minIndex[1] + ":" + PathBoard[minIndex[0]][minIndex[1]]);
			double time = PathBoard[minIndex[0]][minIndex[1]];
			Pacman eP = game.getPacmans().get(minIndex[0]);			
			Fruit eF = game.getFruits().get(minIndex[1]);			
			eP.getPath().add(eF.getCoords());
			eP.getPath().addTime(time);
			AddRemoveRowCol(minIndex);
		}
	}
	/**
	 * for returning game after path been calculated
	 * @return
	 */
	public Game getGameWithPaths() {
		return game;
	}

	/**
	 * Funtion to calculate the time when pacman reaches fruit
	 * @param p = pacman
	 * @param f = fruit
	 * @return double time;
	 */
	private double timePacmanToFruit(Pacman p, Fruit f) {

		MyCoords mc = new MyCoords();
		double t = 0;
		double distance = mc.distance3d(p.getCoords(), f.getCoords());
		t = distance / p.getSpeed();

		return t;
	}
	/**
	 * Function returns int of min value in given matrix
	 * @param mat = given matrix
	 * @return int[2]: [i,j]
	 */
	private int[] MatrixMin(double[][] mat) {
		int i=0,j=0;
		double min = Double.MAX_VALUE;
		int[] index = new int[2];
		for(i=0; i<mat.length; i++) {
			for (j=0; j<mat[0].length; j++) {
				if (min > mat[i][j] && mat[i][j] >= 0) {
					min = mat[i][j];
					index[0] = i;
					index[1] = j;
				}
			}
		}


		return index;
	}

	/**
	 * returns time value at given index
	 * @param i
	 * @param j
	 * @return
	 */
	public double getTime(int i,int j) {
		return PathBoard[i][j];
	}
	/**
	 * recalculate time for specific pacman to eat all fruits
	 * @param row = pacman row
	 */
	public void reCalculatePacmanRow(int[] index, double time) {
//		Iterator<Fruit> fruIterator = game.getFruits().iterator();
		Pacman ePac = new Pacman(game.getFruits().get(index[1]).getCoords(),
				game.getPacmans().get(index[0]).getSpeed(),
				game.getPacmans().get(index[0]).getRadius());
		Iterator<Fruit> eFru = game.getFruits().iterator();
		for(int fi = 0; fi < PathBoard[0].length; fi++) {
			Pacman c;
			Fruit ff = eFru.next();
			if (PathBoard[index[0]][fi] >= 0) {
				PathBoard[index[0]][fi]= timePacmanToFruit(ePac, ff) + time;
			}
		}
	}
	/**
	 * This function Adding wasted time to used pacman and removing eaten fruit
	 * @param minIndex = index of min time in PathBoard
	 */
	private void AddRemoveRowCol(int[] minIndex) {
		int[] index = minIndex;//index = [i,j]			// why? useless, points to the same adress
		double timeAdd = getTime(index[0], index[1]);

		reCalculatePacmanRow(minIndex,timeAdd);
		//Adding wasted time to time board
//		for (int pi=0; pi<PathBoard[0].length; pi++) {
//			if (PathBoard[index[0]][pi] != 0)
//				PathBoard[index[0]][pi]+=timeAdd;
//		}
		//Removing fruit from board by nullifying value.
		for (int fi = 0; fi<PathBoard.length; fi++) {
			PathBoard[fi][index[1]]=-2;
		}

	}



}
