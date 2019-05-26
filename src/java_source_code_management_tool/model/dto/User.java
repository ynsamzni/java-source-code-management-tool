package java_source_code_management_tool.model.dto;

/**
 * This class consists of methods that operate on or return a user.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class User 
{
	private String username;
	private String password;
	private int accessLevel;
	private JavaSourceFile javaSourceFile;
	
	/**
	 * Constructs a new user.
	 */
	public User()
	{
		
	}

	/**
	 * Constructs a new user with the specified username, password and access level.
	 * 
	 * @param username the user username.
	 * @param password the user password.
	 * @param accessLevel the user access level (0: user / 1: admin).
	 */
	public User(String username, String password, int accessLevel)
	{
		this.username = username;
		this.password = password;
		this.accessLevel = accessLevel;
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
	 * Returns the saved user username.
	 * 
	 * @return the saved user username.
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Sets the user password.
	 * 
	 * @param password the user password.
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * Returns the saved user password.
	 * 
	 * @return the saved user password.
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Sets the user access level.
	 * 
	 * @param accessLevel the user access level (0: user / 1: admin).
	 */
	public void setAccessLevel(int accessLevel)
	{
		this.accessLevel = accessLevel;
	}

	/**
	 * Returns the saved user access level.
	 * 
	 * @return the saved user access level (0: user / 1: admin).
	 */
	public int getAccessLevel()
	{
		return accessLevel;
	}
	
	/**
	 * Returns the Java source file the user is working on.
	 * 
	 * @return the Java source file the user is working on.
	 */
	public JavaSourceFile getJavaSourceFile()
	{
		return javaSourceFile;
	}

	/**
	 * Sets the Java source file the user is working on.
	 * 
	 * @param javaSourceFile the Java source file the user working on.
	 */
	public void setJavaSourceFile(JavaSourceFile javaSourceFile)
	{
		this.javaSourceFile = javaSourceFile;
	}
	
	/**
	 * Translates the object to String for display.
	 * 
	 * @return object translated to String for display.
	 */
	public String toString()
	{
		return "username: " + username + " | password: " + password + " | accessLevel: " + accessLevel;
	}
}
