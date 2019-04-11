package java_source_code_management_tool.service;

import java.sql.Connection;
import java.sql.SQLException;

import java_source_code_management_tool.dao.DescriptionDAO;
import java_source_code_management_tool.dao.JavaSourceFileDAO;
import java_source_code_management_tool.dao.VersionDAO;
import java_source_code_management_tool.model.JavaSourceFile;
import java_source_code_management_tool.model.Version;
import java_source_code_management_tool.util.ConnectionFactory;
import java_source_code_management_tool.util.DBHelper;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class JavaSourceFileService
{
	private JavaSourceFileDAO javaSourceFileDAO;
	private VersionDAO versionDAO;
	private DescriptionDAO descriptionDAO;
	
	public void saveJavaSourceFileWithNewVersionAndDescriptions(JavaSourceFile javaSourceFile, Version version)
	{
		Connection con = null;
		Integer javaSourceFileId, versionId;
		
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Begin SQL transaction
			con.setAutoCommit(false);
			
			// Initialize DAOs
			javaSourceFileDAO = new JavaSourceFileDAO(con);
			versionDAO = new VersionDAO(con);
			descriptionDAO = new DescriptionDAO(con);
			
			// Save file + version + descriptions
			javaSourceFileId = javaSourceFileDAO.upsertJavaSourceFile(javaSourceFile);
			versionId = versionDAO.insertVersion(version, javaSourceFileId);
			descriptionDAO.insertListDescriptions(version.getListDescriptions(), versionId);
			
			// Commit SQL transaction
			con.commit();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			DBHelper.rollback(con);
		}
		finally
		{
			// Close the connection
			DBHelper.close(con);
		}
	}
	
	public JavaSourceFile getJavaSourceFileWithVersionsAndDescriptions(String pathFs)
	{
		Connection con = null;
		JavaSourceFile javaSourceFile;
		
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Initialize DAOs
			javaSourceFileDAO = new JavaSourceFileDAO(con);
			versionDAO = new VersionDAO(con);
			descriptionDAO = new DescriptionDAO(con);
			
			// Get file + versions + descriptions
			javaSourceFile = javaSourceFileDAO.getJavaSourceFile(pathFs);
			javaSourceFile.setListVersions(versionDAO.getListVersions(pathFs));
			for(int i=0; i<javaSourceFile.getListVersions().size(); i++)
				javaSourceFile.getVersion(i).setListDescriptions(descriptionDAO.getListDescriptions(pathFs, javaSourceFile.getVersion(i).getVersion()));
		}
		finally
		{
			// Close the connection
			DBHelper.close(con);
		}
		
		return javaSourceFile;
	}
}
