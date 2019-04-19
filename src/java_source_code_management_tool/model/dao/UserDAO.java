package java_source_code_management_tool.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java_source_code_management_tool.model.dto.User;
import java_source_code_management_tool.util.DBHelper;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserDAO
{
	private Connection con = null;

	public UserDAO(Connection con)
	{
		this.con = con;
	}
	
	public void addUser(User user)
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
	
	public ArrayList<User> getListUsers()
	{
		ArrayList<User> users = new ArrayList<User>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try
		{
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT * FROM user_usr");
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the list of users
			while(rs.next())
				users.add(new User(
						rs.getString("usr_username"),
						rs.getString("usr_password"),
						rs.getInt("usr_access_level")
						));
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
		
		return users;
	}
}
