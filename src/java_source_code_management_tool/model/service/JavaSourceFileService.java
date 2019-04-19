package java_source_code_management_tool.model.service;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;

import java_source_code_management_tool.model.dao.DescriptionDAO;
import java_source_code_management_tool.model.dao.JavaSourceFileDAO;
import java_source_code_management_tool.model.dao.VersionDAO;
import java_source_code_management_tool.model.dto.JavaSourceFile;
import java_source_code_management_tool.model.dto.Version;
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
	private PropertyChangeSupport propertyChangeSupport;
	private JavaSourceFile currentJavaSourceFile;
	
	public JavaSourceFileService()
	{
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	public JavaSourceFile getCurrentJavaSourceFile()
	{
		return currentJavaSourceFile;
	}
	
	public void addVersion(Version version)
	{
		Connection con = null;
		Integer javaSourceFileId, versionId;
		
		// Save version in database
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
			
			// Save latest file content from file system
			currentJavaSourceFile.setContentFromPathFs();
			javaSourceFileId = javaSourceFileDAO.upsertJavaSourceFile(currentJavaSourceFile);
			
			// Save new version + descriptions
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
		
		// Save version locally
		currentJavaSourceFile.addVersion(version);
		
		// Notify view about the change
		propertyChangeSupport.firePropertyChange("NEWVERSION", null, version);
	}
	
	public void loadJavaSourceFile(String pathFs)
	{
		Connection con = null;
		JavaSourceFile javaSourceFile;
		
		// Get data
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Initialize DAOs
			javaSourceFileDAO = new JavaSourceFileDAO(con);
			versionDAO = new VersionDAO(con);
			descriptionDAO = new DescriptionDAO(con);
			
			// Check if Java source file is available on the database
			if((javaSourceFile = javaSourceFileDAO.getJavaSourceFile(pathFs)) != null)
			{
				// Get file + versions + descriptions from database
				javaSourceFile.setListVersions(versionDAO.getListVersions(pathFs));
				for(int i=0; i<javaSourceFile.getListVersions().size(); i++)
					javaSourceFile.getVersion(i).setListDescriptions(descriptionDAO.getListDescriptions(pathFs, javaSourceFile.getVersion(i).getVersion()));
			}
			else
			{
				// Get file from file system
				javaSourceFile = new JavaSourceFile(pathFs);
				javaSourceFile.setContentFromPathFs();
			}
		}
		finally
		{
			// Close the connection
			DBHelper.close(con);
		}
		
		// Save Java source file locally
		currentJavaSourceFile = javaSourceFile;
		
		// Notify view about the change
		propertyChangeSupport.firePropertyChange("NEWJAVASOURCEFILE", null, javaSourceFile);	
	}
}
