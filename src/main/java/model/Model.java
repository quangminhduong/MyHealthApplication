package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import dao.HealthRecordDao;
import dao.HealthRecordDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;

public class Model {
	private UserDao userDao;
	private HealthRecordDaoImpl healthRecordDao;
	private User currentUser; 
	
	public Model() {
		userDao = new UserDaoImpl();
		healthRecordDao = new HealthRecordDaoImpl();
	}
	
	public void setup() throws SQLException {
		userDao.setup();
		healthRecordDao.setup();
	}
	public UserDao getUserDao() {
		return userDao;
	}
	
	public User getCurrentUser() {
		return this.currentUser;
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}
	
	public HealthRecordDao getHealthRecordDao() {
		return healthRecordDao;
	}
	// Generate password hash with MD5 algorithm
	public String getPasswordHash(String password) {
		// Create MessageDigest instance for MD5
		MessageDigest md = null;
		String generatedPassword = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		  // Add password bytes to digest
		  md.update(password.getBytes());
		
		  // Get the hash's bytes
		  byte[] bytes = md.digest();
		
		  // This bytes[] has bytes in decimal format. Convert it to hexadecimal format
		  StringBuilder sb = new StringBuilder();
		  for (int i = 0; i < bytes.length; i++) {
		    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		  }
		  generatedPassword = sb.toString();
		return generatedPassword;

	}
}
