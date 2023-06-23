package com.sudokusolver.sudokusolver;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
	private static final int SIZE = 9;

	public void start(Stage primaryStage) throws IOException {

		Sudoku sudoku = new Sudoku();
		int[][] puzzle = sudoku.generatePuzzle();
		GridPane gridPane = createGridPane(puzzle);
		VBox box = new VBox();
		Button solveButton = new Button();
		solveButton.setText("Solve");
		solveButton.setAlignment(Pos.BASELINE_CENTER);
		solveButton.setOnAction(e-> solvePuzzle(gridPane, sudoku, puzzle));
		box.getChildren().addAll(gridPane, solveButton);
		box.setAlignment(Pos.CENTER);
		Scene scene = new Scene(box, 400, 400);

		primaryStage.setTitle("Sudoku Grid");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void solvePuzzle(GridPane gridPane, Sudoku sudoku, int[][] puzzle) {
		int[][] solution = new int[SIZE][SIZE];
		boolean hasBlankSpace = false;
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				TextField textField = (TextField) gridPane.getChildren().get(row * SIZE + col);
				String text = textField.getText();
				if (text.isEmpty()) {
					hasBlankSpace = true;
					showTooltip(textField, "Please fill in all cells");
					return; // Exit the method if a blank space is found
				}
				solution[row][col] = Integer.parseInt(text);
			}
		}

		if (hasBlankSpace) {
			return; // Don't proceed further if there is a blank space
		}

		boolean isSolved = Sudoku.validateSudoku(solution);
		if (isSolved) {
			System.out.println("Congratulations! You solved the puzzle!");
		} else {
			System.out.println("Sorry, the puzzle is not solved correctly.");
			System.out.println("The correct solution is:");
			int[][] solvedPuzzle = Sudoku.solvePuzzle(puzzle);
			displaySolution(solvedPuzzle, gridPane);
		}
	}

	private void displaySolution(int[][] solution, GridPane gridPane) {
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				TextField textField = (TextField) gridPane.getChildren().get(row * SIZE + col);
				textField.setText(Integer.toString(solution[row][col]));
			}
		}
	}

	private void showTooltip(TextField textField, String message) {
		Tooltip tooltip = new Tooltip(message);
		tooltip.setAutoHide(true);
		tooltip.show(textField, textField.getScene().getWindow().getX() + textField.getScene().getWindow().getWidth(),
				textField.getScene().getWindow().getY());
	}

	private GridPane createGridPane(int[][] puzzle) {    GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(5);
		gridPane.setVgap(5);

		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				int value = puzzle[row][col];
				if (value == 0) {
					TextField cellField = new TextField();
					cellField.textProperty().addListener((observable, oldValue, newValue) -> {
						if (newValue.length() > 1) {
							cellField.setText(newValue.substring(0, 1));
						}
					});
					cellField.setMaxSize(30, 30);
					cellField.setMinSize(30, 30);
					cellField.setStyle("-fx-border-color: black;");
					cellField.setAlignment(Pos.CENTER);
					gridPane.add(cellField, col, row);
				} else {
					TextField cellLabel = new TextField(String.valueOf(value));
					cellLabel.setEditable(false);
					cellLabel.setMaxSize(30, 30);
					cellLabel.setMinSize(30, 30);
					cellLabel.setStyle("-fx-border-color: black;");
					cellLabel.setAlignment(Pos.CENTER);
					gridPane.add(cellLabel, col, row);
				}
			}
		}

		return gridPane;
	}


	public static void main(String[] args) {
		launch();
	}
}