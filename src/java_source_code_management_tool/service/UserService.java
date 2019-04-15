package java_source_code_management_tool.service;

import java.sql.Connection;
import java.util.ArrayList;

import java_source_code_management_tool.dao.UserDAO;
import java_source_code_management_tool.model.User;
import java_source_code_management_tool.util.ConnectionFactory;
import java_source_code_management_tool.util.DBHelper;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserService
{
	private UserDAO userDAO;
	
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
	
	public User getUser(String username, String password)
	{
		Connection con = null;
		User user;
		
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
		
		return user;
	}
	
	public ArrayList<User> getListUsers()
	{
		Connection con = null;
		ArrayList<User> users = new ArrayList<User>();
		
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Initialize DAO
			userDAO = new UserDAO(con);
			
			// Get the list of users
			users = userDAO.getListUsers();
		}
		finally
		{
			// Close the connection
			DBHelper.close(con);
		}
		
		return users;
	}
}
