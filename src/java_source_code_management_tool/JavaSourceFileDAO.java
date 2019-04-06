package java_source_code_management_tool;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class JavaSourceFileDAO extends DAO
{
	public void saveJavaSourceFile(JavaSourceFile javaSourceFile)
	{
		Clob javaSourceFileContent = null;
		Connection con = null;
		PreparedStatement ps = null;
		String sqlQuery;
		
		try
		{
			// Connect to the database
			con = getConnection();
			
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
			close(ps);
			
			// Close the connection
			close(con);
		}
	}
	
	public JavaSourceFile getJavaSourceFile(int id)
	{
		Clob javaSourceFileContent = null;
		JavaSourceFile javaSourceFile = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			// Connect to the database
			con = getConnection();
			
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT * FROM javasourcefile_jsf WHERE jsf_id = ?");
			ps.setInt(1, id);
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the java source file
			if(rs.next())
			{
				javaSourceFileContent = rs.getClob("jsf_content");
				
				javaSourceFile = new JavaSourceFile(
						rs.getString("jsf_path_fs"),
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
			close(rs);
			
			// Close the preparedStatement
			close(ps);
			
			// Close the connection
			close(con);
		}
		
		return javaSourceFile;
	}
	
	public ArrayList<JavaSourceFile> getListJavaSourceFiles()
	{
		ArrayList<JavaSourceFile> javaSourceFiles = new ArrayList<JavaSourceFile>();
		Clob javaSourceFileContent = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try
		{
			// Connect to the database
			con = getConnection();
			
			// Prepare the SQL query
			ps = con.prepareStatement("SELECT * FROM javasourcefile_jsf");
			
			// Execute the SQL query
			rs = ps.executeQuery();
			
			// Get the list of java source files
			while(rs.next())
			{
				javaSourceFileContent = rs.getClob("jsf_content");
				
				javaSourceFiles.add(new JavaSourceFile(
									rs.getString("jsf_path_fs"),
									javaSourceFileContent.getSubString(1, (int) javaSourceFileContent.length())
									));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// Close the resultSet
			close(rs);
			
			// Close the preparedStatement
			close(ps);
			
			// Close the connection
			close(con);
		}
		
		return javaSourceFiles;
	}
}
