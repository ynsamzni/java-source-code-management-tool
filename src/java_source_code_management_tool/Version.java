package java_source_code_management_tool;

import java.util.ArrayList;
import java.sql.Timestamp;

/**
 * @author Jordan & Yanis (Group 4 - Pair 10)
 *
 */
public class Version 
{
	private String versionNumber;
	private String author;
	private Timestamp date;
	private ArrayList<Description> listDescriptions = new ArrayList<Description>();
	
	public Version()
	{
		listDescriptions= new ArrayList<Description>();
	}
	
	public Version(String versionNumber, String author)
	{
		this.versionNumber = versionNumber;
		this.author = author;
		date = new Timestamp(System.currentTimeMillis());
	}
	
	public Version(String versionNumber, String author, Timestamp date)
	{
		this.versionNumber = versionNumber;
		this.author = author;
		this.date = date;
	}
	
	public void setVersion(String versionNumber)
	{
		this.versionNumber = versionNumber;
	}
	
	public String getVersion()
	{
		return versionNumber;
	}
	
	public void setAuthor(String author)
	{
		this.author = author;
	}
	
	public String getAuthor()
	{
		return author;
	}
	
	public void setDate(Timestamp date)
	{
		this.date = date;
	}

	public Timestamp getDate()
	{
		return date;
	}
	
	public void setListDescriptions(ArrayList<Description> listDescriptions)
	{
		this.listDescriptions = listDescriptions;
	}

	public ArrayList<Description> getListDescriptions()
	{
		return listDescriptions;
	}
	
	public void addDescription(Description description)
	{
		listDescriptions.add(description);
	}
	
	public Description getDescription(int index)
	{
		return listDescriptions.get(index);
	}
	
	/**
	 * Translates the object to String for display
	 * 
	 * @return object translated to String for display
	 */
	public String toStringForDisplay()
	{
		return "versionNumber: " + versionNumber + " | author: " + author + " | date: " + date;
	}
}
