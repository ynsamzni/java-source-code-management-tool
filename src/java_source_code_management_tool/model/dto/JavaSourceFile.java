package java_source_code_management_tool.model.dto;

import java.util.ArrayList;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class consists of methods that operate on or return a Java source file.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class JavaSourceFile
{
	private String pathFs;
	private String content;
	private ArrayList<Version> listVersions = new ArrayList<Version>();
	
	/**
	 * Constructs a new Java source file.
	 */
	public JavaSourceFile()
	{
		
	}
	
	/**
	 * Constructs a new Java source file with the specified file path.
	 * 
	 * @param pathFs the path of the file in a file system.
	 */
	public JavaSourceFile(String pathFs)
	{
		this.pathFs = pathFs;
	}
	
	/**
	 * Constructs a new Java source file with the specified file path and file content.
	 * 
	 * @param pathFs the path of the file in a file system.
	 * @param content the saved content of the file
	 */
	public JavaSourceFile(String pathFs, String content)
	{
		this.pathFs = pathFs;
		this.content = content;
	}
	
	/**
	 * Returns the path of the file in a file system.
	 * 
	 * @return the path of the file in a file system.
	 */
	public String getPathFs()
	{
		return pathFs;
	}
	
	/**
	 * Sets the path of the file in a file system.
	 * 
	 * @param pathFs the path of the file in a file system.
	 */
	public void setPathFs(String pathFs)
	{
		this.pathFs = pathFs;
	}
	
	/**
	 * Returns the saved content of the file
	 * @return the saved content of the file
	 */
	public String getContent()
	{
		return content;
	}
	
	/**
	 * Sets the saved content of the file
	 * 
	 * @param content the saved content of the file
	 */
	public void setContent(String content)
	{
		this.content = content;
	}
	
	/**
	 * Sets the saved content of the file from its current available content on the file system.
	 * The file will be retrieved using its known path.
	 */
	public void setContentFromPathFs()
	{
	    try
	    {
	        content = new String(Files.readAllBytes(Paths.get(pathFs)));
	    }
	    catch(IOException e)
	    {
	    	System.out.print("ERROR : Input/output\n");
	    }
	    catch(OutOfMemoryError e)
	    {
	    	System.out.println("ERROR : File is too large (Max allowed: 2GB)\n");
	    }
	}
	
	/**
	 * Returns the list of the versions of the file.
	 * 
	 * @return the list of the versions of the file.
	 */
	public ArrayList<Version> getListVersions()
	{
		return listVersions;
	}
	
	/**
	 * Sets the list of the versions of the file.
	 * 
	 * @param listVersions the list of the versions of the file.
	 */
	public void setListVersions(ArrayList<Version> listVersions)
	{
		this.listVersions = listVersions;
	}
	
	/**
	 * Returns from the list of file versions the version of the file stored at the specified index number.
	 * 
	 * @param index the index number at which the desired version is stored in the list of file versions.
	 * @return the specified version of the file.
	 */
	public Version getVersion(int index)
	{
		return listVersions.get(index);
	}
	
	/**
	 * Adds a version to the file.
	 * 
	 * @param version the version to add to the file.
	 */
	public void addVersion(Version version)
	{
		listVersions.add(version);
	}
	
	/**
	 * Translates the object to String for display
	 * 
	 * @return object translated to String for display
	 */
	public String toString()
	{
		return "pathFs: " + pathFs + " | content: " + content;
	}
}
