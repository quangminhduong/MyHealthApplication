package controller;

import java.io.IOException;
import java.sql.SQLException;

import java.util.Optional;

import javafx.beans.binding.Bindings;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;

import javafx.scene.control.Alert.AlertType;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;

public class ViewRecordController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private TableView <HealthRecord> table;
	@FXML
	private TableColumn <HealthRecord,String> colDate;
	@FXML
	private TableColumn <HealthRecord,Double> colWeight;
	@FXML
	private TableColumn <HealthRecord,Double> colTemperature;
	@FXML
	private TableColumn <HealthRecord,String> colBloodPressure;
	@FXML
	private TableColumn <HealthRecord,String> colNotes;
	@FXML
	private Button back;
	@FXML
	private Button editRecord;
	@FXML
	private Button addRecord;
	@FXML
	private Button deleteRecord;
	ObservableList<HealthRecord> obList;
	public ViewRecordController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	public void initialize() {
		try {
			obList = model.getHealthRecordDao().getRecordList(model.getCurrentUser().getUsername());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Populate data from the database into the table
		colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		colWeight.setCellValueFactory(cellData -> cellData.getValue().weightProperty().asObject());
		colTemperature.setCellValueFactory(cellData -> cellData.getValue().temperatureProperty().asObject());
		// Displaying blood pressure as "lowBloodPressure" - "highBloodPressure"
		colBloodPressure.setCellValueFactory(cellData -> Bindings.createStringBinding(
			    () -> cellData.getValue().getLowBloodPressure() + " - " + cellData.getValue().getHighBloodPressure(),
			    cellData.getValue().lowBloodPressureProperty(),
			    cellData.getValue().highBloodPressureProperty()));
		colNotes.setCellValueFactory(cellData -> cellData.getValue().noteProperty());
		table.setItems(obList);
		editRecord.setOnAction(event->{
			if(table.getSelectionModel().getSelectedItem()==null) {
				// ensure that an item has been chose to edit
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Error");
				error.setHeaderText("Please choose a record to proceed!");
				error.show();
			}
			else {
				// navigate to edit record page 
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditRecordView.fxml"));
				EditRecordController editRecordController = new EditRecordController(stage, model);
				// send the selected record over to edit record page
				editRecordController.setHealthRecord(table.getSelectionModel().getSelectedItem());
				loader.setController(editRecordController);
				GridPane root = null;
				try {
					root = loader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				editRecordController.showStage(root);
				stage.close();
			}
			
		});
		addRecord.setOnAction(event->{
			try {
				// navigate to add record page
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddRecordView.fxml"));
				AddRecordController addRecordController = new AddRecordController(stage, model);
				
				loader.setController(addRecordController);
				GridPane root = loader.load();

				addRecordController.showStage(root);
				stage.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		});
		deleteRecord.setOnAction(event ->{
			if(table.getSelectionModel().getSelectedItem()==null) {
				// ensure that an item has been chose to delete
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Error");
				error.setHeaderText("Please choose a record to proceed!");
				error.show();
			}
			else {
				Alert confirmation = new Alert(AlertType.CONFIRMATION);
				confirmation.setTitle("Delete record");
				confirmation.setHeaderText("Do you want to delete this records?");
				Optional<ButtonType> result = confirmation.showAndWait();
				if(result.get() == ButtonType.OK) {
					// Only delete the record if user has confirmed it
					deleteRecordFromTable(event);
				}
			}
		});
		back.setOnAction(event -> {
			try {
				//back to home screen
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/HomeView.fxml"));
				HomeController homeController = new HomeController(stage, model);
				loader.setController(homeController);
				VBox root = loader.load();
				homeController.showStage(root);
				stage.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	@FXML
	private void deleteRecordFromTable(ActionEvent event) {
		// get selected record and delete it from the table view and the database
		HealthRecord selectedHealthRecord = table.getSelectionModel().getSelectedItem();
		model.getHealthRecordDao().deleteRecord(selectedHealthRecord.getId());
		table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
	}
	
	public void showStage(GridPane root) {
		Scene scene = new Scene(root, 620, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("View all existing record");
		stage.show();
	}

}
