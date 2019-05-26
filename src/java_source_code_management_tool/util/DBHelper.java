package java_source_code_management_tool.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class consists of helper methods that operate on database related objects.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class DBHelper
{
	/**
	 * Closes the specified database connection.
	 * 
	 * @param con the database connection to close.
	 */
	public static void close(Connection con)
	{
		try
		{
			if(con != null)
				con.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the specified PreparedStatement object.
	 * 
	 * @param ps the PreparedStatement to close.
	 */
	public static void close(PreparedStatement ps)
	{
		try
		{
			if(ps != null)
				ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the specified CallableStatement.
	 * 
	 * @param cs the CallableStatement to close.
	 */
	public static void close(CallableStatement cs)
	{
		try
		{
			if(cs != null)
				cs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Closes the specified ResultSet.
	 * 
	 * @param rs the ResultSet to close.
	 */
	public static void close(ResultSet rs)
	{
		try
		{
			if (rs != null)
				rs.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Rollbacks all changes made in the current transaction of the specified database connection.
	 * 
	 * @param con the database connection containing the transaction to rollback.
	 */
	public static void rollback(Connection con)
	{
		try
		{
			if (con != null)
				con.rollback();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
