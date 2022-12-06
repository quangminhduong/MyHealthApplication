package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;

public class AddRecordController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private TextField weight;
	@FXML
	private TextField temperature;
	@FXML
	private TextField lowBloodPressure;
	@FXML
	private TextField highBloodPressure;
	@FXML
	private TextArea note;
	@FXML
	private Label status;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;
	public AddRecordController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	public void initialize() { 
		ok.setOnAction(event -> {
			try {
				HealthRecord healthRecord;
				if (!weight.getText().isEmpty() || !temperature.getText().isEmpty() 
						|| !lowBloodPressure.getText().isEmpty() || !highBloodPressure.getText().isEmpty() 
						|| !note.getText().isEmpty()) {
					//Ensure that all elements are inputed
					
					try {
						// Auto-generate current time
						Date currentDate = Calendar.getInstance().getTime();  
						DateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");    
						String date = dateFormat.format(currentDate);
						// add the inputed field into the database
						healthRecord = model.getHealthRecordDao().createRecord(model.getCurrentUser().getUsername()
								, date, Double.parseDouble(weight.getText()),Double.parseDouble(temperature.getText()) 
								, Integer.parseInt(lowBloodPressure.getText()), Integer.parseInt(highBloodPressure.getText()) 
								, note.getText());
							status.setText("Record created");
							status.setTextFill(Color.GREEN);
					} catch (SQLException e) {
						status.setText(e.getMessage());
						status.setTextFill(Color.RED);
					}
					
				} else {
					status.setText("Empty input detected");
					status.setTextFill(Color.RED);
				}
			} catch (NumberFormatException e) {
				// ensure that user input a valid integer or double for weight, temperature, blood pressure
				status.setText("Please enter a valid number for weight, temperature and blood pressure");
				status.setTextFill(Color.RED);
			}
		});
		cancel.setOnAction(event -> {
			// Navigate back to view record page and refresh it to ensure that the added record can be view
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
	}
	public void showStage(GridPane root) {
		Scene scene = new Scene(root, 650, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Create new health record");
		stage.show();
		
	}

}
