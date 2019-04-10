package java_source_code_management_tool.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class DAOManager
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
}
