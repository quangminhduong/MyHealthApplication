package controller;


import java.sql.SQLException;

import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import model.Model;

public class ProfileViewController {
	private Stage stage;
	private Stage parentStage;
	private Model model;
	@FXML
	private Label username;
	@FXML
	private Label firstName;
	@FXML
	private Label lastName;
	@FXML
	private Button back;
	@FXML
	private TextField userameText;

	public ProfileViewController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	public void initialize() {
		try {
			// populate user's info into the page
			username.setText(model.getUserDao().getUsername(model.getCurrentUser()));
			firstName.setText(model.getUserDao().getFirstName(model.getCurrentUser()));
			lastName.setText(model.getUserDao().getLastName(model.getCurrentUser()));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		back.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Personal Profile");
		stage.show();
	}
}
