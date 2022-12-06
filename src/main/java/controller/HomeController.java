package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import model.Model;


public class HomeController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private MenuItem viewRecord; 
	@FXML
	private MenuItem viewProfile; 
	@FXML
	private MenuItem updateProfile; 
	@FXML
	private MenuItem exportRecord; 
	@FXML
	private MenuItem about; 
	@FXML
	private MenuItem logOut; 
	@FXML
	private Label welcomeMessage;
	public HomeController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	

	public void initialize() {
		//Displaying welcome message
		try {
			welcomeMessage.setText("Welcome " + model.getUserDao().getFirstName(model.getCurrentUser())
					+ " " + model.getUserDao().getLastName(model.getCurrentUser()));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		viewRecord.setOnAction(event->{
			// navigate to view record page 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AllRecordView.fxml"));
			ViewRecordController viewRecordController = new ViewRecordController(stage, model);
			
			loader.setController(viewRecordController);
			GridPane root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			viewRecordController.showStage(root);
			stage.close();
		});
		viewProfile.setOnAction(event->{
			// navigate to view profile page 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileView.fxml"));
			ProfileViewController profileViewController = new ProfileViewController(stage, model);
			
			loader.setController(profileViewController);
			GridPane root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			profileViewController.showStage(root);
			stage.close();
		});
		updateProfile.setOnAction(event->{
			// navigate to update profile page 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ProfileEditView.fxml"));
			UpdateProfileController updateProfileController = new UpdateProfileController(stage, model);
			
			loader.setController(updateProfileController);
			GridPane root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			updateProfileController.showStage(root);
			stage.close();
		});
		exportRecord.setOnAction(event->{
			// navigate to export record page 
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExportRecordView.fxml"));
			ExportRecordController exportRecordController = new ExportRecordController(stage, model);
			
			loader.setController(exportRecordController);
			GridPane root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			exportRecordController.showStage(root);
			stage.close();
		});
		logOut.setOnAction(event ->{
			// navigate to view profile page, set current user to null so that the log in process can be continue
			model.setCurrentUser(null);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginView.fxml"));
			LoginController loginController = new LoginController(stage, model);
			
			loader.setController(loginController);
			GridPane root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			loginController.showStage(root);
		});
		about.setOnAction(event ->{
			// Show the version page
			Alert information = new Alert(AlertType.INFORMATION);
			information.setTitle("About");
			information.setHeaderText("MyHealth");
			information.setContentText("Version"+" "+System.getProperty("javafx.runtime.version"));
			information.showAndWait();
		});
	}
	
	
	
	public void showStage(Pane root) {
		Scene scene = new Scene(root, 600, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("MyHealth");
		stage.show();
	}
}
