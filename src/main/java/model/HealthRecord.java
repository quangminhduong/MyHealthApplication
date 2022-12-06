package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class HealthRecord {
	private LongProperty id;
	private StringProperty username;
	private StringProperty date;
	private DoubleProperty weight;
	private DoubleProperty temperature;
	private IntegerProperty lowBloodPressure;
	private IntegerProperty highBloodPressure;
	private StringProperty note;
	public HealthRecord() {
	}
	
	public HealthRecord(long id, String username, String date, double weight, double temperature, 
			int lowBloodPressure, int hightBloodPressure, String note) {
		this.id = new SimpleLongProperty(id);
		this.username = new SimpleStringProperty(username);
		this.date = new SimpleStringProperty(date);
		this.weight = new SimpleDoubleProperty(weight);
		this.temperature = new SimpleDoubleProperty(temperature);
		this.lowBloodPressure = new SimpleIntegerProperty(lowBloodPressure);
		this.highBloodPressure = new SimpleIntegerProperty(hightBloodPressure);
		this.note = new SimpleStringProperty(note);
	}
	
	public long getId() {
		return id.get();
	}

	public void setId(long id) {
		this.id.set(id);
	}
	
	public LongProperty idProperty() {
		return id;
	}
	
	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}
	
	public StringProperty usernameProperty() {
		return username;
	}
	
	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}
	
	public StringProperty dateProperty() {
		return date;
	}

	public double getWeight() {
		return weight.get();
	}

	public void setWeight(double weight) {
		this.weight.set(weight);
	}
	
	public DoubleProperty weightProperty() {
		return weight;
	}

	public double getTemperature() {
		return temperature.get();
	}

	public void setTemperature(double temperature) {
		this.temperature.set(temperature);
	}
	
	public DoubleProperty temperatureProperty() {
		return temperature;
	}

	public int getLowBloodPressure() {
		return lowBloodPressure.get();
	}

	public void setLowBloodPressure(int lowBloodPressure) {
		this.lowBloodPressure.set(lowBloodPressure);
	}
	
	public IntegerProperty lowBloodPressureProperty() {
		return lowBloodPressure;
	}
	
	public int getHighBloodPressure() {
		return highBloodPressure.get();
	}

	public void setHighBloodPressure(int highBloodPressure) {
		this.highBloodPressure.set(highBloodPressure);
	}
	
	public IntegerProperty highBloodPressureProperty() {
		return highBloodPressure;
	}

	public String getNote() {
		return note.get();
	}

	public void setNote(String note) {
		this.note.set(note);
	}
	
	public StringProperty noteProperty() {
		return note;
	}
	
}