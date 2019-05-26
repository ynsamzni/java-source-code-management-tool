package java_source_code_management_tool.model.dto;

import java.nio.file.Path;
import java.sql.Timestamp;

/**
 * This class consists of methods that return a user history entry.
 * 
 * @author Jordan and Yanis (Group 4 - Pair 10)
 *
 */
public class UserHistory
{
	private String username;
	private Timestamp date;
	private Path javaSourceFilePathFs;
	private String versionNumber;
	
	/**
	 * Constructs a new user history entry.
	 */
	public UserHistory()
	{
		
	}

	/**
	 * Constructs a new user history entry with the specified user username, version date, Java source file path on the file system, and version number.
	 * 
	 * @param username the concerned user username.
	 * @param date the date of operation.
	 * @param javaSourceFilePathFs the path on the file system of the updated Java source file.
	 * @param versionNumber the added version number.
	 */
	public UserHistory(String username, Timestamp date, Path javaSourceFilePathFs, String versionNumber)
	{
		this.username = username;
		this.date = date;
		this.javaSourceFilePathFs = javaSourceFilePathFs;
		this.versionNumber = versionNumber;
	}
	
	/**
	 * Returns the saved user username.
	 * 
	 * @return the saved user username.
	 */
	public String getUsername()
	{
		return username;
	}
	
	/**
	 * Sets the user username.
	 * 
	 * @param username the user username.
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	/**
	 * Returns the saved version date.
	 * 
	 * @return the saved date of operation.
	 */
	public Timestamp getDate()
	{
		return date;
	}
	
	/**
	 * Sets the version date.
	 * 
	 * @param date the date of operation.
	 */
	public void setDate(Timestamp date)
	{
		this.date = date;
	}
	
	/**
	 * Returns the saved Java source file path on the file system.
	 * 
	 * @return the saved path on the file system of the updated Java source file.
	 */
	public Path getJavaSourceFilePathFs()
	{
		return javaSourceFilePathFs;
	}
	
	/**
	 * Sets the Java source file path on the file system.
	 * 
	 * @param javaSourceFilePathFs the path on the file system of the updated Java source file.
	 */
	public void setJavaSourceFilePathFs(Path javaSourceFilePathFs)
	{
		this.javaSourceFilePathFs = javaSourceFilePathFs;
	}
	
	/**
	 * Returns the saved version number.
	 * 
	 * @return the saved version number that has been added.
	 */
	public String getVersion()
	{
		return versionNumber;
	}
	
	/**
	 * Sets the version number.
	 * 
	 * @param versionNumber the added version number.
	 */
	public void setVersion(String versionNumber)
	{
		this.versionNumber = versionNumber;
	}
}
