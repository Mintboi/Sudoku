package com.sudokusolver.sudokusolver;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.Random;

import java.util.Random;

public class Controller {
	@FXML
	private Label welcomeText;

	@FXML
	protected void onHelloButtonClick() {
		welcomeText.setText("Welcome to JavaFX Application!");
	}

}