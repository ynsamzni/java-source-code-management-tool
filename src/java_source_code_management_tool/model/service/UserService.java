package java_source_code_management_tool.model.service;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.util.ArrayList;

import java_source_code_management_tool.model.dao.UserDAO;
import java_source_code_management_tool.model.dao.UserHistoryDAO;
import java_source_code_management_tool.model.dto.User;
import java_source_code_management_tool.model.dto.UserHistory;
import java_source_code_management_tool.util.ConnectionFactory;
import java_source_code_management_tool.util.DBHelper;

/**
 * This class consists of model methods which persist local and remote data on the database related to users following the Model-View-Controller pattern.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserService
{
	private UserDAO userDAO;
	private UserHistoryDAO userHistoryDAO;
	private User currentUser;
	private PropertyChangeSupport propertyChangeSupport;
	
	/**
	 * Constructs a new user service acting as a model.
	 */
	public UserService()
	{
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	/**
	 * Adds listener of property changes in the user service.
	 * 
	 * @param listener the listener that is notified of property change in the user service.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	/**
	 * Returns the currently loaded user.
	 * 
	 * @return the currently loaded user.
	 */
	public User getCurrentUser()
	{
		return currentUser;
	}
	
	/**
	 * Adds the specified user on the database.
	 * 
	 * @param user the user to add on the database.
	 */
	public void addUser(User user)
	{
		Connection con = null;
		
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Initialize DAO
			userDAO = new UserDAO(con);
			
			// Add user
			userDAO.insertUser(user);
		}
		finally
		{
			// Close the connection
			DBHelper.close(con);
		}
	}
	
	/**
	 * Deletes the user which has the specified username from the database.
	 * 
	 * @param username the username of the user to delete from the database.
	 */
	public void deleteUser(String username)
	{
		Connection con = null;
		
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Initialize DAO
			userDAO = new UserDAO(con);
			
			// Delete user
			userDAO.deleteUser(username);
		}
		finally
		{
			// Close the connection
			DBHelper.close(con);
		}
		
		// Notify view about the change
		propertyChangeSupport.firePropertyChange("DELETEDUSER", username, null);
	}
	
	/**
	 * Loads from the database the user which has the specified username and password.
	 * 
	 * @param username the username of the user to look for.
	 * @param password the password of the user to look for.
	 * @return if the user has been found.
	 */
	public boolean loadUser(String username, String password)
	{
		Connection con = null;
		User user;
		boolean userExists = false;
		
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Initialize DAO
			userDAO = new UserDAO(con);
			
			// Get the user
			user = userDAO.getUser(username, password);				
		}
		finally
		{
			// Close the connection
			DBHelper.close(con);
		}
		
		// Check if an existing user has been found
		if(user != null)
		{
			userExists = true;
			
			// Save user locally
			currentUser = user;
			
			// Notify view about the change
			propertyChangeSupport.firePropertyChange("NEWLOGGEDINUSER", null, user);
		}
		
		return userExists;
	}
	
	/**
	 * Unloads the currently loaded user.
	 */
	public void unloadUser()
	{
		currentUser = null;
	}
	
	/**
	 * Returns the database list of user usernames.
	 * 
	 * @return the database list of user usernames.
	 */
	public ArrayList<String> getListUserUsernames()
	{
		Connection con = null;
		ArrayList<String> userUsernames = new ArrayList<String>();
		
		// Connect to the database
		con = ConnectionFactory.getConnection();
		
		// Initialize DAO
		userDAO = new UserDAO(con);
		
		// Get list of user usernames stored on database
		userUsernames = userDAO.getListUserUsernames();

		// Close the connection
		DBHelper.close(con);
		
		return userUsernames;
	}
	
	/**
	 * Returns the list of user history entries linked to the specified user username.
	 * 
	 * @param username the user username linked to user history entries to look for.
	 * @return the list of user history entries linked to the specified user username.
	 */
	public ArrayList<UserHistory> getUserHistory(String username)
	{
		Connection con = null;
		ArrayList<UserHistory> userHistory = new ArrayList<UserHistory>();
		
		// Connect to the database
		con = ConnectionFactory.getConnection();
		
		// Initialize DAO
		userHistoryDAO = new UserHistoryDAO(con);
		
		// Get user history from database
		userHistory = userHistoryDAO.getUserHistory(username);

		// Close the connection
		DBHelper.close(con);
		
		return userHistory;
	}
}
