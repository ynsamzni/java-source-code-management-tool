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
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserService
{
	private UserDAO userDAO;
	private UserHistoryDAO userHistoryDAO;
	private User currentUser;
	private PropertyChangeSupport propertyChangeSupport;
	
	public UserService()
	{
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public User getCurrentUser()
	{
		return currentUser;
	}
	
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
			userDAO.addUser(user);
		}
		finally
		{
			// Close the connection
			DBHelper.close(con);
		}
	}
	
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
