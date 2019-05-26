package java_source_code_management_tool.model.dao;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java_source_code_management_tool.model.dto.JavaSourceFile;
import java_source_code_management_tool.util.DBHelper;

/**
 * This class consists of methods that operate on or return Java source files from the database.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class JavaSourceFileDAO
{
	private Connection con = null;
	
	/**
	 * Constructs a new Java source file DAO.
	 * 
	 * @param con the connection to the database.
	 */
	public JavaSourceFileDAO(Connection con)
	{
		this.con = con;
	}
	/**
	 * If the specified Java source file exists in the database, update its content.
	 * Otherwise, insert the specified Java source file into the database.
	 * 
	 * @param javaSourceFile the Java source file to upsert (update/insert) in the database.
	 * @return The upserted (updated/inserted) Java source file database ID.
	 */
	public Integer upsertJavaSourceFile(JavaSourceFile javaSourceFile)
	{
		Clob javaSourceFileContent = null;
		PreparedStatement ps = null;
		String sqlQuery;
		
		try
		{		
			// Convert the java source file for the database
			javaSourceFileContent = con.createClob();
			javaSourceFileContent.setString(1, javaSourceFile.getContent());
			
			// Prepare the SQL query
			sqlQuery =
			"MERGE INTO javasourcefile_jsf dest\n" +
			"USING (SELECT ? jsf_path_fs, ? jsf_content FROM dual) src\n" +
			"ON (dest.jsf_path_fs = src.jsf_path_fs)\n" +
			"WHEN MATCHED THEN UPDATE SET dest.jsf_content = src.jsf_content\n" +
			"WHEN NOT MATCHED THEN INSERT (jsf_path_fs, jsf_content) VALUES (src.jsf_path_fs, src.jsf_content)";
			
			ps = con.prepareStatement(sqlQuery);
			ps.setString(1, javaSourceFile.getPathFs());
			ps.setClob(2, javaSourceFileContent);
			
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
		
		return getJavaSourceFileId(javaSourceFile.getPathFs());
	}
	
	/**
	 * Returns the database Java source file ID linked to the specified Java source file path on the file system.
	 * 
	 * @param pathFs the Java source file path on the file system.
	 * @return the database Java source file ID linked to the specified Java source file path on the file system.
	 */
	public Integer getJavaSourceFileId(String pathFs)
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer id = null;
		
		try
		{		
			// Prepare the SQL query			
			ps = con.prepareStatement("SELECT jsf_id FROM javasourcefile_jsf WHERE jsf_path_fs = ?");
			ps.setString(1, pathFs);
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the java source file ID
			if(rs.next())
				id = rs.getInt("jsf_id");
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
		
		return id;
	}
	
	/**
	 * Returns the database Java source file linked to the specified Java source file path on the file system.
	 * 
	 * @param pathFs the Java source file path on the file system.
	 * @return the database Java source file linked to the specified Java source file path on the file system.
	 */
	public JavaSourceFile getJavaSourceFile(String pathFs)
	{
		Clob javaSourceFileContent = null;
		JavaSourceFile javaSourceFile = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{			
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT * FROM javasourcefile_jsf WHERE jsf_path_fs = ?");
			ps.setString(1, pathFs);
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the java source file
			if(rs.next())
			{
				javaSourceFileContent = rs.getClob("jsf_content");
				
				javaSourceFile = new JavaSourceFile(
						pathFs,
						javaSourceFileContent.getSubString(1, (int) javaSourceFileContent.length())
						);
			}
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
		
		return javaSourceFile;
	}
	
	/**
	 * Returns the list of database Java source file paths.
	 * 
	 * @return the list of database Java source file paths.
	 */
	public ArrayList<String> getListJavaSourceFilePathsFs()
	{
		ArrayList<String> javaSourceFilePathsFs = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{		
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT jsf_path_fs FROM javasourcefile_jsf");
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the list of java source file paths
			while(rs.next())		
				javaSourceFilePathsFs.add(rs.getString("jsf_path_fs"));
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
		
		return javaSourceFilePathsFs;
	}
}
