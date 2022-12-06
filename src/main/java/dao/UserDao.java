package dao;

import java.sql.SQLException;

import model.User;

/**
 * A data access object (DAO) is a pattern that provides an abstract interface 
 * to a database or other persistence mechanism. 
 * the DAO maps application calls to the persistence layer and provides some specific data operations 
 * without exposing details of the database. 
 */
public interface UserDao {
	void setup() throws SQLException;
	User getUser(String username, String password) throws SQLException;
	User createUser(String username, String password, String firstName, String lastName) throws SQLException;
	String getUsername(User user) throws SQLException;
	String getFirstName(User user) throws SQLException;
	String getLastName(User user) throws SQLException;
	void updateProfile(String firstName, String lastName, String username);
}
