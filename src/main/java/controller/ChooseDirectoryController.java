package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.HealthRecord;
import model.Model;

public class ChooseDirectoryController {
	private Model model;
	private Stage stage;
	private Stage parentStage;
	@FXML
	private TextField path;
	@FXML
	private TextField name;
	@FXML
	private Label status;
	@FXML
	private Button choose;
	@FXML
	private Button ok;
	@FXML
	private Button cancel;

	ArrayList<HealthRecord> choseRecord;
	
	public ChooseDirectoryController(Stage parentStage, Model model) {
		this.stage = new Stage();
		this.parentStage = parentStage;
		this.model = model;
	}
	
	public void initialize(){
		System.out.println(choseRecord);
		DirectoryChooser directoryChooser = new DirectoryChooser();
		// Set the initial path to the C directory, which is the default directory for any computer to ensure compatibility
		directoryChooser.setInitialDirectory(new File("C:/"));
		
		choose.setOnAction(event -> {
			//Display the chosen directory to the text field
			File selectedDirectory = directoryChooser.showDialog(stage);
			path.setText(selectedDirectory.getAbsolutePath());
		});
		ok.setOnAction(event ->{
			if(!path.getText().isEmpty() && !name.getText().isEmpty()) {
				// Ensure that the file path and file name is entered
				BufferedWriter writer = null;
				 try {
					 // save to a .csv file from the given path
			        File file = new File(path.getText()+"\\"+name.getText()+".csv");
			        writer = new BufferedWriter(new FileWriter(file));
			        // comma can be used to divide into columns in csv file
			        writer.write("Date,Weight(kg),Temperature,Blood Pressure,Notes");
			        writer.newLine();
			        for (int i =0;i<choseRecord.size();i++) {
			        	// write every record into the file
			        	writer.write(choseRecord.get(i).getDate()+","+choseRecord.get(i).getWeight()+","
			        			+ choseRecord.get(i).getTemperature()+","
			        			+ choseRecord.get(i).getLowBloodPressure()+"->"
			        			+ choseRecord.get(i).getHighBloodPressure()+","
			        			+ choseRecord.get(i).getNote());
			            writer.newLine();
				       
			        }
			        writer.close();
			        Alert information = new Alert(AlertType.INFORMATION);
			        information.setTitle("");
			        information.setHeaderText("Records exported!");
			        information.showAndWait();
					stage.close();
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    } 
			}
			else {
				Alert error = new Alert(AlertType.ERROR);
				error.setTitle("Error");
				error.setHeaderText("Please choose a file path to save your file or enter a valid file name!");
				error.show();
			}
			
		});
		
		cancel.setOnAction(event -> {
			stage.close();
		});
	}
	public void showStage(GridPane root) {
		Scene scene = new Scene(root, 450, 300);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Chose a directory to save the file");
		stage.show();
		
	}
	
	public void setHealthRecord(ArrayList<HealthRecord> choseRecord) {
		this.choseRecord = choseRecord;		
	}
}
