package com.sudokusolver.sudokusolver;

import java.util.Random;

public class Sudoku {
	private static final int SIZE = 9;

	public static int[][] generatePuzzle() {
		int[][] puzzle = new int[SIZE][SIZE];
		Random rand = new Random();

		// Fill the puzzle with valid numbers
		fillDiagonalBoxes(puzzle);
		solveSudoku(puzzle);

		// Set some cells as blanks (0)
		setBlanks(puzzle, rand);

		return puzzle;
	}

	private static void fillDiagonalBoxes(int[][] puzzle) {
		for (int box = 0; box < SIZE; box += 3) {
			fillBox(puzzle, box, box);
		}
	}

	private static void fillBox(int[][] puzzle, int startRow, int startCol) {
		Random rand = new Random();
		int[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		shuffleArray(numbers);

		int numIndex = 0;
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				puzzle[startRow + row][startCol + col] = numbers[numIndex];
				numIndex++;
			}
		}
	}

	private static void shuffleArray(int[] array) {
		Random rand = new Random();
		for (int i = array.length - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1);
			int temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
	}

	public static boolean solveSudoku(int[][] puzzle) {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if (puzzle[row][col] == 0) {
					for (int num = 1; num <= SIZE; num++) {
						if (isValid(puzzle, row, col, num)) {
							puzzle[row][col] = num;

							if (solveSudoku(puzzle)) {
								return true;
							} else {
								puzzle[row][col] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	public static int[][] solvePuzzle(int[][] puzzle) {
		int[][] solution = new int[SIZE][SIZE];
		if (solve(puzzle, solution, 0, 0)) {
			return solution; // Return the solved puzzle
		} else {
			return null; // Puzzle is unsolvable
		}
	}

	private static boolean solve(int[][] puzzle, int[][] solution, int row, int col) {
		if (row == SIZE) {
			row = 0;
			if (++col == SIZE) {
				return true; // Puzzle solved
			}
		}

		if (puzzle[row][col] != 0) {
			// Copy the original value to the solution array
			solution[row][col] = puzzle[row][col];
			return solve(puzzle, solution, row + 1, col);
		}

		for (int num = 1; num <= SIZE; num++) {
			if (isValidMove(puzzle, row, col, num)) {
				puzzle[row][col] = num;
				solution[row][col] = num; // Assign the value in the solution array

				if (solve(puzzle, solution, row + 1, col)) {
					return true; // Solution found
				}

				puzzle[row][col] = 0; // Reset the cell in the puzzle array
				solution[row][col] = 0; // Reset the cell in the solution array
			}
		}

		return false; // No solution found
	}

	private static boolean isValidMove(int[][] puzzle, int row, int col, int num) {
		// Check row and column
		for (int i = 0; i < SIZE; i++) {
			if (puzzle[row][i] == num || puzzle[i][col] == num) {
				return false;
			}
		}

		// Check 3x3 box
		int boxRow = (row / 3) * 3;
		int boxCol = (col / 3) * 3;
		for (int i = boxRow; i < boxRow + 3; i++) {
			for (int j = boxCol; j < boxCol + 3; j++) {
				if (puzzle[i][j] == num) {
					return false;
				}
			}
		}

		return true; // Move is valid
	}

	public static boolean validateSudoku(int[][] puzzle) {
		// Check rows
		for (int row = 0; row < SIZE; row++) {
			if (!isValidGroup(puzzle[row])) {
				return false;
			}
		}
		return true;
	}

	public static boolean isValid(int[][] puzzle, int row, int col, int num) {
		return !isUsedInRow(puzzle, row, num) &&
				!isUsedInColumn(puzzle, col, num) &&
				!isUsedInBox(puzzle, row - row % 3, col - col % 3, num);
	}
	private static boolean isValidGroup(int[] group) {
		boolean[] visited = new boolean[SIZE];
		for (int num : group) {
			if (num != 0) {
				if (visited[num - 1]) {
					return false; // Duplicate number found
				} else {
					visited[num - 1] = true;
				}
			}
		}
		return true;
	}

	private static boolean isUsedInRow(int[][] puzzle, int row, int num) {
		for (int col = 0; col < SIZE; col++) {
			if (puzzle[row][col] == num) {
				return true;
			}
		}
		return false;
	}

	private static boolean isUsedInColumn(int[][] puzzle, int col, int num) {
		for (int row = 0; row < SIZE; row++) {
			if (puzzle[row][col] == num) {
				return true;
			}
		}
		return false;
	}

	private static boolean isUsedInBox(int[][] puzzle, int boxStartRow, int boxStartCol, int num) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (puzzle[boxStartRow + row][boxStartCol + col] == num) {
					return true;
				}
			}
		}
		return false;
	}

	private static void setBlanks(int[][] puzzle, Random rand) {
		int numBlanks = 40; // Number of blank cells to set

		while (numBlanks > 0) {
			int row = rand.nextInt(SIZE);
			int col = rand.nextInt(SIZE);

			if (puzzle[row][col] != 0) {
				puzzle[row][col] = 0;
				numBlanks--;
			}
		}
	}
}
