package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.User;

public class UserDaoImpl implements UserDao {
	private final String TABLE_NAME = "users";

	public UserDaoImpl() {
	}
	
	// Setting up a database table of user
	@Override
	public void setup() throws SQLException {
		try (Connection connection = Database.getConnection();
				Statement stmt = connection.createStatement();) {
			String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (username VARCHAR(10) NOT NULL,"
					+ "password VARCHAR(8) NOT NULL, firstName VARCHAR(10) NOT NULL, "
					+ "lastName VARCHAR(10) NOT NULL, " + "PRIMARY KEY (username))";
			stmt.executeUpdate(sql);
		} 
	}
	// Compare the inputed username and password to the databse
	@Override
	public User getUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE username = ? AND password = ?";
		try (Connection connection = Database.getConnection(); 
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					return user;
				}
				return null;
			} 
		}
	}
	// add the User into the database
	@Override
	public User createUser(String username, String password, String firstName, String lastName) throws SQLException {
		String sql = "INSERT INTO " + TABLE_NAME + " VALUES (?, ?,?,?)";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);
			stmt.executeUpdate();
			return new User(username, password, firstName, lastName);
		} 
	}
	// Retrieve the username of a user
	@Override
	public String getUsername(User user) throws SQLException {
		String sql = "SELECT username FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, user.getUsername());
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.getString("username");
			}
		} 
	}
	// Retrieve the first name of a user
	@Override
	public String getFirstName(User user) throws SQLException {
		String sql = "SELECT firstName FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, user.getUsername());
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.getString("firstName");
			}
		} 
	}
	// Retrieve the last name of a user
	@Override
	public String getLastName(User user) throws SQLException {
		String sql = "SELECT lastName FROM " + TABLE_NAME + " WHERE username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);) {
			stmt.setString(1, user.getUsername());
			try (ResultSet rs = stmt.executeQuery()) {
				return rs.getString("lastName");
			}
		} 
	}
	// Update the personal information of a user
	@Override
	public void updateProfile(String firstName, String lastName, String username) {
		String sql = "UPDATE " + TABLE_NAME + " SET firstName = ?, lastName = ? WHERE  username = ?";
		try (Connection connection = Database.getConnection();
				PreparedStatement stmt = connection.prepareStatement(sql);){
			stmt.setString(1, firstName);
			stmt.setString(2, lastName);
			stmt.setString(3, username);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
