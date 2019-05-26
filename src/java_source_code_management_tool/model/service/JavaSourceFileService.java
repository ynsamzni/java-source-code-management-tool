package java_source_code_management_tool.model.service;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import java_source_code_management_tool.model.dao.DescriptionDAO;
import java_source_code_management_tool.model.dao.JavaSourceFileDAO;
import java_source_code_management_tool.model.dao.VersionDAO;
import java_source_code_management_tool.model.dto.JavaSourceFile;
import java_source_code_management_tool.model.dto.Version;
import java_source_code_management_tool.util.ConnectionFactory;
import java_source_code_management_tool.util.DBHelper;

/**
 * This class consists of model methods which persist local and remote data on the database related to Java source files following the Model-View-Controller pattern.
 * 
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
	
	/**
	 * Constructs a new Java source file service acting as a model.
	 */
	public JavaSourceFileService()
	{
		propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	/**
	 * Adds listener of property changes in the Java source file service.
	 * 
	 * @param listener the listener that is notified of property change in the Java source file service.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener)
	{
		propertyChangeSupport.addPropertyChangeListener(listener);
	}
	
	/**
	 * Returns the currently loaded Java source file.
	 * 
	 * @return the currently loaded Java source file.
	 */
	public JavaSourceFile getCurrentJavaSourceFile()
	{
		return currentJavaSourceFile;
	}
	
	/**
	 * Adds the specified version to the currently loaded Java source file locally and on the database.
	 * 
	 * @param version the version to add to the currently loaded Java source file locally and on the database.
	 */
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
	
	/**
	 * Loads from the database the Java source file which has the specified file system path.
	 * 
	 * @param pathFs the Java source file path on the file system of the file to load.
	 */
	public void loadJavaSourceFileFromDb(String pathFs)
	{
		Connection con = null;
		JavaSourceFile javaSourceFile;
		
		// Get data from database
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Initialize DAOs
			javaSourceFileDAO = new JavaSourceFileDAO(con);
			versionDAO = new VersionDAO(con);
			descriptionDAO = new DescriptionDAO(con);
			
			// Get Java source file + versions + descriptions from database
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
		
		// Save Java source file locally
		currentJavaSourceFile = javaSourceFile;
		
		// Notify view about the change
		propertyChangeSupport.firePropertyChange("NEWJAVASOURCEFILE", null, javaSourceFile);	
	}
	
	/**
	 * Loads from the file system the Java source file which has the specified file system path.
	 * 
	 * @param pathFs the Java source file path on the file system of the file to load.
	 */
	public void loadJavaSourceFileFromFs(String pathFs)
	{
		Connection con = null;
		JavaSourceFile javaSourceFile;
		
		// Get Java source file from file system
		javaSourceFile = new JavaSourceFile(pathFs);
		javaSourceFile.setContentFromPathFs();
		
		// Get additional data from database
		try
		{
			// Connect to the database
			con = ConnectionFactory.getConnection();
			
			// Initialize DAOs
			javaSourceFileDAO = new JavaSourceFileDAO(con);
			versionDAO = new VersionDAO(con);
			descriptionDAO = new DescriptionDAO(con);
			
			// If Java source file has versions on the database
			if(javaSourceFileDAO.getJavaSourceFile(pathFs) != null)
			{
				// Get versions + descriptions from database
				javaSourceFile.setListVersions(versionDAO.getListVersions(pathFs));
				for(int i=0; i<javaSourceFile.getListVersions().size(); i++)
					javaSourceFile.getVersion(i).setListDescriptions(descriptionDAO.getListDescriptions(pathFs, javaSourceFile.getVersion(i).getVersion()));
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
	
	/**
	 * Returns the list of database Java source file paths.
	 * 
	 * @return the list of database Java source file paths.
	 */
	public ArrayList<String> getListJavaSourceFilePathsFs()
	{
		Connection con = null;
		ArrayList<String> javaSourceFilePathsFs = new ArrayList<String>();
		
		// Connect to the database
		con = ConnectionFactory.getConnection();
		
		// Initialize DAO
		javaSourceFileDAO = new JavaSourceFileDAO(con);
		
		// Get list of Java source file FS paths stored on database
		javaSourceFilePathsFs = javaSourceFileDAO.getListJavaSourceFilePathsFs();

		// Close the connection
		DBHelper.close(con);
		
		return javaSourceFilePathsFs;
	}
}
