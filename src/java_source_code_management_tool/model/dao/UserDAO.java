package java_source_code_management_tool.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java_source_code_management_tool.model.dto.User;
import java_source_code_management_tool.util.DBHelper;

/**
 * This class consists of methods that operate on or return users from the database.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class UserDAO
{
	private Connection con = null;

	/**
	 * Constructs a new user DAO.
	 * 
	 * @param con the connection to the database.
	 */
	public UserDAO(Connection con)
	{
		this.con = con;
	}
	
	/**
	 * Inserts the specified user into the database.
	 * 
	 * @param user the user to insert into the database.
	 */
	public void insertUser(User user)
	{
		PreparedStatement ps = null;
		
		try
		{			
			// Prepare the SQL query
			ps = con.prepareStatement("INSERT INTO user_usr (usr_username, usr_password, usr_access_level) VALUES (?, ?, ?)");
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getAccessLevel());
			
			// Execute the SQL query
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Close the preparedStatement
			DBHelper.close(ps);
		}
	}
	
	/**
	 * Deletes the specified user from the database.
	 * 
	 * @param username the username of the user to delete from the database.
	 */
	public void deleteUser(String username)
	{
		PreparedStatement ps = null;
		
		try
		{
			// Prepare the SQL query
			ps = con.prepareStatement("DELETE FROM user_usr WHERE usr_username = ?");
			ps.setString(1, username);
			
			// Execute the SQL query
			ps.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Close the preparedStatement
			DBHelper.close(ps);
		}
	}
	
	/**
	 * Returns the database user which has the specified username and password.
	 * 
	 * @param username the username of the user to look for.
	 * @param password the password of the user to look for.
	 * @return the database user which has the specified username and password.
	 */
	public User getUser(String username, String password)
	{
		User user = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT * FROM user_usr WHERE usr_username = ? AND usr_password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the user
			if(rs.next())
				user = new User(
						rs.getString("usr_username"),
						rs.getString("usr_password"),
						rs.getInt("usr_access_level")
						);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Close the resultSet
			DBHelper.close(rs);
			
			// Close the preparedStatement
			DBHelper.close(ps);
		}
		
		return user;
	}
	
	/**
	 * Returns the list of database user usernames.
	 * 
	 * @return the list of database user usernames.
	 */
	public ArrayList<String> getListUserUsernames()
	{
		ArrayList<String> userUsernames = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT usr_username FROM user_usr");
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the list of users
			while(rs.next())
				userUsernames.add(rs.getString("usr_username"));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Close the resultSet
			DBHelper.close(rs);
			
			// Close the preparedStatement
			DBHelper.close(ps);
		}
		
		return userUsernames;
	}
}
