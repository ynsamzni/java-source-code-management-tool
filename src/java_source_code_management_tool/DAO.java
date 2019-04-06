package java_source_code_management_tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class DAO
{
	private final static String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	private final static String dbLogin = "BDD1";
	private final static String dbPass = "BDD1";
	
	public Connection getConnection()
	{
		Connection con = null;
		
		try
		{
			con = DriverManager.getConnection(dbUrl, dbLogin, dbPass);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return con;
	}
	
	public void close(Connection con)
	{
		try
		{
			if(con != null)
				con.close();
		} 
		catch (SQLException ignore)
		{
		}
	}
	
	public void close(PreparedStatement ps)
	{
		try
		{
			if(ps != null)
				ps.close();
		}
		catch (SQLException ignore)
		{
		}
	}
	
	public void close(ResultSet rs)
	{
		try
		{
			if (rs != null)
				rs.close();
		} 
		catch (SQLException ignore)
		{
		}
	}
}
