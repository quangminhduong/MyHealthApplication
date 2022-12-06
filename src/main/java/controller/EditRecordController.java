package controller;

import java.io.IOException;
import java.sql.SQLException;

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

public class EditRecordController {
	private HealthRecord healthRecord;
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
	private Button update;
	@FXML
	private Button cancel;
	public EditRecordController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	public void initialize() throws SQLException {
		// Retrieve the record attribute and populate it into page
		weight.setText(Double.toString(healthRecord.getWeight()));
		temperature.setText(Double.toString(healthRecord.getTemperature()));
		lowBloodPressure.setText(String.valueOf(healthRecord.getLowBloodPressure()));
		highBloodPressure.setText(String.valueOf(healthRecord.getHighBloodPressure()));
		note.setText(healthRecord.getNote());
		update.setOnAction(event -> {
			if (!weight.getText().isEmpty() || !temperature.getText().isEmpty() 
					|| !lowBloodPressure.getText().isEmpty() || !highBloodPressure.getText().isEmpty() 
					|| !note.getText().isEmpty()) {
				// Ensure that there is no empty input
				// Modify it in the database
				model.getHealthRecordDao().updateRecord(Double.parseDouble(weight.getText())
						,Double.parseDouble(temperature.getText()) 
						, Integer.parseInt(lowBloodPressure.getText()), Integer.parseInt(highBloodPressure.getText()) 
						, note.getText(), healthRecord.getId());
	
				status.setText("Record modified");
				status.setTextFill(Color.GREEN);
					
				
			} else {
				status.setText("Empty input detected");
				status.setTextFill(Color.RED);
			}
		});
		cancel.setOnAction(event -> {
			// Navigate back to view record page and refresh it to ensure that the edited record can be view
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
		stage.setTitle("Update current record");
		stage.show();
		
	}
	public void setHealthRecord(HealthRecord selectedItem) {
		this.healthRecord = selectedItem;		
	}

}
