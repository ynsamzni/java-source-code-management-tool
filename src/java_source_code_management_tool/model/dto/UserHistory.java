package java_source_code_management_tool.model.dto;

import java.sql.Timestamp;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class UserHistory
{
	private String username;
	private Timestamp date;
	private String javaSourceFilePathFs;
	private String versionNumber;
	
	public UserHistory()
	{
		
	}

	public UserHistory(String username, Timestamp date, String javaSourceFilePathFs, String versionNumber)
	{
		this.username = username;
		this.date = date;
		this.javaSourceFilePathFs = javaSourceFilePathFs;
		this.versionNumber = versionNumber;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public Timestamp getDate()
	{
		return date;
	}
	
	public void setDate(Timestamp date)
	{
		this.date = date;
	}
	
	public String getJavaSourceFilePathFs()
	{
		return javaSourceFilePathFs;
	}
	
	public void setJavaSourceFilePathFs(String javaSourceFilePathFs)
	{
		this.javaSourceFilePathFs = javaSourceFilePathFs;
	}
	
	public String getVersion()
	{
		return versionNumber;
	}
	
	public void setVersion(String versionNumber)
	{
		this.versionNumber = versionNumber;
	}
}
