package java_source_code_management_tool.service;

import java.sql.Connection;
import java.util.ArrayList;

import java_source_code_management_tool.dao.DAOManager;
import java_source_code_management_tool.dao.UserDAO;
import java_source_code_management_tool.model.User;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserService
{
	private DAOManager daoManager;
	private UserDAO userDAO;
	
	public UserService(DAOManager daoManager)
	{
		this.daoManager = daoManager;
	}
	
	public void addUser(User user)
	{
		Connection con = null;
		
		try
		{
			// Connect to the database
			con = daoManager.getConnection();
			
			// Initialize DAO
			userDAO = new UserDAO(con);
			
			// Add user
			userDAO.addUser(user);
		}
		finally
		{
			// Close the connection
			daoManager.close(con);
		}
	}
	
	public ArrayList<User> getListUsers()
	{
		Connection con = null;
		ArrayList<User> users = new ArrayList<User>();
		
		try
		{
			// Connect to the database
			con = daoManager.getConnection();
			
			// Initialize DAO
			userDAO = new UserDAO(con);
			
			// Get the list of users
			users = userDAO.getListUsers();
		}
		finally
		{
			// Close the connection
			daoManager.close(con);
		}
		
		return users;
	}
}
