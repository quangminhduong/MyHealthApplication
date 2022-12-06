package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import model.Model;

public class UpdateProfileController {
	private Stage stage;
	private Stage parentStage;
	private Model model;
	@FXML
	private Label username;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private Button confirm;
	@FXML
	private Button back;
	@FXML
	private Label status;

	public UpdateProfileController(Stage parentStage, Model model) {
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
		confirm.setOnAction(event -> {
			if (!firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
				model.getUserDao().updateProfile(firstName.getText(), lastName.getText()
						, model.getCurrentUser().getUsername());
				// Ensure that there is no empty input
				Alert information = new Alert(AlertType.INFORMATION);
				information.setTitle("");
				information.setHeaderText("Profile Updated");
				information.showAndWait();
				try {
					// Navigate back to home page and refresh it to ensure that the edited profile can be view
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
					HomeController homeController = new HomeController(stage, model);
					
					loader.setController(homeController);
					VBox root = loader.load();

					homeController.showStage(root);
					stage.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
				
			} else {
				status.setText("Empty input detected");
				status.setTextFill(Color.RED);
			}
		});
		back.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Update Personal Profile");
		stage.show();
	}
}
