package java_source_code_management_tool.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class consists of helper methods that operate on or return a connection to the database.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class ConnectionFactory
{
	private static String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String dbLogin = "BDD1";
	private static String dbPass = "BDD1";
	private static Connection con = null;
	
	/**
	 * Returns the connection to the database.
	 * 
	 * @return the connection to the database.
	 */
	public static Connection getConnection()
	{
		try
		{
			if(con == null || con.isClosed())
			{
				con = DriverManager.getConnection(dbUrl, dbLogin, dbPass);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return con;
	}
}
