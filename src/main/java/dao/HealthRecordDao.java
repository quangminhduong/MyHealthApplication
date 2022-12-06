package dao;

import java.sql.SQLException;

import javafx.collections.ObservableList;
import model.HealthRecord;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface HealthRecordDao {
	void setup() throws SQLException;

	HealthRecord createRecord(String username, String date, double weight, double temperature, int lowBloodPressure,
			int hightBloodPressure, String note) throws SQLException;

	ObservableList<HealthRecord> getRecordList(String username) throws SQLException;

	void deleteRecord(long id);
	void updateRecord(double weight, double temperature, int lowBloodPressurem, int highBloodPressure
			, String note, long id);
}
