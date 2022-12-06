package controller;


import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;


public class SignupController {
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button createUser;
	@FXML
	private Button close;
	@FXML
	private Label status;
	
	private Stage stage;
	private Stage parentStage;
	private Model model;
	
	public SignupController(Stage parentStage, Model model) {
		// Initialize sign up panel
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}

	@FXML
	public void initialize() {
		// Generate events as user click on create user
		createUser.setOnAction(event -> {
			if (!username.getText().isEmpty() && !password.getText().isEmpty()
					&& !firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
				User user;
				try {
					
				      
					// add user to the database
					user = model.getUserDao().createUser(username.getText(), model.getPasswordHash(password.getText())
							, firstName.getText(), firstName.getText());
					if (user != null) {
						status.setText("Created " + user.getUsername());
						status.setTextFill(Color.GREEN);
					}
				} catch (SQLException e) {
					// Ask the user to input again if username existed
					if(e.toString().equals("org.sqlite.SQLiteException: [SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed: users.username)")) {
						status.setText("Username existed, please try another!");
						status.setTextFill(Color.RED);
					}
					else {
						status.setText(e.getMessage());
						status.setTextFill(Color.RED);
					}
				}
				
			} else {
				status.setText("Empty input detected");
				status.setTextFill(Color.RED);
			}
		});
		//back to login page
		close.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 450);
		stage.setScene(scene);
		stage.setResizable(true);
		stage.setTitle("Sign up");
		stage.show();
	}
}
