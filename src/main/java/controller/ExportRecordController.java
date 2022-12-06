package controller;

import java.io.IOException;
import java.sql.SQLException;

import java.util.ArrayList;

import java.util.Optional;

import javafx.beans.binding.Bindings;

import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;

import javafx.scene.control.Alert.AlertType;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;

public class ExportRecordController {
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
	private Button ok;
	@FXML
	ObservableList<HealthRecord> obList;
	public ExportRecordController(Stage parentStage, Model model) {
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
		// Ensure that user can select multiple items
		table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		ArrayList<HealthRecord> choseRecords = new ArrayList<HealthRecord>();
		table.setOnMousePressed(new EventHandler<MouseEvent>() {
			// this method will simply add any clicked elements into the list, therefore duplication of elements will be the case here
            @Override
            public void handle(MouseEvent event) {
                ObservableList<HealthRecord> selectedItems = table.getSelectionModel().getSelectedItems();
                // Add the chosen record(s) with no duplication to the list
                for (int i = 0;i < selectedItems.size();i++) {
                	if (!choseRecords.contains(selectedItems.get(i))) {
                		choseRecords.add(selectedItems.get(i));
                	}
                }
            }
        });
		ok.setOnAction(event -> {
			if(choseRecords.size()==0) {
				// Make sure that user has chose some record to export
				Alert confirmation = new Alert(AlertType.ERROR);
				confirmation.setTitle("Error");
				confirmation.setHeaderText("Please select one or more records to proceed?");
				confirmation.show();
			}
			else {
				Alert confirmation = new Alert(AlertType.CONFIRMATION);
				confirmation.setTitle("Export records");
				confirmation.setHeaderText("Do you want to export these records?");
				Optional<ButtonType> result = confirmation.showAndWait();
				if(result.get() == ButtonType.OK) {

					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChooseDirectoryView.fxml"));
					ChooseDirectoryController chooseDirectoryController = new ChooseDirectoryController(stage, model);
					// send the chosen records to the next page
					chooseDirectoryController.setHealthRecord(choseRecords);
					loader.setController(chooseDirectoryController);
					GridPane root = null;
					try {
						root = loader.load();
					} catch (IOException e) {
						e.printStackTrace();
					}
					chooseDirectoryController.showStage(root);
					confirmation.close();
				}
			}
			
		});
		back.setOnAction(event -> {
			stage.close();
			parentStage.show();
		});
	}

	public void showStage(GridPane root) {
		Scene scene = new Scene(root, 620, 500);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Export records");
		stage.show();
	}
	
}
