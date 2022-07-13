package com.cp.gol;

public class GameOfLife {
	
	/**
	 * @param grid
	 * Utility function to print the grid 
	 */
	public void printGrid(boolean[][] grid) {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				System.out.print((grid[i][j] ? "*" : ".") +" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * @param grid
	 * @param position
	 * @return alive neighbors
	 * This function counts the number of alive neighbors for given position in current generation
	 */
	public int countAliveNeighbours(boolean[][] grid, int i, int j) {
		int alive = 0;
		boolean isFirstCol = j == 0;
		boolean isLastCol = j == grid[0].length - 1;
		if(i > 0) {
			if(!isFirstCol && grid[i - 1][j - 1])
				alive++;
			if(grid[i - 1][j])
				alive++;
			if(!isLastCol && grid[i - 1][j + 1])
				alive++;
		}
		if(!isFirstCol && grid[i][j - 1])
			alive++;
		if(!isLastCol && grid[i][j + 1])
			alive++;
		if(i < grid.length - 1) {
			if(!isFirstCol && grid[i + 1][j - 1])
				alive++;
			if(grid[i + 1][j])
				alive++;
			if(!isLastCol && grid[i + 1][j + 1])
				alive++;
		}
		
		return alive;
	}
	
	/**
	 * @param isAlive
	 * @param aliveNeighbours
	 * @return isAlive
	 * Using the rules of Game of life this method determines if this cell will be alive in next generation
	 * 1. An alive cell will remain alive only if it has either 2 or 3 alive neighbors
	 * 2. A dead cell will become live only if it has exactly 3 live neighbors
	 */
	public boolean applyRules(boolean isAlive, int aliveNeighbours) {
		return isAlive ? aliveNeighbours == 2 || aliveNeighbours == 3
				: aliveNeighbours == 3;
	}
	
	/**
	 * @param grid
	 * @return next generation grid
	 * This function takes the current generation and calculates the next generation
	 */
	public boolean[][] nextGeneration(boolean[][] grid) {
		boolean[][] nextGen = new boolean[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				int aliveNeighbors = countAliveNeighbours(grid, i, j);
				nextGen[i][j] = applyRules(grid[i][j], aliveNeighbors);
			}
		}
		return nextGen;
	}
	
	/**
	 * @param grid
	 * @param generations
	 * @throws InterruptedException
	 */
	public void printNextGenerations(boolean[][] grid, int generations) throws InterruptedException {
		boolean[][] currentGeneration = grid;
		for(int i = 0; i < generations; i++) {
			Thread.sleep(500);
			boolean[][] nextGeneration = nextGeneration(currentGeneration);
			printGrid(nextGeneration);
			currentGeneration = nextGeneration;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		boolean[][] gridConfig = new boolean[15][15];
		GameOfLife game = new GameOfLife();
		game.printGrid(gridConfig);
//		Glider pattern
		gridConfig[1][2] = true;
		gridConfig[2][3] = true;
		gridConfig[3][1] = true;
		gridConfig[3][2] = true;
		gridConfig[3][3] = true;

		/*
		Oscillator pattern
		gridConfig[1][1] = true;
		gridConfig[1][2] = true;
		gridConfig[1][3] = true;
		
		Still Life pattern
		gridConfig[1][1] = true;
		gridConfig[1][2] = true;
		gridConfig[2][1] = true;
		gridConfig[2][2] = true;
		
		*/
		
		
		game.printNextGenerations(gridConfig, 10);
	}

}
