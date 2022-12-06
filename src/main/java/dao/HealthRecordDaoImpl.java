package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.HealthRecord;


public class HealthRecordDaoImpl implements HealthRecordDao {
	private final String TABLE_NAME = "healthRecords";

	public HealthRecordDaoImpl() {
	}
	
	// Setting up a database table of health record
	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (recordID INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "username VARCHAR(10) NOT NULL,"
					+ "date VARCHAR(10) NOT NULL, weight DECIMAL(10,2), "
					+ "temperature DECIMAL(10,2), lowBloodPressure INT(255), "
					+ "highBloodPressure INT(255), note VARCHAR(255), "
					+ "CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users(username))";
			stmt.executeUpdate(sql);
		} 
	}
	// Add the record into the database with the given attribute
	@Override
	public HealthRecord createRecord(String username, String date, double weight, double temperature, 
			int lowBloodPressure, int hightBloodPressure, String note) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?,?,?,?,?,?,?,?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				) {
			stmt.setString(2, username);
			stmt.setString(3, date);
			stmt.setDouble(4, weight);
			stmt.setDouble(5, temperature);
			stmt.setInt(6, lowBloodPressure);
			stmt.setInt(7, hightBloodPressure);
			stmt.setString(8, note);
			stmt.executeUpdate();			
			return new HealthRecord(Statement.RETURN_GENERATED_KEYS, username, date, weight, temperature, lowBloodPressure, hightBloodPressure , note);
		} 
	}
	// Get all records of the current user
	@Override
	public ObservableList<HealthRecord> getRecordList(String username) throws SQLException {
		ObservableList<HealthRecord> obList = FXCollections.observableArrayList();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				obList.add(new HealthRecord(rs.getInt("recordId"),rs.getString("username"), rs.getString("date")
						, rs.getDouble("weight"), rs.getDouble("temperature")
						, rs.getInt("lowBloodPressure"), rs.getInt("highBloodPressure")
						, rs.getString("note")));
			}
			return obList;
		} 
	}
	// Delete a record with a given record id, which is auto-generated every time a record is created
	@Override
	public void deleteRecord(long id) {
		String sql = "DELETE FROM " + TABLE_NAME + " WHERE recordId = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			stmt.setLong(1, id);	
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	// Update a record with the new attribute for a given record id
	@Override
	public void updateRecord(double weight, double temperature, int lowBloodPressurem, int highBloodPressure
			, String note, long id) {
		String sql = "UPDATE " + TABLE_NAME + " SET weight = ?, temperature = ?, lowBloodPressure = ?"
				+ ", highBloodPressure = ?, note = ? WHERE  recordId = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			stmt.setDouble(1, weight);
			stmt.setDouble(2, temperature);
			stmt.setInt(3, lowBloodPressurem);
			stmt.setInt(4, highBloodPressure);
			stmt.setString(5, note);
			stmt.setLong(6, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
