package java_source_code_management_tool;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class User 
{
	private String username;
	private String password;
	private int accessLevel;
	private JavaSourceFile javaSourceFile;
	
	public User()
	{
		
	}

	public User(String username, String password, int accessLevel)
	{
		this.username = username;
		this.password = password;
		this.accessLevel = accessLevel;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getPassword()
	{
		return password;
	}

	public void setAccessLevel(int accessLevel)
	{
		this.accessLevel = accessLevel;
	}

	public int getAccessLevel()
	{
		return accessLevel;
	}
	
	public JavaSourceFile getJavaSourceFile()
	{
		return javaSourceFile;
	}

	public void setJavaSourceFile(JavaSourceFile javaSourceFile)
	{
		this.javaSourceFile = javaSourceFile;
	}
	
	/**
	 * Translates the object to String for display
	 * 
	 * @return object translated to String for display
	 */
	public String toStringForDisplay()
	{
		return "username: " + username + " | password: " + password + " | accessLevel: " + accessLevel;
	}
}
