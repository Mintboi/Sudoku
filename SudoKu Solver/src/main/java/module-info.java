module com.sudokusolver.sudokusolver {
	requires javafx.controls;
	requires javafx.fxml;


	opens com.sudokusolver.sudokusolver to javafx.fxml;
	exports com.sudokusolver.sudokusolver;
}