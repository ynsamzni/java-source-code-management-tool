package java_source_code_management_tool.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class DBHelper
{
	public static void close(Connection con)
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
	
	public static void close(PreparedStatement ps)
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
	
	public static void close(CallableStatement cs)
	{
		try
		{
			if(cs != null)
				cs.close();
		}
		catch (SQLException ignore)
		{
		}
	}
	
	public static void close(ResultSet rs)
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
	
	public static void rollback(Connection con)
	{
		try
		{
			if (con != null)
				con.rollback();
		} 
		catch (SQLException ignore)
		{
		}
	}
}
