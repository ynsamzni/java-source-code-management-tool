package java_source_code_management_tool.model.dto;

import java.util.ArrayList;
import java.sql.Timestamp;

/**
 * This class consists of methods that operate on or return a version.
 * 
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class Version 
{
	private String versionNumber;
	private String author;
	private Timestamp date;
	private ArrayList<Description> listDescriptions = new ArrayList<Description>();
	
	/**
	 * Constructs a new version.
	 */
	public Version()
	{
		listDescriptions= new ArrayList<Description>();
	}
	
	/**
	 * Constructs a new version with the specified version number and author.
	 * 
	 * @param versionNumber the version number.
	 * @param author the author username.
	 */
	public Version(String versionNumber, String author)
	{
		this.versionNumber = versionNumber;
		this.author = author;
		date = new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * Constructs a new version with the specified version number, author and date.
	 * 
	 * @param versionNumber the version number.
	 * @param author the author username.
	 * @param date the version date.
	 */
	public Version(String versionNumber, String author, Timestamp date)
	{
		this.versionNumber = versionNumber;
		this.author = author;
		this.date = date;
	}
	
	/**
	 * Sets the the version number.
	 * 
	 * @param versionNumber the version number.
	 */
	public void setVersion(String versionNumber)
	{
		this.versionNumber = versionNumber;
	}
	
	/**
	 * Returns the saved version number.
	 * 
	 * @return the saved version number.
	 */
	public String getVersion()
	{
		return versionNumber;
	}
	
	/**
	 * Sets the author username.
	 * 
	 * @param author the author username.
	 */
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	/**
	 * Returns the saved author username.
	 * 
	 * @return the saved author username.
	 */
	public String getAuthor()
	{
		return author;
	}
	
	/**
	 * Sets the version date.
	 * 
	 * @param date the version date.
	 */
	public void setDate(Timestamp date)
	{
		this.date = date;
	}

	/**
	 * Returns the saved version date.
	 * 
	 * @return the saved version date.
	 */
	public Timestamp getDate()
	{
		return date;
	}
	
	/**
	 * Sets the list of the descriptions of the version.
	 * 
	 * @param listDescriptions the list of the descriptions of the version.
	 */
	public void setListDescriptions(ArrayList<Description> listDescriptions)
	{
		this.listDescriptions = listDescriptions;
	}

	/**
	 * Returns the saved list of the descriptions of the version.
	 * 
	 * @return the saved list of the descriptions of the version.
	 */
	public ArrayList<Description> getListDescriptions()
	{
		return listDescriptions;
	}
	
	/**
	 * Adds the specified description to the version.
	 * 
	 * @param description the description to add to the version.
	 */
	public void addDescription(Description description)
	{
		listDescriptions.add(description);
	}
	
	/**
	 * Returns from the saved list of version descriptions the description stored at the specified index number.
	 * 
	 * @param index the index number at which the desired description is stored in the list of version descriptions.
	 * @return the specified description of the version.
	 */
	public Description getDescription(int index)
	{
		return listDescriptions.get(index);
	}
	
	/**
	 * Translates the object to String for display.
	 * 
	 * @return object translated to String for display.
	 */
	public String toString()
	{
		return "versionNumber: " + versionNumber + " | author: " + author + " | date: " + date;
	}
}
